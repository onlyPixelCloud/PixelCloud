package de.haizon.pixelcloud.player;

import de.haizon.pixelcloud.CloudPlugin;
import de.haizon.pixelcloud.api.packets.CloudPacket;
import de.haizon.pixelcloud.api.packets.Packet;
import de.haizon.pixelcloud.api.packets.PacketType;
import de.haizon.pixelcloud.api.player.ICloudPlayer;
import de.haizon.pixelcloud.api.services.ICloudService;
import de.haizon.pixelcloud.api.services.impl.CloudServiceImpl;
import org.json.JSONObject;

import java.util.UUID;

/**
 * JavaDoc this file!
 * Created: 03.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class CloudPlayerImpl implements ICloudPlayer {

    private String uuid;
    private CloudServiceImpl connectedService, connectedProxy;

    private CloudPlayerImpl(){

    }

    public CloudPlayerImpl(String uuid, CloudServiceImpl connectedService, CloudServiceImpl connectedProxy) {
        this.uuid = uuid;
        this.connectedService = connectedService;
        this.connectedProxy = connectedProxy;
    }

    public void update(){
        CloudPacket<CloudPlayerImpl> cloudPacket = new CloudPacket<>(PacketType.PLAYER_UPDATE.name(), this);
        CloudPlugin.getInstance().getPacketFunction().sendPacket(cloudPacket);
    }

    @Override
    public void connect(ICloudService var1) {

        CloudPacket<JSONObject> cloudPacket = new CloudPacket<>(PacketType.SEND_BACK_TO_CLIENT.name(), new JSONObject().put("backType", PacketType.PLAYER_CONNECT_TO_SERVICE.name()).put("uniqueId", getUniqueId()).put("service", var1.getName()));
        CloudPlugin.getInstance().getPacketFunction().sendPacket(cloudPacket);

    }

    @Override
    public void sendActionbar(String var1) {

        CloudPacket<JSONObject> cloudPacket = new CloudPacket<>(PacketType.SEND_BACK_TO_CLIENT.name(), new JSONObject().put("backType", PacketType.PLAYER_SEND_ACTIONBAR.name()).put("uniqueId", getUniqueId()).put("message", var1));
        CloudPlugin.getInstance().getPacketFunction().sendPacket(cloudPacket);

    }

    @Override
    public void sendMessage(String var1) {

        CloudPacket<JSONObject> cloudPacket = new CloudPacket<>(PacketType.SEND_BACK_TO_CLIENT.name(), new JSONObject().put("backType", PacketType.PLAYER_SEND_MESSAGE.name()).put("uniqueId", getUniqueId()).put("message", var1));
        CloudPlugin.getInstance().getPacketFunction().sendPacket(cloudPacket);

    }

    @Override
    public void kick() {

        CloudPacket<JSONObject> cloudPacket = new CloudPacket<>(PacketType.SEND_BACK_TO_CLIENT.name(), new JSONObject().put("backType", PacketType.PLAYER_KICK.name()).put("uniqueId", getUniqueId()).put("message", "You were kicked..."));
        CloudPlugin.getInstance().getPacketFunction().sendPacket(cloudPacket);

    }

    @Override
    public void kick(String var1) {
        CloudPacket<JSONObject> cloudPacket = new CloudPacket<>(PacketType.SEND_BACK_TO_CLIENT.name(), new JSONObject().put("backType", PacketType.PLAYER_KICK.name()).put("uniqueId", getUniqueId()).put("message", var1));
        CloudPlugin.getInstance().getPacketFunction().sendPacket(cloudPacket);
    }

    @Override
    public void sendTablist(String var1, String var2) {

        CloudPacket<JSONObject> cloudPacket = new CloudPacket<>(PacketType.SEND_BACK_TO_CLIENT.name(), new JSONObject().put("backType", PacketType.PLAYER_SEND_TABLIST.name()).put("uniqueId", getUniqueId()).put("header", var1).put("footer", var2));
        CloudPlugin.getInstance().getPacketFunction().sendPacket(cloudPacket);

    }

    @Override
    public void setProperty(String var1, Object var2) {
        //TODO: set properties...
    }

    @Override
    public ICloudService getConnectedProxy() {
        return connectedProxy;
    }


    @Override
    public ICloudService getConnectedService() {
        return connectedService;
    }

    @Override
    public JSONObject getProperties() {
        return null;
    }

    @Override
    public String getUniqueId() {
        return uuid;
    }

}
