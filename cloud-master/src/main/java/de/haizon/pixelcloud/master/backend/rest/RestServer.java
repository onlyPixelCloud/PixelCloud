package de.haizon.pixelcloud.master.backend.rest;

import com.sun.net.httpserver.HttpServer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Arrays;

/**
 * JavaDoc this file!
 * Created: 23.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class RestServer {

    public RestServer() {

        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(3817), 0);

            httpServer.createContext("/api/server", (exchange -> {

                if(exchange.getRequestMethod().equalsIgnoreCase("GET")){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("onlineCount", 1);
                    jsonObject.put("servers", Arrays.asList("Proxy-1", "Lobby-1"));

                    String response = jsonObject.toString(2);

                    exchange.sendResponseHeaders(200, response.getBytes().length);
                    OutputStream outputStream = exchange.getResponseBody();
                    outputStream.write(response.getBytes());
                    outputStream.flush();
                } else {
                    exchange.sendResponseHeaders(405, -1);
                }

                exchange.close();

            }));

            httpServer.setExecutor(null);
            httpServer.start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
