package com.fuzzyapps.conferenciaepiscopalboliviana;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ImageButton networkStatus;
    protected boolean status;
    public static CircleImageView profileImageM;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        fragmentManager = getFragmentManager();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bienvenido, Geovani");
        networkStatus = (ImageButton) findViewById(R.id.networkStatus);
        status = true;
        networkStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status){
                    networkStatus.setBackgroundResource(R.mipmap.ic_signal_wifi_off_white_24dp);
                    //Toast.makeText(getApplicationContext(),"false",Toast.LENGTH_SHORT).show();
                }else{
                    networkStatus.setBackgroundResource(R.mipmap.ic_wifi_white_24dp);
                    //Toast.makeText(getApplicationContext(),"true",Toast.LENGTH_SHORT).show();
                }
                status = !status;
            }
        });
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //set First Fragmetn
        fragmentManager.beginTransaction()
                .replace(R.id.contentFrame, new nav_obrasFragment())
                .commit();
        //Cambio de fragment cuando se hace click a la imagen
        View header = navigationView.getHeaderView(0);
        profileImageM = (CircleImageView) header.findViewById(R.id.profileImage2);
        //Listener del circle iamge
        profileImageM.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                navigationView.setCheckedItem(R.id.nav_perfil);
                fragmentManager.beginTransaction()
                        .replace(R.id.contentFrame, new nav_perfilFragment())
                        .commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_registro_obras) {
            // Handle the camera action
            fragmentManager.beginTransaction()
                    .replace(R.id.contentFrame, new nav_obrasFragment())
                    .commit();
        } else if (id == R.id.nav_calendario) {
            fragmentManager.beginTransaction()
                    .replace(R.id.contentFrame, new nav_calendarioFragment())
                    .commit();
        } else if (id == R.id.nav_registro_tipo_obra) {
            fragmentManager.beginTransaction()
                    .replace(R.id.contentFrame, new nav_tipoObraFragment())
                    .commit();
        } else if (id == R.id.nav_registro_departamento) {
            fragmentManager.beginTransaction()
                    .replace(R.id.contentFrame, new nav_departamentoFragment())
                    .commit();
        } else if (id == R.id.nav_reportes_estadistico) {
            fragmentManager.beginTransaction()
                    .replace(R.id.contentFrame, new nav_estadisticasFragment())
                    .commit();
        } else if (id == R.id.nav_perfil) {
            fragmentManager.beginTransaction()
                    .replace(R.id.contentFrame, new nav_perfilFragment())
                    .commit();
        } else if (id == R.id.nav_acercade) {
            fragmentManager.beginTransaction()
                    .replace(R.id.contentFrame, new nav_acercaDeFragment())
                    .commit();
        } else if (id == R.id.nav_salir) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 500);
        } else if (id == R.id.nav_sync) {
            Toast.makeText(getApplicationContext(),"Sincronizando...", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
