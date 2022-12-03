package de.haizon.pixelcloud.master.backend.configuration.defaults;

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
public class DefaultVelocityConfiguration implements IServiceConfiguration {
    @Override
    public void configure(ICloudService cloudService, File file) {
        File configFile = new File(file, "velocity.toml");

        if(configFile.exists()) configFile.delete();

        if(!configFile.exists()){
            CloudMaster.getInstance().getFileManager().copyFileOutOfJar(configFile, "/files/velocity.toml");
        }
        FileEditor fileEditor = new FileEditor(configFile);
        fileEditor.replaceLine("bind = \"0.0.0.0:25577\"", "bind = \"0.0.0.0:25565\"");
        fileEditor.replaceLine("show-max-players = 500", "show-max-players = " + cloudService.getCloudGroup().getMaxPlayers());
        fileEditor.save();

    }
}
