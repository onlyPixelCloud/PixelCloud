package de.haizon.pixelcloud.api.commands;

import de.haizon.pixelcloud.api.console.ICommandSender;
import org.jline.reader.Candidate;

import java.util.List;

/**
 * JavaDoc this file!
 * Created: 02.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public interface ICommandHandler {

    void handle(ICommandSender iCommandSender, String[] args);

    List<Candidate> getSuggestions();

}
