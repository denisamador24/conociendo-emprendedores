package com.example.conociendoemprendedores.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.conociendoemprendedores.R;
import java.util.List;

public class ProductosAdapter extends BaseAdapter{

    private Context context;
    private List<Productos> productos2List;

    public ProductosAdapter(Context context, List<Productos> productos2List) {
        this.context = context;
        this.productos2List = productos2List;
    }

    @Override
    public int getCount() {
        return productos2List.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        TextView nombre;
        TextView precio;

        Productos productos = productos2List.get(position);

        if(convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_productos, null);

        imageView = convertView.findViewById(R.id.imageProducto_itemListProducto);
        nombre = convertView.findViewById(R.id.nombre_producto_itemListProducto);
        precio = convertView.findViewById(R.id.precio_producto_itemsListProducto);

        imageView.setImageBitmap(productos.imagen);
        nombre.setText(productos.nombre);
        precio.setText(String.valueOf(productos.precio));

        return convertView;
    }

}
