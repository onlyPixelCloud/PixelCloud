package de.haizon.pixelcloud.bootstrap.velocity.packets.inbound;

import com.velocitypowered.api.proxy.Player;
import de.haizon.pixelcloud.api.packets.Packet;
import de.haizon.pixelcloud.api.packets.PacketType;
import de.haizon.pixelcloud.api.packets.abstracts.PacketReceiveFunction;
import de.haizon.pixelcloud.bootstrap.velocity.VelocityBootstrap;
import net.kyori.adventure.text.Component;
import org.json.JSONObject;

import java.util.UUID;

/**
 * JavaDoc this file!
 * Created: 04.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class PacketInCloudPlayerKick extends PacketReceiveFunction {

    public PacketInCloudPlayerKick() {
        super(PacketType.PLAYER_KICK.name());
    }

    @Override
    public void received(Packet packet) {

        JSONObject jsonObject = (JSONObject) packet.content;

        if(VelocityBootstrap.getInstance().getProxyServer().getPlayer(UUID.fromString(jsonObject.getString("uniqueId"))).isPresent()){
            Player player = VelocityBootstrap.getInstance().getProxyServer().getPlayer(UUID.fromString(jsonObject.getString("uniqueId"))).get();
            player.disconnect(Component.text(jsonObject.getString("message")));
        }

    }

}
