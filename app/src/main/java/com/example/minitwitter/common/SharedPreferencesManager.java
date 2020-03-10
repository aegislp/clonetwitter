package com.example.minitwitter.common;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private static final String APP_SETTING_FILE = "APP_SETTINGS";


    public SharedPreferencesManager(){

    }

    private static SharedPreferences getShatedPreferences(){

        return MyApp.getContext().getSharedPreferences(APP_SETTING_FILE, Context.MODE_PRIVATE);
    }

    public static void setSomeStringValue(String dataLabel, String dataValue){
        SharedPreferences.Editor editor = getShatedPreferences().edit();
        editor.putString(dataLabel,dataValue);
        editor.commit();

    }

    public static void setSomeBooleanValue(String dataLabel, boolean dataValue){
        SharedPreferences.Editor editor = getShatedPreferences().edit();
        editor.putBoolean(dataLabel,dataValue);
        editor.commit();

    }

    public static String getSomeStringValue(String dataLabel) {

        return getShatedPreferences().getString(dataLabel,null);

    }
    public static Boolean getSomeBooleanValue(String dataLabel) {

        return getShatedPreferences().getBoolean(dataLabel,false);

    }



}
