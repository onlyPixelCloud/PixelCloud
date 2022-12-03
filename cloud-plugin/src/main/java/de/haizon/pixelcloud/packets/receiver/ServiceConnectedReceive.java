package de.haizon.pixelcloud.packets.receiver;

import de.haizon.pixelcloud.CloudPlugin;
import de.haizon.pixelcloud.api.packets.Packet;
import de.haizon.pixelcloud.api.packets.PacketType;
import de.haizon.pixelcloud.api.packets.abstracts.PacketReceiveFunction;
import de.haizon.pixelcloud.api.services.ICloudService;
import de.haizon.pixelcloud.bootstrap.velocity.VelocityBootstrap;
import org.json.JSONObject;

import java.net.InetSocketAddress;

/**
 * JavaDoc this file!
 * Created: 24.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class ServiceConnectedReceive extends PacketReceiveFunction {
    public ServiceConnectedReceive() {
        super(PacketType.SERVICE_REGISTER.name());
    }

    @Override
    public void received(Packet packet) {

        JSONObject jsonObject = (JSONObject) packet.content;

        ICloudService cloudService = CloudPlugin.getInstance().getCloudServiceImplementation().getCloudServices().stream().filter(service -> service.getName().equalsIgnoreCase(jsonObject.getString("name"))).findAny().orElse(null);

        if(cloudService == null) return;

        VelocityBootstrap.getInstance().registerService(cloudService.getName(), new InetSocketAddress("127.0.0.1", cloudService.getPort()));
        System.out.println("registered {" + cloudService.getName() + ":" + cloudService.getPort() + "}");

    }
}
