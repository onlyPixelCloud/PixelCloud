package de.haizon.pixelcloud.api.player;

import de.haizon.pixelcloud.api.services.ICloudService;
import org.json.JSONObject;

import java.util.Map;
import java.util.UUID;

/**
 * JavaDoc this file!
 * Created: 22.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public interface ICloudPlayer {

    /**
     * Connect to player to another service
     * @param var1
     */
    void connect(ICloudService var1);

    /**
     * Sends the player a action bar
     * @param var1
     */
    void sendActionbar(String var1);

    void sendMessage(String var1);

    void kick();

    void kick(String var1);

    void sendTablist(String var1, String var2);

    void setProperty(String var1, Object var2);

    ICloudService getConnectedProxy();

    ICloudService getConnectedService();

    JSONObject getProperties();

    String getUniqueId();

}
