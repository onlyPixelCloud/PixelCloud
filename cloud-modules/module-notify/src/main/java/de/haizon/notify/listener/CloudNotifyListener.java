package de.haizon.notify.listener;

import de.haizon.notify.NotifyModule;
import de.haizon.pixelcloud.api.event.EventInject;
import de.haizon.pixelcloud.api.event.IEventListener;
import de.haizon.pixelcloud.api.event.nodes.CloudServiceLoggedInEvent;
import de.haizon.pixelcloud.api.event.nodes.CloudServiceLoggedOutEvent;
import de.haizon.pixelcloud.api.packets.CloudPacket;
import de.haizon.pixelcloud.api.packets.PacketType;
import de.haizon.pixelcloud.api.services.ICloudService;
import de.haizon.pixelcloud.api.services.version.GroupType;
import de.haizon.pixelcloud.master.CloudMaster;
import org.json.JSONObject;

/**
 * JavaDoc this file!
 * Created: 04.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class CloudNotifyListener implements IEventListener {

    @EventInject
    public void handle(CloudServiceLoggedInEvent event){
        String message = NotifyModule.getInstance().getJsonConfig().getProperty("service_logged_in");
        if (message == null || message.isEmpty()) {
            System.out.println("yes");
            return;
        }

        sendCloudMessage(message.replace("{service_name}", event.getCloudService().getName()), event.getCloudService());
    }

    @EventInject
    public void handle(CloudServiceLoggedOutEvent event){

        String message = NotifyModule.getInstance().getJsonConfig().getProperty("service_logged_out");
        if (message == null || message.isEmpty()) {
            return;
        }

        sendCloudMessage(message.replace("{service_name}", event.getCloudService().getName()), event.getCloudService());
    }

    private void sendCloudMessage(String message, ICloudService cloudService){
        CloudPacket<JSONObject> cloudPacket = new CloudPacket<>(PacketType.SEND_CLOUD_MESSAGE.name(), new JSONObject().put("type", GroupType.PROXY.name()).put("message", message).put("actionType", "RUN_COMMAND").put("action", "/server " + cloudService.getName()));
        CloudMaster.getInstance().getPacketFunction().sendPacket(cloudPacket);
    }

}
