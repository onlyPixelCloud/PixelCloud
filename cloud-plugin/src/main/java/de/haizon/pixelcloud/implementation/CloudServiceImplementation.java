package de.haizon.pixelcloud.implementation;

import de.haizon.pixelcloud.CloudPlugin;
import de.haizon.pixelcloud.api.group.ICloudGroup;
import de.haizon.pixelcloud.api.packets.Packet;
import de.haizon.pixelcloud.api.packets.abstracts.PacketReceiveFunction;
import de.haizon.pixelcloud.api.services.ICloudService;
import de.haizon.pixelcloud.api.services.impl.CloudServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * JavaDoc this file!
 * Created: 24.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class CloudServiceImplementation extends PacketReceiveFunction {

    private static final List<ICloudService> cloudServices = new ArrayList<>();

    public CloudServiceImplementation() {
        super("receive_service");
    }

    public List<ICloudService> getCloudServices() {
        return cloudServices;
    }

    @Override
    public void received(Packet packet) {

        if(packet.content instanceof CloudServiceImpl){
            ICloudService cloudService = (ICloudService) packet.content;

            if(cloudServices.stream().anyMatch(cloudService1 -> cloudService1.getName().equalsIgnoreCase(cloudService.getName()))) {
                cloudServices.remove(cloudServices.stream().filter(cloudService1 -> cloudService1.getName().equalsIgnoreCase(cloudService.getName())).findAny().orElse(null));
                cloudService.getCloudGroup().getOnlineServices().remove(cloudServices.stream().filter(cloudService1 -> cloudService1.getName().equalsIgnoreCase(cloudService.getName())).findAny().orElse(null));
            }

            cloudServices.add(cloudService);

            ICloudGroup cloudGroup = CloudPlugin.getInstance().getCloudGroupImplementation().getCloudGroups().stream().filter(iCloudGroup -> iCloudGroup.getName().equalsIgnoreCase(cloudService.getCloudGroup().getName())).findFirst().orElse(null);

            if(cloudGroup == null) return;

            if(cloudGroup.getOnlineServices().stream().anyMatch(iCloudService -> iCloudService.getName().equalsIgnoreCase(cloudService.getName()))){
                cloudGroup.getOnlineServices().remove(cloudGroup.getOnlineServices().stream().filter(iCloudService -> iCloudService.getName().equalsIgnoreCase(cloudService.getName())).findAny().orElse(null));
            }

            cloudGroup.getOnlineServices().add(cloudService);

        }

    }
}
