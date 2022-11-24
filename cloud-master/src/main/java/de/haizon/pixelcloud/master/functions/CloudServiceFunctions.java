package de.haizon.pixelcloud.master.functions;

import de.haizon.pixelcloud.api.group.ICloudGroup;
import de.haizon.pixelcloud.api.player.ICloudPlayer;
import de.haizon.pixelcloud.api.services.ICloudService;
import de.haizon.pixelcloud.api.services.status.CloudServiceStatus;
import de.haizon.pixelcloud.api.services.version.IGroupVersion;
import de.haizon.pixelcloud.api.template.TemplateType;
import de.haizon.pixelcloud.master.CloudMaster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * JavaDoc this file!
 * Created: 23.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class CloudServiceFunctions {

    private final List<ICloudService> cloudServices;

    public CloudServiceFunctions() {
        this.cloudServices = new ArrayList<>();
    }

    public void fetch(){

        CloudMaster.getInstance().getCloudGroupFunctions().getCloudGroups().forEach(iCloudGroup -> {
            for(int i = iCloudGroup.getMinServices(); i < (iCloudGroup.getMaxServices() + 1); i++){
                int finalI = i;
                ICloudService cloudService = new ICloudService() {

                    private CloudServiceStatus cloudServiceStatus = CloudServiceStatus.STOPPED;
                    private final CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
                    private final UUID uuid = UUID.randomUUID();

                    @Override
                    public String getGroupName() {
                        return iCloudGroup.getName();
                    }

                    @Override
                    public int getServiceId() {
                        return finalI;
                    }

                    @Override
                    public UUID getUniqueId() {
                        return uuid;
                    }

                    @Override
                    public String getName() {
                        return iCloudGroup.getName() + "-" + getServiceId();
                    }

                    @Override
                    public List<ICloudPlayer> getCurrentPlayers() {
                        return new ArrayList<>();
                    }

                    @Override
                    public ICloudGroup getCloudGroup() {
                        return iCloudGroup;
                    }

                    @Override
                    public CloudServiceStatus getServiceStatus() {
                        return cloudServiceStatus;
                    }

                    @Override
                    public CopyOnWriteArrayList<String> getConsoleMessages() {
                        return copyOnWriteArrayList;
                    }

                    @Override
                    public IGroupVersion getVersion() {
                        return iCloudGroup.getGroupVersion();
                    }

                    @Override
                    public int getPort() {
                        return randomPort();
                    }

                    @Override
                    public void setStatus(CloudServiceStatus cloudServiceStatus) {
                        this.cloudServiceStatus = cloudServiceStatus;
                    }

                    @Override
                    public boolean isStatic() {
                        return iCloudGroup.getTemplate().getTemplateType().equals(TemplateType.STATIC);
                    }

                    @Override
                    public void start() {
                        try {
                            CloudMaster.getInstance().getCloudServiceRunner().start(this);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void update() {

                        //TODO: send updated variables to all tcp

                    }
                };

                CloudMaster.getInstance().getCloudLogger().info("Service " + cloudService.getName() + " was registered [" + cloudService.getUniqueId() + "/" + cloudService.getCloudGroup().getName() + "]");

                cloudServices.add(cloudService);

            }
        });

    }

    private int randomPort(){
        return new Random().nextInt(40000 - 10000 + 1) + 10000;
    }

    public List<ICloudService> getCloudServices() {
        return cloudServices;
    }
}
