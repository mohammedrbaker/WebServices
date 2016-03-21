package com.example.stephan.webservices;

import android.os.AsyncTask;

/**
 * asyncTask to get weather information from the internet.
 *
 * It will use an AsyncResponse to give the information back to the activity.
 */
public class TagAsyncTask extends AsyncTask<String,Integer,String> {

    /**
     * Function in the activity to give the information.
     * ! So these functions must be present!
     */
    public interface AsyncResponse {
        void processFinish(String output);
        void updateProcess(String updateMessage);
    }

    // fields
    public AsyncResponse delegate = null;       // initialize to null;

    /**
     * Function to set on what activity to respond too.
     */
    public TagAsyncTask(AsyncResponse delegate){
        this.delegate = delegate;
    }

    /**
     * Before launch
     */
    @Override
    protected void onPreExecute(){
        delegate.updateProcess("Getting weather info...");
    }

    /**
     * Launch
     */
    @Override
    protected String doInBackground(String... param) {

        String search = param[0];
        String searchMethod = param[1];
        String request =
                new HttpRequestHelper().downloadFromServer(search, searchMethod);
        return request;
    }

    /**
     * When finished.
     */
    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
    }

}