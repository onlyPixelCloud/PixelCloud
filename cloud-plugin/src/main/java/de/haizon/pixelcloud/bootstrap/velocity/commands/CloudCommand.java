package de.haizon.pixelcloud.bootstrap.velocity.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import de.haizon.pixelcloud.api.commands.Command;
import de.haizon.pixelcloud.bootstrap.velocity.commands.interfaces.SubCommand;
import de.haizon.pixelcloud.bootstrap.velocity.commands.sub.EditCommand;
import de.haizon.pixelcloud.bootstrap.velocity.commands.sub.ListCommand;
import de.haizon.pixelcloud.bootstrap.velocity.commands.sub.ServiceCommand;
import net.kyori.adventure.text.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JavaDoc this file!
 * Created: 24.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class CloudCommand implements SimpleCommand {

    private final Map<Command, SubCommand> subCommands = new HashMap<>();

    @Override
    public void execute(Invocation invocation) {

        if (invocation.source() instanceof Player player) {

            String[] args = invocation.arguments();

            register(ListCommand.class, EditCommand.class, ServiceCommand.class);

            if(args.length == 0){
                player.sendMessage(Component.text());
                subCommands.forEach((command, subCommand) -> player.sendMessage(Component.text("§7/cloud §c" + command.name() + " §8- §7" + command.description())));
                player.sendMessage(Component.text());
                return;
            }

            String subCommandArg = args[0];

            if(getCommandHandlerByName(subCommandArg) == null){
                player.sendMessage(Component.text("§7This command cannot be found§8..."));
                return;
            }

            SubCommand subCommand = getCommandHandlerByName(subCommandArg);
            subCommand.apply(player, args);

        }

    }

    @Override
    public List<String> suggest(Invocation invocation) {

        String[] args = invocation.arguments();
        List<String> suggestions = new ArrayList<>();

        if(args.length == 0){
            subCommands.forEach((command, subCommand) -> suggestions.add(command.name()));
        }

        return suggestions;
    }

    @SafeVarargs
    public final void register(Class<? extends SubCommand>... command) {
        try {
            for (Class<? extends SubCommand> aClass : command) {
                subCommands.put(aClass.getAnnotation(Command.class), aClass.getDeclaredConstructor().newInstance());
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public SubCommand getCommandHandlerByName(String command) {
        return subCommands.get(subCommands.keySet().stream().filter(command1 -> command1.name().equalsIgnoreCase(command)).findAny().orElse(null));
    }


}
