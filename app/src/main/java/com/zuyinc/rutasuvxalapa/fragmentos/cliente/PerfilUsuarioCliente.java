package com.zuyinc.rutasuvxalapa.fragmentos.cliente;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.common.internal.ConnectionTelemetryConfiguration;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.zuyinc.rutasuvxalapa.R;
import com.zuyinc.rutasuvxalapa.fragmentos.seleccionarImagenFragment;
import com.zuyinc.rutasuvxalapa.modelo.entidades.Usuario;

import org.w3c.dom.Text;

public class PerfilUsuarioCliente extends Fragment {

    private static final String PATH_USUARIO = "usuario";

    private Usuario usuarioActual;

    private TextView tviewNombreUsuario;
    private TextView tviewCorreoUsuario;
    private TextView tviewPasswordUsuario;
    private ImageView iviewImagenPerfil;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeContainer;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceUsuario;

    Context thisFragmentContext;

    public PerfilUsuarioCliente() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil_usuario_cliente, container, false);

        swipeContainer = view.findViewById(R.id.swipeContainer_fragmentPerfilUsuarioCliente);
        tviewNombreUsuario = view.findViewById(R.id.textview_nombreUsuario_fragmentPerfilUsuarioCliente);
        tviewCorreoUsuario = view.findViewById(R.id.textview_correoUsuario_fragmentPerfilUsuarioCliente);
        tviewPasswordUsuario = view.findViewById(R.id.textview_passwordUsuario_fragmentPerfilUsuarioCliente);
        iviewImagenPerfil = view.findViewById(R.id.iv_imagenPerfil_fragmentPerfilUsuarioCliente);
        progressBar = view.findViewById(R.id.progressBar);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceUsuario = firebaseDatabase.getReference(PATH_USUARIO);

        thisFragmentContext = tviewNombreUsuario.getContext();

        deslizarRefrescarFragmento();

        iviewImagenPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_activity_main_cliente, new seleccionarImagenFragment(PATH_USUARIO, firebaseAuth, firebaseUser)).addToBackStack(null).commit();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null){
            progressBar.setVisibility(View.VISIBLE);
            mostrarDatosUsuario(firebaseUser);
        }else{
            Toast.makeText(thisFragmentContext, "No se puede mostrar el perfil de ususario en estos momentos",Toast.LENGTH_LONG ).show();
        }

        return view;
    }

    private void deslizarRefrescarFragmento(){
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (firebaseUser != null){
                    mostrarDatosUsuario(firebaseUser);
                }else{
                    Toast.makeText(thisFragmentContext, "No se puede mostrar el perfil de ususario en estos momentos",Toast.LENGTH_LONG ).show();
                }
                swipeContainer.setRefreshing(false);
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    private void mostrarDatosUsuario(FirebaseUser firebaseUser){
        String usuarioUid = firebaseUser.getUid();
        databaseReferenceUsuario.child(usuarioUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuarioActual = snapshot.getValue(Usuario.class);
                if(usuarioActual != null){
                    tviewNombreUsuario.setText(usuarioActual.getNombreUsuario());
                    tviewCorreoUsuario.setText(usuarioActual.getCorreo());
                    tviewPasswordUsuario.setText(usuarioActual.getPassword());

                    Uri uri = firebaseUser.getPhotoUrl();

                    final RequestOptions options = new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL);

                    Glide.with(thisFragmentContext).load(uri).apply(options).into(iviewImagenPerfil);
                }else{
                    Toast.makeText(thisFragmentContext, "Algo no salio bien", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(thisFragmentContext, "Algo no salio bien", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}