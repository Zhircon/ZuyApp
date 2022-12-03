package com.zuyinc.rutasuvxalapa.fragmentos.administrador;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Matrix;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.zuyinc.rutasuvxalapa.R;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Camion;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Ruta;

import java.util.ArrayList;
import java.util.List;

public class ModificarEliminarCamionAdministrador extends Fragment {

    private static final String TAG_PRUEBAS = "PRUEBAS_X";
    private static final String PATH_CAMION = "camion";
    private static final String PATH_RUTA   = "ruta";

    Camion camionSeleccionado;

    Context thisFragmentContext;
    Spinner spinnerCamion;
    EditText etUrlImagen;
    EditText etNumero;
    EditText etColor;
    Button btnModificar;
    Button btnEliminar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceCamion;
    DatabaseReference databaseReferenceRuta;

    public ModificarEliminarCamionAdministrador() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modificar_eliminar_camion_administrador, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceCamion = firebaseDatabase.getReference(PATH_CAMION);
        databaseReferenceRuta = firebaseDatabase.getReference(PATH_RUTA);

        spinnerCamion = view.findViewById(R.id.spinner_FragmentModificarEliminarCamion);
        etUrlImagen = view.findViewById(R.id.et_UrlImagen_FragmentModificarEliminarCamion);
        etNumero = view.findViewById(R.id.et_Numero_FragmentModificarEliminarCamion);
        etColor = view.findViewById(R.id.et_Color_FragmentModificarEliminarCamion);
        btnModificar = view.findViewById(R.id.btn_Modificar_FragmentModificarEliminarCamion);
        btnEliminar = view.findViewById(R.id.btn_Eliminar_FragmentModificarEliminarCamion);

        thisFragmentContext = spinnerCamion.getContext();

        databaseReferenceCamion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final List<Camion> camiones = new ArrayList<>();
                for (DataSnapshot camionSnapshot : snapshot.getChildren()){
                    Camion camionTmp = camionSnapshot.getValue(Camion.class);
                    camionTmp.setUidCamion(camionSnapshot.getKey());
                    camiones.add(camionTmp);
                }
                ArrayAdapter<Camion> adapterCamiones = new ArrayAdapter<Camion>(thisFragmentContext, android.R.layout.simple_spinner_item, camiones);
                adapterCamiones.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCamion.setAdapter(adapterCamiones);
                spinnerCamion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        camionSeleccionado = (Camion) parent.getSelectedItem();
                        etUrlImagen.setText(camionSeleccionado.getUrlImagen());
                        etColor.setText(camionSeleccionado.getColor());
                        etNumero.setText(String.valueOf(camionSeleccionado.getNumero()));
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
                if(camionSeleccionado != null){
                    actualizarCamionFirebase();
                }else{
                    Toast.makeText(thisFragmentContext, "Seleccione un elemento primero", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (camionSeleccionado != null){
                    AlertDialog.Builder alertConfirmacion = new AlertDialog.Builder(thisFragmentContext);
                    alertConfirmacion.setMessage("Se eliminara el camion... ¿Desea continuar?").setCancelable(false)
                            .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    eliminarCamionFirebase();
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

    private void actualizarCamionFirebase(){
        if(!TextUtils.isEmpty(etUrlImagen.getText().toString().trim()) || !TextUtils.isEmpty(etColor.getText().toString().trim()) || !TextUtils.isEmpty(etNumero.getText().toString().trim())){
            try{
                if(Integer.parseInt(etNumero.getText().toString()) <=0 ){
                    Toast.makeText(thisFragmentContext, "El numero del camion no puede ser menor o igual a cero", Toast.LENGTH_SHORT).show();
                }else{
                    camionSeleccionado.setUrlImagen(etUrlImagen.getText().toString());
                    camionSeleccionado.setColor(etColor.getText().toString());
                    camionSeleccionado.setNumero(Integer.parseInt(etNumero.getText().toString()));
                    String uidObjetoActualizar = camionSeleccionado.getUidCamion();
                    camionSeleccionado.setUidCamion(null);
                    databaseReferenceCamion.child(uidObjetoActualizar).setValue(camionSeleccionado);
                    Toast.makeText(thisFragmentContext, "Actualizado", Toast.LENGTH_SHORT).show();
                }
            }catch (NumberFormatException e){
                Toast.makeText(thisFragmentContext, "Ingreso caracter o un numero no valido en el numero de telefono", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void eliminarCamionFirebase(){
        String uidCamionEliminar = camionSeleccionado.getUidCamion();
        databaseReferenceCamion.child(uidCamionEliminar).removeValue();
        actualizarRutasCamionesFirebase(uidCamionEliminar);
        uidCamionEliminar = null;
        camionSeleccionado = null;
        etNumero.setText(null);
        etColor.setText(null);
        etUrlImagen.setText(null);
        Toast.makeText(thisFragmentContext, "Eliminado", Toast.LENGTH_SHORT).show();
    }

    private void actualizarRutasCamionesFirebase(String uidCamionEliminado){
        Query queryRutas = databaseReferenceRuta.orderByChild("uidCamion").equalTo(uidCamionEliminado);
        queryRutas.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot rutaSapshot : snapshot.getChildren()){
                    Ruta rutaTmp = rutaSapshot.getValue(Ruta.class);
                    rutaTmp.setUidCamion(null);
                    String uidObjetoActualizar = rutaSapshot.getKey();
                    databaseReferenceRuta.child(uidObjetoActualizar).setValue(rutaTmp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}