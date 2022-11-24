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
@Command(name = "clear", description = "Clears the console")
public class ClearCommand implements ICommandHandler {
    @Override
    public void handle(ICommandSender iCommandSender, String[] args) {
        if(args[0].equalsIgnoreCase("clear")){
            CloudMaster.getInstance().getCloudLogger().clear();
            iCommandSender.sendMessage("Cleared the console...");
        }
    }

    @Override
    public List<Candidate> getSuggestions() {
        return null;
    }
}
