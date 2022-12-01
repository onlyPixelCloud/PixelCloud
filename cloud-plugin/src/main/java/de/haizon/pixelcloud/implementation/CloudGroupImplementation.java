package de.haizon.pixelcloud.implementation;

import de.haizon.pixelcloud.api.group.ICloudGroup;
import de.haizon.pixelcloud.api.packets.Packet;
import de.haizon.pixelcloud.api.packets.abstracts.PacketReceiveFunction;
import de.haizon.pixelcloud.api.services.version.GroupType;
import de.haizon.pixelcloud.api.services.version.IGroupVersion;
import de.haizon.pixelcloud.api.template.ITemplate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * JavaDoc this file!
 * Created: 24.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class CloudGroupImplementation extends PacketReceiveFunction {

    private final List<ICloudGroup> cloudGroups;

    public CloudGroupImplementation() {
        super("receive_group");
        this.cloudGroups = new ArrayList<>();
    }

    public List<ICloudGroup> getCloudGroups() {
        return cloudGroups;
    }

    @Override
    public void received(Packet packet) {

        if(packet.cloudGroup == null) return;

        ICloudGroup cloudGroup = packet.cloudGroup;

        if(!cloudGroups.contains(cloudGroup)) cloudGroups.add(cloudGroup);

    }

}
