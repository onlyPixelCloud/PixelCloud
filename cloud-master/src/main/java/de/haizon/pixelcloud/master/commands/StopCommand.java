package de.haizon.pixelcloud.master.commands;

import de.haizon.pixelcloud.api.commands.Command;
import de.haizon.pixelcloud.api.commands.ICommandHandler;
import de.haizon.pixelcloud.api.console.ICommandSender;
import de.haizon.pixelcloud.master.CloudMaster;
import org.jline.reader.Candidate;

import java.util.List;

/**
 * JavaDoc this file!
 * Created: 23.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
@Command(name = "stop", description = "Stops the cloud and all current running services")
public class StopCommand implements ICommandHandler {
    @Override
    public void handle(ICommandSender iCommandSender, String[] args) {

        if(args[0].equalsIgnoreCase("stop")){

            iCommandSender.sendMessage("The cloud is stopping...");

            CloudMaster.getInstance().getCloudServiceRunner().getRunningServices().forEach((cloudService, process) -> CloudMaster.getInstance().getCloudServiceRunner().shutdown(cloudService));

            CloudMaster.getInstance().getConsoleManager().stopThread();
            System.exit(1);
        }

    }

    @Override
    public List<Candidate> getSuggestions() {
        return null;
    }
}
