package com.fuzzyapps.conferenciaepiscopalboliviana;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


/**
 * A simple {@link Fragment} subclass.
 */
public class nav_obrasFragment extends Fragment {
    public static String api_listar_obra = "http://"+Globals.ip+"/restfulApi/public/api/v1/obras/";
    private View view;
    public ListView listView;
    ArrayList<String> ArrayListObras = new ArrayList<>();
    LayoutInflater layoutInflater;
    public nav_obrasFragment() {
        // Required empty public constructor
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.nav_obras_fragment, container, false);
        layoutInflater = getActivity().getLayoutInflater();
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        listView = (ListView) view.findViewById(R.id.listView);
        actualizarListview();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayAlertDialog();
            }
        });
        return view;
    }
    private void displayAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Nueva Obra");
        View view= layoutInflater.inflate(R.layout.menu_obra, null);
        builder.setView(view);
        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getActivity(),"Nueva Obra.",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                dialog.cancel();
            }
        });
        builder.show();
    }
    public void actualizarListview(){
        ArrayListObras.clear();
        listView.setAdapter(null);
        new obraAPI("list").execute();
    }
    private void setAdaptertoListView() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, ArrayListObras);
        listView.setAdapter(arrayAdapter);
    }
    /*
    * OBRAS
    * */
    public class obraAPI extends AsyncTask<String, String, String> {
        public String id;
        public String tipo;
        public String cargado;
        public String opcion;
        public obraAPI(String tipo, String cargado, String opcion) {
            //new
            this.tipo = tipo;
            this.cargado = cargado;
            this.opcion = opcion;
        }
        public obraAPI(String id, String tipo, String cargado, String opcion) {
            //update
            this.id = id;
            this.tipo = tipo;
            this.cargado = cargado;
            this.opcion = opcion;
        }
        public obraAPI(String id, String opcion) {
            //delete
            this.id = id;
            this.opcion = opcion;
        }
        public obraAPI(String opcion) {
            //list
            this.opcion = opcion;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... p) {
            URL url;
            String response = "";
            switch (opcion){
                case "list":
                    HttpHandler sh = new HttpHandler();
                    // Making a request to url and getting response
                    String jsonStr = sh.makeServiceCall(api_listar_obra);
                    Log.e("getTipoDeObra ", "Response from url: " + jsonStr);
                    if (jsonStr != null) {
                        try {
                            // Getting JSON Array node
                            JSONArray json = new JSONArray(jsonStr);
                            // looping through All Contacts
                            for (int i = 0; i < json.length(); i++) {
                                JSONObject c = json.getJSONObject(i);
                                String id = c.getString("id");
                                String nombre = c.getString("nombre");
                                String horaini = c.getString("horario_inicio");
                                String horafin = c.getString("horario_fin");
                                String poblacionneta = c.getString("poblacion_neta");
                                String posx = c.getString("pos_x");
                                String posy = c.getString("pos_y");
                                String fechaentrega = c.getString("fecha_entrega");
                                String imagenurl = c.getString("imagen_url");

                                ArrayListObras.add(
                                                id+". "+nombre
                                                +"\n Horario de Atención"
                                                +"\n "+horaini+" - "+horafin
                                                +"\n Población"
                                                +"\n "+poblacionneta
                                                +"\n Datos Geográficos"
                                                +"\n "+posx +" , "+posy
                                                +"\n Fecha de Entrega"
                                                +"\n "+fechaentrega
                                                +"\n Imagen"
                                                +"\n "+imagenurl
                                                +"\n ");
                            }
                        } catch (final JSONException e) {
                            Log.e("getDepartamentos ", "Json parsing error: " + e.getMessage());
                            //msg("Json parsing error: " + e.getMessage());
                        }
                    } else {
                        Log.e("getDepartamentos", "Couldn't get json from server.");
                    }
                    break;
                case "new":
                    break;
                case "update":
                    break;
                case "delete":
                    break;
                default:
                    break;
            }
            return response;
        }
        @Override
        protected void onPostExecute(String result) {
            //Update the UI
            switch (opcion){
                case "list":
                    setAdaptertoListView();
                    break;
                case "new":
                    actualizarListview();
                    break;
                case "update":
                    actualizarListview();
                    break;
                case "delete":
                    actualizarListview();
                    break;
            }
        }
    }
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
