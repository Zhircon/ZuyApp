package com.zuyinc.rutasuvxalapa.fragmentos.administrador;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.database.Logger;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.zuyinc.rutasuvxalapa.R;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Colonia;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Facultad;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Parada;

import java.util.ArrayList;
import java.util.List;

public class ModificarEliminarColoniaAdministrador extends Fragment {

    private static final String TAG_PRUEBA_FIREBASE = "PRUEBA_FIREBASE";
    private static final String PATH_COLONIA = "colonia";
    private static final String PATH_FACULTAD = "facultad";
    private static final String PATH_PARADA = "parada";

    Colonia coloniaSeleccionada;

    Context thisFragmentContext;
    Spinner spinnerColonia;
    EditText etNombreColonia;
    EditText etCodigoPostal;
    Button btnModificar;
    Button btnEliminar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceColonia;
    DatabaseReference databaseReferenceFacultad;
    DatabaseReference databaseReferenceParada;


    public ModificarEliminarColoniaAdministrador() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modificar_eliminar_colonia_administrador, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceColonia = firebaseDatabase.getReference(PATH_COLONIA);
        databaseReferenceFacultad = firebaseDatabase.getReference(PATH_FACULTAD);
        databaseReferenceParada = firebaseDatabase.getReference(PATH_PARADA);

        spinnerColonia = view.findViewById(R.id.spinner_FragmentModificarEliminarColonia);
        etNombreColonia = view.findViewById(R.id.et_Nombre_FragmentModificarEliminarColonia);
        etCodigoPostal = view.findViewById(R.id.et_CP_FragmentModificarEliminarColonia);
        btnModificar = view.findViewById(R.id.btn_Modificar_FragmentModificarEliminarColonia);
        btnEliminar = view.findViewById(R.id.btn_Eliminar_FragmentModificarEliminarColonia);

        thisFragmentContext = etCodigoPostal.getContext();

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
                        coloniaSeleccionada = (Colonia) parent.getSelectedItem();
                        etNombreColonia.setText(String.valueOf(coloniaSeleccionada.getNombre()));
                        etCodigoPostal.setText(String.valueOf(coloniaSeleccionada.getCodigoPostal()));
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

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(coloniaSeleccionada != null){
                    actualizar();
                }else{
                    Toast.makeText(thisFragmentContext, "Seleccione un elemento primero", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(coloniaSeleccionada != null){
                    AlertDialog.Builder alertConfirmacion = new AlertDialog.Builder(thisFragmentContext);
                    alertConfirmacion.setMessage("Eliminar la colonia eliminara tambien las facultades y paradas asociadas a ella... ¿Desea continuar?").setCancelable(false)
                            .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    eliminar();
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
                    Toast.makeText(thisFragmentContext, "Seleccione un elemento primero", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void actualizar(){
        if(!TextUtils.isEmpty(etNombreColonia.getText().toString().trim()) || !TextUtils.isEmpty(etCodigoPostal.getText().toString().trim())){
            try{
                if(Integer.parseInt(etCodigoPostal.getText().toString()) <=0){
                    Toast.makeText(thisFragmentContext, "El codigo postal de la colonia no puede ser menor o igual a cero", Toast.LENGTH_SHORT).show();
                }else{
                    coloniaSeleccionada.setNombre(etNombreColonia.getText().toString());
                    coloniaSeleccionada.setCodigoPostal(Integer.parseInt(etCodigoPostal.getText().toString()));
                    String uidObjetoActualizar = coloniaSeleccionada.getUidColonia();
                    coloniaSeleccionada.setUidColonia(null);
                    databaseReferenceColonia.child(uidObjetoActualizar).setValue(coloniaSeleccionada);
                    Toast.makeText(thisFragmentContext, "Actualizado", Toast.LENGTH_SHORT).show();
                }
            }catch (NumberFormatException e){
                Toast.makeText(thisFragmentContext, "Ingreso caracter o un numero no valido en el numero de telefono", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void eliminar(){
        String uidColoniaEliminar = coloniaSeleccionada.getUidColonia();
        databaseReferenceColonia.child(uidColoniaEliminar).removeValue();
        actualizarFacultades(uidColoniaEliminar);
        actualizarParadas(uidColoniaEliminar);
        uidColoniaEliminar = null;
        coloniaSeleccionada = null;
        etNombreColonia.setText(null);
        etCodigoPostal.setText(null);
        Toast.makeText(thisFragmentContext, "Eliminado", Toast.LENGTH_SHORT).show();
    }

    private void actualizarFacultades(String uidColoniaEliminada){
        Query queryFacultad = databaseReferenceFacultad.orderByChild("uidColonia").equalTo(uidColoniaEliminada);
        Log.i(TAG_PRUEBA_FIREBASE,"QUERY FACULTAD : " + queryFacultad.getSpec().toString());
        queryFacultad.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot facultadSnapshot : snapshot.getChildren()){
                    Log.i(TAG_PRUEBA_FIREBASE,"REFERENCIA 1 : " + databaseReferenceFacultad.getPath().toString());
                    String uidObjetoEliminar = facultadSnapshot.getKey();
                    Log.i(TAG_PRUEBA_FIREBASE,"Key del objeto a actualizar" + uidObjetoEliminar);
                    databaseReferenceFacultad.child(uidObjetoEliminar).removeValue();
                    Log.i(TAG_PRUEBA_FIREBASE,"REFERENCIA 3 : " + databaseReferenceFacultad.getPath().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void actualizarParadas(String uidColoniaEliminada){
        Query queryParada = databaseReferenceParada.orderByChild("uidColonia").equalTo(uidColoniaEliminada);
        Log.i(TAG_PRUEBA_FIREBASE,"QUERY PARADA : " + queryParada.getSpec().toString());
        queryParada.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot paradaSnapshot : snapshot.getChildren()){
                    Log.i(TAG_PRUEBA_FIREBASE,"REFERENCIA PARADA 1 : " + databaseReferenceParada.getPath().toString());
                    String uidObjetoEliminar = paradaSnapshot.getKey();
                    Log.i(TAG_PRUEBA_FIREBASE,"Key del objeto a actualizar" + uidObjetoEliminar);
                    databaseReferenceParada.child(uidObjetoEliminar).removeValue();
                    Log.i(TAG_PRUEBA_FIREBASE,"REFERENCIA 3 : " + databaseReferenceParada.getPath().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}