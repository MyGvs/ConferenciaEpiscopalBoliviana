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
import android.widget.AdapterView;
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
public class nav_tipoObraFragment extends Fragment {
    public static String api_listar_tipo_obra = Globals.completeApiURL+"/tipo_de_obra/";
    public static String api_nuevo_tipo_de_obra = Globals.completeApiURL+"/nuevo_tipo_de_obra/";
    public static String api_actualizar_tipo_de_obra = Globals.completeApiURL+"/actualizar_tipo_de_obra/";
    public static String api_borrar_tipo_de_obra = Globals.completeApiURL+"/borrar_tipo_de_obra/";
    private View view;
    public ListView listView;
    ArrayList<String> ArrayListTipoObra = new ArrayList<>();
    LayoutInflater layoutInflater;

    public nav_tipoObraFragment() {
        // Required empty public constructor
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.nav_tipo_obra_fragment, container, false);
        layoutInflater = getActivity().getLayoutInflater();
        listView = (ListView) view.findViewById(R.id.listView);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayAlertDialog();
            }
        });
        actualizarListview();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //update
                String item = listView.getItemAtPosition(position).toString();
                displayAlertDialogUpdate(item);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //delete
                String item = listView.getItemAtPosition(position).toString();
                final String[] splt = item.split("\\.");
                //delete
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Desea eliminar el Tipo de Obra, "+splt[1]+"?");
                builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new tipoObraAPI(splt[0],"delete").execute();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();
                    }
                });
                builder.show();
                return true;
            }
        });
        return view;
    }
    public void actualizarListview(){
        ArrayListTipoObra.clear();
        listView.setAdapter(null);
        new tipoObraAPI("list").execute();
    }
    private void setAdaptertoListView() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, ArrayListTipoObra);
        listView.setAdapter(arrayAdapter);
    }
    private void displayAlertDialogUpdate(String item) {
        final String[] splt = item.split("\\.");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Modificar Tipo de Obra, "+splt[1]);
        View view= layoutInflater.inflate(R.layout.menu_tipo_obra, null);
        builder.setView(view);
        final EditText inputTipodeObra = (EditText)view.findViewById(R.id.inputTipodeObra);
        builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(!inputTipodeObra.getText().toString().equals("")){
                    new tipoObraAPI(splt[0],inputTipodeObra.getText().toString(), "0","update").execute();
                }else{
                    Toast.makeText(getActivity(),"Error: No hay texto.",Toast.LENGTH_SHORT).show();
                }
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
    private void displayAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Nuevo Tipo de Obra");
        View view= layoutInflater.inflate(R.layout.menu_tipo_obra, null);
        builder.setView(view);
        final EditText inputTipodeObra = (EditText)view.findViewById(R.id.inputTipodeObra);
        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(!inputTipodeObra.getText().toString().equals("")){
                    new tipoObraAPI(inputTipodeObra.getText().toString(),"0","new").execute();
                }else{
                    Toast.makeText(getActivity(),"Error: No hay texto.",Toast.LENGTH_SHORT).show();
                }
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
    /*
    * TIPO DE OBRA
    * */
    public class tipoObraAPI extends AsyncTask<String, String, String> {
        public String id;
        public String tipo;
        public String cargado;
        public String opcion;
        public tipoObraAPI(String tipo, String cargado, String opcion) {
            //new
            this.tipo = tipo;
            this.cargado = cargado;
            this.opcion = opcion;
        }
        public tipoObraAPI(String id, String tipo, String cargado, String opcion) {
            //update
            this.id = id;
            this.tipo = tipo;
            this.cargado = cargado;
            this.opcion = opcion;
        }
        public tipoObraAPI(String id, String opcion) {
            //delete
            this.id = id;
            this.opcion = opcion;
        }
        public tipoObraAPI(String opcion) {
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
                    String jsonStr = sh.makeServiceCall(api_listar_tipo_obra);
                    Log.e("getTipoDeObra ", "Response from url: " + jsonStr);
                    if (jsonStr != null) {
                        try {
                            // Getting JSON Array node
                            JSONArray json = new JSONArray(jsonStr);
                            // looping through All Contacts
                            for (int i = 0; i < json.length(); i++) {
                                JSONObject c = json.getJSONObject(i);
                                String id = c.getString("id");
                                String tipo = c.getString("tipo");
                                String cargado = c.getString("cargado");
                                ArrayListTipoObra.add(id+". "+tipo);
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
                    try {
                        url = new URL(api_nuevo_tipo_de_obra);

                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setReadTimeout(15000);
                        conn.setConnectTimeout(15000);
                        conn.setRequestMethod("POST");
                        conn.setDoInput(true);
                        conn.setDoOutput(true);

                        OutputStream os = conn.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(
                                new OutputStreamWriter(os, "UTF-8"));
                        HashMap<String, String> postDataParams = new HashMap<String, String>();
                        postDataParams.put("tipo",tipo);
                        writer.write(getPostDataString(postDataParams));
                        writer.flush();
                        writer.close();
                        os.close();
                        int responseCode=conn.getResponseCode();

                        if (responseCode == HttpsURLConnection.HTTP_OK) {
                            String line;
                            BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            while ((line=br.readLine()) != null) {
                                response+=line;
                            }
                        }
                        else {
                            response="";

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "update":
                    try {
                        url = new URL(api_actualizar_tipo_de_obra);

                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setReadTimeout(15000);
                        conn.setConnectTimeout(15000);
                        conn.setRequestMethod("POST");
                        conn.setDoInput(true);
                        conn.setDoOutput(true);

                        OutputStream os = conn.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(
                                new OutputStreamWriter(os, "UTF-8"));
                        HashMap<String, String> postDataParams = new HashMap<String, String>();
                        postDataParams.put("tipo",tipo);
                        postDataParams.put("id",id);
                        postDataParams.put("cargado",cargado);
                        writer.write(getPostDataString(postDataParams));
                        writer.flush();
                        writer.close();
                        os.close();
                        int responseCode=conn.getResponseCode();

                        if (responseCode == HttpsURLConnection.HTTP_OK) {
                            String line;
                            BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            while ((line=br.readLine()) != null) {
                                response+=line;
                            }
                        }
                        else {
                            response="";

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "delete":
                    try {
                        url = new URL(api_borrar_tipo_de_obra);

                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setReadTimeout(15000);
                        conn.setConnectTimeout(15000);
                        conn.setRequestMethod("POST");
                        conn.setDoInput(true);
                        conn.setDoOutput(true);

                        OutputStream os = conn.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(
                                new OutputStreamWriter(os, "UTF-8"));
                        HashMap<String, String> postDataParams = new HashMap<String, String>();
                        postDataParams.put("id",id);
                        writer.write(getPostDataString(postDataParams));
                        writer.flush();
                        writer.close();
                        os.close();
                        int responseCode=conn.getResponseCode();

                        if (responseCode == HttpsURLConnection.HTTP_OK) {
                            String line;
                            BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            while ((line=br.readLine()) != null) {
                                response+=line;
                            }
                        }
                        else {
                            response="";

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
