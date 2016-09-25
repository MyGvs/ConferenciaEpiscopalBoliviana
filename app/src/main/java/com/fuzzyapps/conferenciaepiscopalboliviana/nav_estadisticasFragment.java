package com.fuzzyapps.conferenciaepiscopalboliviana;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class nav_estadisticasFragment extends Fragment {

    private View view;
    public nav_estadisticasFragment() {
        // Required empty public constructor
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.nav_estadisticas_fragment, container, false);
        return view;
    }

}
