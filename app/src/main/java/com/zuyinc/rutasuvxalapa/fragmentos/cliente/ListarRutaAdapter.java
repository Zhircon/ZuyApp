package com.zuyinc.rutasuvxalapa.fragmentos.cliente;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.zuyinc.rutasuvxalapa.R;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Ruta;

public class ListarRutaAdapter extends FirebaseRecyclerAdapter <Ruta, ListarRutaAdapter.myViewHolder>{

    public ListarRutaAdapter(@NonNull FirebaseRecyclerOptions<Ruta> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ListarRutaAdapter.myViewHolder holder, int position, @NonNull Ruta ruta) {
        holder.numeroRuta.setText(String.valueOf(ruta.getNumeroRuta()));
        holder.calleInicial.setText(ruta.getNombreCallePtoInicial());
        holder.calleFinal.setText(ruta.getNombreCallePtoFinal());
        Glide.with(holder.imagenRuta.getContext()).load(ruta.getUrlImagenRuta()).into(holder.imagenRuta);

        holder.imagenRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_activity_main_cliente, new ImageMapFragment(ruta)).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @Override
    public ListarRutaAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ruta_layout_design,parent,false);
        return new ListarRutaAdapter.myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView numeroRuta;
        TextView calleInicial;
        TextView calleFinal;
        ImageView imagenRuta;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            numeroRuta = itemView.findViewById(R.id.ruta_lay_design_NumeroRuta);
            calleInicial = itemView.findViewById(R.id.ruta_lay_design_CalleInicial);
            calleFinal = itemView.findViewById(R.id.ruta_lay_design_CalleFinal);
            imagenRuta = itemView.findViewById(R.id.ruta_lay_design_ImagenRuta);
        }
    }
}
