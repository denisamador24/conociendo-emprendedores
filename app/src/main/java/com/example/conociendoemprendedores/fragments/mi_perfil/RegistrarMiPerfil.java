package com.example.conociendoemprendedores.fragments.mi_perfil;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.conociendoemprendedores.Datos.AdminSQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegistrarMiPerfil implements Response.Listener<JSONObject>, Response.ErrorListener{
    private Context context;
    private String username;
    private String url = "https://conociendo-emprendedores.000webhostapp.com/ConsultarDatosMiPerfil.php?";


    public RegistrarMiPerfil (Context context){
        this.context = context;
    }

    public void startMethod(String username){
        this.username = username;

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url+"username="+username, null, this, this);
        requestQueue.add(jsonObjectRequest);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(context, "E.RegistrarMiPerfil", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray jsonArray = response.optJSONArray("micuenta");

        String nombre, telefono, oficio, direccion, comunidad, imagen;
        int id;

        try{
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            id = jsonObject.optInt("id_usuario");
            nombre = jsonObject.optString("nombre");
            telefono = jsonObject.optString("telefono");
            oficio = jsonObject.optString("oficio");
            direccion = jsonObject.optString("direccion");
            comunidad = jsonObject.optString("comunidad");
            imagen = jsonObject.optString("imagen");

            Toast.makeText(context, "paso", Toast.LENGTH_SHORT).show();
            Thread.sleep(1000);

            AdminSQLiteOpenHelper DB = new AdminSQLiteOpenHelper(context, "DB",null, 1);
            SQLiteDatabase database = DB.getWritableDatabase();

            ContentValues contentValues = new ContentValues();

            contentValues.put("id_usuario", id);
            contentValues.put("nombre", nombre);
            contentValues.put("telefono", telefono);
            contentValues.put("oficio", oficio);
            contentValues.put("direccion", direccion);
            contentValues.put("comunidad", comunidad);
            contentValues.put("foto", imagen);

            database.insert("miPerfil",null, contentValues);

            Toast.makeText(context, "IS", Toast.LENGTH_SHORT).show();
        }catch (JSONException | InterruptedException e){
            e.printStackTrace();
        }
    }
}
