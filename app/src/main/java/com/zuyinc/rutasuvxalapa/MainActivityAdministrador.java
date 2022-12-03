package com.zuyinc.rutasuvxalapa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.zuyinc.rutasuvxalapa.fragmentos.administrador.AgregarCamionAdministrador;
import com.zuyinc.rutasuvxalapa.fragmentos.administrador.AgregarColoniaAdministrador;
import com.zuyinc.rutasuvxalapa.fragmentos.administrador.AgregarFacultadAdministrador;
import com.zuyinc.rutasuvxalapa.fragmentos.administrador.AgregarItinerarioAdministrador;
import com.zuyinc.rutasuvxalapa.fragmentos.administrador.AgregarParadaAdministrador;
import com.zuyinc.rutasuvxalapa.fragmentos.administrador.AgregarRutaAdministrador;
import com.zuyinc.rutasuvxalapa.fragmentos.administrador.ListarComentarioAdministrador;
import com.zuyinc.rutasuvxalapa.fragmentos.administrador.ModificarEliminarCamionAdministrador;
import com.zuyinc.rutasuvxalapa.fragmentos.administrador.ModificarEliminarColoniaAdministrador;
import com.zuyinc.rutasuvxalapa.fragmentos.administrador.ModificarEliminarFacultadAdministrador;
import com.zuyinc.rutasuvxalapa.fragmentos.administrador.ModificarEliminarItinerarioAdministrador;
import com.zuyinc.rutasuvxalapa.fragmentos.administrador.ModificarEliminarParadaAdministrador;
import com.zuyinc.rutasuvxalapa.fragmentos.administrador.ModificarEliminarRutaAdministrador;

public class MainActivityAdministrador extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    FirebaseAuth firebaseAuth;
    FirebaseUser fbaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_administrador);

        Toolbar toolbar = findViewById(R.id.toolbar_activity_main_administrador);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout_activity_main_administrador);

        NavigationView navigationView = findViewById(R.id.navigation_view_activity_main_administrador);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //fragmento por defecto
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_activity_main_administrador,
                    new ListarComentarioAdministrador()).commit();
            navigationView.setCheckedItem(R.id.opc_ListarComentarioAdministrador);
        }

        firebaseAuth = FirebaseAuth.getInstance();
        fbaseUser = firebaseAuth.getCurrentUser();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.opc_ListarComentarioAdministrador:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_activity_main_administrador,
                        new ListarComentarioAdministrador()).commit();
                break;

            case R.id.opc_AgregarColoniaAdministrador:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_activity_main_administrador,
                        new AgregarColoniaAdministrador()).commit();
                break;

            case R.id.opc_ModificarEliminarColoniaAdministrador:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_activity_main_administrador,
                        new ModificarEliminarColoniaAdministrador()).commit();
                break;

            case R.id.opc_AgregarParadaAdministrador:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_activity_main_administrador,
                        new AgregarParadaAdministrador()).commit();
                break;

            case R.id.opc_ModificarEliminarParadaAdministrador:ParadaAdministrador:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_activity_main_administrador,
                        new ModificarEliminarParadaAdministrador()).commit();
                break;

            case R.id.opc_AgregarFacultadAdministrador:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_activity_main_administrador,
                        new AgregarFacultadAdministrador()).commit();
                break;

            case R.id.opc_ModificarEliminarFacultadAdministrador:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_activity_main_administrador,
                        new ModificarEliminarFacultadAdministrador()).commit();
                break;


            case R.id.opc_AgregarCamionAdministrador:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_activity_main_administrador,
                        new AgregarCamionAdministrador()).commit();
                break;

            case R.id.opc_ModificarEliminarCamionAdministrador:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_activity_main_administrador,
                        new ModificarEliminarCamionAdministrador()).commit();
                break;

            case R.id.opc_AgregarRutaAdministrador:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_activity_main_administrador,
                        new AgregarRutaAdministrador()).commit();
                break;

            case R.id.opc_ModificarEliminarRutaAdministrador:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_activity_main_administrador,
                        new ModificarEliminarRutaAdministrador()).commit();
                break;

            case R.id.opc_AgregarItinerarioAdministrador:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_activity_main_administrador,
                        new AgregarItinerarioAdministrador()).commit();
                break;

            case R.id.opc_ModificarEliminarItinerarioAdministrador:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_activity_main_administrador,
                        new ModificarEliminarItinerarioAdministrador()).commit();
                break;

            case R.id.opc_CerrarSesion:
                cerrarSesion();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void cerrarSesion(){
        firebaseAuth.signOut();
        startActivity(new Intent(MainActivityAdministrador.this, MainActivity.class));
        Toast.makeText(this, "Sesion cerrada exitosamente", Toast.LENGTH_SHORT).show();
        finish();
    }
}