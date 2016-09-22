package com.fuzzyapps.conferenciaepiscopalboliviana;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class obrasFragment extends Fragment {
    public View view;
    private Spinner filtro;
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arraySpinner);
        filtro.setAdapter(adapter);
        return view;
    }

}
