package com.zuyinc.rutasuvxalapa.fragmentos.cliente;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.zuyinc.rutasuvxalapa.R;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Camion;

public class ListarCamionAdapter extends FirebaseRecyclerAdapter<Camion, ListarCamionAdapter.myViewHolder> {

    public ListarCamionAdapter(@NonNull FirebaseRecyclerOptions<Camion> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Camion camion) {
        holder.numeroCamion.setText(String.valueOf(camion.getNumero()));
        holder.colorCamion.setText(camion.getColor());
        Glide.with(holder.imagenCamion.getContext()).load(camion.getUrlImagen()).into(holder.imagenCamion);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.camion_layout_design,parent,false);
        return new ListarCamionAdapter.myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView numeroCamion;
        TextView colorCamion;
        ImageView imagenCamion;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            numeroCamion = itemView.findViewById(R.id.cam_lay_design_NumeroCamion);
            colorCamion = itemView.findViewById(R.id.cam_lay_design_ColorCamion);
            imagenCamion = itemView.findViewById(R.id.cam_lay_design_ImagenCamion);
        }
    }
}
