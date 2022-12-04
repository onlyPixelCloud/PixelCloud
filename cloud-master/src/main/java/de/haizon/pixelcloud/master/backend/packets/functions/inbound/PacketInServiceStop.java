package de.haizon.pixelcloud.master.backend.packets.functions.inbound;

import de.haizon.pixelcloud.api.packets.Packet;
import de.haizon.pixelcloud.api.packets.PacketType;
import de.haizon.pixelcloud.api.packets.abstracts.PacketReceiveFunction;
import de.haizon.pixelcloud.master.CloudMaster;
import org.json.JSONObject;

import java.util.Objects;

/**
 * JavaDoc this file!
 * Created: 04.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class PacketInServiceStop extends PacketReceiveFunction {

    public PacketInServiceStop() {
        super(PacketType.SERVICE_STOP.name());
    }

    @Override
    public void received(Packet packet) {

        JSONObject jsonObject = (JSONObject) packet.content;

        CloudMaster.getInstance().getCloudServiceRunner().shutdown(Objects.requireNonNull(CloudMaster.getInstance().getCloudServiceFunctions().getCloudServices().stream().filter(cloudService -> cloudService.getName().equalsIgnoreCase(jsonObject.getString("service"))).findFirst().orElse(null)));

    }

}
