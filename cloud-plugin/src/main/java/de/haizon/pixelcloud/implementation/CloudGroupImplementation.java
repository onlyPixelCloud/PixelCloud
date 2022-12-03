package de.haizon.pixelcloud.implementation;

import de.haizon.pixelcloud.api.group.ICloudGroup;
import de.haizon.pixelcloud.api.packets.Packet;
import de.haizon.pixelcloud.api.packets.abstracts.PacketReceiveFunction;
import de.haizon.pixelcloud.api.services.impl.CloudGroupImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * JavaDoc this file!
 * Created: 24.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class CloudGroupImplementation extends PacketReceiveFunction {

    private static final List<ICloudGroup> cloudGroups = new ArrayList<>();

    public CloudGroupImplementation() {
        super("receive_group");
    }

    public List<ICloudGroup> getCloudGroups() {
        return cloudGroups;
    }

    @Override
    public void received(Packet packet) {

        if(packet.content instanceof CloudGroupImpl){
            ICloudGroup cloudGroup = (ICloudGroup) packet.content;

            if(cloudGroups.stream().anyMatch(cloudService1 -> cloudService1.getName().equalsIgnoreCase(cloudGroup.getName()))) {
                cloudGroups.remove(cloudGroups.stream().filter(cloudService1 -> cloudService1.getName().equalsIgnoreCase(cloudGroup.getName())).findAny().orElse(null));
            }

            cloudGroups.add(cloudGroup);

        }

    }

}
