package com.example.conociendoemprendedores.adaptadores;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Usuarios {

    public int id;
    public String nombre;
    public String telefono;
    public String direccion;
    public String oficio;
    public String comunidad;
    public String UrlImagen;
    public Bitmap Imagen;
    private String  username, password;

    public Usuarios(int id, String nombre, String telefono, String direccion, String oficio, String comunidad, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.oficio = oficio;
        this.comunidad = comunidad;
        this.UrlImagen = imagen;
        this.oficio = oficio;

        /*try {
            byte[] byteCode = Base64.decode(imagen, Base64.DEFAULT);
            this.Imagen = BitmapFactory.decodeByteArray(byteCode, 0, byteCode.length);
        }catch (Exception e){

        }*/
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public String getUrlImagen() {
        return UrlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.UrlImagen = urlImagen;

        try {
            byte[] byteCode = Base64.decode(urlImagen, Base64.DEFAULT);
            this.Imagen = BitmapFactory.decodeByteArray(byteCode, 0, byteCode.length);
        }catch (Exception e){

        }
    }

    public Bitmap getImagen() {
        return Imagen;
    }

    public void setImagen(Bitmap imagen) {
        Imagen = imagen;
    }

    public String getOficio() {
        return oficio;
    }

    public void setOficio(String oficio) {
        this.oficio = oficio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
