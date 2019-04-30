package com.shakib.bdlabit.pmpquizprep.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.shakib.bdlabit.pmpquizprep.BuildConfig;

/**
 * Created by razon30 on 16-04-19.
 */

public class SharePreferenceSingleton {

    private static SharePreferenceSingleton sharedPreferencesGlobal;
    private SharedPreferences sharedPreferences;

    public static SharePreferenceSingleton getInstance(Context context) {
        if (sharedPreferencesGlobal == null) {
            sharedPreferencesGlobal = new SharePreferenceSingleton(context);
        }
        return sharedPreferencesGlobal;
    }

    private SharePreferenceSingleton(Context context) {
        sharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID,Context.MODE_PRIVATE);
    }

    public void saveString(String key, String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public void saveBoolean(String key, Boolean bool){
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean(key, bool);
        prefsEditor.commit();
    }

    public String getString(String key) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getString(key, "0");
        }
        return "0";
    }

    public Boolean getBoolean(String key){

        if(sharedPreferences!= null){
            return  sharedPreferences.getBoolean(key, false);
        }
        return false;
    }

    public void clearData(){
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.clear();
        prefsEditor.commit();
    }

}
