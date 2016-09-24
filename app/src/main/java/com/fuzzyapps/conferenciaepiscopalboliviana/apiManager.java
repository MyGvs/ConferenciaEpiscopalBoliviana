package com.fuzzyapps.conferenciaepiscopalboliviana;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Geovani on 21/09/2016.
 */

public class apiManager extends AsyncTask<Void, Void, String> {
    protected void onPreExecute() {

    }
    @Override
    protected String doInBackground(Void... params) {
        try {
            return null;
        }catch(Exception e) {
            Log.e("GVS - ERROR", e.getMessage(), e);
            return null;
        }
    }
    protected void onPostExecute(String response) {

    }
}
