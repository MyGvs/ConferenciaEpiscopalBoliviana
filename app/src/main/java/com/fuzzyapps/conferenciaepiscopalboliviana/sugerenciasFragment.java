package com.fuzzyapps.conferenciaepiscopalboliviana;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class sugerenciasFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    public View view;
    LayoutInflater layoutInflater;

    public sugerenciasFragment() {
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
            view = inflater.inflate(R.layout.fragment_sugerencias, container, false);
        } catch (InflateException e) {
            /* map is already there, just return view as it is */
            //Toast.makeText(getActivity(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        layoutInflater = getActivity().getLayoutInflater();
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayAlertDialog();
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment)  this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng laPaz = new LatLng(-16.499, -68.118);
        mMap.addMarker(new MarkerOptions().position(laPaz).snippet("Reunión 15:00 - 17:00").title("Hay un Evento!"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(laPaz));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(laPaz.latitude, laPaz.longitude), 12.0f));
    }
    private void displayAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Nueva Sugerencia de Obra");
        View view= layoutInflater.inflate(R.layout.menu_sugerencia, null);
        builder.setView(view);
        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getActivity(),"Nueva Sugerencia.",Toast.LENGTH_SHORT).show();
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
    @Override
    public void onResume(){
        super.onResume();
        try{
            SharedPreferences preferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        }catch (Exception e){
            Toast.makeText(getContext(), ""+e, Toast.LENGTH_SHORT).show();
        }
        if(Globals.status){
            setAllViews(Globals.usuario);
        }
    }
    private void setAllViews(String name){
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Bienvenid@, "+name);
    }
}
