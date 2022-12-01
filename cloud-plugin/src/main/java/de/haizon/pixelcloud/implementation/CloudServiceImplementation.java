package de.haizon.pixelcloud.implementation;

import com.sun.jdi.IntegerType;
import de.haizon.pixelcloud.CloudPlugin;
import de.haizon.pixelcloud.api.group.ICloudGroup;
import de.haizon.pixelcloud.api.packets.Packet;
import de.haizon.pixelcloud.api.packets.abstracts.PacketReceiveFunction;
import de.haizon.pixelcloud.api.player.ICloudPlayer;
import de.haizon.pixelcloud.api.services.ICloudService;
import de.haizon.pixelcloud.api.services.status.CloudServiceStatus;
import de.haizon.pixelcloud.api.services.version.IGroupVersion;
import de.haizon.pixelcloud.api.template.TemplateType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * JavaDoc this file!
 * Created: 24.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class CloudServiceImplementation extends PacketReceiveFunction {

    private final List<ICloudService> cloudServices;

    public CloudServiceImplementation() {
        super("receive_service");
        this.cloudServices = new ArrayList<>();
    }

    public List<ICloudService> getCloudServices() {
        return cloudServices;
    }

    @Override
    public void received(Packet packet) {

        if(packet.cloudService == null) return;

        ICloudService cloudService = packet.cloudService;

        if (!cloudServices.contains(cloudService)) cloudServices.add(cloudService);

    }
}
