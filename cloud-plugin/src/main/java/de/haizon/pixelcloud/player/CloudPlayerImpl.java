package de.haizon.pixelcloud.player;

import de.haizon.pixelcloud.CloudPlugin;
import de.haizon.pixelcloud.api.packets.CloudPacket;
import de.haizon.pixelcloud.api.packets.PacketType;
import de.haizon.pixelcloud.api.player.ICloudPlayer;
import de.haizon.pixelcloud.api.services.ICloudService;
import org.json.JSONObject;

import java.util.UUID;

/**
 * JavaDoc this file!
 * Created: 03.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class CloudPlayerImpl implements ICloudPlayer {

    private final UUID uuid;

    public CloudPlayerImpl(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void connect(ICloudService var1) {

        CloudPacket<JSONObject> cloudPacket = new CloudPacket<>(PacketType.SEND_BACK_TO_CLIENT.name(), new JSONObject().put("backType", PacketType.PLAYER_CONNECT_TO_SERVICE.name()).put("uniqueId", getUniqueId().toString()).put("service", var1.getName()));
        CloudPlugin.getInstance().getPacketFunction().sendPacket(cloudPacket);
        System.out.println("send: " + var1.getName());

    }

    @Override
    public void sendActionbar(String var1) {

    }

    @Override
    public void sendMessage(String var1) {

    }

    @Override
    public void kick() {

    }

    @Override
    public void kick(String var1) {

    }

    @Override
    public void sendTablist(String var1, String var2) {

    }

    @Override
    public void setProperty(String var1, Object var2) {

    }

    @Override
    public ICloudService getConnectedProxy() {
        return null;
    }


    @Override
    public ICloudService getConnectedService() {
        return null;
    }

    @Override
    public JSONObject getProperties() {
        return null;
    }

    @Override
    public UUID getUniqueId() {
        return uuid;
    }

}
