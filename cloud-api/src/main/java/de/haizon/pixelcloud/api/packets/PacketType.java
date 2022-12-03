package de.haizon.pixelcloud.api.packets;

/**
 * JavaDoc this file!
 * Created: 24.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public enum PacketType {

    SERVICE_ONLINE,
    SERVICE_STOPPED,
    PLAYER_CONNECTED,
    PLAYER_DISCONNECTED,
    SERVICE_REGISTER,

    SEND_BACK_TO_CLIENT,

    PLAYER_CONNECT_TO_SERVICE,
    PLAYER_SEND_ACTIONBAR,
    PLAYER_KICK,

}
