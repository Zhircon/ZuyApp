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

public class ModificarEliminarFacultadAdministrador extends Fragment {

    private static final String TAG_PRUEBAS   = "PRUEBAS_X";
    private static final String PATH_FACULTAD = "facultad";
    private static final String PATH_COLONIA  = "colonia";

    Facultad facultadSeleccionada;
    Colonia coloniaSeleccionadaGuardada;
    Colonia coloniaSeleccionadaNueva;

    Spinner spinnerFacultad;
    Spinner spinnerColonia;
    EditText etImagenUrl;
    EditText etNombreFacultad;
    EditText etCalleFacultad;
    EditText etNumeroTelefono;
    EditText etCorreo;
    EditText etSitioWeb;
    Button btnModificar;
    Button btnEliminar;

    Context thisFragmentContext;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceFacultad;
    DatabaseReference databaseReferenceColonia;

    public ModificarEliminarFacultadAdministrador() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modificar_eliminar_facultad_administrador, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceFacultad = firebaseDatabase.getReference(PATH_FACULTAD);
        databaseReferenceColonia  = firebaseDatabase.getReference(PATH_COLONIA);

        spinnerFacultad = view.findViewById(R.id.spinnerSelecFac_FragmentModificarEliminarFacultad);
        spinnerColonia  = view.findViewById(R.id.spinnerColonia_FragmentModificarEliminarFacultad);
        etImagenUrl = view.findViewById(R.id.et_UrlImagen_FragmentModificarEliminarFacultad);
        etNombreFacultad = view.findViewById(R.id.et_NombreFacultad_FragmentModificarEliminarFacultad);
        etCalleFacultad = view.findViewById(R.id.et_CalleFacultad_FragmentModificarEliminarFacultad);
        etNumeroTelefono = view.findViewById(R.id.et_NumeroTelefono_FragmentModififcarEliminarFacultad);
        etCorreo = view.findViewById(R.id.et_Correo_FragmentModificarEliminarFacultad);
        etSitioWeb = view.findViewById(R.id.et_SitioWeb_FragmentModificarEliminarFacultad);
        btnModificar = view.findViewById(R.id.btn_Modificar_FragmentModificarEliminarFacultad);
        btnEliminar = view.findViewById(R.id.btn_Eliminar_FragmentModificarEliminarFacultad);

        thisFragmentContext = etSitioWeb.getContext();

        actualizarSpinnerFacultad();

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(facultadSeleccionada != null){
                    actualizarFacultad();
                }else{
                    Toast.makeText(thisFragmentContext, "Seleccione una facultad primero", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(facultadSeleccionada != null){
                    AlertDialog.Builder alertConfirmacion = new AlertDialog.Builder(thisFragmentContext);
                    alertConfirmacion.setMessage("Se eliminara la facultad y esta accion no se puede deshacer ¿Desea continuar?").setCancelable(false).
                            setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    eliminarFacultad();
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
                    Toast.makeText(thisFragmentContext, "Seleccione una facultad primero", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void actualizarSpinnerFacultad(){
        databaseReferenceFacultad.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final List<Facultad> facultades = new ArrayList<>();
                for (DataSnapshot facultadSnapshot : snapshot.getChildren()){
                    Facultad facultadTmp = facultadSnapshot.getValue(Facultad.class);
                    facultadTmp.setUidFacultad(facultadSnapshot.getKey());
                    facultades.add(facultadTmp);
                }
                ArrayAdapter<Facultad> adapterFacultades = new ArrayAdapter<Facultad>(thisFragmentContext, android.R.layout.simple_spinner_item, facultades);
                adapterFacultades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerFacultad.setAdapter(adapterFacultades);
                spinnerFacultad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        facultadSeleccionada = (Facultad) parent.getSelectedItem();
                        actualizarSpinnerColonia();
                        etImagenUrl.setText(String.valueOf(facultadSeleccionada.getUrlImagenFacultad()));
                        etNombreFacultad.setText(String.valueOf(facultadSeleccionada.getNombre()));
                        etCalleFacultad.setText(String.valueOf(facultadSeleccionada.getCalle()));
                        etNumeroTelefono.setText(String.valueOf(facultadSeleccionada.getNumeroTelefono()));
                        etCorreo.setText(String.valueOf(facultadSeleccionada.getCorreo()));
                        etSitioWeb.setText(String.valueOf(facultadSeleccionada.getSitioWeb()));
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
                        if(coloniaTmp.getUidColonia().equals(facultadSeleccionada.getUidColonia())){
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

    private void actualizarFacultad(){
        if(!TextUtils.isEmpty(etImagenUrl.getText().toString().trim()) || !TextUtils.isEmpty(etCalleFacultad.getText().toString().trim()) || !TextUtils.isEmpty(etNombreFacultad.getText().toString().trim()) || !TextUtils.isEmpty(etNumeroTelefono.getText().toString().trim()) || !TextUtils.isEmpty(etCorreo.getText().toString().trim()) || !TextUtils.isEmpty(etSitioWeb.getText().toString().trim())){
            try {
                facultadSeleccionada.setUidColonia(coloniaSeleccionadaNueva.getUidColonia());
                facultadSeleccionada.setUrlImagenFacultad(etImagenUrl.getText().toString());
                facultadSeleccionada.setCalle(etCalleFacultad.getText().toString());
                facultadSeleccionada.setCorreo(etCorreo.getText().toString());
                facultadSeleccionada.setNombre(etNombreFacultad.getText().toString());
                facultadSeleccionada.setNumeroTelefono(Long.parseLong(etNumeroTelefono.getText().toString()));
                facultadSeleccionada.setSitioWeb(etSitioWeb.getText().toString());
                String uidObjetoActualizar = facultadSeleccionada.getUidFacultad();
                facultadSeleccionada.setUidFacultad(null);
                databaseReferenceFacultad.child(uidObjetoActualizar).setValue(facultadSeleccionada);
                Toast.makeText(thisFragmentContext, "Actualizado", Toast.LENGTH_SHORT).show();
            }catch (NumberFormatException e){
                Toast.makeText(thisFragmentContext, "Ingreso caracter o un numero no valido en el numero de telefono", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void eliminarFacultad(){
        String uidFacultadEliminar = facultadSeleccionada.getUidFacultad();
        databaseReferenceFacultad.child(uidFacultadEliminar).removeValue();
        uidFacultadEliminar = null;
        facultadSeleccionada = null;
        coloniaSeleccionadaGuardada = null;
        coloniaSeleccionadaNueva = null;
        etSitioWeb.setText(null);
        etCalleFacultad.setText(null);
        etCorreo.setText(null);
        etNumeroTelefono.setText(null);
        etNombreFacultad.setText(null);
        etImagenUrl.setText(null);
    }
}