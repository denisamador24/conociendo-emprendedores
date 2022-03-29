package com.example.conociendoemprendedores.Datos;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.conociendoemprendedores.R;
import com.example.conociendoemprendedores.adaptadores.Usuarios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ConsultarUsernameIgual implements Response.Listener<JSONObject>, Response.ErrorListener{
    private Context context;
    private String[] datos;

    private RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;
    StringRequest stringRequest;

    public ConsultarUsernameIgual (Context context){
        this.context = context;
    }

    public void startMethod(String[] datos){
        this.datos = datos;

        requestQueue = Volley.newRequestQueue(context);

        String URLquery = context.getString(R.string.servidor1)+"InsertarDatosCE.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URLquery, null,this, this);
        requestQueue.add(jsonObjectRequest);
    }

    public void stardMethod2(Usuarios usuarios){

        stringRequest = new StringRequest(Request.Method.POST,
                context.getString(R.string.servidor1)+"/InsertarDatosCE.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        if(s.trim().equalsIgnoreCase("registra")) {
                            Toast.makeText(context, "Bien", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "No registra", Toast.LENGTH_SHORT).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "Error: "+ volleyError, Toast.LENGTH_LONG).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String nombre = usuarios.nombre;
                String oficio = usuarios.oficio;
                String telefono = usuarios.telefono;
                String comunidad = usuarios.comunidad;
                String direccion = usuarios.direccion;
                String username = usuarios.getUsername();
                String password = usuarios.getPassword();
                String imagen = usuarios.UrlImagen;

                Map<String, String> stringMap = new HashMap<>();
                stringMap.put("nombre", nombre);
                stringMap.put("oficio", oficio);
                stringMap.put("telefono", telefono);
                stringMap.put("comonidad", comunidad);
                stringMap.put("direccion", direccion);
                stringMap.put("username", username);
                stringMap.put("password", password);
                stringMap.put("imagen", imagen);

                return stringMap;
            }
        };

        requestQueue.add(stringRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(context, "Error consultar username iglual", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(context, response+"Corecta conexion username comparacion", Toast.LENGTH_SHORT).show();

        JSONArray jsonArray = response.optJSONArray("usuario");
        JSONObject jsonObject = null;
        String usernameTem = "";

        try {
            jsonObject = jsonArray.getJSONObject(0);
        } catch (JSONException e) {}

        usernameTem = jsonObject.optString("username");

        if(!usernameTem.equals(datos[6])){

            RegistrarNuevoUsuario registar = new RegistrarNuevoUsuario(context);
            registar.startMethod(datos);

        } else {
            Toast.makeText(context, "No se puede registrar, el nombre de usuario ya existe", Toast.LENGTH_SHORT).show();
        }
    }
}
