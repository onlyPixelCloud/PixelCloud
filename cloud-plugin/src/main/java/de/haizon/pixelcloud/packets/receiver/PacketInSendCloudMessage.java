package de.haizon.pixelcloud.packets.receiver;

import de.haizon.pixelcloud.api.packets.Packet;
import de.haizon.pixelcloud.api.packets.PacketType;
import de.haizon.pixelcloud.api.packets.abstracts.PacketReceiveFunction;
import de.haizon.pixelcloud.bootstrap.spigot.SpigotBootstrap;
import de.haizon.pixelcloud.bootstrap.velocity.VelocityBootstrap;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.json.JSONObject;

/**
 * JavaDoc this file!
 * Created: 04.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class PacketInSendCloudMessage extends PacketReceiveFunction {

    public PacketInSendCloudMessage() {
        super(PacketType.SEND_CLOUD_MESSAGE.name());
    }

    @Override
    public void received(Packet packet) {

        var jsonObject = (JSONObject) packet.content;

        switch (jsonObject.getString("type")){
            case "PROXY" -> VelocityBootstrap.getInstance().getProxyServer().getAllPlayers().forEach(player -> {

                if (jsonObject.has("permission")) {
                    if (!player.hasPermission(jsonObject.getString("permission"))) {
                        return;
                    }
                }

                if(jsonObject.has("actionType")){
                    switch (jsonObject.getString("actionType")){
                        case "RUN_COMMAND" -> {

                            var miniMessage = MiniMessage.miniMessage();
                            var component = miniMessage.deserialize("<click:" + jsonObject.getString("actionType").toLowerCase() + ":" + jsonObject.getString("action") + ">" + jsonObject.getString("message") + "</click>");

                            player.sendMessage(component);

                        }
                        case "HOVER" -> {

                            var miniMessage = MiniMessage.miniMessage();
                            var component = miniMessage.deserialize("<hover:show_text:" + jsonObject.getString("action") + ">" + jsonObject.getString("message"));

                            player.sendMessage(component);

                        }
                    }
                } else {
                    player.sendMessage(Component.text(jsonObject.getString("message")));
                }



            });
            case "SERVER", "LOBBY" -> SpigotBootstrap.getInstance().getServer().getOnlinePlayers().forEach(player -> {
                if(jsonObject.has("permission")){
                    if(player.hasPermission(jsonObject.getString("permission"))){
                        player.sendMessage(jsonObject.getString("message"));
                    }
                } else {
                    player.sendMessage(jsonObject.getString("message"));
                }

            });
        }

    }
}
