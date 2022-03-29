package com.example.conociendoemprendedores;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.conociendoemprendedores.adaptadores.Productos;
import com.example.conociendoemprendedores.conexiones.VolleySingleton;
import com.example.conociendoemprendedores.fragments.detallesusuarios.RecyclerViewAdapterProductos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActivityDetallesProducto extends AppCompatActivity {

    private ImageSlider imageSlider;
    private TextView tv_nombre, tv_precio, tv_descripcion, textViewTitulo;
    private List<SlideModel> slideModels;
    private Productos productos;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_productos);

        imageSlider = findViewById(R.id.imagenSlider_imagensProducto_detallesProductoActivity);
        tv_nombre = (TextView) findViewById(R.id.textViewNombreProducto_DetallesProductoActivity);
        tv_precio = (TextView) findViewById(R.id.textViewPrecio_DetallesProductoActivity);
        tv_descripcion = (TextView) findViewById(R.id.textViewDescripcion_DetallesProductiActivity);
        textViewTitulo = (TextView) findViewById(R.id.textViewTitulo_DetallesProductoActivity);

        productos = RecyclerViewAdapterProductos.getProducto();
        slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(getString(R.string.servidor1) + "/"  + productos.imagenUrl));
        tv_nombre.setText(productos.getNombre());
        tv_precio.setText(String.valueOf(productos.getPrecio()));
        tv_descripcion.setText(productos.getDescripcion());
        textViewTitulo.setText("Prodcuto");


        String url = getString(R.string.servidor1) + "/ImagenesProductos.php?pertenece="+productos.getId();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray jsonArray = response.optJSONArray("imagenes");

                JSONObject jsonObject = null;

                for(int i = 0; i < jsonArray.length(); i++){
                    try {
                        jsonObject = jsonArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String imagenUrl =  jsonObject.optString("imagen");
                    slideModels.add(new SlideModel(imagenUrl));
                }
                imageSlider.setImageList(slideModels, true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getVolleyInstacia(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
}