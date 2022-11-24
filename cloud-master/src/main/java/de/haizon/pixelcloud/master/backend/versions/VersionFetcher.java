package de.haizon.pixelcloud.master.backend.versions;

import de.haizon.pixelcloud.api.services.version.GroupType;
import de.haizon.pixelcloud.api.services.version.IGroupVersion;
import de.haizon.pixelcloud.master.backend.loader.WebsiteContentLoader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * JavaDoc this file!
 * Created: 23.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class VersionFetcher {

    private final List<IGroupVersion> groupVersions;

    public VersionFetcher() {
        this.groupVersions = new ArrayList<>();
    }

    public void fetch(){

        try {
            JSONObject jsonObject = new JSONObject(new WebsiteContentLoader().loadContent("https://haizon.de/api/versions.json"));

            JSONArray jsonArray = jsonObject.getJSONArray("versions");

            jsonArray.toList().forEach(o -> {

                String[] values = o.toString().split(";");

                groupVersions.add(new IGroupVersion() {
                    @Override
                    public GroupType getType() {
                        return Arrays.stream(GroupType.values()).filter(groupType -> groupType.name().equalsIgnoreCase(values[0])).findFirst().orElse(null);
                    }

                    @Override
                    public String getName() {
                        return values[1];
                    }

                    @Override
                    public String getUrl() {
                        return values[2];
                    }
                });

            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public List<IGroupVersion> getFetchedVersions() {
        return groupVersions;
    }
}
