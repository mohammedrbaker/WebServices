package com.example.stephan.webservices;

import android.os.AsyncTask;

/**
 * Created by Stephan on 14-3-2016.
 *
 */
public class TagAsyncTask extends AsyncTask<String,Integer,String> {

    // you may separate this or combined to caller class.
    public interface AsyncResponse {
        void processFinish(String output);
        void updateProcess(String updateMessage);
    }

    public AsyncResponse delegate = null;

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