package com.fuzzyapps.conferenciaepiscopalboliviana;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class obrasFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    //private clase_Obra[] Obras;
    private ArrayList<clase_Obra> ArrayListObras = new ArrayList<>();
    public View view;
    private Spinner filtro;
    private Button buscarObra;
    public static String api_listar_obra = Globals.completeApiURL+"/obras/";

    public obrasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_obras, container, false);
        String[] arraySpinner = new String[] {
                "1. Parroquia",
                "2. Casa Religiosa",
                "3. Obra de Educación",
                "4. Obra de Salud",
                "5. Obra de Protección Social",
                "6. Casa de Encuentros",
                "7. Obra Administrativa",
                "8. Obra de Movilidad Humana",
                "9. Obra Productiva",
                "10. Obra Penitenciaria"
        };
        filtro = (Spinner) view.findViewById(R.id.filtro);
        buscarObra = (Button) view.findViewById(R.id.buscarObra);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arraySpinner);
        filtro.setAdapter(adapter);


        //Set RecyclerView Adapter
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(ArrayListObras, getContext());
        mRecyclerView.setAdapter(mAdapter);


        actualizarListview();
        return view;
    }
    public void actualizarListview(){
        new obraAPI("list").execute();
    }
    private void notificarCambios() {
        mAdapter.notifyDataSetChanged();
    }
    /*
    * OBRAS
    * */
    public class obraAPI extends AsyncTask<String, String, String> {
        public String id;
        public String tipo;
        public String cargado;
        public String opcion;
        public obraAPI(String tipo, String cargado, String opcion) {
            //new
            this.tipo = tipo;
            this.cargado = cargado;
            this.opcion = opcion;
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
            ArrayListObras.clear();
        }
        @Override
        protected String doInBackground(String... p) {
            String response = "";
            switch (opcion){
                case "list":
                    HttpHandler sh = new HttpHandler();
                    // Making a request to url and getting response
                    String jsonStr = sh.makeServiceCall(api_listar_obra);
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
                                String jurisdiccion = c.getString("jurisdiccion");
                                String tipo = c.getString("tipo");
                                String urlImagen = c.getString("imagen_url");
                                clase_Obra obraEncontrada = new clase_Obra(id, nombre, jurisdiccion, tipo, Globals.completeImageURL+urlImagen);
                                ArrayListObras.add(obraEncontrada);
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
                    break;
                case "update":
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
                    notificarCambios();
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
    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private final Picasso picasso;
        private ArrayList<clase_Obra> obrasDataSet = new ArrayList<>();
        private Context context;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView obraNombre;
            public TextView obraJurisdiccion;
            public TextView obraTipo;
            public TextView obraID;
            public Button obraVerMas;
            public ImageView obraImagen;

            public ViewHolder(View v) {
                super(v);
                obraNombre = (TextView) v.findViewById(R.id.obraNombre);
                obraJurisdiccion = (TextView) v.findViewById(R.id.obraJurisdiccion);
                obraTipo = (TextView) v.findViewById(R.id.obraTipo);
                obraID = (TextView) v.findViewById(R.id.obraID);
                obraVerMas = (Button) v.findViewById(R.id.obraVerMas);
                obraImagen = (ImageView) v.findViewById(R.id.obraImagen);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(ArrayList<clase_Obra> _obrasDataSet, final Context context) {
            obrasDataSet = _obrasDataSet;
            this.picasso = Picasso.with(context);
            this.context = context;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_obra, parent, false);
            // set the view's size, margins, paddings and layout parameters
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            //holder.mTextView.setText(mDataset[position]);
            final clase_Obra item = obrasDataSet.get(position);
            holder.obraID.setText(item.obraID);
            holder.obraNombre.setText(item.obraNombre);
            holder.obraTipo.setText(item.obraTipo);
            holder.obraJurisdiccion.setText(item.obraJurisdiccion);
            picasso.cancelRequest(holder.obraImagen);
            picasso.load(item.obraImagenUrl)
                    .noPlaceholder()
                    .resizeDimen(R.dimen.simple_card_image_width, R.dimen.simple_card_image_height)
                    .centerCrop()
                    .into(holder.obraImagen);
            holder.obraVerMas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, detalleObra.class);
                    i.putExtra("obraID",holder.obraID.getText().toString());
                    context.startActivity(i);

                    //Toast.makeText(context, ""+item.obraNombre,Toast.LENGTH_SHORT).show();
                }
            });

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return obrasDataSet.size();
        }
    }
}
