package de.haizon.pixelcloud.packets.receiver;

import de.haizon.pixelcloud.api.packets.Packet;
import de.haizon.pixelcloud.api.packets.PacketType;
import de.haizon.pixelcloud.api.packets.abstracts.PacketReceiveFunction;
import org.json.JSONObject;

/**
 * JavaDoc this file!
 * Created: 24.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class ServiceConnectedReceive extends PacketReceiveFunction {
    public ServiceConnectedReceive() {
        super(PacketType.SERVICE_ONLINE.name());
    }

    @Override
    public void received(Packet packet) {

        JSONObject jsonObject = new JSONObject(packet.content);

        if(jsonObject.getString("type").equalsIgnoreCase("SERVER")){

            String name = jsonObject.getString("name");
            int port = jsonObject.getInt("port");



        }

    }
}
