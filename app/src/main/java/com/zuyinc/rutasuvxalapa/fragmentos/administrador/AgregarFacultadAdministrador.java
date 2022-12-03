package com.zuyinc.rutasuvxalapa.fragmentos.administrador;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zuyinc.rutasuvxalapa.R;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Colonia;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Facultad;

import java.util.ArrayList;
import java.util.List;

public class AgregarFacultadAdministrador extends Fragment {

    private static final String PATH_FACULTAD = "facultad";
    private static final String PATH_COLONIA = "colonia";

    String urlImagen;
    String nombreFacultad;
    String calleFacultad;
    Long numeroTelefono;
    String correo;
    String sitioWeb;
    Colonia coloniaSeleccionada;

    Context thisFragmentContext;
    EditText etUrlImagen;
    EditText etNombreFacultad;
    EditText etCalleFacultad;
    EditText etnumeroTelefono;
    EditText etCorreo;
    EditText etSitioWeb;
    Spinner spinnerColonia;
    Button btnGuardar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceColonia;
    DatabaseReference databaseReferenceFacultad;

    public AgregarFacultadAdministrador() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_agregar_facultad_administrador, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceColonia = firebaseDatabase.getReference(PATH_COLONIA);

        etUrlImagen = view.findViewById(R.id.et_UrlImagen_FragmentAgregarFacultad);
        etNombreFacultad = view.findViewById(R.id.et_NombreFacultad_FragmentAgregarFacultad);
        etCalleFacultad = view.findViewById(R.id.et_CalleFacultad_FragmentAgregarFacultad);
        etnumeroTelefono = view.findViewById(R.id.et_NumeroTelefono_FragmentAgregarFacultad);
        etCorreo = view.findViewById(R.id.et_Correo_FragmentAgregarFacultad);
        etSitioWeb = view.findViewById(R.id.et_SitioWeb_FragmentAgregarFacultad);
        spinnerColonia = view.findViewById(R.id.spinnerColonia_FragmentAgregarFacultad);
        btnGuardar = view.findViewById(R.id.btn_Guardar_FragmentAgregarFacultad);

        thisFragmentContext = etUrlImagen.getContext();

        databaseReferenceColonia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final List<Colonia> colonias = new ArrayList<>();
                for (DataSnapshot coloniaSnapshot : snapshot.getChildren()){
                    Colonia coloniaTmp = coloniaSnapshot.getValue(Colonia.class);
                    coloniaTmp.setUidColonia(coloniaSnapshot.getKey());
                    colonias.add(coloniaTmp);
                }
                ArrayAdapter<Colonia> adapterColonias = new ArrayAdapter<Colonia>(thisFragmentContext, android.R.layout.simple_spinner_item, colonias);
                adapterColonias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerColonia.setAdapter(adapterColonias);
                spinnerColonia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        coloniaSeleccionada = (Colonia)parent.getSelectedItem();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                validarDatos();
            }
        });

        return view;
    }

    private void validarDatos(){

        try{
            urlImagen = etUrlImagen.getText().toString();
            nombreFacultad = etNombreFacultad.getText().toString();
            calleFacultad = etCalleFacultad.getText().toString();
            numeroTelefono = Long.parseLong(etnumeroTelefono.getText().toString());
            correo = etCorreo.getText().toString();
            sitioWeb = etSitioWeb.getText().toString();

            if(TextUtils.isEmpty(urlImagen)){
                Toast.makeText(getActivity().getBaseContext(), "Ingrese la direccion de url", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(nombreFacultad)) {
                Toast.makeText(getActivity().getBaseContext(), "Ingrese el nombre de la facultad", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(calleFacultad)) {
                Toast.makeText(getActivity().getBaseContext(), "Ingrese la calle de la facultad", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(etnumeroTelefono.getText().toString())) {
                Toast.makeText(getActivity().getBaseContext(), "Ingrese el numero de telefono", Toast.LENGTH_SHORT).show();
            }
            else if(numeroTelefono <= 0){
                Toast.makeText(getActivity().getBaseContext(), "El numero de telefono no puede ser menor o igual a cero", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(correo)){
                Toast.makeText(getActivity().getBaseContext(), "Ingrese el correo de la facultad", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(sitioWeb)){
                Toast.makeText(getActivity().getBaseContext(), "Ingrese el sitio web de la facultad", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(coloniaSeleccionada.getUidColonia())){
                Toast.makeText(getActivity().getBaseContext(), "Seleccione la colonia desde el spinner", Toast.LENGTH_SHORT).show();
            }
            else{
                agregarFacultad();
                etUrlImagen.setText(null);
                etNombreFacultad.setText(null);
                etCalleFacultad.setText(null);
                etnumeroTelefono.setText(null);
                etCorreo.setText(null);
                etSitioWeb.setText(null);
            }
        }catch (NumberFormatException e){
            Toast.makeText(getActivity().getBaseContext(), "Ingreso caracter o un numero no valido en el numero de telefono", Toast.LENGTH_SHORT).show();
        }
    }

    private void agregarFacultad(){
        Facultad facultad = new Facultad(nombreFacultad, calleFacultad, numeroTelefono, correo, sitioWeb, urlImagen, coloniaSeleccionada.getUidColonia());
        databaseReferenceFacultad = firebaseDatabase.getReference(PATH_FACULTAD);
        databaseReferenceFacultad.push().setValue(facultad);
        Toast.makeText(getActivity().getApplicationContext(), "Se ha agregado la facultad exitosamente", Toast.LENGTH_SHORT).show();
    }

}