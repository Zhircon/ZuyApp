package com.zuyinc.rutasuvxalapa.fragmentos.administrador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.zuyinc.rutasuvxalapa.R;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Comentario;

public class ListarComentarioAdapter extends FirebaseRecyclerAdapter<Comentario, ListarComentarioAdapter.myViewHolder> {

    public ListarComentarioAdapter(@NonNull FirebaseRecyclerOptions<Comentario> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Comentario comentario) {
        holder.descripcionComentario.setText(comentario.getDescripcionComentario());
        holder.fechaComentario.setText(comentario.getFechaComentario());
        holder.tipoComentario.setText(comentario.getTipoComentario());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comentario_layout_design,parent,false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView descripcionComentario;
        TextView fechaComentario;
        TextView tipoComentario;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            descripcionComentario = itemView.findViewById(R.id.com_lay_design_DescripcionComentario);
            fechaComentario = itemView.findViewById(R.id.com_lay_design_FechaComentario);
            tipoComentario = itemView.findViewById(R.id.com_lay_design_TipoComentario);

        }
    }
}
