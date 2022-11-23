package de.haizon.pixelcloud.master.console.command;

import de.haizon.pixelcloud.api.commands.Command;
import de.haizon.pixelcloud.api.commands.ICommandHandler;
import de.haizon.pixelcloud.master.commands.CreateCommand;
import de.haizon.pixelcloud.master.commands.HelpCommand;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * JavaDoc this file!
 * Created: 02.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class CommandManager {

    private final Map<Command, ICommandHandler> commandHandlers;

    public CommandManager() {
        this.commandHandlers = new HashMap<>();

        register(HelpCommand.class, CreateCommand.class);
    }

    @SafeVarargs
    public final void register(Class<? extends ICommandHandler>... command) {
        try {
            for (Class<? extends ICommandHandler> aClass : command) {
                commandHandlers.put(aClass.getAnnotation(Command.class), aClass.getDeclaredConstructor().newInstance());
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public ICommandHandler getCommandHandlerByName(String command) {
        return commandHandlers.get(commandHandlers.keySet().stream().filter(command1 -> command1.name().equalsIgnoreCase(command)).findAny().orElse(null));
    }

    public Map<Command, ICommandHandler> getCommandHandlers() {
        return commandHandlers;
    }

}
