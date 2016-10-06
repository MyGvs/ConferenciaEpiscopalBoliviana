package com.fuzzyapps.conferenciaepiscopalboliviana;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class obra_nav_encargadoFragment extends Fragment {
    private ScrollView scrollView;
    private View view;
    private TextView profileName, profileDireccion, profileTelefono, profileBiografia;
    public ImageView profileImage;
    private Picasso picasso;
    private String getPerfil= Globals.completeApiURL+"/obras_obispo/";
    public obra_nav_encargadoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.obra_nav_encargado_fragment, container, false);
        picasso = Picasso.with(getContext());
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        scrollView.setVisibility(View.GONE);
        profileName = (TextView) view.findViewById(R.id.profileName);
        profileDireccion = (TextView) view.findViewById(R.id.profileDireccion);
        profileTelefono = (TextView) view.findViewById(R.id.profileTelefono);
        profileBiografia = (TextView) view.findViewById(R.id.profileBiografia);
        profileImage = (ImageView) view.findViewById(R.id.profileImage);
        new obraAPI(detalleObra._idObra).execute();
        return view;
    }
    public class obraAPI extends AsyncTask<String, String, String> {
        public String id;
        public String imagenUrl, direccion, telefono, biografia, apellido_materno, apellido_paterno, nombres;

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
            String response = "";
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(getPerfil+id);
            Log.e("FuzzyApps", "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    // Getting JSON Array node
                    JSONArray json = new JSONArray(jsonStr);
                    // looping through All Contacts
                    for (int i = 0; i < json.length(); i++) {
                        JSONObject c = json.getJSONObject(i);
                        apellido_materno = c.getString("apellido_materno");
                        apellido_paterno = c.getString("apellido_paterno");
                        nombres = c.getString("nombres");
                        biografia = c.getString("biografia");
                        telefono = c.getString("telefono");
                        direccion = c.getString("direccion");
                        imagenUrl = c.getString("imagen_url");
                    }
                    response = "true";
                } catch (final JSONException e) {
                    Log.e("getDepartamentos ", "Json parsing error: " + e.getMessage());
                    //msg("Json parsing error: " + e.getMessage());
                }
            } else {
                Log.e("getDepartamentos", "Couldn't get json from server.");
            }
            return response;
        }
        @Override
        protected void onPostExecute(String result) {
            Log.e("FuzzyApps", "RESULT: " + result);
            if(result.equals("true")) {
                cargarDatos(nombres, apellido_materno, apellido_paterno, direccion, telefono, biografia, imagenUrl);
            }
        }
    }

    private void cargarDatos(String nombres, String apellido_materno, String apellido_paterno, String direccion, String telefono, String biografia, String imagenUrl) {
        scrollView.setVisibility(View.VISIBLE);
        profileName.setText(nombres + " " + apellido_paterno + " " + apellido_materno);
        profileDireccion.setText(direccion);
        profileTelefono.setText(telefono);
        profileBiografia.setText(biografia);
        picasso.cancelRequest(profileImage);
        picasso.load(Globals.completeImageURL + imagenUrl)
                .noPlaceholder()
                .resizeDimen(R.dimen.encargado_profile_width, R.dimen.encargado_profile_height)
                .centerCrop()
                .into(profileImage);
    }
}
