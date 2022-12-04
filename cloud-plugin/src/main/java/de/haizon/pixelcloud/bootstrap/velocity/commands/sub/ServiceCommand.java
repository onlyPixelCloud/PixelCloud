package de.haizon.pixelcloud.bootstrap.velocity.commands.sub;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import de.haizon.pixelcloud.CloudPlugin;
import de.haizon.pixelcloud.api.commands.Command;
import de.haizon.pixelcloud.api.packets.CloudPacket;
import de.haizon.pixelcloud.api.packets.PacketType;
import de.haizon.pixelcloud.api.services.ICloudService;
import de.haizon.pixelcloud.api.services.status.CloudServiceStatus;
import de.haizon.pixelcloud.bootstrap.velocity.VelocityBootstrap;
import de.haizon.pixelcloud.bootstrap.velocity.commands.interfaces.SubCommand;
import net.kyori.adventure.text.Component;
import org.checkerframework.checker.units.qual.C;
import org.json.JSONObject;

/**
 * JavaDoc this file!
 * Created: 04.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
@Command(name = "service", description = "Get information about a service")
public class ServiceCommand implements SubCommand {

    @Override
    public void apply(Player player, String[] args) {
        if (args[0].equalsIgnoreCase("service") || args[0].equalsIgnoreCase("ser")) {
            player.sendMessage(Component.text(args.length));
            if (args.length < 3) {


                player.sendMessage(Component.text());
                player.sendMessage(Component.text("§7/cloud service §c<Service> §7info"));
                player.sendMessage(Component.text("§7/cloud service §c<Service> §7players"));
                player.sendMessage(Component.text("§7/cloud service §c<Service> §7stop"));
                player.sendMessage(Component.text());

            } else if (args.length == 3) {

                String serviceName = args[1];

                ICloudService cloudService = CloudPlugin.getInstance().getCloudServiceImplementation().getCloudServices().stream().filter(service -> service.getName().equalsIgnoreCase(serviceName)).findFirst().orElse(null);

                if (cloudService == null) {
                    player.sendMessage(Component.text("§7Service §c" + serviceName + " §7cannot be found§8..."));
                    return;
                }

                RegisteredServer registeredServer = VelocityBootstrap.getInstance().getProxyServer().getServer(serviceName).orElse(null);

                if (registeredServer == null) {
                    player.sendMessage(Component.text("§7Server §c" + serviceName + " §7cannot be found§8..."));
                    return;
                }

                switch (args[2]) {
                    case "info" -> {

                        player.sendMessage(Component.text());
                        player.sendMessage(Component.text("§7Info §c" + cloudService.getName()));
                        player.sendMessage(Component.text());
                        player.sendMessage(Component.text("§7Group§8: §c" + cloudService.getGroupName()));
                        player.sendMessage(Component.text("§7Status§8: " + (cloudService.getServiceStatus().equals(CloudServiceStatus.STARTED) ? "§aStarted" : cloudService.getServiceStatus().equals(CloudServiceStatus.STARTING) ? "§eStarting" : "§4Stopped")));
                        player.sendMessage(Component.text("§7Port§8: §c" + cloudService.getPort()));
                        player.sendMessage(Component.text("§7Connected players§8: §c" + registeredServer.getPlayersConnected().size()));
                        player.sendMessage(Component.text());
                    }
                    case "stop" -> {
                        CloudPacket<JSONObject> cloudPacket = new CloudPacket<>(PacketType.SERVICE_STOP.name(), new JSONObject().put("service", cloudService.getName()));
                        CloudPlugin.getInstance().getPacketFunction().sendPacket(cloudPacket);
                    }
                    case "players" -> {
                        player.sendMessage(Component.text());
                        registeredServer.getPlayersConnected().forEach(online -> player.sendMessage(Component.text("§8- §7" + online.getUsername())));
                        player.sendMessage(Component.text());
                    }
                }
            }
        }

    }

}
