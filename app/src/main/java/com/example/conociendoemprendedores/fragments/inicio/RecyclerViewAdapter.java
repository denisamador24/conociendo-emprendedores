package com.example.conociendoemprendedores.fragments.inicio;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.conociendoemprendedores.DetallesUsuariosActivity;
import com.example.conociendoemprendedores.R;
import com.example.conociendoemprendedores.adaptadores.Usuarios;
import com.example.conociendoemprendedores.conexiones.VolleySingleton;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Usuarios> usuariosList;
    private LayoutInflater inflater;
    private Context mContext;
    private static Usuarios usuariosInfo;

    public RecyclerViewAdapter(List<Usuarios> listElemtsList, Context context) {
        this.inflater = LayoutInflater.from(context);
        this.usuariosList = listElemtsList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list_usuarios, null);
        return new RecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter.ViewHolder holder, final int position) {
        holder.bingData(usuariosList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return usuariosList.size();
    }

    public void setItems(List<Usuarios> items){
        usuariosList = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView nombre, oficio;

        public ViewHolder(View itemsView){
            super(itemsView);

            imageView = (ImageView) itemView.findViewById(R.id.ImageUsuario);
            nombre = itemView.findViewById(R.id.textViewNombre_ItemUsuarios);
            oficio = itemView.findViewById(R.id.textViewOficio_ItemUsuarios);


        }


        void bingData(final Usuarios usuarios, int position){
            nombre.setText(usuarios.getNombre());
            oficio.setText(usuarios.getOficio());

            if(usuarios.UrlImagen.equalsIgnoreCase("")){
                imageView.setImageResource(R.drawable.icono_perfil);
            } else {
                CargarImagen(usuarios.UrlImagen);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetallesUsuariosActivity.class);
                    intent.putExtra("telefono", usuarios.getTelefono());
                    usuariosInfo = usuarios;
                    mContext.startActivity(intent);
                }
            });
        }

        private void CargarImagen(String urlImagen) {
            String url = mContext.getString(R.string.servidor1) + "/" +urlImagen;


            ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                }
            }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });

            VolleySingleton.getVolleyInstacia(mContext).addToRequestQueue(imageRequest);
        }

    }

    public static Usuarios getusuario(){
        return usuariosInfo;
    }
}
