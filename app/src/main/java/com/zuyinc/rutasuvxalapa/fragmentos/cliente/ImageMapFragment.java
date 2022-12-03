package com.zuyinc.rutasuvxalapa.fragmentos.cliente;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zuyinc.rutasuvxalapa.R;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Ruta;

public class ImageMapFragment extends Fragment {

    private Ruta ruta;
    private ImageView ivImagen;

    private Context thisFragmentContext;

    public ImageMapFragment() {
        // Required empty public constructor
    }

    public ImageMapFragment(Ruta ruta) {
        this.ruta = ruta;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_map, container, false);

        ivImagen = view.findViewById(R.id.imageView_Fragment_Image_Map);

        thisFragmentContext = ivImagen.getContext();

        Glide.with(thisFragmentContext).load(ruta.getUrlImagenRuta()).into(ivImagen);

        return view;
    }

    public void onBackPressed(){
        AppCompatActivity activity = (AppCompatActivity) thisFragmentContext;
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_activity_main_cliente, new ListarRutaCliente()).addToBackStack(null).commit();
    }

}