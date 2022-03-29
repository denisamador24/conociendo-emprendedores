package com.example.conociendoemprendedores.fragments.detallesusuarios;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.conociendoemprendedores.ActivityDetallesProducto;
import com.example.conociendoemprendedores.R;
import com.example.conociendoemprendedores.adaptadores.Productos;
import com.example.conociendoemprendedores.conexiones.VolleySingleton;

import java.util.List;


public class RecyclerViewAdapterProductos extends RecyclerView.Adapter<RecyclerViewAdapterProductos.ViewHolder> {
    private List<Productos> productosList;
    private Context context;
    private LayoutInflater inflater;
    private static Productos productosS;

    public RecyclerViewAdapterProductos(List<Productos> productos, Context context){
        this.productosList = productos;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerViewAdapterProductos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = inflater.inflate(R.layout.item_list_productos, null);

        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterProductos.ViewHolder holder, int position) {
        holder.bingData(position);

    }

    @Override
    public int getItemCount() {
        return productosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewNombre, textViewPrecio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageProducto_itemListProducto);
            textViewNombre = itemView.findViewById(R.id.nombre_producto_itemListProducto);
            textViewPrecio = itemView.findViewById(R.id.precio_producto_itemsListProducto);
        }

        @SuppressLint("SetTextI18n")
        public void bingData(int position) {

            Productos productos = productosList.get(position);

            String imageproductos = productos.getImagenUrl();
            CargarImagen(imageproductos);
            textViewNombre.setText(productos.getNombre());
            textViewPrecio.setText("C$ " + String.valueOf(productos.getPrecio()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ActivityDetallesProducto.class);
                    productosS = productos;
                    context.startActivity(intent);
                }
            });
        }

        private void CargarImagen(String nombre) {

            String url = context.getString(R.string.servidor1) + "/" + nombre;


            ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    imageView.setImageBitmap(response);
                }
            }, 0, 0, ImageView.ScaleType.CENTER, null,
                    new Response.ErrorListener() {


                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            VolleySingleton.getVolleyInstacia(context).addToRequestQueue(imageRequest);
        }
    }

    public static Productos getProducto(){
        return productosS;
    }
}
