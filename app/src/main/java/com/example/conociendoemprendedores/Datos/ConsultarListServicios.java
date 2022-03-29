package com.example.conociendoemprendedores.Datos;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

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

public class ConsultarListServicios implements Response.Listener<JSONObject>, Response.ErrorListener{

    private Context context;
    private int idUser;
    List<Productos> serviciosList;
    GridView gridView;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;

    public ConsultarListServicios(Context context, int idUser){
        this.context = context;
        this.idUser = idUser;
    }

    public void startMethod(GridView gridView){
        this.gridView = gridView;

        requestQueue = Volley.newRequestQueue(context);
        serviciosList = new ArrayList();

        String url = context.getString(R.string.servidor1) + "/ConsultarServiciosUsuario.php?pertenece="+idUser;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(context, "Consultado Servicios", Toast.LENGTH_SHORT).show();

        JSONArray jsonArray = response.optJSONArray("servicios");

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

                serviciosList.add(new Productos(i, nombre, precio, descripcion, imagen, ""));
            }
        }catch (JSONException e){

        }

        ProductosAdapter adapter = new ProductosAdapter(context, serviciosList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
