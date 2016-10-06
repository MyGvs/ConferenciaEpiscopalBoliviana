package com.fuzzyapps.conferenciaepiscopalboliviana;

/**
 * Created by Geovani on 25/09/2016.
 */

public class Globals {
    //dirección de la web
    public static String ip = "http://192.168.1.6:8080";
    public static String bodyURL = "/software2_obras_iglesia";
    public static String bodyURLImages = "/templates";
    public static String completeImageURL = ip+bodyURL+bodyURLImages;
    public static String completeApiURL = ip+bodyURL+"/restfulApi/public/api/v1";
    public static boolean status;
}
/*
//BASIC API CONSTRUCTOR
public class obraAPI extends AsyncTask<String, String, String> {
        public String id;

        public obraAPI(String id) {
            //obtener perfi de encargado
            this.id = id;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
        }
    }
 */