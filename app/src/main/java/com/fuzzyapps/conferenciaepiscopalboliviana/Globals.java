package com.fuzzyapps.conferenciaepiscopalboliviana;

/**
 * Created by Geovani on 25/09/2016.
 */

public class Globals {
    //dirección de la web
    //public static String ip = "http://192.168.1.5:8080";
    public static String ip = "http://software2iglesia.16mb.com";
    //public static String bodyURL = "/software2_obras_iglesia";
    public static String bodyURL = "";
    public static String bodyURLImages = "/";
    public static String completeImageURL = ip+bodyURL+bodyURLImages;
    public static String completeImageURLNOBODY = ip+bodyURL;
    public static String completeApiURL = ip+bodyURL+"/restfulApi/public/api/v1";
    //true: logueado, false: no logueado :v
    public static boolean status = false;
    public static int idObra;
    public static String idUsuario;
    public static String usuario;
    public static String nombres;
    public static String email;
    public static String apellido_paterno;
    public static String apellido_materno;
    public static String tipo_usuario;
    public static String imagen_url;
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