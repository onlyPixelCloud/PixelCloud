package de.haizon.pixelcloud.bootstrap.velocity.packets.inbound;

import de.haizon.pixelcloud.api.packets.Packet;
import de.haizon.pixelcloud.api.packets.PacketType;
import de.haizon.pixelcloud.api.packets.abstracts.PacketReceiveFunction;
import de.haizon.pixelcloud.bootstrap.velocity.VelocityBootstrap;
import org.json.JSONObject;

import java.util.Objects;
import java.util.UUID;

/**
 * JavaDoc this file!
 * Created: 03.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class PacketInCloudPlayerConnect extends PacketReceiveFunction {

    public PacketInCloudPlayerConnect() {
        super(PacketType.PLAYER_CONNECT_TO_SERVICE.name());
    }

    @Override
    public void received(Packet packet) {

        JSONObject jsonObject = (JSONObject) packet.content;

        String uniqueId = jsonObject.getString("uniqueId");
        String service = jsonObject.getString("service");

        Objects.requireNonNull(VelocityBootstrap.getInstance().getProxyServer().getPlayer(UUID.fromString(uniqueId)).orElse(null)).createConnectionRequest(VelocityBootstrap.getInstance().getProxyServer().getServer(service).orElse(null)).connect();

    }

}
