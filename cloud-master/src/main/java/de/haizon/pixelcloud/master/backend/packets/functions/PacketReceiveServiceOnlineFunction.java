package de.haizon.pixelcloud.master.backend.packets.functions;

import de.haizon.pixelcloud.api.console.Color;
import de.haizon.pixelcloud.api.group.ICloudGroup;
import de.haizon.pixelcloud.api.packets.Packet;
import de.haizon.pixelcloud.api.packets.PacketType;
import de.haizon.pixelcloud.api.packets.abstracts.PacketReceiveFunction;
import de.haizon.pixelcloud.api.services.ICloudService;
import de.haizon.pixelcloud.api.services.status.CloudServiceStatus;
import de.haizon.pixelcloud.master.CloudMaster;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * JavaDoc this file!
 * Created: 24.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class PacketReceiveServiceOnlineFunction extends PacketReceiveFunction {

    public PacketReceiveServiceOnlineFunction() {
        super(PacketType.SERVICE_ONLINE.name());
    }

    @Override
    public void received(Packet packet) {

        JSONObject jsonObject = new JSONObject(packet.content.toString());

        ICloudService cloudService = CloudMaster.getInstance().getCloudServiceFunctions().getCloudServices().stream().filter(service -> service.getName().equalsIgnoreCase(jsonObject.getString("name"))).findFirst().orElse(null);

        if(cloudService == null) return;

        cloudService.setStatus(CloudServiceStatus.STARTED);
        CloudMaster.getInstance().getCloudLogger().info("Service " + Color.RED.getColor() + cloudService.getName() + Color.RESET.getColor() + " was successfully connected to the wrapper!");

        for (ICloudService service : CloudMaster.getInstance().getCloudServiceFunctions().getCloudServices()) {
            Packet send = new Packet();
            send.id = "receive_service";
            send.cloudService = service;
            CloudMaster.getInstance().getPacketFunction().sendPacket(send);
        }

        for (ICloudGroup iCloudGroup : CloudMaster.getInstance().getCloudGroupFunctions().getCloudGroups()) {
            Packet send = new Packet();
            send.id = "receive_group";
            send.cloudGroup = iCloudGroup;
            CloudMaster.getInstance().getPacketFunction().sendPacket(send);
        }

    }

}
