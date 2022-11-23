package de.haizon.pixelcloud.master.commands;

import de.haizon.pixelcloud.api.commands.Command;
import de.haizon.pixelcloud.api.commands.ICommandHandler;
import de.haizon.pixelcloud.api.console.ICommandSender;
import de.haizon.pixelcloud.master.CloudMaster;
import org.jline.reader.Candidate;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * JavaDoc this file!
 * Created: 22.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
@Command(name = "help", description = "Shows all commands with description")
public class HelpCommand implements ICommandHandler {

    @Override
    public void handle(ICommandSender iCommandSender, String[] args) {

        if (args[0].equalsIgnoreCase("help")) {

            iCommandSender.sendMessage(" ");

            for (Command command : CloudMaster.getInstance().getCommandManager().getCommandHandlers().keySet()) {
                iCommandSender.sendMessage(command.name() + " | " + command.description());
            }

            iCommandSender.sendMessage(" ");

        }

    }

    @Override
    public List<Candidate> getSuggestions() {
        return null;
    }

}
