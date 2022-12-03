package com.zuyinc.rutasuvxalapa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Usuario;

import java.util.HashMap;

public class Pantalla_De_Registro extends AppCompatActivity {

    private static final String PATH_USUARIO = "usuario";

    private String nombreUsuario = " ";
    private String correo = " ";
    private String password = " ";
    private String confirmarPassword = " ";
    private String tipoUsuario = " ";

    private EditText et_NombreUsuario;
    private EditText et_Correo;
    private EditText et_Contraseña;
    private EditText et_ConfirmarContraseña;
    private Button btn_RegistrarUsuario;
    private TextView txt_TengoUnaCuenta;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_de_registro);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Registrar");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        et_NombreUsuario = findViewById(R.id.et_NombreUsuario);
        et_Correo = findViewById(R.id.et_Correo);
        et_Contraseña = findViewById(R.id.et_Contraseña);
        et_ConfirmarContraseña = findViewById(R.id.et_ConfirmarContraseña);
        btn_RegistrarUsuario = findViewById(R.id.btn_RegistrarUsuario);
        txt_TengoUnaCuenta = findViewById(R.id.txt_TengoUnaCuenta);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(Pantalla_De_Registro.this);
        progressDialog.setTitle("Espere por favor");
        progressDialog.setCanceledOnTouchOutside(false);

        btn_RegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //
                validarDatos();
            }
        });

        txt_TengoUnaCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                startActivity(new Intent(Pantalla_De_Registro.this, Pantalla_De_Login.class));
            }
        });
    }

    private void validarDatos(){
        nombreUsuario = et_NombreUsuario.getText().toString();
        correo = et_Correo.getText().toString();
        password = et_Contraseña.getText().toString();
        confirmarPassword = et_ConfirmarContraseña.getText().toString();

        if(TextUtils.isEmpty(nombreUsuario)){
            Toast.makeText(this, "Ingrese nombre", Toast.LENGTH_SHORT).show();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            Toast.makeText(this, "Ingrese correo", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Ingrese contraseña", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(confirmarPassword)) {
            Toast.makeText(this, "Confirma contraseña", Toast.LENGTH_SHORT).show();
        }
        else if(!password.equals(confirmarPassword)){
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }
        else{
            crearCuenta();
        }
    }

    private void crearCuenta(){
        progressDialog.setMessage("Creando su cuenta...");
        progressDialog.show();

        //Crear un usuario en FireBase
        firebaseAuth.createUserWithEmailAndPassword(correo, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //
                        guardarInformacion();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Pantalla_De_Registro.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void guardarInformacion(){
        progressDialog.setMessage("Guardando su informacion");
        progressDialog.dismiss();
        tipoUsuario = "cliente";

        firebaseUser = firebaseAuth.getCurrentUser();

        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(nombreUsuario).build();
        firebaseUser.updateProfile(profileChangeRequest);

        Usuario usuarioNuevo = new Usuario(correo, nombreUsuario, password, tipoUsuario);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(PATH_USUARIO);
        databaseReference.child(firebaseUser.getUid())
                .setValue(usuarioNuevo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(Pantalla_De_Registro.this, "Cuenta creada con exito", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Pantalla_De_Registro.this, MainActivityCliente.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Pantalla_De_Registro.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}