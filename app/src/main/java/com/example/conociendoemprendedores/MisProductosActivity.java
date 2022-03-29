package com.example.conociendoemprendedores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.conociendoemprendedores.adaptadores.Productos;
import com.example.conociendoemprendedores.conexiones.VolleySingleton;
import com.example.conociendoemprendedores.fragments.detallesusuarios.RecyclerViewAdapterProductos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MisProductosActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Productos> productos;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_productos);

        recyclerView = findViewById(R.id.recyclerViewMisProductos);
        button = (Button) findViewById(R.id.ButtonAgregarProducto);
        int id = getIntent().getExtras().getInt("id");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistarProductoActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });


        productos = new ArrayList<>();
        String url = getString(R.string.servidor1) + "/ConsultarProductosUsuario.php?pertenece="+id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = response.optJSONArray("productos");

                        int id;
                        String nombre;
                        double precio;
                        String descripcion;
                        String categoria;
                        String imagenUrl;
                        JSONObject jsonObject = null;

                        try{
                            for(int i = 0; i < jsonArray.length(); i++){
                                jsonObject = jsonArray.getJSONObject(i);

                                id = jsonObject.optInt("id");
                                nombre = jsonObject.optString("nombre");
                                precio = jsonObject.getDouble("precio");
                                descripcion = jsonObject.getString("descripcion");
                                categoria = jsonObject.getString("categoria");
                                imagenUrl = jsonObject.getString("imagen");

                                productos.add(new Productos(id, nombre, precio, descripcion, imagenUrl, categoria));
                            }
                        } catch (JSONException e){

                        }

                        setRecyclerViewProductos();
                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), ""+error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        VolleySingleton.getVolleyInstacia(this).addToRequestQueue(jsonObjectRequest);
    }

    private void setRecyclerViewProductos(){
        RecyclerViewAdapterProductos adapterProductos = new RecyclerViewAdapterProductos(productos,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapterProductos);
    }

    @Override
    public void onBackPressed() {
        finish();
    }


}