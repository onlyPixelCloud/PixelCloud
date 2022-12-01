package de.haizon.pixelcloud.master.backend.packets;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import de.haizon.pixelcloud.api.packets.Packet;
import de.haizon.pixelcloud.api.packets.abstracts.PacketReceiveFunction;
import de.haizon.pixelcloud.master.CloudMaster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JavaDoc this file!
 * Created: 24.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class PacketFunction {

    private final Server server;

    private final List<PacketReceiveFunction> functions;

    public PacketFunction() {
        functions = new ArrayList<>();

        server = new Server();
        server.start();
        try {
            server.bind(4020, 4080);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Kryo kryo = server.getKryo();
        kryo.setRegistrationRequired(false);

        server.addListener(new Listener(){
            @Override
            public void received(Connection connection, Object object) {

                if(object instanceof Packet packet){

                    Optional<PacketReceiveFunction> optionalPacket = functions.stream().filter(packetReceiveFunction -> packetReceiveFunction.getId().equalsIgnoreCase(packet.id)).findAny();

                    optionalPacket.stream().parallel().forEach(packetReceiveFunction -> packetReceiveFunction.received(packet));

                }

            }
        });

    }

    public void registerPacketReceiver(PacketReceiveFunction packetReceiveFunction){
        functions.add(packetReceiveFunction);
    }

    public List<PacketReceiveFunction> getFunctions() {
        return functions;
    }

    public void sendPacket(Packet packet){
        server.sendToAllTCP(packet);
    }

}
