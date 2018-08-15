package com.sandbox50572.testhttpandroid;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HttpWork implements Runnable {
    @Override
    public void run() {
        String mybla = sendGet();
    }

    private String sendGet(){
        try{
            String mystr = "http://192.168.0.12:8080/JollyRoger/send";
            URL obj = new URL(mystr);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Charset", "UTF-8");
            InputStream response = con.getInputStream();
            Scanner s = new Scanner(response).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            return result;
        }
        catch (Exception e) {
            return e.toString();
        }
    }
}
