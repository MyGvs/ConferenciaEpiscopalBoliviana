package com.fuzzyapps.conferenciaepiscopalboliviana;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInstaller;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import de.hdodenhof.circleimageview.CircleImageView;

public class loginFragment extends Fragment {
    ScrollView welcome, loginUser;
    TextView nombreUsuario;
    public static String new_user = Globals.completeApiURL+"/nuevo_usuario/";
    public static String login_user = Globals.completeApiURL+"/loguear_usuario/";
    private View view;
    private Button loginButton, registerButton, panel;
    LayoutInflater layoutInflater;
    private TextView errorInputUsuario, errorInputPassword, errorInputPasswordRepeat, errorInputName, errorInputFirstName, errorInputLastName, errorInputEmail, errorInputPhone, errorText;//, uploadStatus;
    private EditText inputUsuario, inputPassword, inputPasswordRepeat, inputName, inputFirstName, inputLastName, inputEmail, inputPhone;
    private EditText input_email, input_password;
    private Picasso picasso;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int CHOICE_AVATAR_FROM_CAMERA_CROP = 2;
    boolean imageSelected = false;
    public String imageName;
    AlertDialog alertPopUp;
    public loginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        errorText = (TextView) view.findViewById(R.id.errorText);
        loginButton = (Button) view.findViewById(R.id.loginButton);
        registerButton = (Button) view.findViewById(R.id.registerButton);
        panel = (Button) view.findViewById(R.id.panel);
        input_email = (EditText) view.findViewById(R.id.input_email);
        input_password = (EditText) view.findViewById(R.id.input_password);
        layoutInflater = getActivity().getLayoutInflater();
        welcome = (ScrollView) view.findViewById(R.id.welcome);
        loginUser = (ScrollView) view.findViewById(R.id.loginUser);
        nombreUsuario = (TextView) view.findViewById(R.id.nombreUsuario);
        picasso = Picasso.with(getActivity());
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(!input_email.getText().toString().equals("") && !input_password.getText().toString().equals("")){
                    try {
                        InputMethodManager inputManager = (InputMethodManager)
                                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    }catch (Exception e){}
                    input_email.clearFocus();
                    input_password.clearFocus();
                    loguearUsuario();
                }
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
               showRegisterPopUp();
            }
        });
        panel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity() , NavigationActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    private void loguearUsuario() {
        new NewUserAPI(
                input_email.getText().toString(),
                input_password.getText().toString(),
                "login"
        ).execute();
    }
    private void showRegisterPopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Registro de Usuario");
        View popView= layoutInflater.inflate(R.layout.item_registro, null);
        builder.setView(popView);
        // EditText
        inputUsuario = (EditText) popView.findViewById(R.id.inputUsuario);
        inputPassword = (EditText) popView.findViewById(R.id.inputPassword);
        inputPasswordRepeat = (EditText) popView.findViewById(R.id.inputPasswordRepeat);
        inputName = (EditText) popView.findViewById(R.id.inputName);
        inputFirstName = (EditText) popView.findViewById(R.id.inputFirstName);
        inputLastName = (EditText) popView.findViewById(R.id.inputLastName);
        inputEmail = (EditText) popView.findViewById(R.id.inputEmail);
        inputPhone = (EditText) popView.findViewById(R.id.inputPhone);
        // Buttons
        /*Button pickImageButton = (Button) popView.findViewById(R.id.pickImageButton);*/
        Button registerButtonPopUp = (Button) popView.findViewById(R.id.registerButtonPopUp);
        //TextView Errors
        errorInputUsuario = (TextView) popView.findViewById(R.id.errorInputUsuario);
        errorInputPassword = (TextView) popView.findViewById(R.id.errorInputPassword);
        errorInputPasswordRepeat = (TextView) popView.findViewById(R.id.errorInputPasswordRepeat);
        errorInputName = (TextView) popView.findViewById(R.id.errorInputName);
        errorInputFirstName = (TextView) popView.findViewById(R.id.errorInputFirstName);
        errorInputLastName = (TextView) popView.findViewById(R.id.errorInputLastName);
        errorInputEmail = (TextView) popView.findViewById(R.id.errorInputEmail);
        errorInputPhone = (TextView) popView.findViewById(R.id.errorInputPhone);
        //uploadStatus = (TextView) popView.findViewById(R.id.uploadStatus);
        alertPopUp = builder.create();
        /*pickImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                abrirGaleria();
            }
        });*/
        registerButtonPopUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(!inputUsuario.getText().toString().equals("") && inputUsuario.getText().toString().length() <= 20
                        && !inputPassword.getText().toString().equals("") && inputPassword.getText().toString().length() <= 20
                        && !inputPassword.getText().toString().equals("") && inputPassword.getText().toString().length() <= 20
                        && inputPasswordRepeat.getText().toString().equals(inputPassword.getText().toString())
                        && !inputName.getText().toString().equals("") && inputName.getText().toString().length() <= 50
                        && !inputFirstName.getText().toString().equals("") && inputFirstName.getText().toString().length() <= 50
                        && !inputLastName.getText().toString().equals("") && inputLastName.getText().toString().length() <= 50
                        && !inputEmail.getText().toString().equals("") && inputEmail.getText().toString().length() <= 50
                        && !inputPhone.getText().toString().equals("") && inputPhone.getText().toString().length() >= 7){
                    String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
                    imageName = timeStamp;
                    //Registrar usuario
                    new NewUserAPI(
                            inputUsuario.getText().toString(),
                            inputPassword.getText().toString(),
                            inputName.getText().toString(),
                            inputFirstName.getText().toString(),
                            inputLastName.getText().toString(),
                            "/images/"+imageName+".jpg",
                            inputEmail.getText().toString(),
                            inputPhone.getText().toString(),
                            "register"
                    ).execute();
                }else{
                    if(!inputUsuario.getText().toString().equals("") && inputUsuario.getText().toString().length() <= 20) {
                        errorInputUsuario.setVisibility(View.GONE);
                    }else{
                        errorInputUsuario.setVisibility(View.VISIBLE);
                        errorInputUsuario.setText("*Falta llenar este campo, debe contener hasta de 20 caracteres.");
                    }
                    if(!inputPassword.getText().toString().equals("") && inputPassword.getText().toString().length() <= 20) {
                        errorInputPassword.setVisibility(View.GONE);
                    }else{
                        errorInputPassword.setVisibility(View.VISIBLE);
                        errorInputPassword.setText("*Falta llenar este campo, debe contener hasta de 20 caracteres.");
                    }
                    if(inputPasswordRepeat.getText().toString().equals(inputPassword.getText().toString())){
                        errorInputPasswordRepeat.setVisibility(View.GONE);
                    }else{
                        errorInputPasswordRepeat.setVisibility(View.VISIBLE);
                        errorInputPasswordRepeat.setText("*Las contraseñas no coinciden.");
                    }
                    if(!inputName.getText().toString().equals("") && inputName.getText().toString().length() <= 50){
                        errorInputName.setVisibility(View.GONE);
                    }else{
                        errorInputName.setVisibility(View.VISIBLE);
                        errorInputName.setText("*Falta llenar este campo, debe contener hasta de 50 caracteres.");
                    }
                    if(!inputFirstName.getText().toString().equals("") && inputFirstName.getText().toString().length() <= 50){
                        errorInputFirstName.setVisibility(View.GONE);
                    }else{
                        errorInputFirstName.setVisibility(View.VISIBLE);
                        errorInputFirstName.setText("*Falta llenar este campo, debe contener hasta de 50 caracteres.");
                    }
                    if(!inputLastName.getText().toString().equals("") && inputLastName.getText().toString().length() <= 50){
                        errorInputLastName.setVisibility(View.GONE);
                    }else{
                        errorInputLastName.setVisibility(View.VISIBLE);
                        errorInputLastName.setText("*Falta llenar este campo, debe contener hasta de 50 caracteres.");
                    }
                    if(!inputEmail.getText().toString().equals("") && inputEmail.getText().toString().length() <= 50){
                        errorInputEmail.setVisibility(View.GONE);
                    }else{
                        errorInputEmail.setVisibility(View.VISIBLE);
                        errorInputEmail.setText("*Falta llenar este campo, debe contener hasta de 50 caracteres.");
                    }
                    if(!inputPhone.getText().toString().equals("") && inputPhone.getText().toString().length() >= 7){
                        errorInputPhone.setVisibility(View.GONE);
                    }else{
                        errorInputPhone.setVisibility(View.VISIBLE);
                        errorInputPhone.setText("*Falta llenar este campo, número no valido.");
                    }
                }
            }
        });
        alertPopUp.show();
    }
    private void cancelDialog(){
        alertPopUp.cancel();
    }
    private void abrirGaleria() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }
    private Intent getCropIntent(Intent intent) {
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 128);
        intent.putExtra("outputY", 128);
        intent.putExtra("return-data", true);
        return intent;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CHOICE_AVATAR_FROM_CAMERA_CROP) {
                //ToastUtils.toastType0(mActivity, "CHOICE_AVATAR_FROM_CAMERA", Toast.LENGTH_SHORT);
                Bitmap avatar = getBitmapFromData(data);
                //profileImagePreview.setImageBitmap(avatar);
                //uploadImageToServer(avatar);
                imageSelected = true;
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Intent intent = new Intent("com.android.camera.action.CROP");
                Uri uri = data.getData();
                intent.setDataAndType(uri, "image/*");
                startActivityForResult(getCropIntent(intent), CHOICE_AVATAR_FROM_CAMERA_CROP);
            }
        }
    }
    private void setErrorInView() {
        errorText.setVisibility(View.VISIBLE);
        errorText.setText("Usuario o Contraseña equivocados.");
    }

    private void setAllViews(String name){
        welcome.setVisibility(View.VISIBLE);
        loginUser.setVisibility(View.GONE);
        nombreUsuario.setText("Bienvenid@, "+name);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Bienvenid@, "+name);
    }
    private void setSessionVariables(String id, String usuario, String nombres, String apellido_paterno, String apellido_materno, String tipo_usuario, String imagen_url){
        Globals.idUsuario = id;
        Globals.usuario = usuario;
        Globals.nombres = nombres;
        Globals.apellido_paterno = apellido_paterno;
        Globals.apellido_materno = apellido_materno;
        Globals.tipo_usuario = tipo_usuario;
        Globals.imagen_url = imagen_url;
        Globals.status = true;
        errorText.setVisibility(View.GONE);
        errorText.setText("");
        setAllViews(usuario);
        msg(tipo_usuario+"Bienvenido@, "+nombres+" "+apellido_paterno+" "+apellido_materno);
        if(tipo_usuario.equals("2")){
            Intent intent = new Intent(getActivity() , NavigationActivity.class);
            startActivity(intent);
            panel.setVisibility(View.VISIBLE);
        }
        /*((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Soy genial");
        Globals.status = true;
        Globals.usuario = "GVS";
        Globals.idUsuario = 1;
        SharedPreferences preferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        preferences.edit().putString("usuarios", "jajaja").apply();
        preferences.edit().putInt("idUsuario", 1).apply();
        preferences.edit().putBoolean("status", true).apply();*/
    }
    public static Bitmap getBitmapFromData(Intent data) {
        Bitmap photo = null;
        Uri photoUri = data.getData();
        if (photoUri != null) {
            photo = BitmapFactory.decodeFile(photoUri.getPath());
        }
        if (photo == null) {
            Bundle extra = data.getExtras();
            if (extra != null) {
                photo = (Bitmap) extra.get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            }
        }

        return photo;
    }
    public void msg(String message){
        Toast.makeText(getContext(), message,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onResume() {
        super.onResume();
        if(Globals.status){
            setAllViews(Globals.usuario);
        }
        try{
            if(Globals.tipo_usuario.equals("2")){
                panel.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){}
    }
    @Override
    public void onStop() {
        super.onStop();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    /*
    * POST NEW USER
    * */
    public class NewUserAPI extends AsyncTask<String, String, String> {
        String _usuario, _clave, _nombre, _apellidoP, _apellidoM, _nombreImagen, _email, _telefono, _opcion;
        String session_usuario, session_id, session_nombres, session_apellido_paterno, session_apellido_materno, session_tipo_de_usuario, session_imagen_url;
        public NewUserAPI(String usuario, String clave, String nombre, String apellidoP, String apellidoM, String nombreImagen, String email, String telefono, String opcion){
            this._usuario = usuario;
            this._clave = clave;
            this._nombre = nombre;
            this._apellidoP = apellidoP;
            this._apellidoM = apellidoM;
            this._email = email;
            this._telefono = telefono;
            this._nombreImagen = nombreImagen;
            this._opcion = opcion;
        }
        public NewUserAPI(String usuario, String clave, String opcion){
            this._usuario = usuario;
            this._clave = clave;
            this._opcion = opcion;
        }
        @Override
        protected String doInBackground(String... params) {
            URL url;
            String response = "";
            switch (_opcion){
                case "register":
                    try {
                        url = new URL(new_user);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setReadTimeout(15000);
                        conn.setConnectTimeout(15000);
                        conn.setRequestMethod("POST");
                        conn.setDoInput(true);
                        conn.setDoOutput(true);
                        OutputStream os = conn.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        HashMap<String, String> postDataParams = new HashMap<String, String>();
                        postDataParams.put("usuario", _usuario);
                        postDataParams.put("clave", _clave);
                        postDataParams.put("nombres", _nombre);
                        postDataParams.put("apellido_paterno", _apellidoP);
                        postDataParams.put("apellido_materno", _apellidoM);
                        postDataParams.put("email", _email);
                        postDataParams.put("telefono", _telefono);
                        postDataParams.put("imagen_url", "/images/userDefault.png");
                        writer.write(getPostDataString(postDataParams));
                        writer.flush();
                        writer.close();
                        os.close();
                        int responseCode=conn.getResponseCode();
                        if (responseCode == HttpsURLConnection.HTTP_OK) {
                            response="OK";
                        }
                        else {
                            response="";

                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                        Log.e("ERROR",""+e.getMessage());
                    }
                    break;
                case "login":
                    HttpHandler sh = new HttpHandler();
                    // Making a request to url and getting response
                    String jsonStr = sh.makeServiceCall(login_user+_usuario+","+_clave);
                    Log.e("USUARIO ", "Response from url: " + jsonStr);
                    if (jsonStr != null) {
                        try {
                            // Getting JSON Array node
                            JSONArray json = new JSONArray(jsonStr);
                            JSONObject c = json.getJSONObject(0);
                            session_id = c.getString("id");
                            session_usuario = c.getString("usuario");
                            session_nombres = c.getString("nombres");
                            session_apellido_paterno = c.getString("apellido_paterno");
                            session_apellido_materno = c.getString("apellido_materno");
                            session_tipo_de_usuario = c.getString("obras_tipo_usuario_id");
                            session_imagen_url = c.getString("imagen_url");
                            //imagen_url
                            response="OK";

                        } catch (final JSONException e) {
                            response="";
                            Log.e("getLOGIN ", "Json parsing error: " + e.getMessage());
                        }
                    } else {
                        response="";
                        Log.e("getLOGIN", "Couldn't get json from server.");
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
            switch (_opcion){
                case "register":
                    if(result.equals("OK")){
                        msg("Registro Exitoso!");
                        cancelDialog();
                    }else{
                        msg("Registro Fallido, No hay conexión a internet.");
                    }
                    break;
                case "login":
                    if(result.equals("OK")){
                        setSessionVariables(session_id, session_usuario, session_nombres, session_apellido_paterno, session_apellido_materno, session_tipo_de_usuario, session_imagen_url);
                    }else{
                        setErrorInView();
                    }
                    break;
                default:
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
