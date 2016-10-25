package com.fuzzyapps.conferenciaepiscopalboliviana;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.Toast;

public class detalleObra extends AppCompatActivity {
    public static String _idObra;
    public static String _idUsuario;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_obra);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        _idObra = getIntent().getExtras().getString("obraID");
        //Toast.makeText(getApplicationContext(), ""+_idObra,Toast.LENGTH_SHORT).show();
    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            obra_nav_perfilFragment fragmentOne = null;
            obra_nav_eventosFragment fragmentTwo = null;
            obra_nav_recaudacionFragment fragmentThree = null;
            obra_nav_mapaFragment fragmentFour = null;
            obra_nav_encargadoFragment fragmentFive = null;
            switch (position){
                case 0:
                    if(fragmentOne == null){
                        fragmentOne = new obra_nav_perfilFragment();
                    }
                    return fragmentOne;
                case 1:
                    if(fragmentTwo == null){
                        fragmentTwo = new obra_nav_eventosFragment();
                    }
                    return fragmentTwo;
                /*case 2:
                    fragmentThree = new obra_nav_recaudacionFragment();
                    return fragmentThree;*/
                case 2:
                    if(fragmentFour == null){
                        fragmentFour = new obra_nav_mapaFragment();
                    }
                    return fragmentFour;
                case 3:
                    if(fragmentFive == null){
                        fragmentFive = new obra_nav_encargadoFragment();
                    }
                    return fragmentFive;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "PERFIL";
                case 1:
                    return "EVENTOS";
                /*case 2:
                    return "COLECTA";*/
                case 2:
                    return "MAPA";
                case 3:
                    return "GESTOR";
            }
            return null;
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
        getSupportActionBar().setTitle("Bienvenid@, "+name);
    }
}
