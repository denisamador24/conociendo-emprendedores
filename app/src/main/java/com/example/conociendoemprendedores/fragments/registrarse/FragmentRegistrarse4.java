package com.example.conociendoemprendedores.fragments.registrarse;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.conociendoemprendedores.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class FragmentRegistrarse4 extends Fragment {

    private ImageView imageViewFoto;
    private Bitmap imagen;
    private boolean seleccionoImagen = false;
    private EditText editTextNombre;
    private EditText editTextOficio;
    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;

    public FragmentRegistrarse4 (){

    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container,
                             Bundle savedStanceStatec){
       View view = layoutInflater.inflate(R.layout.fragment_registrase4, container, false);

        imageViewFoto =(ImageView) view.findViewById(R.id.fragment_registrarse_imageCrearPerfil);
        editTextOficio = (EditText) view.findViewById(R.id.fragment_registrarse_editTextOficio);
        editTextNombre = (EditText) view.findViewById(R.id.editText_nombre_fragmentgRegistrase);

        if (PermisoVersionSuperiores()){
            imageViewFoto.setEnabled(true);
        } else {
            imageViewFoto.setEnabled(false);
        }

        imageViewFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] opciones = {"Tomar foto", "Elegir de la galeria", "Cancelar"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Elige una opcion");

                builder.setItems(opciones, new DialogInterface.OnClickListener() {
                    @SuppressLint("IntentReset")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(opciones[which].equals("Tomar foto")){
                            Toast.makeText(getContext(), "No habilitado aun...", Toast.LENGTH_SHORT).show();

                        } else if(opciones[which].equals("Elegir de la galeria")){
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/");
                            startActivityForResult(intent.createChooser(intent, "Seleccione"), COD_SELECCIONA);
                        } else {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });


        return view;
    }

    //Revisar los permisos de de camara y almacenamiento
    private boolean PermisoVersionSuperiores() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) { //Validamos si estmos en andrid
            return true;
        }

        //(getContext().checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED ) &&
        if((getContext().checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)){
            return true;
        }

        //shouldShowRequestPermissionRationale(CAMERA)  ||
        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){
            CargarDialologo();
        } else {
            requestPermissions(new String[] {WRITE_EXTERNAL_STORAGE, CAMERA}, 100);
        }

        return false;
    }

    //Confirmar permiso
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 100){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
            && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                imageViewFoto.setEnabled(true);
            } else {
                Toast.makeText(getContext(), "Permiso denegado no pudes agragar una imagen", Toast.LENGTH_LONG).show();
            }
        }
    }


    // Comenzar a soloicitar los permisos
    private void CargarDialologo() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(getContext());
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debebs aceptar los permisos de alcenamiento para agregar fotos");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 requestPermissions(new String[] {WRITE_EXTERNAL_STORAGE, CAMERA}, 100);
            }
        });
        dialogo.show();
    }


    // Cargar imagen de almacenamiento o galeria en ImagenView
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case COD_SELECCIONA:
                Uri path = data.getData();

                try {
                    imagen = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), path);
                    imageViewFoto.setImageBitmap(imagen);
                    seleccionoImagen = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case COD_FOTO:
                break;
        }


    }

    //Metotdo para bajar la calidad de la imagen seleccionada
    private Bitmap redimencionarImagen(Bitmap bitmap, float mAncho, float mAlto){

        int ancho = bitmap.getWidth();
        int alto = bitmap.getHeight();

        if(ancho > mAncho || alto > mAlto){
            float escalaAncho = ancho/mAncho;
            float escalaAlto = alto/mAlto;

            Matrix matrix  = new Matrix();
            matrix.postScale(escalaAncho, escalaAncho);

            return Bitmap.createBitmap(imagen,0,0, ancho, alto, matrix, false);
        } else {
            return imagen;
        }


    }

    //
    public String getOficio(){
        String oficio = editTextOficio.getText().toString();
        return oficio;
    }

    public String getNombre(){
        String nombre = editTextNombre.getText().toString();
        return nombre;
    }

    public String getImagen(){
        if(seleccionoImagen) {
            //imagen = redimencionarImagen(imagen, 600, 800);

            ByteArrayOutputStream array = new ByteArrayOutputStream();
            imagen.compress(Bitmap.CompressFormat.JPEG, 100, array);
            byte[] imaganeByte = array.toByteArray();
            String imagenString = Base64.encodeToString(imaganeByte, Base64.DEFAULT);

            return  imagenString;
        }
        return "";
    }


    public boolean CamposValidos(){
        if(getOficio().equals("") || getNombre().equals("")){
            return false;
        } else {
            return true;
        }
    }
}
