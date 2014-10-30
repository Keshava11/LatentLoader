package com.latentloader;

import com.google.android.gms.common.Scopes;
import com.google.api.services.youtube.YouTubeScopes;

/**
 * Created by ravikumar on 10/20/2014.
 */
public interface AppConstants {
    public static final int SEARCH_VIDEO = 1;

    public static final String SEARCH_VIDEO_MSG = "Searching Videos";

    public static final String DIALOG_TITLE = "Loading";

    public static final long NUMBER_OF_VIDEOS_RETURNED = 25;
    public static final String APP_NAME = LatentLoaderApplication.appName();

    // Register an API key here: https://code.google.com/apis/console
    // Note : This is the browser key instead of android key as Android key was generating Service config errors (403)
    public static final String KEY = "YOUR_API_KEY";
    public static final String[] SCOPES = {Scopes.PROFILE, YouTubeScopes.YOUTUBE};
}
