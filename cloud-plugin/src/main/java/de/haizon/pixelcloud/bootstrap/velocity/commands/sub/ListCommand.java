package de.haizon.pixelcloud.bootstrap.velocity.commands.sub;

import com.velocitypowered.api.proxy.Player;
import de.haizon.pixelcloud.CloudPlugin;
import de.haizon.pixelcloud.api.commands.Command;
import de.haizon.pixelcloud.api.player.ICloudPlayer;
import de.haizon.pixelcloud.api.services.status.CloudServiceStatus;
import de.haizon.pixelcloud.bootstrap.velocity.commands.interfaces.SubCommand;
import net.kyori.adventure.text.Component;

/**
 * JavaDoc this file!
 * Created: 03.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
@Command(name = "list", description = "Lists all cloud services and groups")
public class ListCommand implements SubCommand {

    @Override
    public void apply(Player player, String[] args) {

        if(args.length == 1){
            if(args[0].equalsIgnoreCase("list")){

                ICloudPlayer cloudPlayer = CloudPlugin.getInstance().getCloudPlayerManager().getCloudPlayer(player.getUniqueId());
                cloudPlayer.connect(CloudPlugin.getInstance().getCloudServiceImplementation().getCloudServices().stream().filter(cloudService -> cloudService.getName().equalsIgnoreCase("Lobby-2")).findFirst().orElse(null));

                player.sendMessage(Component.text());
                CloudPlugin.getInstance().getCloudGroupImplementation().getCloudGroups().forEach(iCloudGroup -> {
                    player.sendMessage(Component.text("§8» §7" + iCloudGroup.getName() + " §8(§c" + iCloudGroup.getOnlineServices().size() + " service§8)"));
                    iCloudGroup.getOnlineServices().forEach(cloudService -> player.sendMessage(Component.text("§8- §7" + cloudService.getName() + " §8(§c0§8/§4" + iCloudGroup.getMaxPlayers() + " §8| §c" + (cloudService.getServiceStatus().equals(CloudServiceStatus.STARTED) ? "§aSTARTED" : cloudService.getServiceStatus().equals(CloudServiceStatus.STARTING) ? "§eSTARTING" : "§4STOPPED") + "§8)")));
                    player.sendMessage(Component.text());
                });
                player.sendMessage(Component.text());

            }
        }

    }

}
