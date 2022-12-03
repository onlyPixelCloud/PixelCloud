package de.haizon.pixelcloud.api.services.impl;

import de.haizon.pixelcloud.api.group.ICloudGroup;
import de.haizon.pixelcloud.api.services.ICloudService;
import de.haizon.pixelcloud.api.services.version.GroupType;
import de.haizon.pixelcloud.api.services.version.IGroupVersion;
import de.haizon.pixelcloud.api.template.ITemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * JavaDoc this file!
 * Created: 03.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class CloudGroupImpl implements ICloudGroup {

    private String name, type;
    private GroupVersionImpl groupVersion;
    private int maxServices, minServices, maxHeap, percentage, maxPlayers;
    private boolean maintenance;
    private TemplateImpl template;
    private ArrayList<ICloudService> cloudServices;

    private CloudGroupImpl(){

    }

    public CloudGroupImpl(String name, GroupVersionImpl groupVersion, int maxServices, int minServices, int maxHeap, int percentage, int maxPlayers, boolean maintenance, TemplateImpl template, String type) {
        this.name = name;
        this.groupVersion = groupVersion;
        this.maxServices = maxServices;
        this.minServices = minServices;
        this.maxHeap = maxHeap;
        this.percentage = percentage;
        this.maxPlayers = maxPlayers;
        this.maintenance = maintenance;
        this.template = template;
        this.type = type;
        this.cloudServices = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMaxServices() {
        return maxServices;
    }

    @Override
    public int getMinServices() {
        return minServices;
    }

    @Override
    public int getMaxHeap() {
        return maxHeap;
    }

    @Override
    public int getPercentageToStartNewService() {
        return percentage;
    }

    @Override
    public IGroupVersion getGroupVersion() {
        return groupVersion;
    }

    @Override
    public GroupType getGroupType() {
        return Arrays.stream(GroupType.values()).filter(groupType -> groupType.name().equalsIgnoreCase(type)).findAny().orElse(null);
    }

    @Override
    public boolean isMaintenance() {
        return maintenance;
    }

    @Override
    public int getMaxPlayers() {
        return maxPlayers;
    }

    @Override
    public ITemplate getTemplate() {
        return template;
    }

    @Override
    public List<ICloudService> getOnlineServices() {
        return cloudServices;
    }
}
