package com.redsponge.bot;

import javax.print.DocFlavor.INPUT_STREAM;
import java.io.IOException;
import java.io.InputStream;
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
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(System.getenv("PORT")));
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
                        in = url.openStream();
                        in.close();
                    } catch (IOException ignored) {}
                }
            }, 0, 1000*60*5); //EVERY FIVE MINUTES
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
