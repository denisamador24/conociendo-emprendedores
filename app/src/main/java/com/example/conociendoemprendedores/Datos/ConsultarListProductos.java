package com.example.conociendoemprendedores.Datos;

import android.content.Context;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.conociendoemprendedores.R;
import com.example.conociendoemprendedores.adaptadores.Productos;
import com.example.conociendoemprendedores.adaptadores.ProductosAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConsultarListProductos implements Response.Listener<JSONObject>, Response.ErrorListener{

    Context context;
    private int idUser;
    private List<Productos> productosList;
    private GridView gridView;
    private JsonObjectRequest jsonObjectRequest;


    public ConsultarListProductos(Context context, int idUser){
        this.context = context;
        this.idUser = idUser;
    }

    public void startMethod(GridView gridView){
        this.gridView = gridView;

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        productosList = new ArrayList();

        String url = context.getString(R.string.servidor1) + "/ConsultarProductosUsuario.php?pertenece="+idUser;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        requestQueue.add(jsonObjectRequest);
    }
    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray jsonArray = response.optJSONArray("productos");
        String nombre;
        double precio;
        String descripcion;
        String imagen;
        JSONObject jsonObject = null;
        try {
            for (int i = 0; i < jsonArray.length(); i++) {

                jsonObject = jsonArray.getJSONObject(i);

                nombre = jsonObject.optString("nombre");
                precio = jsonObject.getDouble("precio");
                descripcion = jsonObject.optString("descripcion");
                imagen = jsonObject.optString("imagen");

                productosList.add(new Productos(i, nombre, precio, descripcion, imagen, ""));
            }
        }catch (JSONException e){

        }

        ProductosAdapter adapter = new ProductosAdapter(context, productosList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
