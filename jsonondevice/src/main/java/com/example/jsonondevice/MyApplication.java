package com.example.jsonondevice;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

/**
 * Created by bradl_000 on 2014/01/22.
 */
public class MyApplication extends Application {
    public final static String tag = "MyApplication";
    public static Context s_applicationContent = null;

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        Log.d(tag,"Configuration Changed");
    }

    @Override
    public void onCreate(){
        super.onCreate();
        s_applicationContent = getApplicationContext();
        Log.d(tag,"OnCreate");
    }

    @Override
    public void onLowMemory(){
        super.onLowMemory();
        Log.d(tag,"On Low Memory");
    }

    @Override
    public void onTerminate(){
        super.onTerminate();
        Log.d(tag,"On Terminate");
    }
}
