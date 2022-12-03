package com.zuyinc.rutasuvxalapa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Usuario;

public class Pantalla_De_Login extends AppCompatActivity {

    private static final String TAG_PRUEBAS = "PRUEBAS_X";
    private static final String PATH_USUARIO = "usuario";
    private Usuario usuarioActual;

    private String correo = "";
    private String password = "";

    private EditText et_CorreoLogin;
    private EditText et_PasswordLogin;
    private Button btn_Ingresar;
    private TextView txt_NuevoUsuario;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_de_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Iniciar Sesion");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        et_CorreoLogin = findViewById(R.id.et_CorreoLogin);
        et_PasswordLogin = findViewById(R.id.et_PasswordLogin);
        btn_Ingresar = findViewById(R.id.btn_Ingresar);
        txt_NuevoUsuario = findViewById(R.id.txt_NuevoUsuario);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceUsuario = firebaseDatabase.getReference(PATH_USUARIO);

        progressDialog = new ProgressDialog(Pantalla_De_Login.this);
        progressDialog.setTitle("Espere por favor");
        progressDialog.setCanceledOnTouchOutside(false);

        btn_Ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarDatos();
            }
        });

        txt_NuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Pantalla_De_Login.this, Pantalla_De_Registro.class));
            }
        });
    }

    private void validarDatos(){

        correo = et_CorreoLogin.getText().toString();
        password = et_PasswordLogin.getText().toString();

        if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            Toast.makeText(this, "Correo invalido", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Ingrese Contraseña",Toast.LENGTH_SHORT).show();
        }
        else{
            inicioDeUsuarioFirebase();
        }
    }

    //AQUI IRIA EL CAMBIO DE MENU CON EL TIPO DE USUARIO

    private void inicioDeUsuarioFirebase(){
        progressDialog.setMessage("Iniciando Sesion...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(correo, password)
                .addOnCompleteListener(Pantalla_De_Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            firebaseUser = firebaseAuth.getCurrentUser();
                            String usuarioUid = firebaseUser.getUid();
                            databaseReferenceUsuario.child(usuarioUid).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    usuarioActual = snapshot.getValue(Usuario.class);
                                    try{
                                        if(usuarioActual!=null){
                                            if(usuarioActual.getTipoUsuario().equals("administrador")){
                                                startActivity(new Intent(Pantalla_De_Login.this, MainActivityAdministrador.class));
                                            }else if(usuarioActual.getTipoUsuario().equals("cliente")){
                                                startActivity(new Intent(Pantalla_De_Login.this, MainActivityCliente.class));
                                            }
                                            Toast.makeText(Pantalla_De_Login.this, "Bienvenido(a): " + usuarioActual.getCorreo(), Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }catch (NullPointerException e){
                                        Log.i(TAG_PRUEBAS, e.getMessage());
                                        Toast.makeText(Pantalla_De_Login.this, "Algo no salio bien", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(Pantalla_De_Login.this, "Algo no salio bien", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(Pantalla_De_Login.this,"Verifique si el correo y contraseña son los correctos", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Pantalla_De_Login.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}