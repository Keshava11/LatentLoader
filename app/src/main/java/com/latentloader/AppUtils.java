package com.latentloader;

import android.widget.Toast;

/**
 * Created by ravikumar on 10/20/2014.
 */
public class AppUtils {

    public static void showToast(String iMessage) {
        Toast.makeText(LatentLoaderApplication.getAppContext(), iMessage, Toast.LENGTH_SHORT).show();
    }
}
