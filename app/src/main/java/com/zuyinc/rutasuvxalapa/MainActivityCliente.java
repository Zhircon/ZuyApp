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
import com.zuyinc.rutasuvxalapa.fragmentos.cliente.AcercaDeCliente;
import com.zuyinc.rutasuvxalapa.fragmentos.cliente.AgregarComentarioCliente;
import com.zuyinc.rutasuvxalapa.fragmentos.cliente.ListarCamionCliente;
import com.zuyinc.rutasuvxalapa.fragmentos.cliente.ListarFacultadCliente;
import com.zuyinc.rutasuvxalapa.fragmentos.cliente.ListarRutaCliente;
import com.zuyinc.rutasuvxalapa.fragmentos.cliente.PerfilUsuarioCliente;

public class MainActivityCliente extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    FirebaseAuth firebaseAuth;
    FirebaseUser fbaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cliente);

        Toolbar toolbar = findViewById(R.id.toolbar_activity_main_cliente);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout_activity_main_cliente);

        NavigationView navigationView = findViewById(R.id.navigation_view_activity_main_cliente);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //fragmento por defecto
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_activity_main_cliente,
                    new PerfilUsuarioCliente()).commit();
            navigationView.setCheckedItem(R.id.opc_PerfilCliente);
        }

        firebaseAuth = FirebaseAuth.getInstance();
        fbaseUser = firebaseAuth.getCurrentUser();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){

            case R.id.opc_PerfilCliente:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_activity_main_cliente,
                        new PerfilUsuarioCliente()).commit();
                break;

            case R.id.opc_ListarRutaCliente:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_activity_main_cliente,
                        new ListarRutaCliente()).commit();
                break;

            case R.id.opc_ListarFacultadCliente:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_activity_main_cliente,
                        new ListarFacultadCliente()).commit();
                break;

            case R.id.opc_ListarCamionCliente:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_activity_main_cliente,
                        new ListarCamionCliente()).commit();
                break;

            case R.id.opc_AgregarComentarioCliente:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_activity_main_cliente,
                        new AgregarComentarioCliente()).commit();
                break;

            case R.id.opc_CerrarSesion:
                cerrarSesion();
                break;

            case R.id.opc_CompartirAplicacion:
                        Toast.makeText(this, "TEXTO TEMPORAL DE COMPARTIR APLICACION", Toast.LENGTH_SHORT).show();
                break;

            case R.id.opc_AcercaDe:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_activity_main_cliente,
                        new AcercaDeCliente()).commit();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void cerrarSesion(){
        firebaseAuth.signOut();
        startActivity(new Intent(MainActivityCliente.this, MainActivity.class));
        Toast.makeText(this, "Sesion cerrada exitosamente", Toast.LENGTH_SHORT).show();
        finish();
    }
}