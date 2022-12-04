package de.haizon.pixelcloud.bootstrap.velocity.events;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import de.haizon.pixelcloud.CloudPlugin;
import de.haizon.pixelcloud.api.packets.CloudPacket;
import de.haizon.pixelcloud.api.packets.PacketType;
import de.haizon.pixelcloud.api.services.ICloudService;
import de.haizon.pixelcloud.api.services.status.CloudServiceStatus;
import de.haizon.pixelcloud.api.services.version.GroupType;
import de.haizon.pixelcloud.bootstrap.velocity.VelocityBootstrap;
import net.kyori.adventure.text.Component;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * JavaDoc this file!
 * Created: 24.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class ConnectEvents {

    @Subscribe
    public void handle(PostLoginEvent event){

        List<ICloudService> cloudServices = new ArrayList<>();

        CloudPlugin.getInstance().getCloudServiceImplementation().getCloudServices().forEach(cloudService -> {
            if(cloudService.getCloudGroup().getGroupType().equals(GroupType.LOBBY) && cloudService.getServiceStatus().equals(CloudServiceStatus.STARTED)) cloudServices.add(cloudService);
        });

        if (cloudServices.isEmpty()) {
            event.getPlayer().disconnect(Component.text("§cLobby server was not found!"));
        }

        CloudPacket<JSONObject> cloudPacket = new CloudPacket<>(PacketType.PLAYER_CONNECTED.name(), new JSONObject().put("uniqueId", event.getPlayer().getUniqueId().toString()).put("name", event.getPlayer().getUsername()).put("address", event.getPlayer().getRemoteAddress().getHostName()).put("service", CloudPlugin.getInstance().thisService().getName()));
        CloudPlugin.getInstance().getPacketFunction().sendPacket(cloudPacket);

    }

    @Subscribe
    public void handle(ServerPreConnectEvent event){
        if(!event.getResult().isAllowed()) return;

        Player player = event.getPlayer();

        ICloudService lobbyService = CloudPlugin.getInstance().getCloudServiceImplementation().getCloudServices().stream().filter(iCloudService -> iCloudService.getCloudGroup().getGroupType().equals(GroupType.LOBBY)).findFirst().orElse(null);

        if (lobbyService == null) {
            return;
        }

        RegisteredServer serverInfo;

        if(event.getOriginalServer().getServerInfo().getName().equals("fallback")){
            serverInfo = VelocityBootstrap.getInstance().getProxyServer().getServer(lobbyService.getName()).orElse(null);
        } else {
            serverInfo = event.getOriginalServer();
        }

        if(serverInfo == null){
            player.disconnect(Component.text("§cNo fallback server was found..."));
            return;
        }

        CloudPlugin.getInstance().getCloudPlayerManager().register(player.getUniqueId());
        event.setResult(ServerPreConnectEvent.ServerResult.allowed(serverInfo));

    }

    @Subscribe
    public void handle(DisconnectEvent event){
        CloudPacket<JSONObject> cloudPacket = new CloudPacket<>(PacketType.PLAYER_DISCONNECTED.name(), new JSONObject().put("uniqueId", event.getPlayer().getUniqueId()).put("name", event.getPlayer().getUsername()));
        CloudPlugin.getInstance().getPacketFunction().sendPacket(cloudPacket);
    }

}
