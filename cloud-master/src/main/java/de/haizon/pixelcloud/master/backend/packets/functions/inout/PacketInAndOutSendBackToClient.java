package de.haizon.pixelcloud.master.backend.packets.functions.inout;

import de.haizon.pixelcloud.api.packets.CloudPacket;
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
public class PacketInAndOutSendBackToClient extends PacketReceiveFunction {

    public PacketInAndOutSendBackToClient() {
        super(PacketType.SEND_BACK_TO_CLIENT.name());
    }

    @Override
    public void received(Packet packet) {

        JSONObject jsonObject = (JSONObject) packet.content;

        CloudPacket<JSONObject> cloudPacket = new CloudPacket<>(jsonObject.getString("backType"), jsonObject);
        CloudMaster.getInstance().getPacketFunction().sendPacket(cloudPacket);

    }
}
