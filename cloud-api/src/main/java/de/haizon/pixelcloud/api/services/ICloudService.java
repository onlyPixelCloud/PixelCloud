package de.haizon.pixelcloud.api.services;

import de.haizon.pixelcloud.api.group.ICloudGroup;
import de.haizon.pixelcloud.api.packets.CloudPacket;
import de.haizon.pixelcloud.api.player.ICloudPlayer;
import de.haizon.pixelcloud.api.services.impl.CloudServiceImpl;
import de.haizon.pixelcloud.api.services.status.CloudServiceStatus;
import de.haizon.pixelcloud.api.services.version.IGroupVersion;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * JavaDoc this file!
 * Created: 02.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public interface ICloudService {

    /**
     * Returns the group name from the service
     */
    String getGroupName();

    /**
     * Returns the service id eg. Lobby-1 then the id will be 1
     */
    int getServiceId();

    /**
     * Returns the service name
     */
    String getName();

    /**
     * Returns a list with all players whp online on this service
     */
    List<ICloudPlayer> getCurrentPlayers();

    /**
     * Returns the cloud group
     */
    ICloudGroup getCloudGroup();

    /**
     * Returns the cloud service status
     */
    CloudServiceStatus getServiceStatus();

    /**
     * Returns all messages of the console
     */
    CopyOnWriteArrayList<String> getConsoleMessages();

    /**
     * Returns the group version form the service. e.g Paperspigot-1.8.8
     */
    IGroupVersion getVersion();

    /**
     * Returns the port from the service
     */
    int getPort();

    /**
     * Set's the service state
     */
    void setStatus(CloudServiceStatus cloudServiceStatus);

    /**
     * Returns if the service is static
     */
    boolean isStatic();

    /**
     * Starts the service
     */
    void start();

    /**
     * Updates the service to the network
     */
    CloudPacket<CloudServiceImpl> update();

}
