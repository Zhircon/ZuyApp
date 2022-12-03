package com.zuyinc.rutasuvxalapa.fragmentos.administrador;

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
import com.zuyinc.rutasuvxalapa.modelo.entidades.Comentario;

public class ListarComentarioAdministrador extends Fragment {

    private static final String PATH_COMENTARIO = "comentario";
    RecyclerView recView;
    ListarComentarioAdapter listarComentarioAdapter;

    public ListarComentarioAdministrador() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listar_comentario_administrador, container, false);

        recView = (RecyclerView)view.findViewById(R.id.recView_FragmentoLeerComentarioAdmin);

        recView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Comentario> options =
                new FirebaseRecyclerOptions.Builder<Comentario>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(PATH_COMENTARIO), Comentario.class)
                        .build();

        listarComentarioAdapter = new ListarComentarioAdapter(options);
        recView.setAdapter(listarComentarioAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        listarComentarioAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        listarComentarioAdapter.stopListening();
    }

}