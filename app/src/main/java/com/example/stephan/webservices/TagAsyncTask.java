package com.example.stephan.webservices;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

/**
 * Created by Stephan on 14-3-2016.
 *
 * cant be private?
 */
public class TagAsyncTask extends AsyncTask<String,Integer,String> {

    public interface AsyncResponse {
        void processFinish(String output);
    }
    public AsyncResponse delegate = null;

    @Override
    protected void onPreExecute(){
        Log.d("PreExceute", "On pre Exceute......");
    }

    @Override
    protected String doInBackground(String... param) {

        String search = param[0];
        Log.v("background", search);
        String request =
                new HttpRequestHelper().downloadFromServer(search);
        int i = 0;
        publishProgress(i);


        return request;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {

    }

    @Override
    protected void onPostExecute(String result) {
        ///showDialog("Downloaded " + result + " bytes");
        Log.v("donwloaded", result);

        delegate.processFinish(result);
    }

}