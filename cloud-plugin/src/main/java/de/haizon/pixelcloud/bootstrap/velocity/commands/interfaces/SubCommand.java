package de.haizon.pixelcloud.bootstrap.velocity.commands.interfaces;

import com.velocitypowered.api.proxy.Player;

/**
 * JavaDoc this file!
 * Created: 03.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public interface SubCommand {

    void apply(Player player, String[] args);

}
