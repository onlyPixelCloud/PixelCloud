package de.haizon.pixelcloud.bootstrap.spigot;

import de.haizon.pixelcloud.CloudPlugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * JavaDoc this file!
 * Created: 23.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class SpigotBootstrap extends JavaPlugin {

    private static SpigotBootstrap instance;

    @Override
    public void onEnable() {
        instance = this;

        new CloudPlugin();

    }

    public static SpigotBootstrap getInstance() {
        return instance;
    }
}
