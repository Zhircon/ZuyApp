package com.zuyinc.rutasuvxalapa.fragmentos.administrador;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zuyinc.rutasuvxalapa.R;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Colonia;

public class AgregarColoniaAdministrador extends Fragment {

    private static final String PATH_COLONIA = "colonia";
    String nombreColonia;
    int codigoPostal;

    Context thisFragmentContext;
    EditText etNombreColonia;
    EditText etcodigoPostal;
    Button btnGuardar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public AgregarColoniaAdministrador() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_agregar_colonia_administrador, container, false);
    firebaseDatabase = FirebaseDatabase.getInstance();
    databaseReference = firebaseDatabase.getReference(PATH_COLONIA);

    etNombreColonia = view.findViewById(R.id.et_Nombre_FragmentAgregarColonia);
    etcodigoPostal = view.findViewById(R.id.et_CP_FragmentAgregarColonia);
    btnGuardar = view.findViewById(R.id.btn_Guardar_FragmentAgregarColonia);

    thisFragmentContext = etNombreColonia.getContext();

    btnGuardar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            validarDatos();
        }
    });

    return view;
    }

    public void validarDatos(){

        try{

            nombreColonia = etNombreColonia.getText().toString();
            codigoPostal = Integer.parseInt(etcodigoPostal.getText().toString());

            if(TextUtils.isEmpty(nombreColonia)){
                Toast.makeText(getActivity().getBaseContext(), "Ingrese el nombre de la colonia", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(etcodigoPostal.getText().toString())){
                Toast.makeText(getActivity().getBaseContext(), "Ingrese el codigo postal", Toast.LENGTH_SHORT).show();
            }
            else if(codigoPostal <= 0){
                Toast.makeText(getActivity().getBaseContext(), "El codigo postal no puede ser menor o igual a cero", Toast.LENGTH_SHORT).show();
            }
            else{
                agregarColonia();
                etNombreColonia.setText(null);
                etcodigoPostal.setText(null);
            }
        }catch (NumberFormatException e){
            Toast.makeText(getActivity().getBaseContext(), "Ingreso caracter o un numero no valido en el codigo postal", Toast.LENGTH_SHORT).show();
        }

    }

    private void agregarColonia(){
        Colonia colonia = new Colonia(nombreColonia, codigoPostal);
        databaseReference.push().setValue(colonia);
        Toast.makeText(getActivity().getApplicationContext(), "Se agrego la colonia correctamente", Toast.LENGTH_SHORT).show();
    }
}