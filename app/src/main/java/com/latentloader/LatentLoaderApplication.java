package com.latentloader;

import android.app.Application;
import android.content.Context;

/**
 * This is the application class that holds the application level data
 * Created by ravikumar on 10/20/2014.
 */
public class LatentLoaderApplication extends Application {

    private static Context sContext = null;

    public static Context getAppContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }

    public static String appName() {
        return getAppContext().getString(R.string.app_name);
    }
}
