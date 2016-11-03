package com.fuzzyapps.conferenciaepiscopalboliviana;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import static android.content.Context.LOCATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class nav_obrasFragment extends Fragment {
    private Obra obraActualizar;
    public static String api_listar_obra = Globals.completeApiURL+"/mis_obras/";
    public static String api_agregar_obra = Globals.completeApiURL+"/agregar_obra/";
    public static String api_actualizar_obra = Globals.completeApiURL+"/actualizar_obra/";
    public static String lista_jurisddiones = Globals.completeApiURL+"/jurisdiccion/";
    public static String lista_departamento = Globals.completeApiURL+"/departamentos/";
    public static String lista_tipo_obra = Globals.completeApiURL+"/tipo_de_obra/";
    public static String api_obra = Globals.completeApiURL+"/obra/";
    //Error Text View
    private TextView errorIinputObraNombre, errorHoraInicio, errorHoraFin, errorInputPoblacionNeta, errorTextviewLatLong,
            errorInputFechaEntrega, errorTelefono1, errorFax, errorMunicipio, errorZonaUrbana,
            errorPaginaWeb, errorProvincia, errorCasilla, errorDireccion;
    private TextView textviewLatLong;
    private Button horaInicio, horaFin, inputFechaEntrega, addObra, posicionButton;
    private EditText inputObraNombre, inputPoblacionNeta, telefono1, telefono2, fax,
            municipio, zonaUrbana, paginaWeb, provincia, casilla, direccion;
    private CheckBox lunes, martes, miercoles, jueves, viernes, sabado, domingo;
    private Spinner spinnerDep, spinnerJur, spinnerTip;
    private View view;
    public ListView listView;
    ArrayList<String> ArrayListObras = new ArrayList<>();
    ArrayList<String> ArrayListDepartamentos = new ArrayList<>();
    ArrayList<String> ArrayListTipoObra = new ArrayList<>();
    ArrayList<String> ArrayListJurisdiccion = new ArrayList<>();
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
        final boolean[] horai = {false};
        final boolean[] horaf = {false};
        final boolean[] fechaentrega = {false};
        final boolean pos=true;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Nueva Obra");
        View view2 = layoutInflater.inflate(R.layout.menu_obra, null);
        builder.setView(view2);
        spinnerDep = (Spinner) view2.findViewById(R.id.spinnerDep);
        spinnerJur = (Spinner) view2.findViewById(R.id.spinnerJur);
        spinnerTip = (Spinner) view2.findViewById(R.id.spinnerTip);

        errorIinputObraNombre = (TextView) view2.findViewById(R.id.errorIinputObraNombre);
        errorHoraInicio = (TextView) view2.findViewById(R.id.errorHoraInicio);
        errorHoraFin = (TextView) view2.findViewById(R.id.errorHoraFin);
        errorInputPoblacionNeta = (TextView) view2.findViewById(R.id.errorInputPoblacionNeta);
        errorTextviewLatLong = (TextView) view2.findViewById(R.id.errorTextviewLatLong);
        errorInputFechaEntrega = (TextView) view2.findViewById(R.id.errorInputFechaEntrega);
        errorTelefono1 = (TextView) view2.findViewById(R.id.errorTelefono1);
        errorFax = (TextView) view2.findViewById(R.id.errorFax);
        errorMunicipio = (TextView) view2.findViewById(R.id.errorMunicipio);
        errorZonaUrbana = (TextView) view2.findViewById(R.id.errorZonaUrbana);
        errorPaginaWeb = (TextView) view2.findViewById(R.id.errorPaginaWeb);
        errorProvincia = (TextView) view2.findViewById(R.id.errorProvincia);
        errorCasilla = (TextView) view2.findViewById(R.id.errorCasilla);
        errorDireccion = (TextView) view2.findViewById(R.id.errorDireccion);

        textviewLatLong = (TextView) view2.findViewById(R.id.textviewLatLong);

        horaInicio = (Button) view2.findViewById(R.id.horaInicio);
        horaFin = (Button) view2.findViewById(R.id.horaFin);
        inputFechaEntrega = (Button) view2.findViewById(R.id.inputFechaEntrega);
        addObra = (Button) view2.findViewById(R.id.addObra);
        posicionButton = (Button) view2.findViewById(R.id.posicionButton);

        lunes = (CheckBox) view2.findViewById(R.id.lunes);
        martes = (CheckBox) view2.findViewById(R.id.martes);
        miercoles = (CheckBox) view2.findViewById(R.id.miercoles);
        jueves = (CheckBox) view2.findViewById(R.id.jueves);
        viernes = (CheckBox) view2.findViewById(R.id.viernes);
        sabado = (CheckBox) view2.findViewById(R.id.sabado);
        domingo = (CheckBox) view2.findViewById(R.id.domingo);

        inputObraNombre = (EditText) view2.findViewById(R.id.inputObraNombre);
        inputPoblacionNeta = (EditText) view2.findViewById(R.id.inputPoblacionNeta);
        telefono1 = (EditText) view2.findViewById(R.id.telefono1);
        // opcional
        telefono2 = (EditText) view2.findViewById(R.id.telefono2);
        // opcional
        fax = (EditText) view2.findViewById(R.id.fax);
        municipio = (EditText) view2.findViewById(R.id.municipio);
        zonaUrbana = (EditText) view2.findViewById(R.id.zonaUrbana);
        // opcional
        paginaWeb = (EditText) view2.findViewById(R.id.paginaWeb);
        provincia = (EditText) view2.findViewById(R.id.provincia);
        casilla = (EditText) view2.findViewById(R.id.casilla);
        direccion = (EditText) view2.findViewById(R.id.direccion);
        actualizarSpinnerDepartamento();
        actualizarSpinnerJurisdiccion();
        actualizarSpinnerTipoObra();
        posicionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        horaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        horaInicio.setText( selectedHour + ":" + selectedMinute+":00");
                        horai[0] = true;
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Selecciona la hora inicial de atención");
                mTimePicker.show();
            }
        });
        horaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        horaFin.setText( selectedHour + ":" + selectedMinute+":00");
                        horaf[0] = true;
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Selecciona la hora final de atención");
                mTimePicker.show();
            }
        });
        inputFechaEntrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);
                int month = mcurrentTime.get(Calendar.MONTH);
                int year = mcurrentTime.get(Calendar.YEAR);
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        inputFechaEntrega.setText( year + "-" + (month+1) + "-" + dayOfMonth);
                        fechaentrega[0] = true;
                    }
                }, year, month, day);
                mDatePicker.setTitle("Fecha de entrega de la obra");
                mDatePicker.show();
            }
        });
        final AlertDialog alert = builder.create();
        addObra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!inputObraNombre.getText().toString().equals("") && !inputPoblacionNeta.getText().toString().equals("") && !telefono1.getText().toString().equals("")
                        && !municipio.getText().toString().equals("") && !zonaUrbana.getText().toString().equals("")
                        && !provincia.getText().toString().equals("") && !casilla.getText().toString().equals("") && !direccion.getText().toString().equals("")
                        && horai[0] && horaf[0] && fechaentrega[0] && pos){
                    Calendar mcurrentTime = Calendar.getInstance();
                    int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);
                    int month = mcurrentTime.get(Calendar.MONTH);
                    int year = mcurrentTime.get(Calendar.YEAR);
                    int lunes_=0,martes_=0,miercoles_=0,jueves_=0,viernes_=0,sabado_=0,domingo_=0;
                    if(lunes.isChecked()){
                        lunes_ = 1;
                    }
                    if(martes.isChecked()){
                        martes_ = 1;
                    }
                    if(miercoles.isChecked()){
                        miercoles_ = 1;
                    }
                    if(jueves.isChecked()){
                        jueves_ = 1;
                    }
                    if(viernes.isChecked()){
                        viernes_ = 1;
                    }
                    if(sabado.isChecked()){
                        sabado_ = 1;
                    }
                    if(domingo.isChecked()){
                        domingo_ = 1;
                    }
                    Obra nuevaObra = new Obra(
                            inputObraNombre.getText().toString(),
                            horaInicio.getText().toString(),
                            horaFin.getText().toString(),
                            inputPoblacionNeta.getText().toString(),
                            "19.55",
                            "17.55",
                            inputFechaEntrega.getText().toString(),
                            (spinnerTip.getSelectedItemPosition()+1)+"",
                            (spinnerJur.getSelectedItemPosition()+1)+"",
                            Globals.idUsuario+"",
                            (spinnerDep.getSelectedItemPosition()+1)+"",
                            telefono1.getText().toString(),
                            telefono2.getText().toString(),
                            fax.getText().toString(),
                            municipio.getText().toString(),
                            zonaUrbana.getText().toString(),
                            paginaWeb.getText().toString(),
                            provincia.getText().toString(),
                            casilla.getText().toString(),
                            direccion.getText().toString(),
                            lunes_,
                            martes_,
                            miercoles_,
                            jueves_,
                            viernes_,
                            sabado_,
                            domingo_,
                            year+"-"+(month+1)+"-"+day
                    );
                    new obraAPI("new",nuevaObra).execute();
                    alert.cancel();
                }else{
                    if(inputObraNombre.getText().toString().equals("")){
                        errorIinputObraNombre.setText("*Este campo es obligatorio");
                        errorIinputObraNombre.setVisibility(View.VISIBLE);
                    }else{
                        errorIinputObraNombre.setVisibility(View.GONE);
                    }
                    if(inputPoblacionNeta.getText().toString().equals("")){
                        errorInputPoblacionNeta.setText("*Este campo es obligatorio");
                        errorInputPoblacionNeta.setVisibility(View.VISIBLE);
                    }else{
                        errorInputPoblacionNeta.setVisibility(View.GONE);
                    }
                    if(telefono1.getText().toString().equals("")){
                        errorTelefono1.setText("*Este campo es obligatorio");
                        errorTelefono1.setVisibility(View.VISIBLE);
                    }else{
                        errorTelefono1.setVisibility(View.GONE);
                    }
                    if(municipio.getText().toString().equals("")){
                        errorMunicipio.setText("*Este campo es obligatorio");
                        errorMunicipio.setVisibility(View.VISIBLE);
                    }else{
                        errorMunicipio.setVisibility(View.GONE);
                    }
                    if(zonaUrbana.getText().toString().equals("")){
                        errorZonaUrbana.setText("*Este campo es obligatorio");
                        errorZonaUrbana.setVisibility(View.VISIBLE);
                    }else{
                        errorZonaUrbana.setVisibility(View.GONE);
                    }
                    if(provincia.getText().toString().equals("")){
                        errorProvincia.setText("*Este campo es obligatorio");
                        errorProvincia.setVisibility(View.VISIBLE);
                    }else{
                        errorProvincia.setVisibility(View.GONE);
                    }
                    if(casilla.getText().toString().equals("")){
                        errorCasilla.setText("*Este campo es obligatorio");
                        errorCasilla.setVisibility(View.VISIBLE);
                    }else{
                        errorCasilla.setVisibility(View.GONE);
                    }
                    if(direccion.getText().toString().equals("")){
                        errorDireccion.setText("*Este campo es obligatorio");
                        errorDireccion.setVisibility(View.VISIBLE);
                    }else{
                        errorDireccion.setVisibility(View.GONE);
                    }
                    if(!horai[0]){
                        errorHoraInicio.setText("*Selecciona la hora inicial");
                        errorHoraInicio.setVisibility(View.VISIBLE);
                    }else{
                       errorHoraInicio.setVisibility(View.GONE);
                    }
                    if(!horaf[0]){
                        errorHoraFin.setText("*Selecciona la hora final");
                        errorHoraFin.setVisibility(View.VISIBLE);
                    }else{
                        errorHoraFin.setVisibility(View.GONE);
                    }
                    if(!fechaentrega[0]){
                        errorInputFechaEntrega.setText("*Selecciona una Fecha");
                        errorInputFechaEntrega.setVisibility(View.VISIBLE);
                    }else{
                        errorInputFechaEntrega.setVisibility(View.GONE);
                    }
                    if(!pos){
                        errorTextviewLatLong.setText("*Falta obtener la posición del GPS (activar el GPS)");
                        errorTextviewLatLong.setVisibility(View.VISIBLE);
                    }else{
                        errorTextviewLatLong.setVisibility(View.GONE);
                    }
                }
            }
        });
        alert.show();
    }
    public void actualizarSpinnerDepartamento(){
        ArrayListDepartamentos.clear();
        spinnerDep.setAdapter(null);
        new djtAPI("departamento").execute();
    }
    public void actualizarSpinnerJurisdiccion(){
        ArrayListJurisdiccion.clear();
        spinnerJur.setAdapter(null);
        new djtAPI("jurisdiccion").execute();
    }
    public void actualizarSpinnerTipoObra(){
        ArrayListTipoObra.clear();
        spinnerTip.setAdapter(null);
        new djtAPI("tipo").execute();
    }
    private void setAdapterSpinnerDepartamento() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, ArrayListDepartamentos);
        spinnerDep.setAdapter(arrayAdapter);
    }
    private void setAdapterSpinnerJurisdiccion() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, ArrayListJurisdiccion);
        spinnerJur.setAdapter(arrayAdapter);
    }
    private void setAdapterSpinnerTipoObra() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, ArrayListTipoObra);
        spinnerTip.setAdapter(arrayAdapter);
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
        public Obra obra;
        public obraAPI(String opcion, Obra obra) {
            //new
            this.opcion = opcion;
            this.obra = obra;
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
            Log.e("opcion",opcion);
            switch (opcion){
                case "list":
                    HttpHandler sh = new HttpHandler();
                    // Making a request to url and getting response
                    //mis obras
                    String jsonStr = sh.makeServiceCall(api_listar_obra+Globals.idUsuario);
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
                                String fechaentrega = c.getString("fecha_entrega");
                                String jurisdiccion = c.getString("jurisdiccion");
                                String tipo = c.getString("tipo");
                                String departamento = c.getString("departamento");

                                ArrayListObras.add(
                                                id+". "+nombre
                                                +"\n Horario de Atención:"
                                                +"\n "+horaini+" - "+horafin
                                                +"\n Población:"
                                                +"\n "+poblacionneta
                                                +"\n Fecha de Entrega:"
                                                +"\n "+fechaentrega
                                                +"\n Jurisdicción:"
                                                +"\n "+jurisdiccion
                                                +"\n tipo:"
                                                +"\n "+tipo
                                                +"\n Departamento:"
                                                +"\n "+departamento
                                                );
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
                        url = new URL(api_agregar_obra);
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
                        postDataParams.put("nombre", obra.getNombre());
                        postDataParams.put("horario_inicio", obra.getHorario_inicio());
                        postDataParams.put("horario_fin", obra.getHorario_fin());
                        postDataParams.put("poblacion_neta", obra.getPoblacion_neta());
                        postDataParams.put("longitud", obra.getLongitud());
                        postDataParams.put("latitud", obra.getLatitud());
                        postDataParams.put("fecha_entrega", obra.getFecha_entrega());

                        postDataParams.put("obras_tipo_obra_id", obra.getObras_tipo_obra_id());
                        postDataParams.put("obras_jurisdiccion_id", obra.getObras_jurisdiccion_id());
                        postDataParams.put("obras_usuarios_id", obra.getObras_usuarios_id());
                        postDataParams.put("obras_departamento_id", obra.getObras_departamento_id());

                        postDataParams.put("telefono1", obra.getTelefono1());
                        postDataParams.put("telefono2", obra.getTelefono2());
                        postDataParams.put("fax", obra.getFax());
                        postDataParams.put("municipio", obra.getMunicipio());
                        postDataParams.put("comunidad_zona_urb", obra.getComunidad_zona_urb());

                        postDataParams.put("pagina_web", obra.getPagina_web());
                        postDataParams.put("provincia", obra.getProvincia());
                        postDataParams.put("casilla", obra.getCasilla());
                        postDataParams.put("direccion", obra.getDireccion());
                        postDataParams.put("atencion_lunes", ""+obra.getAtencion_lunes());
                        postDataParams.put("atencion_martes", ""+obra.getAtencion_martes());
                        postDataParams.put("atencion_miercoles", ""+obra.getAtencion_miercoles());
                        postDataParams.put("atencion_jueves", ""+obra.getAtencion_jueves());
                        postDataParams.put("atencion_viernes", ""+obra.getAtencion_viernes());
                        postDataParams.put("atencion_sabado", ""+obra.getAtencion_sabado());
                        postDataParams.put("atencion_domingo", ""+obra.getAtencion_domingo());
                        postDataParams.put("fecha_registro", obra.getFecha_registro());

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
                        url = new URL(api_actualizar_obra);
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
                        postDataParams.put("id", ""+obra.getId());
                        postDataParams.put("nombre", obra.getNombre());
                        postDataParams.put("horario_inicio", obra.getHorario_inicio());
                        postDataParams.put("horario_fin", obra.getHorario_fin());
                        postDataParams.put("poblacion_neta", obra.getPoblacion_neta());
                        postDataParams.put("longitud", obra.getLongitud());
                        postDataParams.put("latitud", obra.getLatitud());
                        postDataParams.put("fecha_entrega", obra.getFecha_entrega());

                        postDataParams.put("obras_tipo_obra_id", obra.getObras_tipo_obra_id());
                        postDataParams.put("obras_jurisdiccion_id", obra.getObras_jurisdiccion_id());
                        postDataParams.put("obras_usuarios_id", obra.getObras_usuarios_id());
                        postDataParams.put("obras_departamento_id", obra.getObras_departamento_id());

                        postDataParams.put("telefono1", obra.getTelefono1());
                        postDataParams.put("telefono2", obra.getTelefono2());
                        postDataParams.put("fax", obra.getFax());
                        postDataParams.put("municipio", obra.getMunicipio());
                        postDataParams.put("comunidad_zona_urb", obra.getComunidad_zona_urb());

                        postDataParams.put("pagina_web", obra.getPagina_web());
                        postDataParams.put("provincia", obra.getProvincia());
                        postDataParams.put("casilla", obra.getCasilla());
                        postDataParams.put("direccion", obra.getDireccion());
                        postDataParams.put("atencion_lunes", ""+obra.getAtencion_lunes());
                        postDataParams.put("atencion_martes", ""+obra.getAtencion_martes());
                        postDataParams.put("atencion_miercoles", ""+obra.getAtencion_miercoles());
                        postDataParams.put("atencion_jueves", ""+obra.getAtencion_jueves());
                        postDataParams.put("atencion_viernes", ""+obra.getAtencion_viernes());
                        postDataParams.put("atencion_sabado", ""+obra.getAtencion_sabado());
                        postDataParams.put("atencion_domingo", ""+obra.getAtencion_domingo());
                        postDataParams.put("fecha_registro", obra.getFecha_registro());

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
    public class djtAPI extends AsyncTask<String, String, String> {
        public String opcion;
        public djtAPI(String opcion) {
            //list
            this.opcion = opcion;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            switch (opcion){
                case "tipo":
                    ArrayListTipoObra.clear();
                    break;
                case "departamento":
                    ArrayListDepartamentos.clear();
                    break;
                case "jurisdiccion":
                    ArrayListJurisdiccion.clear();
                    break;
                case "get":
                    break;
            }
        }
        @Override
        protected String doInBackground(String... params) {
            URL url;
            String response = "";
            HttpHandler sh = new HttpHandler();
            String jsonStr = "";
            switch (opcion){
                case "tipo":
                    // Making a request to url and getting response
                    jsonStr = sh.makeServiceCall(lista_tipo_obra);
                    Log.e("Tipo de Obra ", "Response from url: " + jsonStr);
                    if (jsonStr != null) {
                        try {
                            // Getting JSON Array node
                            JSONArray json = new JSONArray(jsonStr);
                            // looping through All Contacts
                            for (int i = 0; i < json.length(); i++) {
                                JSONObject c = json.getJSONObject(i);
                                String id = c.getString("id");
                                String tipo = c.getString("tipo");
                                ArrayListTipoObra.add(id+". "+tipo);
                            }
                        } catch (final JSONException e) {
                            Log.e("Tipo de Obra ", "Json parsing error: " + e.getMessage());
                        }
                    } else {
                        Log.e("Tipo de Obra ", "Couldn't get json from server.");
                    }
                    break;
                case "departamento":
                    jsonStr = sh.makeServiceCall(lista_departamento);
                    Log.e("Departamentos ", "Response from url: " + jsonStr);
                    if (jsonStr != null) {
                        try {
                            // Getting JSON Array node
                            JSONArray json = new JSONArray(jsonStr);
                            // looping through All Contacts
                            for (int i = 0; i < json.length(); i++) {
                                JSONObject c = json.getJSONObject(i);
                                String id = c.getString("id");
                                String departamento = c.getString("departamento");
                                ArrayListDepartamentos.add(id+". "+departamento);
                            }
                        } catch (final JSONException e) {
                            Log.e("Departamentos ", "Json parsing error: " + e.getMessage());
                        }
                    } else {
                        Log.e("Departamentos ", "Couldn't get json from server.");
                    }
                    break;
                case "jurisdiccion":
                    jsonStr = sh.makeServiceCall(lista_jurisddiones);
                    Log.e("Jurisdiccion ", "Response from url: " + jsonStr);
                    if (jsonStr != null) {
                        try {
                            // Getting JSON Array node
                            JSONArray json = new JSONArray(jsonStr);
                            // looping through All Contacts
                            for (int i = 0; i < json.length(); i++) {
                                JSONObject c = json.getJSONObject(i);
                                String id = c.getString("id");
                                String jurisdiccion = c.getString("jurisdiccion");
                                ArrayListJurisdiccion.add(id+". "+jurisdiccion);
                            }
                        } catch (final JSONException e) {
                            Log.e("Jurisdiccion ", "Json parsing error: " + e.getMessage());
                        }
                    } else {
                        Log.e("Jurisdiccion ", "Couldn't get json from server.");
                    }
                    break;
                case "get":
                    jsonStr = sh.makeServiceCall(api_obra+Globals.idObra);
                    Log.e("get Obra ", "Response from url: " + jsonStr);
                    if (jsonStr != null) {
                        try {
                            // Getting JSON Array node
                            JSONArray json = new JSONArray(jsonStr);
                            // looping through All Contacts
                            for (int i = 0; i < json.length(); i++) {
                                JSONObject c = json.getJSONObject(i);
                                obraActualizar = new Obra(
                                    c.getInt("id"),
                                    c.getString("nombre"),
                                    c.getString("horario_inicio"),
                                    c.getString("horario_fin"),
                                    c.getString("poblacion_neta"),
                                    c.getString("longitud"),
                                    c.getString("latitud"),
                                    c.getString("fecha_entrega"),
                                    c.getString("obras_tipo_obra_id"),
                                    c.getString("obras_jurisdiccion_id"),
                                    c.getString("obras_usuarios_id"),
                                    c.getString("obras_departamento_id"),
                                    c.getString("telefono1"),
                                    c.getString("telefono2"),
                                    c.getString("fax"),
                                    c.getString("municipio"),
                                    c.getString("comunidad_zona_urb"),
                                    c.getString("pagina_web"),
                                    c.getString("provincia"),
                                    c.getString("casilla"),
                                    c.getString("direccion"),
                                    c.getInt("atencion_lunes"),
                                    c.getInt("atencion_martes"),
                                    c.getInt("atencion_miercoles"),
                                    c.getInt("atencion_jueves"),
                                    c.getInt("atencion_viernes"),
                                    c.getInt("atencion_sabado"),
                                    c.getInt("atencion_domingo")
                                );
                            }
                        } catch (final JSONException e) {
                            Log.e("get Obra ", "Json parsing error: " + e.getMessage());
                        }
                    } else {
                        Log.e("get Obra ", "Couldn't get json from server.");
                    }
                    break;
            }
            return response;
        }
        @Override
        protected void onPostExecute(String result) {
            switch (opcion){
                case "tipo":
                    setAdapterSpinnerTipoObra();
                    break;
                case "departamento":
                    setAdapterSpinnerDepartamento();
                    break;
                case "jurisdiccion":
                    setAdapterSpinnerJurisdiccion();
                    break;
                case "get":
                    break;
            }
        }
    }
}