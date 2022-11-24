package de.haizon.pixelcloud.master.commands;

import de.haizon.pixelcloud.api.commands.Command;
import de.haizon.pixelcloud.api.commands.ICommandHandler;
import de.haizon.pixelcloud.api.console.ICommandSender;
import de.haizon.pixelcloud.api.services.ICloudService;
import de.haizon.pixelcloud.master.CloudMaster;
import org.jline.reader.Candidate;

import java.util.List;

/**
 * JavaDoc this file!
 * Created: 23.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
@Command(name = "screen", description = "Shows the log of the cloud service")
public class ScreenCommand implements ICommandHandler {
    @Override
    public void handle(ICommandSender iCommandSender, String[] args) {

        if (args[0].equalsIgnoreCase("screen")) {

            if(args.length == 2){
                ICloudService cloudService = CloudMaster.getInstance().getCloudServiceFunctions().getCloudServices().stream().filter(cloudService1 -> cloudService1.getName().equalsIgnoreCase(args[1])).findAny().orElse(null);

                if(cloudService == null){
                    CloudMaster.getInstance().getCloudLogger().severe("The service " + args[1] + " doesn't exists...");
                    return;
                }

                for (String consoleMessage : cloudService.getConsoleMessages()) {

                    CloudMaster.getInstance().getCloudLogger().write(cloudService.getName(), consoleMessage);

                }
            }

        }

    }

    @Override
    public List<Candidate> getSuggestions() {
        return null;
    }
}
