package com.example.conociendoemprendedores.fragments.mi_perfil;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.conociendoemprendedores.ActivityEditarMiPerfil;
import com.example.conociendoemprendedores.ActivityInicio;
import com.example.conociendoemprendedores.ActivityRegistrarse;
import com.example.conociendoemprendedores.Datos.AdminSQLiteOpenHelper;
import com.example.conociendoemprendedores.Datos.RegistrarNuevoUsuario;
import com.example.conociendoemprendedores.LoginFragment;
import com.example.conociendoemprendedores.MisProductosActivity;
import com.example.conociendoemprendedores.R;
import com.example.conociendoemprendedores.conexiones.VolleySingleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MiPerfilFragment extends Fragment {

    private static View root;

    private EditText et_username, et_password;
    private String username, password;
    private Button buttonRegistrarse, buttonCrearCuenta, buttonMiProductos;
    private Bitmap imagenBM;
    ImageView imageView;
    private FloatingActionButton floatingActionButton;
    private  int id;
    private LoginFragment loginFragment;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(), "DB", null, 1);
        SQLiteDatabase DB = admin.getWritableDatabase();

        Cursor cursor = DB.rawQuery("select nombre, telefono, direccion, oficio, comunidad, foto, id_usuario from miPerfil", null);

        if (cursor.moveToFirst()) {

            root = inflater.inflate(R.layout.fragment_mi_perfil, container, false);


            TextView textViewNombre, textViewOficio, textViewTelefono, textViewDireccion;

            id = cursor.getInt(6);
            String nombre = cursor.getString(0);
            String telefono = cursor.getString(1);
            String direccion = cursor.getString(2);
            String oficio = cursor.getString(3);
            String comunidad = cursor.getString(4);
            String imageS = cursor.getString(5);


            imageView = (ImageView) root.findViewById(R.id.circleImageView_MiPerfil_Fragmnet);
            textViewNombre = root.findViewById(R.id.textView_Nombre_MiPerfilFrafment);
            textViewOficio = root.findViewById(R.id.textView_Oficio_MiPerfiloFragmen);
            textViewTelefono = root.findViewById(R.id.textView_Telefono_MiPerfilFragment);
            textViewDireccion = root.findViewById(R.id.textView_Direccion_MiPerfilFragment);


            CargarImagenUrl(imageS);
            textViewNombre.setText(nombre);
            textViewOficio.setText(oficio);
            textViewTelefono.setText(telefono);
            textViewDireccion.setText(direccion + ", " + comunidad);

            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, comunidades);
            // spinner.setAdapter(adapter);

            floatingActionButton = (FloatingActionButton) root.findViewById(R.id.fab_editar_MiPerfilFragment);
            buttonMiProductos = (Button) root.findViewById(R.id.button_MisProductos_MiPerfilFragment);

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ActivityEditarMiPerfil.class);
                    startActivity(intent);
                }
            });

            buttonMiProductos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), MisProductosActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);

                }
            });

        } else
            {

            root = inflater.inflate(R.layout.fragment_iniciar_sesion, container, false);

            et_username = root.findViewById(R.id.editTextIniciarSesionUsername);
            et_password = root.findViewById(R.id.editTextIniciarSesionPassword);

            buttonRegistrarse = root.findViewById(R.id.ButtonIniciarSesion);
            buttonCrearCuenta = root.findViewById(R.id.ButtonIniciarSesionCrearCuenta);

            buttonRegistrarse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = et_username.getText().toString();
                    String password = et_password.getText().toString();

                    if(username.equals("") || password.equals("")){
                        Toast.makeText(getContext(), "Debes intruducir tu usuario y contraseña", Toast.LENGTH_SHORT).show();
                    } else {

                        String url = getString(R.string.servidor1) + "/ConsultarMiPerfil.php?username=" + username + "&password=" + password;


                        ProgressDialog dialog = new ProgressDialog(getContext());
                        dialog.setMessage("Registrando");
                        dialog.show();
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                        JSONArray jsonArray = response.optJSONArray("miperfil");

                                        String nombre, oficio, telefono, direccion, comunidad, imagen;

                                        JSONObject jsonObject = null;

                                        try {
                                            jsonObject = jsonArray.getJSONObject(0);


                                            nombre = jsonObject.optString("nombre");


                                            if(! nombre.equals("0incorrecto0")) {
                                                id = jsonObject.optInt("id");
                                                oficio = jsonObject.optString("oficio");
                                                telefono = jsonObject.optString("telefono");
                                                direccion = jsonObject.optString("direccion");
                                                comunidad = jsonObject.optString("comarca");
                                                imagen = jsonObject.optString("imagen");

                                                Toast.makeText(getContext(), nombre, Toast.LENGTH_SHORT).show();

                                                AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(), "DB", null, 1);
                                                SQLiteDatabase DB = admin.getWritableDatabase();

                                                ContentValues contentValues = new ContentValues();
                                                contentValues.put("id_usuario", id);
                                                contentValues.put("nombre", nombre);
                                                contentValues.put("telefono", telefono);
                                                contentValues.put("direccion", direccion);
                                                contentValues.put("oficio", oficio);
                                                contentValues.put("comunidad", comunidad);
                                                contentValues.put("foto", imagen);

                                                DB.insert("miPerfil", null, contentValues);

                                                dialog.hide();
                                                Toast.makeText(getContext(), "Exito", Toast.LENGTH_SHORT).show();

                                            } else {
                                                dialog.hide();
                                                Toast.makeText(getContext(), "Datos incorrecto verifica tu usuario y contraseña", Toast.LENGTH_SHORT).show();
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                dialog.hide();
                                Toast.makeText(getContext(), "Error... ", Toast.LENGTH_SHORT).show();
                            }
                        });
                        VolleySingleton.getVolleyInstacia(getContext()).addToRequestQueue(jsonObjectRequest);
                    }
                }
            });

            buttonCrearCuenta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ActivityRegistrarse.class);
                    startActivity(intent);
                    root = null;
                }
            });
        }
        return root;
    }

    private void CargarImagenUrl (String imageS){

        String url = getString(R.string.servidor1) + "/" + imageS;
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);

            }
        }, 0, 0, ImageView.ScaleType.CENTER, null,
                new Response.ErrorListener() {


                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(getContext(),""+error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        VolleySingleton.getVolleyInstacia(getContext()).addToRequestQueue(imageRequest);
    }
}