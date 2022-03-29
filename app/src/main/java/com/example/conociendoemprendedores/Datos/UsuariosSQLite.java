package com.example.conociendoemprendedores.Datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.conociendoemprendedores.adaptadores.Usuarios;

import java.util.ArrayList;
import java.util.List;

public class UsuariosSQLite {

    Context context;
    private List<Usuarios> usuariosList;

    public UsuariosSQLite(Context context){
        this.context = context;
    }

    public boolean ConsultarRegistros (){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, "DB", null, 1);
        SQLiteDatabase DB = admin.getWritableDatabase();

        Cursor consulta = DB.rawQuery("select listdescargada from registros", null);
        if (consulta.moveToFirst()) {
            String registro = consulta.getString(0);
            if(registro.equals("si")){
                DB.close();
                return true;
            } else {
                DB.close();
                return false;
            }
        } else {
            Toast.makeText(context, "else DB", Toast.LENGTH_SHORT).show();
        }
        DB.close();
        return false;
    }

    public List<Usuarios> ConsultarUsuariosSQLite(){
        usuariosList = new ArrayList<>();
        int cantidad = 0;

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, "DB", null, 1);
        SQLiteDatabase DB = admin.getWritableDatabase();

        Cursor consulta = DB.rawQuery("select cantindadU from registros", null);
        if (consulta.moveToFirst()){
            cantidad = consulta.getInt(0);
            Toast.makeText(context, ""+cantidad, Toast.LENGTH_SHORT).show();

            for(int i = 1; i <= cantidad; i++) {
                Cursor consulta2 = DB.rawQuery("select * from usuarios where id_usuario = "+i, null);

                if (consulta2.moveToFirst()) {
                    int id = consulta2.getInt(0);
                    String nombre = consulta2.getString(1);
                    String telefono = consulta2.getString(2);
                    String direccion = consulta2.getString(3);
                    String oficio = consulta2.getString(4);
                    String comunidad = consulta2.getString(5);
                    String imagen = consulta2.getString(6);

                    usuariosList.add(new Usuarios(id, nombre, telefono, direccion, oficio, comunidad, imagen));
                } else {
                    Toast.makeText(context, "no se encontradron datos en la posicion " + i, Toast.LENGTH_SHORT);
                }
            }
        } else {
            Toast.makeText(context, "no se encontro cantidad", Toast.LENGTH_SHORT).show();
        }

        DB.close();
        return usuariosList;
    }

    public void setUsuariosList(List<Usuarios> usuariosList){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, "DB", null, 1);
        SQLiteDatabase DB = admin.getWritableDatabase();

        for(int i = 0; i < usuariosList.size(); i++){
            Usuarios usuarios = usuariosList.get(i);

            ContentValues contentValues = new ContentValues();
            contentValues.put("id_usuario", usuarios.id + 1);
            contentValues.put("nombre", usuarios.nombre);
            contentValues.put("telefono", usuarios.telefono);
            contentValues.put("direccion", usuarios.direccion);
            contentValues.put("oficio", usuarios.oficio);
            contentValues.put("comunidad", usuarios.comunidad);
            contentValues.put("foto", usuarios.UrlImagen);

            DB.insert("usuarios",null, contentValues);
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("listdescargada", "si");
        DB.insert("registros", null, contentValues);

        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("cantindadU", usuariosList.size());
        DB.insert("registros", null, contentValues1);

        DB.close();
    }
}
