package com.fuzzyapps.conferenciaepiscopalboliviana;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
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

public class evento_comentarios extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<_Comentario> ArrayListComentarios = new ArrayList<>();
    private String _eventoID;
    private EditText messageInput;
    private TextView messageTextCount;
    private Button messageSendButton;
    public String getComentarios = Globals.completeApiURL+"/evento_comentarios/";
    public String comentarevento = Globals.completeApiURL+"/comentar_evemto/";
    CardView cardView;
    private boolean send = false;
    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            int cantidad = 255 - Integer.parseInt(String.valueOf(s.length())+"");
            messageTextCount.setText(cantidad+"");
            if(cantidad >= 0){
                send = true;
            }else {
                send = false;
            }
        }
        public void afterTextChanged(Editable s) {
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evento_comentarios);
        messageTextCount = (TextView) findViewById(R.id.messageTextCount);
        messageInput = (EditText) findViewById(R.id.messageInput);
        messageSendButton = (Button) findViewById(R.id.messageSendButton);
        messageInput.addTextChangedListener(mTextEditorWatcher);
        _eventoID = getIntent().getExtras().getString("eventoID");
        cardView = (CardView) findViewById(R.id.cardView);
        //Set RecyclerView Adapter
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(ArrayListComentarios, getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
        actualizarListview();


        messageSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(messageInput.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"No hay texto.",Toast.LENGTH_SHORT).show();
                }else{
                    if(send){
                        //Toast.makeText(getApplicationContext(),""+messageInput.getText().toString(),Toast.LENGTH_SHORT).show();
                        new comentariosAPI(_eventoID, "add", Globals.idUsuario,"2016-10-19","10:00", messageInput.getText().toString()).execute();
                        messageInput.setText("");
                    }else{
                        Toast.makeText(getApplicationContext(),"Mensaje muy largo.",Toast.LENGTH_SHORT).show();
                    }
                }
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                messageInput.clearFocus();
            }
        });
    }
    public void actualizarListview(){
        new comentariosAPI(_eventoID, "list").execute();
    }
    private void notificarCambios() {
        mAdapter.notifyDataSetChanged();
    }
    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private final Picasso picasso;
        private ArrayList<_Comentario> adapterArrayListComentarios = new ArrayList<>();
        private Context context;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class ViewHolder extends RecyclerView.ViewHolder {
            // each data item
            public TextView comentarioID, comentarioTiempo, comentarioMensaje, comentarioNombre;
            public ImageView comentarioImagen;
            Button comentarioEliminarButton;

            public ViewHolder(View v) {
                super(v);
                comentarioID = (TextView) v.findViewById(R.id.comentarioID);
                comentarioTiempo = (TextView) v.findViewById(R.id.comentarioTiempo);
                comentarioMensaje = (TextView) v.findViewById(R.id.comentarioMensaje);
                comentarioNombre = (TextView) v.findViewById(R.id.comentarioNombre);
                comentarioImagen = (ImageView) v.findViewById(R.id.comentarioImagen);
                comentarioEliminarButton = (Button) v.findViewById(R.id.comentarioEliminarButton);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(ArrayList<_Comentario> _obrasDataSet, final Context context) {
            adapterArrayListComentarios = _obrasDataSet;
            this.picasso = Picasso.with(context);
            this.context = context;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comentario, parent, false);
            // set the view's size, margins, paddings and layout parameters
            MyAdapter.ViewHolder vh = new MyAdapter.ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final MyAdapter.ViewHolder holder, final int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            //holder.mTextView.setText(mDataset[position]);
            final _Comentario comentario = adapterArrayListComentarios.get(position);
            holder.comentarioNombre.setText(comentario.nombre);
            holder.comentarioMensaje.setText(comentario.mensaje);
            holder.comentarioTiempo.setText(comentario.fecha);
            holder.comentarioID.setText(comentario.id);

            picasso.cancelRequest(holder.comentarioImagen);
            Log.e("picaso ", "id:"+comentario.id+" url: "+comentario.img_url);
            picasso.load(comentario.img_url)
                    .noPlaceholder()
                    .resizeDimen(R.dimen.imagen_comentario_width, R.dimen.imagen_comentario_height)
                    .centerCrop()
                    .into(holder.comentarioImagen);
            holder.comentarioEliminarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, ""+holder.comentarioID.getText().toString(),Toast.LENGTH_SHORT).show();
                }
            });

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return adapterArrayListComentarios.size();
        }
    }
    public class comentariosAPI extends AsyncTask<String, String, String> {
        public String id;
        public String accion;
        public String idUsuario;
        public String fecha;
        public String hora;
        public String comentario;

        public comentariosAPI(String id, String accion) {
            //obtener perfi de encargado
            this.id = id;
            this.accion = accion;
        }
        public comentariosAPI(String idEvento, String accion, String idUsuario, String fecha, String hora, String comentario) {
            //obtener perfi de encargado
            this.id = idEvento;
            this.accion = accion;
            this.idUsuario = idUsuario;
            this.fecha = fecha;
            this.hora = hora;
            this.comentario = comentario;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ArrayListComentarios.clear();
        }
        @Override
        protected String doInBackground(String... params) {
            URL url;
            String response = "";
            HttpHandler sh = new HttpHandler();
            switch (accion){
                case "list":
                    // Making a request to url and getting response
                    String jsonStr = sh.makeServiceCall(getComentarios+_eventoID);
                    Log.e("getEventos ", "Response from url: " + jsonStr);
                    if (jsonStr != null) {
                        try {
                            // Getting JSON Array node
                            JSONArray json = new JSONArray(jsonStr);
                            // looping through All Contacts
                            for (int i = 0; i < json.length(); i++) {
                                JSONObject c = json.getJSONObject(i);
                                _Comentario comentario = new _Comentario(
                                        c.getString("id")
                                        ,c.getString("fecha")
                                        ,c.getString("comentario")
                                        ,c.getString("nombres")+" "+c.getString("apellido_paterno")+" "+c.getString("apellido_materno")
                                        ,Globals.completeImageURLNOBODY+c.getString("imagen_url"));
                                ArrayListComentarios.add(comentario);
                            }
                        } catch (final JSONException e) {
                            Log.e("getEventos ", "Json parsing error: " + e.getMessage());
                            //msg("Json parsing error: " + e.getMessage());
                        }
                    } else {
                        Log.e("getEventos", "Couldn't get json from server.");
                    }
                    break;
                case "delete":
                    break;
                case "add":
                    try {
                        url = new URL(comentarevento);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setReadTimeout(15000);
                        conn.setConnectTimeout(15000);
                        conn.setRequestMethod("POST");
                        conn.setDoInput(true);
                        conn.setDoOutput(true);
                        OutputStream os = conn.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        HashMap<String, String> postDataParams = new HashMap<String, String>();
                        postDataParams.put("idEvento", id);
                        postDataParams.put("idUsuario", idUsuario);
                        postDataParams.put("comentario", comentario);
                        postDataParams.put("fecha", fecha);
                        postDataParams.put("hora", hora);
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
                default:
                    break;
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            switch (accion) {
                case "list":
                    notificarCambios();
                    break;
                case "delete":
                    actualizarListview();
                    break;
                case "add":
                    actualizarListview();
                    break;
                default:
                    break;
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if(Globals.status){
            setAllViews(Globals.usuario);
        }else{
            cardView.setVisibility(View.GONE);
        }
    }
    private void setAllViews(String name){
        getSupportActionBar().setTitle("Bienvenid@, "+name);
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
