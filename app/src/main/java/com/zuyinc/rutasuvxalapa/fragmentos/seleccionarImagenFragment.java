package com.zuyinc.rutasuvxalapa.fragmentos;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.zuyinc.rutasuvxalapa.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.UUID;

public class seleccionarImagenFragment extends Fragment {

    private String path;
    private Uri uriImage;

    private Button btnSeleccionarImagen;
    private Button btnGuardar;
    private ImageView ivImagen;
    private ProgressBar progressBar;
    private ActivityResultLauncher<String> activityResultLauncherGallery;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;

    private Context thisFragmentContext;

    public seleccionarImagenFragment() {
        // Required empty public constructor
    }

    public seleccionarImagenFragment(String path) {
        this.path = path;
    }

    public seleccionarImagenFragment(String path, FirebaseAuth firebaseAuth, FirebaseUser firebaseUser) {
        this.path = path;
        this.firebaseAuth = firebaseAuth;
        this.firebaseUser = firebaseUser;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_seleccionar_imagen, container, false);

        ivImagen = view.findViewById(R.id.imageView_FragmentoSeleccionarImagen);
        btnSeleccionarImagen = view.findViewById(R.id.btn_seleccionarImagen_FragmentoSeleccionarImagen);
        btnGuardar = view.findViewById(R.id.btn_Guardar_FragmentoSeleccionarImagen);
        progressBar = view.findViewById(R.id.progressBar);

        thisFragmentContext = ivImagen.getContext();

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child(path + "/");

        activityResultLauncherGallery = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if(result != null){
                    uriImage = result;
                    ivImagen.setImageURI(uriImage);
                }
            }
        });

        btnSeleccionarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirSelectorArchivo();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subirArchivo();
                getParentFragmentManager().popBackStack();
            }
        });

        return view;

    }

    private void abrirSelectorArchivo(){
        activityResultLauncherGallery.launch("image/*");
    }

    private void subirArchivo(){

        ProgressDialog dialog = new ProgressDialog(thisFragmentContext);
        dialog.setMessage("Subiendo archivo...");
        dialog.show();

        if(uriImage != null){

            if(path.equals("usuario")) {
                StorageReference fileReference = storageReference.child(firebaseAuth.getCurrentUser().getUid() + "." + getFileExtension(uriImage));

                fileReference.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri downloadUri = uri;
                                firebaseUser = firebaseAuth.getCurrentUser();

                                UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().
                                        setPhotoUri(downloadUri).build();
                                firebaseUser.updateProfile(profileUpdate);
                                dialog.dismiss();
                            }
                        });
                    }
                });
            }else {
                storageReference.putFile(uriImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            dialog.dismiss();
                            Toast.makeText(thisFragmentContext, "Imagen subida con exito", Toast.LENGTH_SHORT).show();
                        }else{
                            dialog.dismiss();
                            Toast.makeText(thisFragmentContext, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(thisFragmentContext, "La carga de la imagen fallo", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }else{
            Toast.makeText(thisFragmentContext, "Por favor seleccione una imagen", Toast.LENGTH_SHORT).show();
        }

    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = thisFragmentContext.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

}