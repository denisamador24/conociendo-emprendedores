package com.example.conociendoemprendedores.Datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create table usuarios (id_usuario int primary key, nombre text,telefono text, direccion text, oficio text, comunidad text, foto text)");
        DB.execSQL("create table miPerfil (id_usuario int primary key, nombre text,telefono text, direccion text, oficio text, comunidad text, foto text)");
        DB.execSQL("create table registros (listdescargada text, registrado text, AUsuarios int, cantindadU int)");
        DB.execSQL("create table productos (id_productos int, nombre text, precio real, descrpcion text, id_pertenece)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
