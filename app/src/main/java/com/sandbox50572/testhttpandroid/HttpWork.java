package com.sandbox50572.testhttpandroid;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class HttpWork implements Runnable {

    private String message;
    private byte[] postData;

    //конструктор
    public HttpWork(String message) {
        this.message = message;
        this.postData = new  byte[0];

    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void run() {
        sendGet();
        //sendPost(message);
    }


    private void sendGet(){

        for (int i=0; i<10; i++) {

            try{
                String mystr = "http://192.168.0.12:8080/JollyRoger/send";
                URL obj = new URL(mystr);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", "Mozilla/5.0");
                con.setRequestProperty("Accept-Charset", "UTF-8");
                con.setRequestProperty("message", message +i);
                InputStream response = con.getInputStream();
                Scanner s = new Scanner(response).useDelimiter("\\A");
                String result = s.hasNext() ? s.next() : "";
                //return result;
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void sendPost(String content) {



        HttpURLConnection conn = null;
        try {
            String urlParameters = "param1=a&message=" + content;
            postData = urlParameters.getBytes(StandardCharsets.UTF_8);//преобразование в байты для передачи по сети
            int postDataLength = postData.length;
            String request = "http://192.168.0.12:8080/JollyRoger/send";//адрес скрипта
            URL url = new URL(request);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(postData);
            wr.flush();//TODO Передача
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
