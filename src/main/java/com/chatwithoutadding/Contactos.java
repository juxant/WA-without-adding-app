package com.chatwithoutadding;

import android.graphics.drawable.Drawable;

public class Contactos {

    private String numeros;
    private Drawable enviar;
    private Drawable borrar;

    public Contactos(String numeros, Drawable enviar, Drawable borrar) {
        this.numeros = numeros;
        this.enviar = enviar;
        this.borrar = borrar;
    }

    public String getNumeros() {
        return numeros;
    }

    public void setNumeros(String numeros) {
        this.numeros = numeros;
    }

    public Drawable getEnviar() {
        return enviar;
    }

    public void setEnviar(Drawable enviar) {
        this.enviar = enviar;
    }

    public Drawable getBorrar() {
        return borrar;
    }

    public void setBorrar(Drawable borrar) {
        this.borrar = borrar;
    }
}
