package de.haizon.pixelcloud;

import de.haizon.pixelcloud.api.packets.Packet;
import de.haizon.pixelcloud.api.packets.PacketType;
import de.haizon.pixelcloud.api.services.ICloudService;
import de.haizon.pixelcloud.config.PluginServiceIdentifier;
import de.haizon.pixelcloud.implementation.CloudGroupImplementation;
import de.haizon.pixelcloud.implementation.CloudServiceImplementation;
import de.haizon.pixelcloud.packets.PacketFunction;
import org.checkerframework.checker.units.qual.C;
import org.json.JSONObject;

/**
 * JavaDoc this file!
 * Created: 23.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class CloudPlugin {

    private static CloudPlugin instance;

    private final PacketFunction packetFunction;
    private final PluginServiceIdentifier pluginServiceIdentifier;
    private final CloudServiceImplementation cloudServiceImplementation;
    private final CloudGroupImplementation cloudGroupImplementation;

    public CloudPlugin() {
        instance = this;
        packetFunction = new PacketFunction();
        pluginServiceIdentifier = new PluginServiceIdentifier();
        cloudServiceImplementation = new CloudServiceImplementation();
        cloudGroupImplementation = new CloudGroupImplementation();

        packetFunction.registerPacketReceiver(cloudServiceImplementation);
        packetFunction.registerPacketReceiver(cloudGroupImplementation);

        Packet packet = new Packet();
        packet.id = PacketType.SERVICE_ONLINE.name();
        packet.content = new JSONObject().put("name", pluginServiceIdentifier.getJsonObject().getString("name")).put("type", pluginServiceIdentifier.getJsonObject().getString("type")).put("port", pluginServiceIdentifier.getJsonObject().getInt("port")).toString();

        sendPacket(packet);

    }

    public void sendPacket(Packet packet){
        getPacketFunction().sendPacket(packet);
    }

    public CloudGroupImplementation getCloudGroupImplementation() {
        return cloudGroupImplementation;
    }

    public CloudServiceImplementation getCloudServiceImplementation() {
        return cloudServiceImplementation;
    }

    public PluginServiceIdentifier getPluginServiceIdentifier() {
        return pluginServiceIdentifier;
    }

    public PacketFunction getPacketFunction() {
        return packetFunction;
    }

    public static CloudPlugin getInstance() {
        return instance;
    }
}
