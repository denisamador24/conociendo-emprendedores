package com.example.conociendoemprendedores.fragments.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.conociendoemprendedores.R;
import com.example.conociendoemprendedores.adaptadores.Usuarios;
import com.example.conociendoemprendedores.conexiones.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewUsuarios;
    private List<Usuarios> usuariosList;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewUsuarios = root.findViewById(R.id.RecyclerViewUsuariosList);

        CaragarUsuarios();
        return root;
    }

    private void CaragarUsuarios(){
        usuariosList = new ArrayList<>();

        String url = getString(R.string.servidor1) + "/ConsultarListaUsuarios.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                JSONArray jsonArray = jsonObject.optJSONArray("usuario");

                int id;
                String imagen;
                String nombre;
                String telefono;
                String direccion;
                String oficio;
                String comarca;
                JSONObject mJsonObject = null;

                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        mJsonObject = jsonArray.getJSONObject(i);

                        id = mJsonObject.optInt("id");
                        nombre = mJsonObject.optString("nombre");
                        telefono = mJsonObject.optString("telefono");
                        direccion = mJsonObject.optString("direccion");
                        oficio = mJsonObject.optString("oficio");
                        comarca = mJsonObject.optString("comunidad");
                        imagen = mJsonObject.optString("imagene");

                        usuariosList.add(new Usuarios(id, nombre, telefono, direccion, oficio, comarca, imagen));
                    }
                    setListRecyclerView();

                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error de coneccion"+e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        },

                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), ""+volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        });

        VolleySingleton.getVolleyInstacia(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void setListRecyclerView(){
        RecyclerViewAdapter adaptador = new RecyclerViewAdapter(usuariosList, getContext());
        recyclerViewUsuarios.setHasFixedSize(true);
        recyclerViewUsuarios.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewUsuarios.setAdapter(adaptador);
    }
}