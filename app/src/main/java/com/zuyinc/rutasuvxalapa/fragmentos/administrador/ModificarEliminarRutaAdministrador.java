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
import com.zuyinc.rutasuvxalapa.modelo.entidades.Camion;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Ruta;

import java.util.ArrayList;
import java.util.List;

public class ModificarEliminarRutaAdministrador extends Fragment {

    private static final String TAG_PRUEBAS  = "PRUEBAS_X";
    private static final String PATH_RUTA    = "ruta";
    private static final String PATH_CAMION  = "camion";

    Ruta rutaSeleccionada;
    Camion camionSeleccionadoGuardado;
    Camion camionSeleccionadoNuevo;

    Spinner spinnerRuta;
    Spinner spinnerCamion;
    EditText etUrlImagen;
    EditText etNumeroRuta;
    EditText etNombreCallePtoInicial;
    EditText etNombreCallePtoFinal;
    EditText etLatitudPtoInicial;
    EditText etLongitudPtoInicial;
    EditText etLatitudPtoFinal;
    EditText etLongitudPtoFinal;
    Button btnModificar;
    Button btnEliminar;

    Context thisFragmentContext;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceRuta;
    DatabaseReference databaseReferenceCamion;

    public ModificarEliminarRutaAdministrador() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modificar_eliminar_ruta_administrador, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceRuta = firebaseDatabase.getReference(PATH_RUTA);
        databaseReferenceCamion = firebaseDatabase.getReference(PATH_CAMION);

        spinnerRuta = view.findViewById(R.id.spinnerSelecRuta_FragmentModificarEliminarRuta);
        spinnerCamion = view.findViewById(R.id.spinnerSelecCamion_FragmentModificarEliminarRuta);
        etUrlImagen = view.findViewById(R.id.et_UrlImagen_FragmentModificarEliminarRuta);
        etNumeroRuta = view.findViewById(R.id.et_NumeroRuta_FragmentModificarEliminarRuta);
        etNombreCallePtoInicial = view.findViewById(R.id.et_NombreCallePtoInicialRuta_FragmentModificarEliminarRuta);
        etNombreCallePtoFinal = view.findViewById(R.id.et_NombreCallePtoFinalRuta_FragmentModificarEliminarRuta);
        etLatitudPtoInicial = view.findViewById(R.id.et_LatitudPtoInicialRuta_FragmentModificarEliminarRuta);
        etLongitudPtoInicial = view.findViewById(R.id.et_LongitudPtoInicialRuta_FragmentModificarEliminarRuta);
        etLatitudPtoFinal = view.findViewById(R.id.et_LongitudPtoFinalRuta_FragmentModificarEliminarRuta);
        etLongitudPtoFinal = view.findViewById(R.id.et_LongitudPtoFinalRuta_FragmentModificarEliminarRuta);
        btnModificar = view.findViewById(R.id.btn_Modificar_FragmentModificarEliminarRuta);
        btnEliminar = view.findViewById(R.id.btn_Eliminar_FragmentModificarEliminarRuta);

        thisFragmentContext = etUrlImagen.getContext();

        actualizarSpinnerRuta();

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rutaSeleccionada != null){
                    actualizarRutaFirebase();
                }else{
                    Toast.makeText(thisFragmentContext, "Seleccione una ruta primero", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rutaSeleccionada != null){
                    AlertDialog.Builder alertConfirmacion = new AlertDialog.Builder(thisFragmentContext);
                    alertConfirmacion.setMessage("Se eliminara la ruta y esta accion no se puede deshacer ¿Desea continuar?").setCancelable(false).
                            setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    eliminarRutaFirebase();
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
                    Toast.makeText(thisFragmentContext, "Seleccione una ruta primero", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void actualizarSpinnerRuta(){
        databaseReferenceRuta.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final List<Ruta> rutas = new ArrayList<>();
                for (DataSnapshot rutaSnapshot : snapshot.getChildren()){
                    Ruta rutaTmp = rutaSnapshot.getValue(Ruta.class);
                    rutaTmp.setUidRuta(rutaSnapshot.getKey());
                    rutas.add(rutaTmp);
                }
                ArrayAdapter<Ruta> adapterRutas = new ArrayAdapter<Ruta>(thisFragmentContext, android.R.layout.simple_spinner_item, rutas);
                adapterRutas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerRuta.setAdapter(adapterRutas);
                spinnerRuta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        rutaSeleccionada = (Ruta) parent.getSelectedItem();
                        actualizarSpinnerCamion();
                        etUrlImagen.setText(rutaSeleccionada.getUrlImagenRuta());
                        etNumeroRuta.setText(String.valueOf(rutaSeleccionada.getNumeroRuta()));
                        etNombreCallePtoInicial.setText(String.valueOf(rutaSeleccionada.getNombreCallePtoInicial()));
                        etNombreCallePtoFinal.setText(String.valueOf(rutaSeleccionada.getNombreCallePtoFinal()));
                        etLatitudPtoInicial.setText(String.valueOf(rutaSeleccionada.getLatitudPtoInicial()));
                        etLongitudPtoInicial.setText(String.valueOf(rutaSeleccionada.getLongitudPtoInicial()));
                        etLatitudPtoFinal.setText(String.valueOf(rutaSeleccionada.getLatitudPtoFinal()));
                        etLongitudPtoFinal.setText(String.valueOf(rutaSeleccionada.getLongitudPtoFinal()));
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

    private void actualizarSpinnerCamion(){
        databaseReferenceCamion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final List<Camion> camiones = new ArrayList<>();
                for (DataSnapshot camionSnapshot : snapshot.getChildren()){
                    Camion camionTmp = camionSnapshot.getValue(Camion.class);
                    camionTmp.setUidCamion(camionSnapshot.getKey());
                    camiones.add(camionTmp);
                    try{
                        if(camionTmp.getUidCamion().equals(rutaSeleccionada.getUidCamion())){
                            camionSeleccionadoGuardado = camionTmp;
                        }
                    }catch (NullPointerException e){

                    }
                }
                int posicionSeleccion = camiones.indexOf(camionSeleccionadoGuardado);
                ArrayAdapter<Camion> adapterCamiones = new ArrayAdapter<Camion>(thisFragmentContext, android.R.layout.simple_spinner_item, camiones);
                adapterCamiones.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCamion.setAdapter(adapterCamiones);
                spinnerCamion.setSelection(posicionSeleccion);
                spinnerCamion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        camionSeleccionadoNuevo = (Camion)  parent.getSelectedItem();
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

    private void actualizarRutaFirebase(){
        if(TextUtils.isEmpty(etUrlImagen.getText().toString().trim()) || TextUtils.isEmpty(etNumeroRuta.getText().toString().trim()) || TextUtils.isEmpty(etLatitudPtoInicial.getText().toString().trim()) || TextUtils.isEmpty(etLongitudPtoInicial.getText().toString().trim()) || TextUtils.isEmpty(etLatitudPtoFinal.getText().toString().trim()) || TextUtils.isEmpty(etLongitudPtoFinal.getText().toString().trim())){
            try{
                rutaSeleccionada.setUidCamion(camionSeleccionadoNuevo.getUidCamion());
                rutaSeleccionada.setUrlImagenRuta(etUrlImagen.getText().toString());
                rutaSeleccionada.setNombreCallePtoInicial(etNombreCallePtoInicial.getText().toString());
                rutaSeleccionada.setNombreCallePtoFinal(etNombreCallePtoFinal.getText().toString());
                rutaSeleccionada.setNumeroRuta(Integer.parseInt(etNumeroRuta.getText().toString()));
                rutaSeleccionada.setLatitudPtoInicial(Float.parseFloat(etLatitudPtoInicial.getText().toString()));
                rutaSeleccionada.setLongitudPtoInicial(Float.parseFloat(etLongitudPtoInicial.getText().toString()));
                rutaSeleccionada.setLatitudPtoFinal(Float.parseFloat(etLatitudPtoFinal.getText().toString()));
                rutaSeleccionada.setLongitudPtoFinal(Float.parseFloat(etLongitudPtoFinal.getText().toString()));
                String uidObjetoActualizar = rutaSeleccionada.getUidRuta();
                rutaSeleccionada.setUidRuta(null);
                databaseReferenceRuta.child(uidObjetoActualizar).setValue(rutaSeleccionada);
                Toast.makeText(thisFragmentContext, "Actualizado", Toast.LENGTH_SHORT).show();
            }catch (NumberFormatException e){
                Toast.makeText(thisFragmentContext, "Ingreso caracter o un numero no valido en el numero de telefono", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void eliminarRutaFirebase(){
        String uidRutaEliminar = rutaSeleccionada.getUidRuta();
        databaseReferenceRuta.child(uidRutaEliminar).removeValue();
        uidRutaEliminar = null;
        rutaSeleccionada = null;
        camionSeleccionadoGuardado = null;
        camionSeleccionadoNuevo = null;
        etUrlImagen.setText(null);
        etNumeroRuta.setText(null);
        etNombreCallePtoInicial.setText(null);
        etNombreCallePtoFinal.setText(null);
        etLatitudPtoInicial.setText(null);
        etLongitudPtoInicial.setText(null);
        etLatitudPtoFinal.setText(null);
        etLongitudPtoFinal.setText(null);
    }
}