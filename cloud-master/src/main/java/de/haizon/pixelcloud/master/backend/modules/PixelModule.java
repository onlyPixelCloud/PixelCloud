package de.haizon.pixelcloud.master.backend.modules;

import de.haizon.pixelcloud.api.services.version.GroupType;

/**
 * JavaDoc this file!
 * Created: 04.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class PixelModule {

    public String name;
    public String version;
    public String[] authors;
    public GroupType[] groupTypes;
    public String fileName;

    public PixelModule(String name, String version, String[] authors, GroupType[] groupTypes, String fileName) {
        this.name = name;
        this.version = version;
        this.authors = authors;
        this.groupTypes = groupTypes;
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String[] getAuthors() {
        return authors;
    }

    public GroupType[] getGroupTypes() {
        return groupTypes;
    }
}
