package com.zuyinc.rutasuvxalapa.fragmentos.cliente;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.zuyinc.rutasuvxalapa.R;

public class GoogleMapFragment extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap mapita;

    public GoogleMapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        getMapAsync(this);
        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapita = googleMap;
        mapita.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
}