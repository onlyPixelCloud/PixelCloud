package de.haizon.pixelcloud.master.backend.packets.functions.inbound;

import de.haizon.pixelcloud.api.packets.Packet;
import de.haizon.pixelcloud.api.packets.PacketType;
import de.haizon.pixelcloud.api.packets.abstracts.PacketReceiveFunction;
import de.haizon.pixelcloud.master.CloudMaster;
import org.json.JSONObject;

/**
 * JavaDoc this file!
 * Created: 03.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class PacketInPlayerConnected extends PacketReceiveFunction {

    public PacketInPlayerConnected() {
        super(PacketType.PLAYER_CONNECTED.name());
    }

    @Override
    public void received(Packet packet) {
        JSONObject jsonObject = (JSONObject) packet.content;

        CloudMaster.getInstance().getCloudLogger().info("Player " + jsonObject.getString("name") + " connected on " + jsonObject.getString("service") + ". [" + jsonObject.getString("uniqueId") + "/" + jsonObject.getString("address") + "]");

    }
}
