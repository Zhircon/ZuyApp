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
import com.zuyinc.rutasuvxalapa.modelo.entidades.Camion;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Colonia;

public class AgregarCamionAdministrador extends Fragment {

    private static final String PATH_CAMION = "camion";

    int numeroCamion;
    String colorCamion;
    String urlImagen;

    Context thisFragmentContext;
    EditText etUrlImagen;
    EditText etNumeroCamion;
    EditText etColorCamion;
    Button btnGuardar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public AgregarCamionAdministrador() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agregar_camion_administrador, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(PATH_CAMION);

        etUrlImagen = view.findViewById(R.id.et_UrlImagen_FragmentAgregarCamion);
        etNumeroCamion = view.findViewById(R.id.et_NumeroCamion_FragmentAgregarCamion);
        etColorCamion = view.findViewById(R.id.et_ColorCamion_FragmentAgregarCamion);
        btnGuardar = view.findViewById(R.id.btn_Guardar_FragmentAgregarCamion);

        thisFragmentContext = btnGuardar.getContext();

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
            colorCamion = etColorCamion.getText().toString();
            urlImagen = etUrlImagen.getText().toString();
            numeroCamion = Integer.parseInt(etNumeroCamion.getText().toString());

            if(TextUtils.isEmpty(colorCamion)){
                Toast.makeText(getActivity().getBaseContext(), "Ingrese el color del camion", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(urlImagen)){
                Toast.makeText(getActivity().getBaseContext(), "Ingrese la url de la imagen del camion", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(etNumeroCamion.getText().toString())){
                Toast.makeText(getActivity().getBaseContext(), "Ingrese el numero del camion", Toast.LENGTH_SHORT).show();
            }
            else if(numeroCamion <=0 ){
                Toast.makeText(getActivity().getBaseContext(), "El numero de camion no puede ser menor o igual a cero", Toast.LENGTH_SHORT).show();
            }
            else{
                agregarCamion();
                etNumeroCamion.setText(null);
                etColorCamion.setText(null);
                etUrlImagen.setText(null);
            }
        }catch(NumberFormatException e){
            Toast.makeText(getActivity().getBaseContext(), "Ingreso caracter o un numero no valido en el numero de camion", Toast.LENGTH_SHORT).show();
        }
    }

    private void agregarCamion(){
        Camion camion = new Camion(numeroCamion, colorCamion, urlImagen);
        databaseReference.push().setValue(camion);
        Toast.makeText(getActivity().getApplicationContext(), "Se agrego el camion correctamente", Toast.LENGTH_SHORT).show();
    }
}