package com.borjabares.pan_ssh.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

public class URLExpander {
	
	public static String expandShortURL(String address) throws IOException {
        URL url = new URL(address);
 
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY); //using proxy may increase latency
        connection.setInstanceFollowRedirects(false);
        connection.connect();
        String expandedURL = connection.getHeaderField("Location");
        connection.getInputStream().close();
        return expandedURL;
    }
	
}
