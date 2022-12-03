package com.zuyinc.rutasuvxalapa.modelo.entidades;

import androidx.annotation.NonNull;

public class Colonia {

    private String uidColonia;
    private String nombre;
    private int codigoPostal;

    public Colonia() {
    }

    public Colonia(String nombre, int codigoPostal) {
        this.nombre = nombre;
        this.codigoPostal = codigoPostal;
    }

    public String getUidColonia() {
        return uidColonia;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCodigoPostal() {
        return codigoPostal;
    }

    public void setUidColonia(String uidColonia) {
        this.uidColonia = uidColonia;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCodigoPostal(int codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    @NonNull
    @Override
    public String toString() {
        return nombre;
    }
}
