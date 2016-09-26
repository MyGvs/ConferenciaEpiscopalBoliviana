package com.fuzzyapps.conferenciaepiscopalboliviana;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class obrasFragment extends Fragment {
    public View view;
    private Spinner filtro;
    private ArrayList<String> Obras = new ArrayList<>();
    public static String api_listar_obra = "http://"+Globals.ip+"/restfulApi/public/api/v1/obras/";
    public ListView listView;
    ArrayList<String> ArrayListObras = new ArrayList<>();
    public obrasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_obras, container, false);
        String[] arraySpinner = new String[] {
                "1. Parroquia",
                "2. Casa Religiosa",
                "3. Obra de Educación",
                "4. Obra de Salud",
                "5. Obra de Protección Social",
                "6. Casa de Encuentros",
                "7. Obra Administrativa",
                "8. Obra de Movilidad Humana",
                "9. Obra Productiva",
                "10. Obra Penitenciaria"
        };
        filtro = (Spinner) view.findViewById(R.id.filtro);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arraySpinner);
        filtro.setAdapter(adapter);
        listView = (ListView) view.findViewById(R.id.listView);
        actualizarListview();
        return view;
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
