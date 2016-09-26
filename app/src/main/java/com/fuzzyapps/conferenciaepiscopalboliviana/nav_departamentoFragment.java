package com.fuzzyapps.conferenciaepiscopalboliviana;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.http.BasicNameValuePair;
import com.koushikdutta.async.http.NameValuePair;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * A simple {@link Fragment} subclass.
 */
public class nav_departamentoFragment extends Fragment {
    public static String url = "http://"+Globals.ip+"/restfulApi/public/api/v1/departamentos/";
    public static String new_departamento = "http://"+Globals.ip+"/restfulApi/public/api/v1/nuevo_departamento/";
    public static String update_departamento = "http://"+Globals.ip+"/restfulApi/public/api/v1/actualizar_departamento/";
    public static String delete_departamento = "http://"+Globals.ip+"/restfulApi/public/api/v1/borrar_departamento/";
    private View view;
    private ListView listView;
    ArrayList<String> departamentos = new ArrayList<>();
    LayoutInflater layoutInflater;

    public nav_departamentoFragment() {
        // Required empty public constructor
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.nav_departamento_fragment, container, false);
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
                String item = listView.getItemAtPosition(position).toString();
                final String[] splt = item.split("\\.");
                //delete
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Desea eliminar el Departamento, "+splt[1]+"?");
                builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new updateDepartamento(splt[0],"delete").execute();
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
        departamentos.clear();
        listView.setAdapter(null);
        new getDepartamentos().execute();
    }
    private void setAdaptertoListView() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, departamentos);
        listView.setAdapter(arrayAdapter);
    }
    private void displayAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Nuevo Departamento");
        View view= layoutInflater.inflate(R.layout.menu_departamento, null);
        builder.setView(view);
        final EditText inputDepartamento = (EditText)view.findViewById(R.id.inputDepartamento);
        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(!inputDepartamento.getText().toString().equals("")){
                    //Toast.makeText(getActivity(),""+inputDepartamento.getText().toString(),Toast.LENGTH_SHORT).show();
                    new CallAPI(inputDepartamento.getText().toString()).execute();
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
    private void displayAlertDialogUpdate(String item) {
        final String[] splt = item.split("\\.");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Modificar departamento, "+splt[1]);
        View view= layoutInflater.inflate(R.layout.menu_departamento, null);
        builder.setView(view);
        final EditText inputDepartamento = (EditText)view.findViewById(R.id.inputDepartamento);
        builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(!inputDepartamento.getText().toString().equals("")){
                    new updateDepartamento(splt[0],inputDepartamento.getText().toString(), "0","update").execute();
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
    /**
     * Async task class to get json by making HTTP call
     */
    private class getDepartamentos extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);
            Log.e("getDepartamentos ", "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    //JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray json = new JSONArray(jsonStr);

                    // looping through All Contacts
                    for (int i = 0; i < json.length(); i++) {
                        JSONObject c = json.getJSONObject(i);

                        String id = c.getString("id");
                        String departamento = c.getString("departamento");
                        String cargado = c.getString("cargado");

                        departamentos.add(id+". "+departamento);
                    }
                } catch (final JSONException e) {
                    Log.e("getDepartamentos ", "Json parsing error: " + e.getMessage());
                    //msg("Json parsing error: " + e.getMessage());
                }
            } else {
                Log.e("getDepartamentos", "Couldn't get json from server.");
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            setAdaptertoListView();
        }

    }
    /*
    * POST NEW DEPARTAMENTO
    * */
    public class CallAPI extends AsyncTask<String, String, String> {
        public String nuevoDepartamento;
        public CallAPI(String nuevoDepartamento) {
            //set context variables if required
            this.nuevoDepartamento = nuevoDepartamento;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... p) {

            URL url;
            String response = "";
            try {
                url = new URL(new_departamento);

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
                postDataParams.put("departamento",nuevoDepartamento);
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

            return response;
        }
        @Override
        protected void onPostExecute(String result) {
            //Update the UI
            actualizarListview();
        }
    }
    /*
    * UPDATE DEPARTAMENTO
    * */
    public class updateDepartamento extends AsyncTask<String, String, String> {
        public String id;
        public String departamento;
        public String cargado;
        public String opcion;
        public updateDepartamento(String id, String departamento, String cargado, String opcion) {
            //set context variables if required
            this.id = id;
            this.departamento = departamento;
            this.cargado = cargado;
            this.opcion = opcion;
        }
        public updateDepartamento(String id, String opcion) {
            //set context variables if required
            this.id = id;
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
                case "update":
                    try {
                        url = new URL(update_departamento);

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
                        postDataParams.put("departamento",departamento);
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
                        url = new URL(delete_departamento);

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
            actualizarListview();
        }
    }
}
