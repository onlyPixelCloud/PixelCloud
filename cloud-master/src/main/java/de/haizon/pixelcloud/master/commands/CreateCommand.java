package de.haizon.pixelcloud.master.commands;

import de.haizon.pixelcloud.api.commands.Command;
import de.haizon.pixelcloud.api.commands.ICommandHandler;
import de.haizon.pixelcloud.api.console.ICommandSender;
import de.haizon.pixelcloud.master.console.setups.GroupSetup;
import de.haizon.pixelcloud.master.console.setups.TemplateSetup;
import org.jline.reader.Candidate;

import java.util.Arrays;
import java.util.List;

/**
 * JavaDoc this file!
 * Created: 22.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
@Command(name = "create", description = "Creates a group or a template")
public class CreateCommand implements ICommandHandler {

    @Override
    public void handle(ICommandSender sender, String[] args) {

        if (args[0].equalsIgnoreCase("create")) {

            if(args.length != 2){
                sender.sendMessage(" ");
                sender.sendMessage("Please use one of the following commands");
                sender.sendMessage(" ");
                getSuggestions().forEach(candidate -> {
                    sender.sendMessage("create " + candidate.value());
                });
                sender.sendMessage(" ");
                return;
            }

            switch (args[1]) {
                case "template" -> {
                    new TemplateSetup();
                }
                case "group" -> {
                    new GroupSetup();
                }
            }

        }

    }

    @Override
    public List<Candidate> getSuggestions() {
        return Arrays.asList(new Candidate("template"), new Candidate("group"));
    }

}
