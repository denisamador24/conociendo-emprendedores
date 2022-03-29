package com.example.conociendoemprendedores;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.example.conociendoemprendedores.conexiones.VolleySingleton;
import com.example.conociendoemprendedores.fragments.registrarse.FragmentRegistrarse1;
import com.example.conociendoemprendedores.fragments.registrarse.FragmentRegistrarse3;
import com.example.conociendoemprendedores.fragments.registrarse.FragmentRegistrarse4;

import java.util.HashMap;
import java.util.Map;

public class  ActivityRegistrarse extends AppCompatActivity{

    private TextView textViewNombre;
    private Button buttonSiguiente;
    private ProgressDialog dialog;

    private String imagen, nombre, oficio, telefono, direccion, comunidad, username, password;
    private int selectFragment = 0;

    FragmentRegistrarse1 fragmentRegistrarse1;
    FragmentRegistrarse3 fragmentRegistrarse3;
    FragmentRegistrarse4 fragmentRegistrarse4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);


        fragmentRegistrarse1 = new FragmentRegistrarse1();
        fragmentRegistrarse3 = new FragmentRegistrarse3();
        fragmentRegistrarse4 = new FragmentRegistrarse4();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_activity_registrarse, fragmentRegistrarse4).commit();

        textViewNombre = (TextView) findViewById(R.id.activity_registrarse_textViewNombre);
        buttonSiguiente = (Button) findViewById(R.id.activity_registrarse_buttonSiguiente);

    }

    public void Siguiente(View view){

        switch (selectFragment){

            case 0: Fragment1(); break;

            case 1: Fragment2(); break;

            case 2: CrearCuenta(); break;
        }
    }

    //Logica del fragment3 para pasar al fragment1
    private void Fragment1(){
        if(fragmentRegistrarse4.CamposValidos()){
            nombre = fragmentRegistrarse4.getNombre();
            oficio = fragmentRegistrarse4.getOficio();
            imagen = fragmentRegistrarse4.getImagen();

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_activity_registrarse, fragmentRegistrarse1).commit();
            selectFragment++;
            textViewNombre.setText(nombre);
        } else {
            Toast.makeText(this, "Debes escribir tu nombre y oficio", Toast.LENGTH_SHORT).show();
        }
    }

    //Logica del fragment1 para pasar al Fragment2
    private void Fragment2(){
        if(fragmentRegistrarse1.CamposValidos()){

            telefono = fragmentRegistrarse1.getTelefono();
            direccion = fragmentRegistrarse1.getDireccion();
            comunidad = fragmentRegistrarse1.getComunidad();

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_activity_registrarse, fragmentRegistrarse3).commit();
            selectFragment++;
        } else {
            Toast.makeText(this, "Debes escribir telefono, dirección y seleccionar una comarca", Toast.LENGTH_LONG).show();
        }
    }


    private void CrearCuenta(){
        if (fragmentRegistrarse3.CamposValidos()){

            username = fragmentRegistrarse3.getUsername();
            password = fragmentRegistrarse3.getPassword();

            Registrar();
        } else {
            Toast.makeText(getApplicationContext(), "Nombre de usuario debe tener 4 o mas caracteres y contraseña 8 o mas caracteres ", Toast.LENGTH_SHORT).show();
        }
    }

    private void Registrar(){
        String url = getString(R.string.servidor1) + "/RegistrarUsuario.php";

        dialog = new ProgressDialog(this);
        dialog.setMessage("Creando perfil");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.hide();
                Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_LONG).show();
                try {
                    Thread.sleep(500);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.hide();
                Toast.makeText(getApplicationContext(), "Error de regsitro: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> stringMap = new HashMap<>();

                if (imagen.equals("")){
                    stringMap.put("imagen", "no");
                } else{
                    stringMap.put("imagen", imagen);
                }

                stringMap.put("nombre", nombre);
                stringMap.put("oficio", oficio);
                stringMap.put("telefono", telefono);
                stringMap.put("comarca", comunidad);
                stringMap.put("direccion", direccion);
                stringMap.put("username", username);
                stringMap.put("password", password);

                return stringMap;
            }
        };


        VolleySingleton.getVolleyInstacia(this).addToRequestQueue(stringRequest);
    }
}