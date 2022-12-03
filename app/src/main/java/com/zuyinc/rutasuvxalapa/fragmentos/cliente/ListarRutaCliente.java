package com.zuyinc.rutasuvxalapa.fragmentos.cliente;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.zuyinc.rutasuvxalapa.R;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Camion;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Ruta;

public class ListarRutaCliente extends Fragment {

    private static final String PATH_RUTA = "ruta";
    RecyclerView recView;
    ListarRutaAdapter listarRutaAdapter;

    public ListarRutaCliente() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listar_ruta_cliente, container, false);

        recView = (RecyclerView)view.findViewById(R.id.recView_FragmentoListarRutasCliente);

        recView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Ruta> options =
                new FirebaseRecyclerOptions.Builder<Ruta>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(PATH_RUTA), Ruta.class)
                        .build();

        listarRutaAdapter = new ListarRutaAdapter(options);
        recView.setAdapter(listarRutaAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        listarRutaAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        listarRutaAdapter.stopListening();
    }

}