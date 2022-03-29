package com.example.conociendoemprendedores.adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.conociendoemprendedores.R;

import java.util.List;

public class UsuariosAdapter extends BaseAdapter {

    private Context context;
    private List<Usuarios> usuariosList;

    public UsuariosAdapter(Context context, List<Usuarios> usuariosList) {
        this.context = context;
        this.usuariosList = usuariosList;
    }


    @Override
    public int getCount() {
        return usuariosList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imagePerfil;
        TextView nombre;
        TextView oficio;
        TextView comarca;

        Usuarios usuarios = usuariosList.get(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_usuarios, null);
        }

        imagePerfil = convertView.findViewById(R.id.ImageUsuario);
        nombre = convertView.findViewById(R.id.textViewNombre_ItemUsuarios);
        oficio = convertView.findViewById(R.id.textViewOficio_ItemUsuarios);
        //comarca = convertView.findViewById(R.id.comarca);

        imagePerfil.setImageBitmap(usuarios.getImagen());
        nombre.setText(usuarios.nombre);
        oficio.setText("Oficio: " + usuarios.oficio);
        //comarca.setText("Comarca: " + usuarios.comunidad);

        return convertView;
    }


}
