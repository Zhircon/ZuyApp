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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zuyinc.rutasuvxalapa.R;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Colonia;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Facultad;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Parada;

import java.util.ArrayList;
import java.util.List;

public class AgregarParadaAdministrador extends Fragment {

    private static final String PATH_PARADA = "parada";
    private static final String PATH_COLONIA = "colonia";

    String nombreCalle;
    float latitud;
    float longitud;
    String urlImagenParada;
    Colonia coloniaSeleccionada;

    Context thisFragmentContext;
    EditText etUrlImagen;
    Spinner spinnerColonia;
    EditText etCalleParada;
    EditText etLatitudParada;
    EditText etLongitudParada;
    Button btnGuardar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceColonia;
    DatabaseReference databaseReferenceParada;

    public AgregarParadaAdministrador() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agregar_parada_administrador, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceColonia = firebaseDatabase.getReference(PATH_COLONIA);

        etUrlImagen = view.findViewById(R.id.et_UrlImagen_FragmentAgregarParada);
        etCalleParada = view.findViewById(R.id.et_CalleParada_FragmentAgregarParada);
        etLatitudParada = view.findViewById(R.id.et_Latitud_FragmentAgregarParada);
        etLongitudParada = view.findViewById(R.id.et_Longitud_FragmentAgregarParada);
        spinnerColonia = view.findViewById(R.id.spinnerColonia_FragmentAgregarParada);
        btnGuardar = view.findViewById(R.id.btn_Guardar_FragmentAgregarParada);

        thisFragmentContext = spinnerColonia.getContext();

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
            urlImagenParada = etUrlImagen.getText().toString();
            nombreCalle = etCalleParada.getText().toString();

            if(TextUtils.isEmpty(urlImagenParada)){
                Toast.makeText(thisFragmentContext, "Ingrese la direccion de url", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(nombreCalle)) {
                Toast.makeText(thisFragmentContext, "Ingrese el nombre de la calle", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(etLatitudParada.getText().toString())) {
                Toast.makeText(thisFragmentContext, "Ingrese la latitud de la parada", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(etLongitudParada.getText().toString())) {
                Toast.makeText(thisFragmentContext, "Ingrese la longitud de la parada", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(coloniaSeleccionada.getUidColonia())){
                Toast.makeText(thisFragmentContext, "Seleccione la colonia desde el spinner", Toast.LENGTH_SHORT).show();
            }
            else{
                latitud = Float.parseFloat(etLatitudParada.getText().toString());
                longitud = Float.parseFloat(etLongitudParada.getText().toString());
                agregarParada();
                etUrlImagen.setText(null);
                etCalleParada.setText(null);
                etLatitudParada.setText(null);
                etLongitudParada.setText(null);
            }
        }catch (NumberFormatException e){
            Toast.makeText(thisFragmentContext, "Ingreso caracter o un numero no valido en los puntos de latitud o longitud", Toast.LENGTH_SHORT).show();
        }
    }

    private void agregarParada(){
        Parada parada = new Parada(nombreCalle, latitud, longitud, urlImagenParada, coloniaSeleccionada.getUidColonia());
        databaseReferenceParada = firebaseDatabase.getReference(PATH_PARADA);
        databaseReferenceParada.push().setValue(parada);
        Toast.makeText(getActivity().getApplicationContext(), "Se ha agregado la parada exitosamente", Toast.LENGTH_SHORT).show();
    }
}