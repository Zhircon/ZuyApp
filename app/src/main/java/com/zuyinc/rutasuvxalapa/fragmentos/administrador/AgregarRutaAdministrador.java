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
import com.zuyinc.rutasuvxalapa.modelo.entidades.Camion;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Colonia;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Ruta;

import java.util.ArrayList;
import java.util.List;

public class AgregarRutaAdministrador extends Fragment {

    private static final String PATH_RUTA = "ruta";
    private static final String PATH_CAMION = "camion";

    int numeroRuta;
    String nombreCallePtoInicial;
    String nombreCallePtoFinal;
    float latitudPtoInicial;
    float longitudPtoInicial;
    float latitudPtoFinal;
    float longitudPtoFinal;
    String urlImagenRuta;
    Camion camionSeleccionado;

    Context thisFragmentContext;
    EditText etUrlImagen;
    EditText etNumeroRuta;
    EditText etNombreCallePtoInicial;
    EditText etNombreCallePtoFinal;
    EditText etLatitudPtoInicial;
    EditText etLongitudPtoInicial;
    EditText etLatitudPtoFinal;
    EditText etLongitudPtoFinal;
    Spinner spinnerCamion;
    Button btnGuardar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceRuta;
    DatabaseReference databaseReferenceCamion;

    public AgregarRutaAdministrador() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agregar_ruta_administrador, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceCamion = firebaseDatabase.getReference(PATH_CAMION);

        etUrlImagen = view.findViewById(R.id.et_UrlImagen_FragmentAgregarRuta);
        etNombreCallePtoInicial = view.findViewById(R.id.et_NombreCallePtoInicialAgregarRuta);
        etNombreCallePtoFinal = view.findViewById(R.id.et_NombreCallePtoFinallAgregarRuta);
        etNumeroRuta = view.findViewById(R.id.et_NumeroRuta_FragmentAgregarRuta);
        etLatitudPtoInicial = view.findViewById(R.id.et_LatitudPtoInicialRuta_FragmentAgregarRuta);
        etLongitudPtoInicial = view.findViewById(R.id.et_LongitudPtoInicialRuta_FragmentAgregarRuta);
        etLatitudPtoFinal = view.findViewById(R.id.et_LatitudPtoFinalRuta_FragmentAgregarRuta);
        etLongitudPtoFinal = view.findViewById(R.id.et_LongitudPtoFinalRuta_FragmentAgregarRuta);
        spinnerCamion = view.findViewById(R.id.spinnerSelecCamion_FragmentAgregarRuta);
        btnGuardar = view.findViewById(R.id.btn_Guardar_FragmentAgregarRuta);

        thisFragmentContext = etNumeroRuta.getContext();

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
                        camionSeleccionado = (Camion)parent.getSelectedItem();
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

        nombreCallePtoInicial = etNombreCallePtoInicial.getText().toString();
        nombreCallePtoFinal = etNombreCallePtoFinal.getText().toString();
        urlImagenRuta = etUrlImagen.getText().toString();

        if(!TextUtils.isEmpty(etNumeroRuta.getText().toString()) || !TextUtils.isEmpty(nombreCallePtoInicial) || !TextUtils.isEmpty(nombreCallePtoFinal) || !TextUtils.isEmpty(etLatitudPtoInicial.getText().toString()) || !TextUtils.isEmpty(etLongitudPtoInicial.getText().toString()) || !TextUtils.isEmpty(etLatitudPtoFinal.getText().toString()) || !TextUtils.isEmpty(etLongitudPtoFinal.getText().toString()) || !TextUtils.isEmpty(urlImagenRuta)){
            try{
                numeroRuta = Integer.parseInt(etNumeroRuta.getText().toString());
                latitudPtoInicial = Float.parseFloat(etLatitudPtoInicial.getText().toString());
                longitudPtoInicial = Float.parseFloat(etLongitudPtoInicial.getText().toString());
                latitudPtoFinal = Float.parseFloat(etLatitudPtoFinal.getText().toString());
                longitudPtoFinal = Float.parseFloat(etLongitudPtoFinal.getText().toString());
                if(numeroRuta <=0 ){
                    Toast.makeText(thisFragmentContext, "El numero de la ruta no puede ser menor o igual a cero", Toast.LENGTH_SHORT).show();
                }else{
                    agregarRuta();
                    limpiarCamposInterfazGrafica();
                }
            }catch(NumberFormatException e){
                Toast.makeText(thisFragmentContext, "Ingreso caracter o un numero no valido en numero de ruta o en los puntos de latitud o longitud", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(thisFragmentContext, "No puede haber ningun campo vacio", Toast.LENGTH_SHORT).show();
        }
    }

    private void agregarRuta(){
        Ruta ruta = new Ruta(numeroRuta, nombreCallePtoInicial, nombreCallePtoFinal, latitudPtoInicial, longitudPtoInicial, latitudPtoFinal, longitudPtoFinal, urlImagenRuta, camionSeleccionado.getUidCamion());
        databaseReferenceRuta = firebaseDatabase.getReference(PATH_RUTA);
        databaseReferenceRuta.push().setValue(ruta);
        Toast.makeText(getActivity().getBaseContext(), "Se ha agregado la ruta exitosamente", Toast.LENGTH_SHORT).show();
    }

    private void limpiarCamposInterfazGrafica(){
        etNombreCallePtoInicial.setText(null);
        etNombreCallePtoFinal.setText(null);
        etUrlImagen.setText(null);
        etNumeroRuta.setText(null);
        etLatitudPtoInicial.setText(null);
        etLongitudPtoInicial.setText(null);
        etLatitudPtoFinal.setText(null);
        etLongitudPtoFinal.setText(null);
    }
}