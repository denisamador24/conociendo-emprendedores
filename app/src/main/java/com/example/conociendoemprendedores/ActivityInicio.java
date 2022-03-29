package com.example.conociendoemprendedores;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.conociendoemprendedores.Datos.AdminSQLiteOpenHelper;

import com.example.conociendoemprendedores.conexiones.VolleySingleton;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ActivityInicio extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ImageView imageViewFoto;
    private TextView textViewnNombre;
    private TextView textViewOficio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Instacia con los dos componentes del activityInicio
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        imageViewFoto = (ImageView) findViewById(R.id.imageViewNav_header_main);
        textViewnNombre = (TextView) findViewById(R.id.textView_nav_header_main);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        //Objeto con los componentes del menu para el DrawerLayout
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.FragmentInicio, R.id.nav_MiPerfil, R.id.nav_AcercaDe)
                .setDrawerLayout(drawer)
                .build();

        //Objeto con istacia con el fragment del archivo content_mail.xml
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        //Enviar el el objeto con la instacia del fragment
        //Abjuntar los componentes del menus y framgments
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        //Enviar objeto con la instancia del navegador de los menus
        //Abjuntar los componentes this.fragments y el fragmnet de archivo xml
        NavigationUI.setupWithNavController(navigationView, navController);

        //Reemplazar datos si esta registrado el usuario
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getApplicationContext(), "DB", null, 1);
        SQLiteDatabase DB = admin.getWritableDatabase();

       /* Cursor cursor = DB.rawQuery("select foto, nombre, oficio from miPerfil ", null);
         if(cursor.moveToFirst()) {
             try {

                 String imageS = cursor.getString(0);
                 String nombre = cursor.getString(1);
                 String oficio = cursor.getString(2);

                 CargarImagen(imageS);
                 textViewnNombre.setText(nombre);;
             } catch (Exception e){

             }
         }*/
    }

    private void CargarImagen(String imageS) {

        String url = getString(R.string.servidor1) + "/" + imageS ;
        Toast.makeText(ActivityInicio.this, url, Toast.LENGTH_LONG).show();


        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                imageViewFoto.setImageBitmap(bitmap);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        VolleySingleton.getVolleyInstacia(this).addToRequestQueue(imageRequest);
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_inicio, menu);
        return true;
    }*/

    //Metodo para retornar el objeto con los menus ya isntanciados y franment del archivo xml
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}