package com.example.conociendoemprendedores.fragments.detallesusuarios;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.conociendoemprendedores.R;
import com.example.conociendoemprendedores.adaptadores.Usuarios;
import com.example.conociendoemprendedores.conexiones.VolleySingleton;
import com.example.conociendoemprendedores.fragments.inicio.RecyclerViewAdapter;

public class InformacionUsuariosFragment extends Fragment {

    private ImageView imageViewPerfil;
    private Usuarios  usuarios;

    public InformacionUsuariosFragment() {
        // Required empty public constructor
    }

    public static InformacionUsuariosFragment newInstance() {

        InformacionUsuariosFragment fragment = new InformacionUsuariosFragment();
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
       View root =  inflater.inflate(R.layout.fragment_informacion_usuarios, container, false);


        TextView textViewNombre, textViewTelefono, textViewOficio, textViewDireccion;


        imageViewPerfil = (ImageView) root.findViewById(R.id.circleImageView_Perfil_informacionUsuarioFragmnet);
        textViewNombre = root.findViewById(R.id.textView_Nombre_informacionUsuarioFragmnet);
        textViewTelefono = root.findViewById(R.id.textView_Telefono_informacionUsuarioFragment);
        textViewOficio = root.findViewById(R.id.textView_Oficio_informacionUsuarioFragmen);
        textViewDireccion = root.findViewById(R.id.textView_Direccion_informacionUsuarioFragment);

        usuarios = RecyclerViewAdapter.getusuario();

        CargarImagen();
        textViewNombre.setText(usuarios.getNombre());
        textViewTelefono.setText(usuarios.getTelefono());
        textViewOficio.setText(usuarios.getOficio());
        textViewDireccion.setText(usuarios.getDireccion());

        return root;
    }

    private void CargarImagen() {

        String url = getContext().getString(R.string.servidor1) + "/"+ usuarios.getUrlImagen();

        if(!url.equals("")) {

            ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    imageViewPerfil.setImageBitmap(bitmap);
                }
            }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });

            VolleySingleton.getVolleyInstacia(getContext()).addToRequestQueue(imageRequest);
        } else {
            imageViewPerfil.setImageResource(R.drawable.icono_inicio);
        }
    }
}