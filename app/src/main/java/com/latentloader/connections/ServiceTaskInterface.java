package com.latentloader.connections;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

public interface ServiceTaskInterface {

    HttpTransport transport = AndroidHttp.newCompatibleTransport();
    JsonFactory jsonFactory = new GsonFactory();
}
