package com.example.conociendoemprendedores;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.conociendoemprendedores.Datos.ConsultarListProductos;
import com.example.conociendoemprendedores.Datos.ConsultarListServicios;

import com.example.conociendoemprendedores.adaptadores.Usuarios;
import com.example.conociendoemprendedores.fragments.inicio.ConsulListUser;
import com.example.conociendoemprendedores.fragments.inicio.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivityDetallesPerfil extends AppCompatActivity{

    private ImageView imagePerfil;
    private TextView tv_nombre, tv_telefono, tv_oficio, tv_direccion;
    private GridView gridView;

    private Usuarios usuarios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detales_de_perfil);

        //Recupero el Id del la posicion del perfil selecionado por el usuario

        usuarios = RecyclerViewAdapter.getusuario();

        RelacionDeComponentes();
        InsertarDatos();

        //ConsultarListProductos productos = new ConsultarListProductos(this, idUser);
        //productos.startMethod(gridView);
    }

    //Metodo para Crear la relacion entre los componentes de la activity
    private void RelacionDeComponentes(){
        imagePerfil = (ImageView) findViewById(R.id.imageView_perfil);
        tv_nombre = (TextView) findViewById(R.id.textView_Nombre);
        tv_telefono = (TextView) findViewById(R.id.textView_Telefono);
        tv_oficio = (TextView) findViewById(R.id.textView_Oficio);
        tv_direccion = (TextView) findViewById(R.id.textView_Direccion);
        gridView = (GridView) findViewById(R.id.galeria);

        FloatingActionButton actionButton = findViewById(R.id.floatingActionButton);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityMensajes.class);
                startActivity(intent);
            }
        });
    }

    //metodo para insertar los datos del perfil segun el Id de la posiscion
    @SuppressLint("SetTextI18n")
    private void InsertarDatos(){

        tv_nombre.setText(usuarios.getNombre());
        tv_telefono.setText(usuarios.getTelefono());
        tv_direccion.setText(usuarios.getDireccion() + ", " + usuarios.getComunidad());
        tv_oficio.setText(usuarios.getOficio());

        CargarImagePerfil();
    }

    private void CargarImagePerfil() {
        if(! usuarios.equals("no")) {

            String url = getString(R.string.servidor1) + "/" + usuarios.getUrlImagen();

            ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    imagePerfil.setImageBitmap(response);
                }
            }, 0, 0, ImageView.ScaleType.CENTER, null,
                    new Response.ErrorListener() {


                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "No se pudo caragar la imagen", Toast.LENGTH_SHORT).show();
                }
            }) {

            };

        } else {
            imagePerfil.setImageResource(R.drawable.icono_perfil);
        }
    }

    public void VerImage(View view){
        Intent intent = new Intent(this, ActivityVerFotos.class);
        intent.putExtra("seleccion", 1);
        startActivity(intent);
    }

    public void Llamar(View view){
        String telefono = usuarios.getTelefono();

        if(!telefono.equals("")) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+telefono));
            startActivity(intent);
        } else {
            Toast.makeText(this, "Este perfil no tiene numero de telefono", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}