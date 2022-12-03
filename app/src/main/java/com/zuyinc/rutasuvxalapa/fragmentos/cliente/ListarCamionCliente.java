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

public class ListarCamionCliente extends Fragment {

    private static final String PATH_CAMION = "camion";
    RecyclerView recView;
    ListarCamionAdapter listarCamionAdapter;

    public ListarCamionCliente() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listar_camion_cliente, container, false);

        recView = (RecyclerView)view.findViewById(R.id.recView_FragmentoListarCamionesCliente);

        recView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Camion> options =
                new FirebaseRecyclerOptions.Builder<Camion>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(PATH_CAMION), Camion.class)
                        .build();

        listarCamionAdapter = new ListarCamionAdapter(options);
        recView.setAdapter(listarCamionAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        listarCamionAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        listarCamionAdapter.stopListening();
    }

}