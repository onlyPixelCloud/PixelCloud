package de.haizon.pixelcloud;

import de.haizon.pixelcloud.api.packets.CloudPacket;
import de.haizon.pixelcloud.api.packets.PacketType;
import de.haizon.pixelcloud.api.services.ICloudService;
import de.haizon.pixelcloud.packets.receiver.PacketInSendCloudMessage;
import de.haizon.pixelcloud.config.PluginServiceIdentifier;
import de.haizon.pixelcloud.implementation.CloudGroupImplementation;
import de.haizon.pixelcloud.implementation.CloudServiceImplementation;
import de.haizon.pixelcloud.packets.PacketFunction;
import de.haizon.pixelcloud.player.CloudPlayerManager;
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
    private final CloudPlayerManager cloudPlayerManager;

    public CloudPlugin() {
        instance = this;
        packetFunction = new PacketFunction();
        pluginServiceIdentifier = new PluginServiceIdentifier();

        cloudGroupImplementation = new CloudGroupImplementation();
        cloudServiceImplementation = new CloudServiceImplementation();
        cloudPlayerManager = new CloudPlayerManager();

        packetFunction.registerPacketReceiver(cloudGroupImplementation);
        packetFunction.registerPacketReceiver(cloudServiceImplementation);
        packetFunction.registerPacketReceiver(new PacketInSendCloudMessage());

        CloudPacket<JSONObject> cloudPacket = new CloudPacket<>(PacketType.SERVICE_ONLINE.name(), new JSONObject().put("name", pluginServiceIdentifier.getJsonObject().getString("name")).put("type", pluginServiceIdentifier.getJsonObject().getString("type")).put("port", pluginServiceIdentifier.getJsonObject().getInt("port")));

        getPacketFunction().sendPacket(cloudPacket);

    }

    public ICloudService thisService(){
        return getCloudServiceImplementation().getCloudServices().stream().filter(cloudService -> cloudService.getName().equalsIgnoreCase(getPluginServiceIdentifier().getJsonObject().getString("name"))).findFirst().orElse(null);
    }

    public CloudPlayerManager getCloudPlayerManager() {
        return cloudPlayerManager;
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
