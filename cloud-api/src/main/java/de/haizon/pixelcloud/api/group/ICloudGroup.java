package de.haizon.pixelcloud.api.group;

import de.haizon.pixelcloud.api.services.version.GroupType;
import de.haizon.pixelcloud.api.services.version.IGroupVersion;
import de.haizon.pixelcloud.api.template.ITemplate;

import java.util.UUID;

/**
 * JavaDoc this file!
 * Created: 23.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public interface ICloudGroup {

    /**
     * Returns the uuid of the group
     */
    UUID getUUID();

    /**
     * Returns the name of the group. e.g. Proxy
     */
    String getName();

    /**
     * Returns the max service count
     */
    int getMaxServices();

    /**
     * Returns the min service count
     */
    int getMinServices();

    /**
     * Returns the max memory of the group
     */
    int getMaxHeap();

    /**
     * Returns the percentage to start a new service
     */
    int getPercentageToStartNewService();

    /**
     * Returns the group version. e.g. PaperSpigot-1.8.8
     */
    IGroupVersion getGroupVersion();

    /**
     * Returns the group type. e.g. SERVER
     */
    GroupType getGroupType();

    /**
     * Returns if maintenance is on
     */
    boolean isMaintenance();

    /**
     * Returns the max players
     */
    int getMaxPlayers();

    /**
     * Returns the group template
     */
    ITemplate getTemplate();

}