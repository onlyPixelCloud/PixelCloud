package de.haizon.pixelcloud.master.backend.configuration.defaults;

import de.haizon.pixelcloud.api.group.ICloudGroup;
import de.haizon.pixelcloud.api.services.ICloudService;
import de.haizon.pixelcloud.master.CloudMaster;
import de.haizon.pixelcloud.master.backend.configuration.IServiceConfiguration;
import de.haizon.pixelcloud.master.backend.files.FileEditor;

import java.io.File;

/**
 * JavaDoc this file!
 * Created: 23.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class DefaultSpigotConfiguration implements IServiceConfiguration {

    @Override
    public void configure(ICloudService cloudService, File file) {

        File newFile = new File(file, "server.properties");

        File spigotYaml = new File(file, "spigot.yml");

        File bukkitYaml = new File(file, "bukkit.yml");

        if(spigotYaml.exists()) spigotYaml.delete();

        if(newFile.exists()) newFile.delete();

        if(bukkitYaml.exists()) bukkitYaml.delete();

        CloudMaster.getInstance().getFileManager().copyFileOutOfJar(newFile, "/files/server.properties");
        CloudMaster.getInstance().getFileManager().copyFileOutOfJar(spigotYaml, "/files/spigot.yml");
        CloudMaster.getInstance().getFileManager().copyFileOutOfJar(bukkitYaml, "/files/bukkit.yml");


        ICloudGroup cloudGroup = CloudMaster.getInstance().getCloudGroupFunctions().getCloudGroups().stream().filter(iCloudGroup -> iCloudGroup.getName().equalsIgnoreCase(cloudService.getGroupName())).findAny().orElse(null);

        if(cloudGroup == null) return;

        FileEditor fileEditor = new FileEditor(newFile);
        fileEditor.replaceLine("server-ip=127.0.0.1", "server-ip=127.0.0.1");
        fileEditor.replaceLine("server-port=25565", "server-port=" + cloudService.getPort());
        fileEditor.replaceLine("max-players=20", "max-players=" + cloudGroup.getMaxPlayers());
        fileEditor.save();

    }

}
