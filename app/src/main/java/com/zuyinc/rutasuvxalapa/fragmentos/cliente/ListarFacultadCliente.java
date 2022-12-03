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
import com.zuyinc.rutasuvxalapa.modelo.entidades.Facultad;

public class ListarFacultadCliente extends Fragment {

    private static final String PATH_FACULTAD = "facultad";
    RecyclerView recView;
    ListarFacultadAdapter listarFacultadAdapter;

    public ListarFacultadCliente() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listar_facultad_cliente, container, false);

        recView = (RecyclerView) view.findViewById(R.id.recView_FragmentoListarFacultadesCliente);
        recView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Facultad> options =
                new FirebaseRecyclerOptions.Builder<Facultad>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(PATH_FACULTAD), Facultad.class)
                        .build();

        listarFacultadAdapter = new ListarFacultadAdapter(options);
        recView.setAdapter(listarFacultadAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        listarFacultadAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        listarFacultadAdapter.stopListening();
    }
}