package com.sandbox50572.testhttpandroid;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    Intent intentService;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //запуск геолокации
        MyLocationListener.SetUpLocationListener(this);


        //запуск геолокации в новом потоке
        //final Context mainContext = this;
        //new Thread(new Runnable() {
        //    @Override
        //    public void run() {
        //        MyLocationListener.SetUpLocationListener(mainContext);
        //    }
        //}).start();


        //находим кнопку
        btnOk = (Button) findViewById(R.id.btnOk);
        //Запуск сервиса
        intentService = new Intent(this,MyService.class);
        //tvInfo = (TextView) findViewById(R.id.tvInfo);

        // создаем обработчик нажатия
        View.OnClickListener oclBtnOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!isMyServiceRunning(MyService.class)) {
                    startService(intentService);
                } else {
                    stopService(intentService);
                }
            }
        };

        // присвоим обработчик кнопке OK (btnOk)
        btnOk.setOnClickListener(oclBtnOk);

    }

    //


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }




}
