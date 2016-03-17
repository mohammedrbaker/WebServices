package com.example.stephan.webservices;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Stephan on 14-3-2016.
 *
 * cant be private?
 */
public class TagAsyncTask extends AsyncTask<String,Integer,String> {

    private MainActivity mainActivity;  // Activity to give date
    private Context context;            // Show updates

    /**
     * Make Async task.
     */
    public TagAsyncTask(MainActivity activity){
        super();
        this.mainActivity = activity;
        this.context = this.mainActivity.getApplicationContext();
    }

    /**
     * Before launch
     */
    @Override
    protected void onPreExecute(){
        Toast.makeText(context, "Getting data from server", Toast.LENGTH_SHORT).show();
    }

    /**
     * Launch
     */
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

    /**
     * Show updates
     */
    @Override
    protected void onProgressUpdate(Integer... progress) {
        Log.v("progress", "inteeger: " + progress[0]);
    }

    /**
     * When finished.
     */
    @Override
    protected void onPostExecute(String result) {
        ///showDialog("Downloaded " + result + " bytes");
        Log.v("donwloaded", result);

        mainActivity.processFinish(result);
    }

}