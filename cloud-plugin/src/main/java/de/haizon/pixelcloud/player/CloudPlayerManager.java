package de.haizon.pixelcloud.player;

import de.haizon.pixelcloud.api.player.ICloudPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * JavaDoc this file!
 * Created: 03.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class CloudPlayerManager {

    private static final List<ICloudPlayer> cloudPlayers = new ArrayList<>();

    public void register(UUID uuid){
        cloudPlayers.add(new CloudPlayerImpl(uuid.toString(), null, null));
    }

    public ICloudPlayer getCloudPlayer(UUID uuid){
        return cloudPlayers.stream().filter(iCloudPlayer -> iCloudPlayer.getUniqueId().equals(uuid.toString())).findFirst().orElse(null);
    }

    public List<ICloudPlayer> getCloudPlayers() {
        return cloudPlayers;
    }

}
