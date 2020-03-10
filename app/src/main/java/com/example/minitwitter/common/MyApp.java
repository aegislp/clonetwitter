package com.example.minitwitter.common;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {

    public static MyApp instance;

    public static MyApp getInstance(){
        return instance;
    }

    public static Context getContext(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
