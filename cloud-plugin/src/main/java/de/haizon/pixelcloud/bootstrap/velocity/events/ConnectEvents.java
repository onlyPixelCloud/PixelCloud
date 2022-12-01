package de.haizon.pixelcloud.bootstrap.velocity.events;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import de.haizon.pixelcloud.CloudPlugin;
import de.haizon.pixelcloud.api.services.ICloudService;
import de.haizon.pixelcloud.api.services.version.GroupType;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.List;

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
            if(cloudService.getCloudGroup().getGroupType().equals(GroupType.LOBBY)) cloudServices.add(cloudService);
        });

        if (cloudServices.isEmpty()) {
            event.getPlayer().disconnect(Component.text("§cLobby server was not found!"));
        }

    }

}
