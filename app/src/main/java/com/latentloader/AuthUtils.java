package com.latentloader;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;

import java.util.Arrays;

/**
 * This is the simple class defining methods for authenticating an user for accessing user specific contents.
 * TODO This class is not used currently. Will add authentication when accessing other api's that involves user authentication
 * Created by ravikumar on 10/20/2014.
 *
 */
public class AuthUtils {

    public static final String ACCOUNT_KEY = "accountName";
    private String mChosenAccountName;

    /**
     * Method for authenticating user. In current context no user specific action is required so avoiding it.
     *
     * @param savedInstanceState
     */
    public void ytUserAuth(Bundle savedInstanceState) {
        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(
                LatentLoaderApplication.getAppContext(), Arrays.asList(AppConstants.SCOPES));
        // set exponential backoff policy
        credential.setBackOff(new ExponentialBackOff());

        if (savedInstanceState != null) {
            mChosenAccountName = savedInstanceState.getString(ACCOUNT_KEY);
        } else {
            loadAccount();
        }
        credential.setSelectedAccountName(mChosenAccountName);
    }

    /**
     * Fetching Account name from app preferences
     */
    public void loadAccount() {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(LatentLoaderApplication.getAppContext());
        mChosenAccountName = sp.getString(ACCOUNT_KEY, null);
    }

    /**
     * Saving account name to app preferences
     */
    public void saveAccount() {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(LatentLoaderApplication.getAppContext());
        sp.edit().putString(ACCOUNT_KEY, mChosenAccountName).commit();
    }

}
