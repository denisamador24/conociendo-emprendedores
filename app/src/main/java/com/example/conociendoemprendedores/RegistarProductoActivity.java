package com.example.conociendoemprendedores;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.conociendoemprendedores.conexiones.VolleySingleton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegistarProductoActivity extends AppCompatActivity {
    private ImageView imageView;
    private EditText editTextNomre, editTextPrecio, editTextDescripcion;
    private Spinner spinnerCategorias;
    private Bitmap imagenP;
    private boolean selecionaImage = false;
    private static final int COD_SELECCIONA = 10;
    private ProgressDialog dialog;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar_producto);

        id = getIntent().getExtras().getInt("id");

        String [] categorias = {"Seleciona el tipo","Producto", "Servicio"};

        imageView = findViewById(R.id.imageView_RegistarProductoActivity);
        editTextNomre = findViewById(R.id.editTextNombre_RegistarProdutcActivity);
        editTextPrecio = findViewById(R.id.editTextPrecio_RegistarProductoActivity);
        editTextDescripcion = findViewById(R.id.editText_Descripcion_RegistarProductoActivity);
        spinnerCategorias = findViewById(R.id.spinner_Categoria_RegistarProductoActivity);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, categorias);
        spinnerCategorias.setAdapter(adapter);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/");
                startActivityForResult(intent.createChooser(intent, "Seleccione"), COD_SELECCIONA);
            }
        });
    }

    public void AgregarProducto(View view){
        String nombre, precio, descripcion, categoria;

        nombre = editTextNomre.getText().toString();
        precio = editTextPrecio.getText().toString();
        descripcion = editTextDescripcion.getText().toString();
        categoria = spinnerCategorias.getSelectedItem().toString();

        if(!nombre.equals("") || !precio.equals("") || !descripcion.equals("") || selecionaImage || !categoria.equals("Seleciona el tipo")){

            dialog = new ProgressDialog(this);
            dialog.setMessage("Agregando " + categoria);
            dialog.show();
            String url = getString(R.string.servidor1) + "/RegistrarProducto.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dialog.hide();
                    Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_LONG).show();
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.hide();
                    Toast.makeText(getApplicationContext(), "No se pudo agregar", Toast.LENGTH_SHORT).show();
                }
            }){

                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> stringMap = new HashMap<>();
                    stringMap.put("pertenece", String.valueOf(id));
                    stringMap.put("nombre", nombre);
                    stringMap.put("precio", precio);
                    stringMap.put("descripcion", descripcion);
                    stringMap.put("categoria", categoria);

                    ByteArrayOutputStream array = new ByteArrayOutputStream();
                    imagenP.compress(Bitmap.CompressFormat.JPEG, 100, array);
                    byte[] imaganeByte = array.toByteArray();
                    String imagenString = Base64.encodeToString(imaganeByte, Base64.DEFAULT);

                    stringMap.put("imagen", imagenString);

                    return stringMap;
                }
            };

            VolleySingleton.getVolleyInstacia(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, "Debes ingresar todos los datos e imagen", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == COD_SELECCIONA) {

            if (data != null) {


                Uri path = data.getData();

                try {
                    imagenP = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                    imageView.setImageBitmap(imagenP);
                    selecionaImage = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}