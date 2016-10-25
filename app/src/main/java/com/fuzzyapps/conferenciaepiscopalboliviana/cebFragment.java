package com.fuzzyapps.conferenciaepiscopalboliviana;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class cebFragment extends Fragment {

    public cebFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ceb, container, false);
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
