package de.haizon.pixelcloud.packets;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import de.haizon.pixelcloud.api.packets.Packet;
import de.haizon.pixelcloud.api.packets.abstracts.PacketReceiveFunction;

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

    private final List<PacketReceiveFunction> functions;
    private final Client client;

    public PacketFunction() {
        this.functions = new ArrayList<>();

        client = new Client();
        client.start();
        try {
            client.connect(5020, "127.0.0.1", 4020, 4080);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Kryo kryo = client.getKryo();
        kryo.setRegistrationRequired(false);

        client.addListener(new Listener(){
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
        client.sendTCP(packet);
        System.out.println("yeaho");
    }

}
