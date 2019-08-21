package com.chatwithoutadding;

import android.graphics.drawable.Drawable;

public class Paises {

    private String nombre;
    private Drawable imagen;
    private int prefijo;

    public Paises(String nombre, Drawable imagen, int prefijo) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.prefijo = prefijo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Drawable getImagen() {
        return imagen;
    }

    public void setImagen(Drawable imagen) {
        this.imagen = imagen;
    }

    public int getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(int prefijo) {
        this.prefijo = prefijo;
    }
}
