package com.example.conociendoemprendedores.fragments.inicio;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.conociendoemprendedores.ActivityDetallesPerfil;
import com.example.conociendoemprendedores.adaptadores.Usuarios;
import com.example.conociendoemprendedores.adaptadores.UsuariosAdapter;
import com.example.conociendoemprendedores.conexiones.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConsulListUser implements Response.Listener<JSONObject>, Response.ErrorListener {

    private List<Usuarios> usuariosList;
    private Context context;
    private ListView listView;
    public static String image;
    public ProgressDialog progressDialog;

    public ConsulListUser(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;
    }

    protected void starMethod(){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Cargando usuarios ...");
        progressDialog.show();

        usuariosList = new ArrayList<>();
        String urlConsultaListUsuarios = "https://conociendo-emprendedores.000webhostapp.com/ConsultarListaUsuarios.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlConsultaListUsuarios, null, this, this);
        VolleySingleton.getVolleyInstacia(context).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(context, "Error de conección", Toast.LENGTH_LONG).show();
        progressDialog.hide();
    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray jsonArray = response.optJSONArray("usuario");

        String imagen;
        String nombre;
        String telefono;
        String direccion;
        String oficio;
        String comarca;
        JSONObject jsonObject = null;

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);

                nombre = jsonObject.optString("nombre");
                telefono = jsonObject.optString("telefono");
                direccion = jsonObject.optString("direccion");
                oficio = jsonObject.optString("oficio");
                comarca = jsonObject.optString("comunidad");
                imagen = jsonObject.optString("imagen");

                usuariosList.add(new Usuarios(i + 1, nombre, telefono, direccion, oficio, comarca, imagen));
            }
            progressDialog.hide();
            PonerDatos();

        } catch (JSONException e) {

        }
    }

    private void PonerDatos(){
        UsuariosAdapter adapter = new UsuariosAdapter(context, usuariosList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Usuarios usuarios = usuariosList.get(position);

                Intent intent = new Intent(context, ActivityDetallesPerfil.class);
                intent.putExtra("id", usuarios.id);
                String[] datos = {
                        usuarios.nombre,
                        usuarios.telefono,
                        usuarios.direccion,
                        usuarios.oficio
                        //usuarios.DatoImagen
                };
                intent.putExtra("datos", datos);
                image = usuarios.UrlImagen;
                context.startActivity(intent);
            }
        });

        //UsuariosSQLite usuariosSQLite = new UsuariosSQLite(context);
        //usuariosSQLite.setUsuariosList(usuariosList);
    }
}