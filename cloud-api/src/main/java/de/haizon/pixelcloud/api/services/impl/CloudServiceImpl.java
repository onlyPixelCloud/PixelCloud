package de.haizon.pixelcloud.api.services.impl;

import de.haizon.pixelcloud.api.group.ICloudGroup;
import de.haizon.pixelcloud.api.packets.CloudPacket;
import de.haizon.pixelcloud.api.packets.PacketType;
import de.haizon.pixelcloud.api.player.ICloudPlayer;
import de.haizon.pixelcloud.api.services.ICloudService;
import de.haizon.pixelcloud.api.services.status.CloudServiceStatus;
import de.haizon.pixelcloud.api.services.version.IGroupVersion;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * JavaDoc this file!
 * Created: 03.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class CloudServiceImpl implements ICloudService {

    private String groupName, name;
    private CloudGroupImpl cloudGroup;
    private int id, port;
    private boolean isStatic;
    private CloudServiceStatus cloudServiceStatus;

    private CloudServiceImpl() {
    }

    public CloudServiceImpl(String groupName, String name, CloudGroupImpl cloudGroup, int id, int port, boolean isStatic, CloudServiceStatus cloudServiceStatus) {
        this.groupName = groupName;
        this.name = name;
        this.cloudGroup = cloudGroup;
        this.id = id;
        this.port = port;
        this.isStatic = isStatic;
        this.cloudServiceStatus = cloudServiceStatus;
    }

    @Override
    public String getGroupName() {
        return groupName;
    }

    @Override
    public int getServiceId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<ICloudPlayer> getCurrentPlayers() {
        return new ArrayList<>();
    }

    @Override
    public ICloudGroup getCloudGroup() {
        return cloudGroup;
    }

    @Override
    public CloudServiceStatus getServiceStatus() {
        return cloudServiceStatus;
    }

    @Override
    public CopyOnWriteArrayList<String> getConsoleMessages() {
        return new CopyOnWriteArrayList<>();
    }

    @Override
    public IGroupVersion getVersion() {
        return cloudGroup.getGroupVersion();
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void setStatus(CloudServiceStatus cloudServiceStatus) {
        this.cloudServiceStatus = cloudServiceStatus;
    }

    @Override
    public boolean isStatic() {
        return isStatic;
    }

    @Override
    public void start() {

    }

    @Override
    public CloudPacket<CloudServiceImpl> update() {
        return new CloudPacket<>("receive_service", this);
    }
}
