package com.redsponge.bot;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Ping {

    public static void main(String[] args) throws Exception{
        System.out.println("DEFINING URL");
        URL url = new URL("https://spongebot-discord.herokuapp.com");
        System.out.println("OPENING CONNECTION");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        System.out.println("OPENING STREAM");
        InputStream stream = url.openStream();
        System.out.println("CLOSING STREAM");
        stream.close();
        System.out.println("DONE");
    }

}
