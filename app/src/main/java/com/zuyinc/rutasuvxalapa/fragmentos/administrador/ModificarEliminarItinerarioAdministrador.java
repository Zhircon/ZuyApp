package com.zuyinc.rutasuvxalapa.fragmentos.administrador;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.zuyinc.rutasuvxalapa.R;
import com.zuyinc.rutasuvxalapa.modelo.entidades.ItinerarioRutaParada;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Parada;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Ruta;

import java.util.ArrayList;
import java.util.List;

public class ModificarEliminarItinerarioAdministrador extends Fragment {

    private static final String TAG_PRUEBAS = "PRUEBAS_X";
    private static final String PATH_PARADA = "parada";
    private static final String PATH_RUTA   = "ruta";
    private static final String PATH_ITINERARIO_RUTA_PARADA = "itinerarioRutaParada";

    private Ruta rutaSeleccionada;
    private Parada paradaSeleccionada;
    private ItinerarioRutaParada itinerarioRutaParadaSeleccionado;

    private Spinner spinnerRuta;
    private Spinner spinnerParada;
    private Button btnEliminar;

    private Context thisFragmentContext;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceRuta;
    private DatabaseReference databaseReferenceParada;
    private DatabaseReference databaseReferenceItinerarioRutaParada;

    public ModificarEliminarItinerarioAdministrador() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modificar_eliminar_itinerario_administrador, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceRuta = firebaseDatabase.getReference(PATH_RUTA);
        databaseReferenceParada = firebaseDatabase.getReference(PATH_PARADA);
        databaseReferenceItinerarioRutaParada = firebaseDatabase.getReference(PATH_ITINERARIO_RUTA_PARADA);

        thisFragmentContext = btnEliminar.getContext();

        actualizarSpinnerRuta();
        actualizarSpinnerParada();

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rutaSeleccionada !=null && paradaSeleccionada != null){
                    if(rutaSeleccionada.getUidRuta() == itinerarioRutaParadaSeleccionado.getUidRuta() && paradaSeleccionada.getUidParada() == itinerarioRutaParadaSeleccionado.getUidParada()){
                        eliminarItinerario();
                    }
                }else{
                    Toast.makeText(thisFragmentContext, "Seleccione una ruta y una parada primero", Toast.LENGTH_SHORT).show();
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
                ArrayAdapter<Ruta> adapterRutas = new ArrayAdapter<Ruta>(thisFragmentContext, android.R.layout.simple_spinner_item,rutas);
                adapterRutas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerRuta.setAdapter(adapterRutas);
                spinnerRuta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        rutaSeleccionada = (Ruta) parent.getSelectedItem();
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

    private void actualizarSpinnerParada(){
        databaseReferenceParada.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final List<Parada> paradas = new ArrayList<>();
                for (DataSnapshot paradaSnapshot : snapshot.getChildren()){
                    Parada paradaTmp = paradaSnapshot.getValue(Parada.class);
                    paradaTmp.setUidParada(paradaSnapshot.getKey());
                    paradas.add(paradaTmp);
                }
                ArrayAdapter<Parada> adapterParadas = new ArrayAdapter<Parada>(thisFragmentContext, android.R.layout.simple_spinner_dropdown_item, paradas);
                adapterParadas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerParada.setAdapter(adapterParadas);
                spinnerParada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        paradaSeleccionada = (Parada) parent.getSelectedItem();
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

    private void eliminarItinerario(){
    }
}