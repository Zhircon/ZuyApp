package com.zuyinc.rutasuvxalapa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Usuario;

public class Pantalla_De_Carga extends AppCompatActivity {

    private static final String PATH_USUARIO = "usuario";
    private Usuario usuarioActual;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_de_carga);

        firebaseAuth = FirebaseAuth.getInstance();

        final int DURACION = 3000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*
                startActivity(new Intent(Pantalla_De_Carga.this, MainActivity.class));
                finish();
                 */
                verificarUsuario();
            }
        },DURACION);
    }

    //AQUI SE DARIA EL CAMBIO DE MENU CLIENTE COLABORADOR UNA VEZ SE OBTENGA TIPO DE USUARIO
    private void verificarUsuario(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser == null){
            startActivity(new Intent(Pantalla_De_Carga.this, MainActivity.class));
            finish();
        }else{
            String usuarioUid = firebaseUser.getUid();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReferenceUsuario = firebaseDatabase.getReference(PATH_USUARIO);
            databaseReferenceUsuario.child(usuarioUid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    usuarioActual = snapshot.getValue(Usuario.class);
                    try{
                        if(usuarioActual!=null){
                            if(usuarioActual.getTipoUsuario().equals("administrador")){
                                startActivity(new Intent(Pantalla_De_Carga.this, MainActivityAdministrador.class));
                            }else if(usuarioActual.getTipoUsuario().equals("cliente")){
                                startActivity(new Intent(Pantalla_De_Carga.this, MainActivityCliente.class));
                            }
                            Toast.makeText(Pantalla_De_Carga.this, "Bienvenido(a): " + usuarioActual.getCorreo(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }catch (NullPointerException e){
                        Toast.makeText(Pantalla_De_Carga.this, "Algo no salio bien", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Pantalla_De_Carga.this, "Algo no salio bien", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}