package de.haizon.pixelcloud.master.backend.runner;

import de.haizon.pixelcloud.api.console.Color;
import de.haizon.pixelcloud.api.event.nodes.CloudServiceLoggedOutEvent;
import de.haizon.pixelcloud.api.group.ICloudGroup;
import de.haizon.pixelcloud.api.services.ICloudService;
import de.haizon.pixelcloud.api.services.impl.CloudGroupImpl;
import de.haizon.pixelcloud.api.services.impl.CloudServiceImpl;
import de.haizon.pixelcloud.api.services.status.CloudServiceStatus;
import de.haizon.pixelcloud.api.template.TemplateType;
import de.haizon.pixelcloud.master.CloudMaster;
import de.haizon.pixelcloud.master.backend.configuration.defaults.DefaultSpigotConfiguration;
import de.haizon.pixelcloud.master.backend.configuration.defaults.DefaultVelocityConfiguration;
import de.haizon.pixelcloud.master.json.JsonLib;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * JavaDoc this file!
 * Created: 23.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class CloudServiceRunner {

    private final HashMap<ICloudService, Process> runningServices;

    public CloudServiceRunner() {
        this.runningServices = new HashMap<>();
    }

    public void startAll(){

        CloudMaster.getInstance().getCloudServiceFunctions().getCloudServices().forEach(cloudService -> CloudMaster.getInstance().getCloudServiceStartQueue().addToQueue(cloudService));
        CloudMaster.getInstance().getCloudServiceStartQueue().startThread();

    }

    public void start(ICloudService cloudService) throws IOException {

        Process process = null;

        configureService(cloudService);

        cloudService.setStatus(CloudServiceStatus.PREPARING);
        CloudMaster.getInstance().getPacketFunction().sendPacket(cloudService.update());

        if (cloudService.getCloudGroup().getOnlineServices().size() > cloudService.getCloudGroup().getMaxServices()) {
            return;
        }

        switch (cloudService.getCloudGroup().getTemplate().getTemplateType()){
            case STATIC -> {

                CloudMaster.getInstance().getFileManager().createFolder("storage/servers/static/" + cloudService.getName() + "/plugins");

                JsonLib jsonLib = JsonLib.empty();
                jsonLib.append("name", cloudService.getName());
                jsonLib.append("id", cloudService.getServiceId());
                jsonLib.append("type", cloudService.getCloudGroup().getGroupType());
                jsonLib.append("port", cloudService.getPort());
                jsonLib.saveAsFile(new File("storage/servers/static/" + cloudService.getName(), "cloud_identify.json"));

                if(!CloudMaster.getInstance().getFileManager().fileExist("storage/servers/static/" + cloudService.getName(), cloudService.getCloudGroup().getGroupVersion().getName() + ".jar")){
                    CloudMaster.getInstance().getFileManager().copyFile("storage/jars/" + cloudService.getCloudGroup().getGroupVersion().getName() + ".jar", "storage/servers/static/" + cloudService.getName() + "/" + cloudService.getCloudGroup().getGroupVersion().getName() + ".jar");
                }

                copyPlugin(cloudService);

                process = createProcessBuilder(cloudService).directory(new File("storage/servers/static/" + cloudService.getName())).start();

            }
            case DYNAMIC -> {

                CloudMaster.getInstance().getFileManager().createFolder("storage/servers/temp/" + cloudService.getName() + "/plugins");

                JsonLib jsonLib = JsonLib.empty();
                jsonLib.append("name", cloudService.getName());
                jsonLib.append("id", cloudService.getServiceId());
                jsonLib.append("type", cloudService.getCloudGroup().getGroupType());
                jsonLib.append("port", cloudService.getPort());
                jsonLib.saveAsFile(new File("storage/servers/temp/" + cloudService.getName() , "cloud_identify.json"));

                if(!CloudMaster.getInstance().getFileManager().fileExist("storage/servers/temp/" + cloudService.getName() , cloudService.getCloudGroup().getGroupVersion().getName() + ".jar")){
                    CloudMaster.getInstance().getFileManager().copyFile("storage/jars/" + cloudService.getCloudGroup().getGroupVersion().getName() + ".jar", "storage/servers/temp/" + cloudService.getName()  + "/" + cloudService.getCloudGroup().getGroupVersion().getName() + ".jar");
                }

                copyPlugin(cloudService);

                process = createProcessBuilder(cloudService).directory(new File("storage/servers/temp/" + cloudService.getName() )).start();

            }
        }

        if(process == null){
            CloudMaster.getInstance().getCloudLogger().severe("An error occurred in the runner function!");
            return;
        }

        cloudService.setStatus(CloudServiceStatus.STARTING);
        CloudMaster.getInstance().getPacketFunction().sendPacket(cloudService.update());

        Process finalProcess = process;

        new Thread(() -> {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(finalProcess.getInputStream()));

            while(finalProcess.isAlive()){
                try {
                    String string;

                    if("".equalsIgnoreCase(bufferedReader.readLine())){
                        return;
                    }

                    string = bufferedReader.readLine();

                    cloudService.getConsoleMessages().add(string);

                } catch (IOException ignored) {}
            }

            try {
                bufferedReader.close();
                CloudMaster.getInstance().getCloudLogger().info("Service §c" + cloudService.getName() + " §rwas stopped.");
                cloudService.setStatus(CloudServiceStatus.STOPPED);
                CloudMaster.getInstance().getEventBus().post(new CloudServiceLoggedOutEvent(cloudService));
                CloudMaster.getInstance().getPacketFunction().sendPacket(cloudService.update());
                start(cloudService);
            } catch (IOException ignored) {}

        }).start();

        runningServices.put(cloudService, process);

    }

    public void start(CloudGroupImpl cloudGroup){

        for(int i = cloudGroup.getMinServices(); i < (cloudGroup.getMaxServices() + 1); i++){
            CloudServiceImpl cloudService = new CloudServiceImpl(cloudGroup.getName(), cloudGroup.getName()  + "-" + i, cloudGroup, i, randomPort(), cloudGroup.getTemplate().getTemplateType().equals(TemplateType.STATIC), CloudServiceStatus.STOPPED);

            CloudMaster.getInstance().getCloudServiceFunctions().getCloudServices().add(cloudService);

            try {
                start(cloudService);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void shutdown(ICloudService cloudService){

        CloudMaster.getInstance().getCloudLogger().info("Service " + Color.RED.getColor() + cloudService.getName() + Color.RESET.getColor() + " trying to stop...");

        executeCommand(cloudService, "end");
        executeCommand(cloudService, "stop");

        if(cloudService.getServiceStatus().equals(CloudServiceStatus.STARTING)){
            getRunningServices().get(cloudService).destroy();
        }

    }

    public void copyPlugin(ICloudService cloudService){
        CloudMaster.getInstance().getFileManager().copyFile("storage/jars/cloud-plugin-1.0-SNAPSHOT-shaded.jar", "storage/servers/" + (cloudService.getCloudGroup().getTemplate().getTemplateType().equals(TemplateType.STATIC) ? "static/" + cloudService.getName() + "/plugins/cloud-plugin-1.0-SNAPSHOT-shaded.jar" : "temp/" + cloudService.getName()  + "/plugins/cloud-plugin-1.0-SNAPSHOT-shaded.jar"));

        CloudMaster.getInstance().getModuleHandler().getModules().forEach(pixelModule -> {
            if(pixelModule.getGroupTypes() == null) return;
            if (Arrays.stream(pixelModule.getGroupTypes()).toList().contains(cloudService.getCloudGroup().getGroupType())) {
                CloudMaster.getInstance().getFileManager().copyFile("modules/" + pixelModule.getFileName(), "storage/servers/" + (cloudService.getCloudGroup().getTemplate().getTemplateType().equals(TemplateType.STATIC) ? "static/" + cloudService.getName() + "/plugins/cloud-plugin-1.0-SNAPSHOT-shaded.jar" : "temp/" + cloudService.getName()  + "plugins/" + pixelModule.getFileName()));
            }
        });

    }

    public void executeCommand(ICloudService cloudService, String command) {
        Process process = runningServices.get(cloudService);
        String cmd = command + "\n";
        try {
            if(process != null && process.getOutputStream() != null){
                process.getOutputStream().write(cmd.getBytes());
                process.getOutputStream().flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void configureService(ICloudService cloudService){

        if (cloudService.getCloudGroup().getGroupVersion().getName().contains("VELOCITY")) {
            DefaultVelocityConfiguration defaultVelocityConfiguration = new DefaultVelocityConfiguration();
            defaultVelocityConfiguration.configure(cloudService, new File("storage/servers/" + (cloudService.getCloudGroup().getTemplate().getTemplateType().equals(TemplateType.STATIC) ? "static/" + cloudService.getName() : "temp/" + cloudService.getName() )));
        } else {
            DefaultSpigotConfiguration defaultSpigotConfiguration = new DefaultSpigotConfiguration();
            defaultSpigotConfiguration.configure(cloudService, new File("storage/servers/" + (cloudService.getCloudGroup().getTemplate().getTemplateType().equals(TemplateType.STATIC) ? "static/" + cloudService.getName() : "temp/" + cloudService.getName() )));
        }
    }

    private List<String> getStartArguments(ICloudService cloudService){
        List<String> arguments = new ArrayList<>();
        ICloudGroup cloudGroupService = CloudMaster.getInstance().getCloudGroupFunctions().getCloudGroups().stream().filter(groupService -> groupService.getName().equals(cloudService.getGroupName())).findAny().orElse(null);

        assert cloudGroupService != null;

        arguments.add("java");
        arguments.add("-XX:-UseAdaptiveSizePolicy");
        arguments.add("-Dcom.mojang.eula.agree=true");
        arguments.add("-Djline.terminal=jline.UnsupportedTerminal");
        arguments.add("-Xms" + cloudGroupService.getMaxHeap() + "M");
        arguments.add("-Xmx" + cloudGroupService.getMaxHeap() + "M");
        arguments.add("-jar");
        arguments.add(cloudService.getVersion().getName() + ".jar");

        return arguments;
    }

    private int randomPort(){
        return new Random().nextInt(40000 - 10000 + 1) + 10000;
    }

    private ProcessBuilder createProcessBuilder(ICloudService cloudService){
        return new ProcessBuilder(getStartArguments(cloudService));
    }

    public HashMap<ICloudService, Process> getRunningServices() {
        return runningServices;
    }
}
