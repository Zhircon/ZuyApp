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
import com.zuyinc.rutasuvxalapa.modelo.entidades.Facultad;

public class ListarFacultadAdapter extends FirebaseRecyclerAdapter <Facultad, ListarFacultadAdapter.myViewHolder>{

    public ListarFacultadAdapter(@NonNull FirebaseRecyclerOptions<Facultad> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Facultad facultad) {
        holder.nombreFacultad.setText(facultad.getNombre());
        holder.calleFacultad.setText(facultad.getCalle());
        holder.telefonoFacultad.setText(String.valueOf(facultad.getNumeroTelefono()));
        holder.correoFacultad.setText(facultad.getCorreo());
        holder.sitioWebFacultad.setText(facultad.getSitioWeb());
        holder.coloniaFacultad.setText(facultad.getUidColonia());
        Glide.with(holder.imagenFacultad.getContext()).load(facultad.getUrlImagenFacultad()).into(holder.imagenFacultad);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.facultad_layout_design,parent,false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        ImageView imagenFacultad;
        TextView nombreFacultad;
        TextView calleFacultad;
        TextView telefonoFacultad;
        TextView correoFacultad;
        TextView sitioWebFacultad;
        TextView coloniaFacultad;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            imagenFacultad = itemView.findViewById(R.id.fac_lay_design_ImagenFacultad);
            nombreFacultad = itemView.findViewById(R.id.fac_lay_design_NombreFacultad);
            calleFacultad = itemView.findViewById(R.id.fac_lay_design_CalleFacultad);
            telefonoFacultad = itemView.findViewById(R.id.fac_lay_design_NumeroTelefonoFacultad);
            correoFacultad = itemView.findViewById(R.id.fac_lay_design_CorreoFacultad);
            sitioWebFacultad = itemView.findViewById(R.id.fac_lay_design_SitioWebFacultad);
            coloniaFacultad = itemView.findViewById(R.id.fac_lay_design_ColoniaFacultad);
        }
    }
}
