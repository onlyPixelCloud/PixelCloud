package de.haizon.pixelcloud.master.console.sender;

import de.haizon.pixelcloud.master.CloudMaster;
import de.haizon.pixelcloud.api.console.ICommandSender;

/**
 * JavaDoc this file!
 * Created: 02.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class ConsoleSender implements ICommandSender {

    @Override
    public void sendMessage(String message) {
        CloudMaster.getInstance().getCloudLogger().info(message);
    }

}
