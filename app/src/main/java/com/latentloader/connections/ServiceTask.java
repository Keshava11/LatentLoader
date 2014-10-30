package com.latentloader.connections;

import android.os.AsyncTask;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
import com.latentloader.AppConstants;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class ServiceTask extends AsyncTask<Object, Void, Object[]> implements
        ServiceTaskInterface {
    private static final String TAG = ServiceTask.class.getSimpleName();
    private ServerResponseListener mServerResponseListener = null;
    private int mRequestCode = 0;

    public void setmServerResponseListener(
            ServerResponseListener mServerResponseListener) {
        this.mServerResponseListener = mServerResponseListener;
    }

    public ServiceTask(int iReqCode) {
        mRequestCode = iReqCode;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mServerResponseListener.prepareRequest(mRequestCode);
    }

    @Override
    protected Object[] doInBackground(Object... params) {
        if (params == null)
            throw new NullPointerException("Parameters to the async task can never be null");

        mServerResponseListener.goBackground();

        Object[] resultDetails = new Object[2];
        resultDetails[0] = mRequestCode;

        switch (mRequestCode) {
            case AppConstants.SEARCH_VIDEO:
                resultDetails[1] = loadVideos((String) params[0]);
                break;
        }

        return resultDetails;
    }

    @Override
    protected void onPostExecute(Object[] result) {
        super.onPostExecute(result);
        mServerResponseListener.completedRequest(result);
    }


    /**
     * Method
     * @see <a>https://developers.google.com/youtube/v3/code_samples/java#search_by_keyword</a>
     * @param queryTerm
     */
    private List<SearchResult> loadVideos(String queryTerm) {
        try {
            // This object is used to make YouTube Data API requests. The last
            // argument is required, but since we don't need anything
            // initialized when the HttpRequest is initialized, we override
            // the interface and provide a no-op function.
            YouTube youtube = new YouTube.Builder(transport, jsonFactory, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName(AppConstants.APP_NAME).build();

            // Define the API request for retrieving search results.
            YouTube.Search.List search = youtube.search().list("id,snippet");

            // Set your developer key from the Google Developers Console for
            // non-authenticated requests. See:
            // https://console.developers.google.com/
            search.setKey(AppConstants.KEY);
            search.setQ(queryTerm);

            // Restrict the search results to only include videos. See:
            // https://developers.google.com/youtube/v3/docs/search/list#type
            search.setType("video");

            // To increase efficiency, only retrieve the fields that the
            // application uses.
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url,snippet/thumbnails/medium/url)");
            search.setMaxResults(AppConstants.NUMBER_OF_VIDEOS_RETURNED);

            // Call the API and print results.
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            if (searchResultList != null) {
                return searchResultList;
            }
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return null;
    }

}
