package com.example.conociendoemprendedores.fragments.detallesusuarios;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.conociendoemprendedores.R;
import com.example.conociendoemprendedores.adaptadores.Productos;
import com.example.conociendoemprendedores.adaptadores.Usuarios;
import com.example.conociendoemprendedores.conexiones.VolleySingleton;
import com.example.conociendoemprendedores.fragments.inicio.RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FotosUsuariosFragment extends Fragment {
    private RecyclerView recyclerViewProductos;
    private List<Productos> productosList;

    public FotosUsuariosFragment() {
        // Required empty public constructor
    }

    public static FotosUsuariosFragment newInstance(){

        FotosUsuariosFragment fragment = new FotosUsuariosFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_fotos_usuarios, container, false);

        Usuarios usuarios = RecyclerViewAdapter.getusuario();
        int idUsuario = usuarios.getId();

        recyclerViewProductos = root.findViewById(R.id.recyclerView_FotosUsuariosFraments);
        productosList = new ArrayList<>();

        String url = getString(R.string.servidor1) + "/ConsultarProductosUsuario.php?pertenece="+idUsuario;
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

                        productosList.add(new Productos(id, nombre, precio, descripcion, imagenUrl, categoria));
                    }
                } catch (JSONException e){

                }

                setRecyclerViewProductos();
            }
            }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getVolleyInstacia(getContext()).addToRequestQueue(jsonObjectRequest);

        return root;
    }

    private void setRecyclerViewProductos(){
        RecyclerViewAdapterProductos adapterProductos = new RecyclerViewAdapterProductos(productosList, getContext());
        recyclerViewProductos.setHasFixedSize(true);
        recyclerViewProductos.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerViewProductos.setAdapter(adapterProductos);
    }
}