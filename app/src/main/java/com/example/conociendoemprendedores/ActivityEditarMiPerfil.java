package com.example.conociendoemprendedores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.conociendoemprendedores.Datos.AdminSQLiteOpenHelper;
import com.example.conociendoemprendedores.conexiones.VolleySingleton;

public class ActivityEditarMiPerfil extends AppCompatActivity {

    private ImageView imageView;
    private EditText editTextNombre, editTextOficio, editTextTelefono, editTextDireccion;
    private Spinner spinnerComarcas;

    String[] comunidades = {
            "Selecciona una comunidad",
            "Moyogalpa",
            "La Paloma",
            "Esquipulas",
            "Los Ángeles",
            "Sacramento",
            "San Lazaro",
            "San Jose",
            "Las Cruzes",
            "La flor",
            "La concha",
            "San Marcos"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_mi_perfil);

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "DB", null, 1);
        SQLiteDatabase DB = admin.getWritableDatabase();

        Cursor cursor = DB.rawQuery("select nombre, telefono, direccion, oficio, comunidad, foto from miPerfil", null);

        if(cursor.moveToFirst()) {

            String nombre = cursor.getString(0);
            String telefono = cursor.getString(1);
            String direccion = cursor.getString(2);
            String oficio = cursor.getString(3);
            String comunidad = cursor.getString(4);
            String imageS = cursor.getString(5);

            imageView = (ImageView) findViewById(R.id.circleImageView_EditarMiperfilActivity);
            editTextNombre = (EditText) findViewById(R.id.editText_Nombre_EditarMiPerfilActivity);
            editTextTelefono = (EditText) findViewById(R.id.editText_Telefono_EditarMiPerfilActivity);
            editTextOficio = (EditText) findViewById(R.id.editText_Oficio_EditarMiPerfilActivity);
            editTextDireccion = (EditText) findViewById(R.id.editText_Direccion_EditarMiPerfil);
            spinnerComarcas = (Spinner) findViewById(R.id.spiner_comarcas_EditarMiPerfilActivity);

            CargarImagen(imageS);
            editTextNombre.setText(nombre);
            editTextTelefono.setText(telefono);
            editTextOficio.setText(oficio);
            editTextDireccion.setText(direccion);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_dropdown_item, comunidades);
            spinnerComarcas.setAdapter(adapter);

            for(int i = 0; i < comunidades.length; i++){
                if(comunidad.equals(comunidades[i])){
                    spinnerComarcas.setSelection(i);
                }
            }
        }
    }

    private void CargarImagen(String nombre) {
        String url = getString(R.string.servidor1) + "/" + nombre;

        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);
            }
        },0, 0, ImageView.ScaleType.CENTER, null,  new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getVolleyInstacia(this).addToRequestQueue(imageRequest);
    }

    public void EditarPerfil(View view){
        Toast.makeText(this, "Funcion en desarrolo", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}