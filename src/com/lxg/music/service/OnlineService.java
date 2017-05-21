package com.lxg.music.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;



public class OnlineService extends Service{
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
