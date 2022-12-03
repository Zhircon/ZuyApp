package com.zuyinc.rutasuvxalapa.fragmentos.administrador;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zuyinc.rutasuvxalapa.R;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Colonia;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Parada;

import java.util.ArrayList;
import java.util.List;

public class ModificarEliminarParadaAdministrador extends Fragment {

    private static final String TAG_PRUEBAS = "PRUEBAS_X";
    private static final String PATH_PARADA = "parada";
    private static final String PATH_COLONIA = "colonia";

    Parada paradaSeleccionada;
    Colonia coloniaSeleccionadaGuardada;
    Colonia coloniaSeleccionadaNueva;

    Spinner spinnerParada;
    Spinner spinnerColonia;
    EditText etImagenUrl;
    EditText etCalleParada;
    EditText etLatitudParada;
    EditText etLongitudParada;
    Button btnModificar;
    Button btnEliminar;

    Context thisFragmentContext;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceParada;
    DatabaseReference databaseReferenceColonia;

    public ModificarEliminarParadaAdministrador() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modificar_eliminar_parada_administrador, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceParada = firebaseDatabase.getReference(PATH_PARADA);
        databaseReferenceColonia = firebaseDatabase.getReference(PATH_COLONIA);

        spinnerParada = view.findViewById(R.id.spinnerSelecParada_FragmentModificarEliminarParada);
        spinnerColonia = view.findViewById(R.id.spinnerSelecColonia_FragmentModificarEliminarParada);
        etImagenUrl = view.findViewById(R.id.et_UrlImagen_FragmentModificarEliminarParada);
        etCalleParada = view.findViewById(R.id.et_CalleParada_FragmentModificarEliminarParada);
        etLatitudParada = view.findViewById(R.id.et_Latitud_FragmentModificarEliminarParada);
        etLongitudParada = view.findViewById(R.id.et_Longitud_FragmentModificarEliminarParada);
        btnModificar = view.findViewById(R.id.btn_Modificar_FragmentModificarEliminarParada);
        btnEliminar = view.findViewById(R.id.btn_Eliminar_FragmentModificarEliminarParada);

        thisFragmentContext = etImagenUrl.getContext();

        actualizarSpinnerParada();

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(paradaSeleccionada != null){
                    actualizarParada();
                }else{
                    Toast.makeText(thisFragmentContext, "Seleccione una parada primero", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(paradaSeleccionada != null){
                    AlertDialog.Builder alertConfirmacion = new AlertDialog.Builder(thisFragmentContext);
                    alertConfirmacion.setMessage("Se eliminara la parada y esta accion no se puede deshacer ¿Desea continuar?").setCancelable(false)
                            .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    eliminarParada();
                                }
                            })
                            .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog popUpConfirmacion = alertConfirmacion.create();
                    popUpConfirmacion.setTitle("Por favor confirme la eliminacion");
                    popUpConfirmacion.show();
                }else{
                    Toast.makeText(thisFragmentContext, "Seleccione una parada primero", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void actualizarSpinnerParada(){
        databaseReferenceParada.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final List<Parada> paradas = new ArrayList<>();
                for (DataSnapshot paradaSnapShot : snapshot.getChildren()){
                    Parada paradaTmp = paradaSnapShot.getValue(Parada.class);
                    paradaTmp.setUidParada(paradaSnapShot.getKey());
                    paradas.add(paradaTmp);
                }
                ArrayAdapter<Parada> adapterParadas = new ArrayAdapter<Parada>(thisFragmentContext, android.R.layout.simple_spinner_item, paradas);
                adapterParadas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerParada.setAdapter(adapterParadas);
                spinnerParada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        paradaSeleccionada = (Parada) parent.getSelectedItem();
                        actualizarSpinnerColonia();
                        etImagenUrl.setText(String.valueOf(paradaSeleccionada.getUrlImagenParada()));
                        etCalleParada.setText(String.valueOf(paradaSeleccionada.getNombreCalle()));
                        etLatitudParada.setText(String.valueOf(paradaSeleccionada.getLatitud()));
                        etLongitudParada.setText(String.valueOf(paradaSeleccionada.getLongitud()));
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
    }

    private void actualizarSpinnerColonia(){
        databaseReferenceColonia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final List<Colonia> colonias = new ArrayList<>();
                for (DataSnapshot coloniaSnapshot : snapshot.getChildren()){
                    Colonia coloniaTmp = coloniaSnapshot.getValue(Colonia.class);
                    coloniaTmp.setUidColonia(coloniaSnapshot.getKey());
                    colonias.add(coloniaTmp);
                    try{
                        if(coloniaTmp.getUidColonia().equals(paradaSeleccionada.getUidColonia())){
                            coloniaSeleccionadaGuardada = coloniaTmp;
                        }
                    }catch (NullPointerException e){

                    }
                }
                int poscicionSeleccion = colonias.indexOf(coloniaSeleccionadaGuardada);
                ArrayAdapter<Colonia> adapterColonias = new ArrayAdapter<Colonia>(thisFragmentContext, android.R.layout.simple_spinner_item, colonias);
                adapterColonias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerColonia.setAdapter(adapterColonias);
                spinnerColonia.setSelection(poscicionSeleccion);
                spinnerColonia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        coloniaSeleccionadaNueva = (Colonia) parent.getSelectedItem();
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
    }

    private void actualizarParada(){
        if(!TextUtils.isEmpty(etImagenUrl.getText().toString().trim()) || !TextUtils.isEmpty(etCalleParada.getText().toString().trim()) || TextUtils.isEmpty(etLatitudParada.getText().toString().trim()) || TextUtils.isEmpty(etLongitudParada.getText().toString().trim())){
            try{
                paradaSeleccionada.setUidColonia(coloniaSeleccionadaNueva.getUidColonia());
                paradaSeleccionada.setUrlImagenParada(etImagenUrl.getText().toString());
                paradaSeleccionada.setLatitud(Float.parseFloat(etLatitudParada.getText().toString()));
                paradaSeleccionada.setLongitud(Float.parseFloat(etLongitudParada.getText().toString()));
                paradaSeleccionada.setNombreCalle(etCalleParada.getText().toString());
                String uidObjetoActualizar = paradaSeleccionada.getUidParada();
                paradaSeleccionada.setUidParada(null);
                databaseReferenceParada.child(uidObjetoActualizar).setValue(paradaSeleccionada);
                Toast.makeText(thisFragmentContext, "Actualizado", Toast.LENGTH_SHORT).show();
            }catch (NumberFormatException e){
                Toast.makeText(thisFragmentContext, "Ingreso caracter o un numero no valido en la latitud o longitud", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void eliminarParada(){
        String uidParadaEliminar = paradaSeleccionada.getUidParada();
        databaseReferenceParada.child(uidParadaEliminar).removeValue();
        uidParadaEliminar  = null;
        paradaSeleccionada = null;
        coloniaSeleccionadaGuardada = null;
        coloniaSeleccionadaNueva = null;
        etLongitudParada.setText(null);
        etLatitudParada.setText(null);
        etCalleParada.setText(null);
        etImagenUrl.setText(null);
    }

}