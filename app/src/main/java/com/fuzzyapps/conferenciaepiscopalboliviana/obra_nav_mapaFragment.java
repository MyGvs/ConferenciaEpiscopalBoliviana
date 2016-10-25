package com.fuzzyapps.conferenciaepiscopalboliviana;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class obra_nav_mapaFragment extends Fragment implements OnMapReadyCallback {
    public static String activeEvents = Globals.completeApiURL+"/obras_ubicacion_eventos_activos/";
    private ArrayList<clase_Evento> ArrayListEventos = new ArrayList<>();
    public Double obra_latitud, obra_longitud;
    public String obra_nombre, obra_direccion;
    private GoogleMap mMap;
    private View view;
    public obra_nav_mapaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.obra_nav_mapa_fragment, container, false);
        } catch (InflateException e) {
            /* map is already there, just return view as it is */
            //Toast.makeText(getActivity(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        SupportMapFragment mapFragment = (SupportMapFragment)  this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //get current date time with Date()
        Date date = new Date();
        //Toast.makeText(getActivity(), ""+dateFormat.format(date),Toast.LENGTH_SHORT).show();
        new ObraEventos(detalleObra._idObra,""+dateFormat.format(date)).execute();
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng laPaz = new LatLng(-16.499, -68.118);
        //mMap.addMarker(new MarkerOptions().position(laPaz).snippet("Reunión 15:00 - 17:00").title("Hay un Evento!"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(laPaz));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(laPaz.latitude, laPaz.longitude), 12.0f));
    }
    public class ObraEventos extends AsyncTask<String, String, String> {
        public String id;
        public String fecha;
        public ObraEventos(String id , String fecha) {
            //obtener perfi de encargado
            this.id = id;
            this.fecha = fecha;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ArrayListEventos.clear();
        }
        @Override
        protected String doInBackground(String... params) {
            String response = "";
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(activeEvents+detalleObra._idObra+","+fecha);
            Log.e("FuzzyApps", "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    // Getting JSON Array node
                    JSONArray json = new JSONArray(jsonStr);
                    // looping through All Contacts
                    for (int i = 0; i < json.length(); i++) {
                        JSONObject c = json.getJSONObject(i);
                        clase_Evento evento = new clase_Evento(
                                Double.parseDouble(c.getString("evento_latitud")),
                                Double.parseDouble(c.getString("evento_longitud")),
                                c.getString("nombre"),
                                c.getString("fecha_evento"),
                                c.getString("lugar"),
                                c.getString("hora_inicio"),
                                c.getString("hora_fin"));
                        obra_latitud = Double.parseDouble(c.getString("latitud"));
                        obra_longitud = Double.parseDouble(c.getString("longitud"));
                        obra_direccion = c.getString("direccion");
                        obra_nombre = c.getString("obra_nombre");
                        ArrayListEventos.add(evento);
                    }
                    response = "true";
                    if(ArrayListEventos.size()==0){
                        response = "";
                    }
                } catch (final JSONException e) {
                    Log.e("getDepartamentos ", "Json parsing error: " + e.getMessage());
                }
            } else {
                Log.e("getDepartamentos", "Couldn't get json from server.");
            }
            return response;
        }
        @Override
        protected void onPostExecute(String result) {
            if(result.equals("true")){
                addMarkersEvento();
                addMarkerObra();
            }
        }
    }

    private void addMarkerObra() {
        LatLng position = new LatLng(obra_latitud, obra_longitud);
        mMap.addMarker(new MarkerOptions().position(position)
                .title(obra_nombre)
                .snippet(obra_direccion));
    }

    private void addMarkersEvento() {
        for (int i=0; i<ArrayListEventos.size(); i++){
            if(i==0){
                //enfocar al primer evento
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(ArrayListEventos.get(i).latitud, ArrayListEventos.get(i).longtud), 17.0f));
            }
            LatLng position = new LatLng(ArrayListEventos.get(i).latitud, ArrayListEventos.get(i).longtud);
            mMap.addMarker(new MarkerOptions().position(position)
                    .title(ArrayListEventos.get(i).fecha+" - "+ArrayListEventos.get(i).nombre)
                    .snippet(ArrayListEventos.get(i).lugar+", Hora: "+ArrayListEventos.get(i).hora_inicio+" - "+ArrayListEventos.get(i).hora_fin)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        }
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
