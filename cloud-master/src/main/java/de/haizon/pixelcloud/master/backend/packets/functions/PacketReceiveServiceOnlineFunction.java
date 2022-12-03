package de.haizon.pixelcloud.master.backend.packets.functions;

import de.haizon.pixelcloud.api.console.Color;
import de.haizon.pixelcloud.api.group.ICloudGroup;
import de.haizon.pixelcloud.api.packets.CloudPacket;
import de.haizon.pixelcloud.api.packets.Packet;
import de.haizon.pixelcloud.api.packets.PacketType;
import de.haizon.pixelcloud.api.packets.abstracts.PacketReceiveFunction;
import de.haizon.pixelcloud.api.services.ICloudService;
import de.haizon.pixelcloud.api.services.impl.CloudGroupImpl;
import de.haizon.pixelcloud.api.services.impl.CloudServiceImpl;
import de.haizon.pixelcloud.api.services.status.CloudServiceStatus;
import de.haizon.pixelcloud.master.CloudMaster;
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

        if(!packet.id.equalsIgnoreCase(getId())) return;

        JSONObject jsonObject = (JSONObject) packet.content;

        ICloudService cloudService = CloudMaster.getInstance().getCloudServiceFunctions().getCloudServices().stream().filter(service -> service.getName().equalsIgnoreCase(jsonObject.getString("name"))).findFirst().orElse(null);

        if(cloudService == null) return;

        cloudService.setStatus(CloudServiceStatus.STARTED);
        CloudMaster.getInstance().getCloudLogger().success("Service " + Color.RED.getColor() + cloudService.getName() + Color.RESET.getColor() + " was successfully connected to the wrapper!");

        CloudPacket<JSONObject> jsonObjectCloudPacket = new CloudPacket<>(PacketType.SERVICE_REGISTER.name(), new JSONObject().put("name", cloudService.getName()));
        CloudMaster.getInstance().getPacketFunction().sendPacket(jsonObjectCloudPacket);

        for (CloudGroupImpl iCloudGroup : CloudMaster.getInstance().getCloudGroupFunctions().getCloudGroups()) {
            CloudPacket<CloudGroupImpl> cloudPacket = new CloudPacket<>("receive_group", iCloudGroup);
            CloudMaster.getInstance().getPacketFunction().sendPacket(cloudPacket);
        }

        for (CloudServiceImpl service : CloudMaster.getInstance().getCloudServiceFunctions().getCloudServices()) {
            CloudPacket<CloudServiceImpl> cloudPacket = new CloudPacket<>("receive_service", service);
            CloudMaster.getInstance().getPacketFunction().sendPacket(cloudPacket);
        }

    }

}
