package com.example.conociendoemprendedores.fragments.mi_perfil;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConsultarMiPerfil implements Response.Listener<JSONObject>, Response.ErrorListener {
    Context context;
    private String username;
    private String password;

    String url = "https://conociendo-emprendedores.000webhostapp.com/ConsultarMiPerfil.php?";

    public ConsultarMiPerfil (Context context){
        this.context = context;
    }

    public void startMethod(String username, String password){
        this.username = username;
        this.password = password;

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url+"username="+username, null, this, this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(context, "Error al inisiar sesion", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray jsonArray = response.optJSONArray("cuenta");

        try{
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String username = jsonObject.optString("username");
            String password = jsonObject.optString("password");

            if(username.equals("no existe") && password.equals("no")){
                Toast.makeText(context, "No se registrado una cuenta con este nombre de usuario", Toast.LENGTH_SHORT).show();

            } else if (this.username.equals(username) && this.password.equals(password)){
                RegistrarMiPerfil registrar = new RegistrarMiPerfil(context);
                registrar.startMethod(username);

            } else{
                Toast.makeText(context, "Contraseña es incorrecta", Toast.LENGTH_SHORT).show();
            }
        }catch (JSONException e){

        }
    }
}
