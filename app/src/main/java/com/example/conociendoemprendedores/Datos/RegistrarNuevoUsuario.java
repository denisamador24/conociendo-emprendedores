package com.example.conociendoemprendedores.Datos;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RegistrarNuevoUsuario implements Response.Listener<JSONObject>, Response.ErrorListener{

    private Context context;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private String[] datos;

    public RegistrarNuevoUsuario(Context context) {
        this.context = context;
    }

    public void startMethod(String[] datos){
        this.datos = datos;
        requestQueue = Volley.newRequestQueue(context);



    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(context, "Error al registrase", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(context, "Registro correcto", Toast.LENGTH_SHORT).show();
    }
}
