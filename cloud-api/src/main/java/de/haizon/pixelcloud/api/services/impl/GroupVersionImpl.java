package de.haizon.pixelcloud.api.services.impl;

import de.haizon.pixelcloud.api.services.version.GroupType;
import de.haizon.pixelcloud.api.services.version.IGroupVersion;

/**
 * JavaDoc this file!
 * Created: 03.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class GroupVersionImpl implements IGroupVersion {

    private GroupType groupType;
    private String name, url;

    private GroupVersionImpl(){

    }

    public GroupVersionImpl(GroupType groupType, String name, String url) {
        this.groupType = groupType;
        this.name = name;
        this.url = url;
    }

    @Override
    public GroupType getType() {
        return groupType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUrl() {
        return url;
    }
}
