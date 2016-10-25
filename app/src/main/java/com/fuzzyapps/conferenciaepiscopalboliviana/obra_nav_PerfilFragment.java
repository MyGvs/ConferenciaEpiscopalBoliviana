package com.fuzzyapps.conferenciaepiscopalboliviana;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
public class obra_nav_perfilFragment extends Fragment {
    private ScrollView scrollView;
    private CheckBox lunes,martes,miercoles,jueves,viernes,sabado,domingo;
    private TextView profileName, profileTipo, profileDireccion, profileTelefono, profileHorario, profileJurisdiccion;
    public ImageView profileImage;
    private String getPerfil= Globals.completeApiURL+"/obras_detalle/";
    private View view;
    private Picasso picasso;
    public obra_nav_perfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.obra_nav_perfil_fragment, container, false);
        picasso = Picasso.with(getContext());
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        scrollView.setVisibility(View.GONE);
        lunes = (CheckBox) view.findViewById(R.id.lunes);
        martes = (CheckBox) view.findViewById(R.id.martes);
        miercoles = (CheckBox) view.findViewById(R.id.miercoles);
        jueves = (CheckBox) view.findViewById(R.id.jueves);
        viernes = (CheckBox) view.findViewById(R.id.viernes);
        sabado = (CheckBox) view.findViewById(R.id.sabado);
        domingo = (CheckBox) view.findViewById(R.id.domingo);
        lunes.setEnabled(false);
        martes.setEnabled(false);
        miercoles.setEnabled(false);
        jueves.setEnabled(false);
        viernes.setEnabled(false);
        sabado.setEnabled(false);
        domingo.setEnabled(false);
        profileName = (TextView) view.findViewById(R.id.profileName);
        profileTipo = (TextView) view.findViewById(R.id.profileTipo);
        profileDireccion = (TextView) view.findViewById(R.id.profileDireccion);
        profileTelefono = (TextView) view.findViewById(R.id.profileTelefono);
        profileHorario = (TextView) view.findViewById(R.id.profileHorario);
        profileJurisdiccion = (TextView) view.findViewById(R.id.profileJurisdiccion);
        profileImage = (ImageView) view.findViewById(R.id.profileImage);
        new obraAPI(detalleObra._idObra).execute();
        return view;
    }
    public class obraAPI extends AsyncTask<String, String, String> {
        public String id;
        public String nombre, tipo, jurisdiccion, imagenUrl, direccion, telefono, horario;
        public int lunes,martes,miercoles,jueves,viernes,sabado,domingo;

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
                        nombre = c.getString("nombre");
                        jurisdiccion = c.getString("jurisdiccion");
                        tipo = c.getString("tipo");
                        imagenUrl = c.getString("imagen_url");
                        direccion = c.getString("direccion");
                        telefono = c.getString("telefono1") +" - "+ c.getString("telefono2");
                        horario = c.getString("horario_inicio") +" - "+ c.getString("horario_fin");
                        lunes = Integer.parseInt(c.getString("atencion_lunes"));
                        martes = Integer.parseInt(c.getString("atencion_martes"));
                        miercoles = Integer.parseInt(c.getString("atencion_miercoles"));
                        jueves = Integer.parseInt(c.getString("atencion_jueves"));
                        viernes = Integer.parseInt(c.getString("atencion_viernes"));
                        sabado = Integer.parseInt(c.getString("atencion_sabado"));
                        domingo = Integer.parseInt(c.getString("atencion_domingo"));
                    }
                    response = "true";
                } catch (final JSONException e) {
                    Log.e("JSON ERROR", "Json parsing error: " + e.getMessage());
                    //msg("Json parsing error: " + e.getMessage());
                }
            } else {
                Log.e("INTERNET", "Couldn't get json from server.");
            }
            return response;
        }
        @Override
        protected void onPostExecute(String result) {
            Log.e("FuzzyApps", "RESULT: " + result);
            //Log.e("FuzzyApps", "URL: " + imagenUrl);
            //Log.e("FuzzyApps", "URL COMPLETA: " + Globals.ip + "/software2_obras_iglesia/templates" + imagenUrl);
            if(result.equals("true")) {
                cargarDatos(nombre, jurisdiccion, tipo, imagenUrl, direccion, telefono, horario, lunes, martes, miercoles, jueves, viernes, sabado, domingo);
            }
        }
    }

    private void cargarDatos(String _nombre, String _jurisdiccion, String _tipo, String _imagenUrl, String _direccion,
                             String _telefono, String _horario, int _lunes, int _martes, int _miercoles,
                             int _jueves, int _viernes, int _sabado, int _domingo) {
        scrollView.setVisibility(View.VISIBLE);
        if(_lunes == 1){
            lunes.setChecked(true);
        }else{
            lunes.setChecked(false);
        }

        if(_martes == 1){
            martes.setChecked(true);
        }else{
            martes.setChecked(false);
        }

        if(_miercoles == 1){
            miercoles.setChecked(true);
        }else{
            miercoles.setChecked(false);
        }

        if(_jueves == 1){
            jueves.setChecked(true);
        }else{
            jueves.setChecked(false);
        }

        if(_viernes == 1){
            viernes.setChecked(true);
        }else{
            viernes.setChecked(false);
        }

        if(_sabado == 1){
            sabado.setChecked(true);
        }else{
            sabado.setChecked(false);
        }

        if(_domingo == 1){
            domingo.setChecked(true);
        }else{
            domingo.setChecked(false);
        }

        profileName.setText(_nombre);
        profileTipo.setText(_tipo);
        profileJurisdiccion.setText(_jurisdiccion);
        profileDireccion.setText(_direccion);
        profileTelefono.setText(_telefono);
        profileHorario.setText(_horario);
        picasso.cancelRequest(profileImage);
        picasso.load(Globals.completeImageURL + _imagenUrl)
                .noPlaceholder()
                .resizeDimen(R.dimen.encargado_profile_width, R.dimen.encargado_profile_height)
                .centerCrop()
                .into(profileImage);
    }
    @Override
    public void onResume() {
        super.onResume();
        if(Globals.status){
            setAllViews(Globals.usuario);
        }
    }
    private void setAllViews(String name){
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Bienvenid@, "+name);
    }
}
