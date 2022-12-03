package com.zuyinc.rutasuvxalapa.fragmentos.cliente;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zuyinc.rutasuvxalapa.R;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Comentario;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class AgregarComentarioCliente extends Fragment{

    private static final String PATH_COMENTARIO = "comentario";
    String tipoComentario;
    String decripcionComentario;
    String uidUsuario;
    String fechaComentario;

    Context thisFragmentContext;
    EditText etmComentario;
    Spinner spinCatComentario;
    Button btnGuardar;

    FirebaseAuth firebaseAuth;
    FirebaseUser usuarioActual;
    FirebaseDatabase databaseFirebase;
    DatabaseReference referenceDatabase;

    String[] categoriaComentario = {"Fallo de la aplicacion(Crash/Bug)", "Solicitud de caracterisitica", "Fallo en la informacion mostrada(Ruta/camion/facultad)", "Otra"};

    public AgregarComentarioCliente() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agregar_comentario_cliente, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        usuarioActual = firebaseAuth.getCurrentUser();

        databaseFirebase = FirebaseDatabase.getInstance();
        referenceDatabase = databaseFirebase.getReference(PATH_COMENTARIO);

        etmComentario = view.findViewById(R.id.etm_Fragment_ComentarioCliente);
        spinCatComentario = view.findViewById(R.id.spinner_FragmentCatComentarioCliente);
        btnGuardar = view.findViewById(R.id.btn_Guardar_FragmentAgregarColonia);

        thisFragmentContext = etmComentario.getContext();

        ArrayAdapter<String> adapterCategoriaComentario = new ArrayAdapter<String>(thisFragmentContext, android.R.layout.simple_spinner_item, categoriaComentario);
        adapterCategoriaComentario.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCatComentario.setAdapter(adapterCategoriaComentario);

        spinCatComentario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoComentario = (String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarDatos();
            }
        });
        return view;
    }

    private void validarDatos(){
        //Obtener los datos
        decripcionComentario = etmComentario.getText().toString();
        fechaComentario = new SimpleDateFormat("dd-MM-yyyy/HH:mm:ss a", Locale.getDefault()).format(System.currentTimeMillis());
        uidUsuario = usuarioActual.getUid();

        if(TextUtils.isEmpty(decripcionComentario)){
            Toast.makeText(getActivity().getBaseContext(), "Ingrese comentarios", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(uidUsuario)) {
            Toast.makeText(getActivity().getBaseContext(), "No se pudo obtener la informacion de su usuario", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(tipoComentario)) {
            Toast.makeText(getActivity().getBaseContext(), "Seleccione el tipo de comentario", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(fechaComentario)){
            Toast.makeText(getActivity().getBaseContext(), "No se pudo obtener la fecha del sistema", Toast.LENGTH_SHORT).show();
        }
        else{
            agregarComentario();
            etmComentario.setText(null);
        }
    }

    private void agregarComentario(){
        Comentario comentario = new Comentario(decripcionComentario, tipoComentario, fechaComentario, uidUsuario);
        referenceDatabase.push().setValue(comentario);
        Toast.makeText(getActivity().getApplicationContext(), "Se ha agregado el comentario exitosamente", Toast.LENGTH_SHORT).show();
    }

}