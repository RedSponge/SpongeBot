package com.redsponge.bot;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import javax.print.DocFlavor.INPUT_STREAM;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class HerokuServerHandler {

    public static void createHandler() {
        createConnection();
        createPingClock();
    }

    /**
     * $PORT BIND EXCEPTION
     */
    private static void createConnection() {
        try {
            System.out.println("CREATING SERVER!");
            HttpServer server = HttpServer.create(new InetSocketAddress(Integer.parseInt(System.getenv("PORT"))), 0);
            server.createContext("/ping", new MyHandler());
            server.setExecutor(null);
            server.start();
            System.out.println("CREATED SERVER!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * PREVENT IDLING
     */
    private static void createPingClock() {
        try {
            URL url = new URL(System.getenv("toPing"));
            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    InputStream in = null;
                    try {
                        System.out.println("Pinging");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        in = url.openStream();
                        System.out.println("Pinged");
                        con.disconnect();
                        in.close();
                    } catch (IOException ignored) {}
                }
            }, 0, 1000*60*5); //EVERY FIVE MINUTES
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "Pong";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream out = exchange.getResponseBody();
            out.write(response.getBytes());
            out.close();
        }
    }

}
