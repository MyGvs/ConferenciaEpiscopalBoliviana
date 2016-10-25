package com.fuzzyapps.conferenciaepiscopalboliviana;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.like.LikeButton;
import com.like.OnLikeListener;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class obra_nav_eventosFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<_Evento> ArrayListEventos = new ArrayList<>();
    private String getEventos = Globals.completeApiURL+"/obras_eventos/";
    private String darlike = Globals.completeApiURL+"/obras_eventos_dar_like/";
    private String quitarlike = Globals.completeApiURL+"/obras_eventos_quitar_like/";
    private String getEventosUser = Globals.completeApiURL+"/obras_eventos_mi_like/";
    private View view;
    public obra_nav_eventosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.obra_nav_eventos_fragment, container, false);
        //Set RecyclerView Adapter
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(ArrayListEventos, getContext());
        mRecyclerView.setAdapter(mAdapter);
        actualizarListview();
        return view;
    }
    public void actualizarListview(){
        new eventoAPI().execute();
    }
    private void notificarCambios() {
        mAdapter.notifyDataSetChanged();
    }
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private final Picasso picasso;
        private ArrayList<_Evento> adapterArrayListEventos = new ArrayList<>();
        private Context context;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item
            public TextView eventoNombre, eventoFecha, eventoHora, eventoReferencia, eventoDescripcion, eventoLikes, eventoComentarios, eventoID;
            public ImageView eventoImagen;
            ImageButton eventoComentarButton;
            LikeButton likeButton;

            public ViewHolder(View v) {
                super(v);
                eventoNombre = (TextView) v.findViewById(R.id.eventoNombre);
                eventoFecha = (TextView) v.findViewById(R.id.eventoFecha);
                eventoHora = (TextView) v.findViewById(R.id.eventoHora);
                eventoReferencia = (TextView) v.findViewById(R.id.eventoReferencia);
                eventoDescripcion = (TextView) v.findViewById(R.id.eventoDescripcion);
                eventoLikes = (TextView) v.findViewById(R.id.eventoLikes);
                eventoComentarios = (TextView) v.findViewById(R.id.eventoComentarios);
                eventoID = (TextView) v.findViewById(R.id.eventoID);
                eventoImagen = (ImageView) v.findViewById(R.id.eventoImagen);
                eventoComentarButton = (ImageButton) v.findViewById(R.id.eventoComentarButton);
                likeButton = (LikeButton) v.findViewById(R.id.likeButton);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(ArrayList<_Evento> _obrasDataSet, final Context context) {
            adapterArrayListEventos = _obrasDataSet;
            this.picasso = Picasso.with(context);
            this.context = context;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evento, parent, false);
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
            final _Evento evento = adapterArrayListEventos.get(position);
            holder.eventoNombre.setText(evento.nombre);
            holder.eventoFecha.setText(evento.fecha_evento);
            holder.eventoHora.setText(evento.hora_inicio +" - "+evento.hora_fin);
            holder.eventoReferencia.setText(evento.lugar);
            holder.eventoDescripcion.setText(evento.descripcion);
            holder.eventoLikes.setText(""+evento.likes);
            holder.eventoComentarios.setText(""+evento.comments);
            holder.eventoID.setText(evento.id);
            if(Globals.status){
                holder.likeButton.setEnabled(true);
                if(evento.miLike.equals("null")){
                }else{
                    if(!evento.miLike.equals("")){
                        holder.likeButton.setLiked(true);
                    }else{
                        holder.likeButton.setLiked(false);
                    }
                }
            }else{
                holder.likeButton.setEnabled(false);
            }
            holder.likeButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    Log.e("LIKE: ", "true");
                    new likeAPI("like", evento.id, Globals.idUsuario,"2016-10-19").execute();
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    Log.e("LIKE: ", "false");
                    new likeAPI("unlike", evento.miLikeId).execute();
                }
            });
            if(evento.imagen_url.endsWith(".jpg") || evento.imagen_url.endsWith(".png") || evento.imagen_url.endsWith(".jpeg")){
                picasso.cancelRequest(holder.eventoImagen);
                picasso.load(evento.imagen_url)
                        .noPlaceholder()
                        .resizeDimen(R.dimen.evento_imagen_width, R.dimen.evento_imagen_height)
                        .centerCrop()
                        .into(holder.eventoImagen);
            }else{
                holder.eventoImagen.setVisibility(View.GONE);
            }
            holder.eventoComentarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, evento_comentarios.class);
                    i.putExtra("eventoID", holder.eventoID.getText().toString());
                    context.startActivity(i);
                    //Toast.makeText(context, ""+holder.eventoID.getText().toString(),Toast.LENGTH_SHORT).show();
                }
            });

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return adapterArrayListEventos.size();
        }
    }
    public class eventoAPI extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            ArrayListEventos.clear();
        }
        @Override
        protected String doInBackground(String... params) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = "";
            if(Globals.status){
                jsonStr = sh.makeServiceCall(getEventosUser+detalleObra._idObra+","+Globals.idUsuario);
            }else{
                jsonStr = sh.makeServiceCall(getEventos+detalleObra._idObra);
            }
            Log.e("getEventos ", "Response from url: " + jsonStr);
            Log.e("URL: ", getEventosUser+detalleObra._idObra+","+Globals.idUsuario);
            if (jsonStr != null) {
                try {
                    // Getting JSON Array node
                    JSONArray json = new JSONArray(jsonStr);
                    // looping through All Contacts
                    for (int i = 0; i < json.length(); i++) {
                        JSONObject c = json.getJSONObject(i);
                        String id = c.getString("id");
                        String nombre = c.getString("nombre");
                        String descripcion = c.getString("descripcion");
                        String fecha_evento = c.getString("fecha_evento");
                        String hora_inicio = c.getString("hora_inicio");
                        String hora_fin = c.getString("hora_fin");
                        String lugar = c.getString("lugar");
                        String imagen_url = c.getString("imagen_url");
                        int likes = c.getInt("likes");
                        int comments = c.getInt("comments");
                        String mi_like = "";
                        String mi_like_id = "";
                        if(Globals.status){
                            mi_like = c.getString("your_like");
                            mi_like_id = c.getString("your_like_id");
                        }
                        _Evento evento = new _Evento(id, nombre, descripcion, fecha_evento,
                                hora_inicio, hora_fin, lugar, Globals.completeImageURL+imagen_url,
                                likes, comments, mi_like, mi_like_id);
                        ArrayListEventos.add(evento);
                    }
                } catch (final JSONException e) {
                    Log.e("getEventos ", "Json parsing error: " + e.getMessage());
                    //msg("Json parsing error: " + e.getMessage());
                }
            } else {
                Log.e("getEventos", "Couldn't get json from server.");
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            notificarCambios();
        }
    }
    public class likeAPI extends AsyncTask<String, String, String> {
        public String opcion, idEvento, idUsuario, fecha, idLike;
        public likeAPI(String opcion, String idEvento, String idUsuario, String fecha){
            this.opcion = opcion;
            this.idEvento = idEvento;
            this.idUsuario = idUsuario;
            this.fecha = fecha;
        }
        public likeAPI(String opcion, String idLike){
            this.opcion = opcion;
            this.idLike = idLike;
        }
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {
            URL url;
            String response = "";
            switch (opcion){
                case "like":
                    try {
                        url = new URL(darlike);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setReadTimeout(15000);
                        conn.setConnectTimeout(15000);
                        conn.setRequestMethod("POST");
                        conn.setDoInput(true);
                        conn.setDoOutput(true);
                        OutputStream os = conn.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        HashMap<String, String> postDataParams = new HashMap<String, String>();
                        postDataParams.put("idEvento", idEvento);
                        postDataParams.put("idUsuario", idUsuario);
                        postDataParams.put("fecha", fecha);
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
                case "unlike":
                    //delete
                    try {
                        url = new URL(quitarlike);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setReadTimeout(15000);
                        conn.setConnectTimeout(15000);
                        conn.setRequestMethod("POST");
                        conn.setDoInput(true);
                        conn.setDoOutput(true);
                        OutputStream os = conn.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        HashMap<String, String> postDataParams = new HashMap<String, String>();
                        postDataParams.put("idLike", idLike);
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
            }
            return response;
        }
        @Override
        protected void onPostExecute(String result) {
            if (!result.equals("")){
                actualizarListview();
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if(Globals.status){
            setAllViews(Globals.usuario);
        }
        actualizarListview();
    }
    private void setAllViews(String name){
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Bienvenid@, "+name);
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
