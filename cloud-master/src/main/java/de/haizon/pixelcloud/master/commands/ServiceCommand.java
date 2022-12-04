package de.haizon.pixelcloud.master.commands;

import de.haizon.pixelcloud.api.commands.Command;
import de.haizon.pixelcloud.api.commands.ICommandHandler;
import de.haizon.pixelcloud.api.console.ICommandSender;
import de.haizon.pixelcloud.api.services.ICloudService;
import de.haizon.pixelcloud.master.CloudMaster;
import org.jline.reader.Candidate;

import java.io.IOException;
import java.util.List;

/**
 * JavaDoc this file!
 * Created: 03.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
@Command(name = "service", description = "Configure and controls a service", aliases = {"ser"})
public class ServiceCommand implements ICommandHandler {

    @Override
    public void handle(ICommandSender iCommandSender, String[] args) {

        if(args[0].equalsIgnoreCase("ser") || args[0].equalsIgnoreCase("service")){

            if(args.length < 2){
                iCommandSender.sendMessage("Please use one of the sub commands");
                return;
            }

            ICloudService cloudService = CloudMaster.getInstance().getCloudServiceFunctions().getCloudServices().stream().filter(service -> service.getName().equalsIgnoreCase(args[1])).findFirst().orElse(null);

            if(cloudService == null) {
                iCommandSender.sendMessage("The service " + args[1] + " cannot be found...");
                return;
            }

            switch (args[2]){
                case "stop", "restart" -> {
                    CloudMaster.getInstance().getCloudServiceRunner().shutdown(cloudService);
                }
                case "execute" -> {

                    StringBuilder stringBuilder = new StringBuilder();
                    for(int i = 3; i < args.length; i++){
                        stringBuilder.append(args[i]).append(" ");
                    }

                    CloudMaster.getInstance().getCloudServiceRunner().executeCommand(cloudService, stringBuilder.toString());

                    CloudMaster.getInstance().getCloudLogger().success("Send §c" + stringBuilder + " §rto service §e" + cloudService.getName() + "§r.");

                }
                case "info" -> {



                }
            }

        }

    }

    @Override
    public List<Candidate> getSuggestions() {
        return null;
    }
}
