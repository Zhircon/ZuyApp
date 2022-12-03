package com.zuyinc.rutasuvxalapa.modelo.entidades;

import androidx.annotation.NonNull;

public class Facultad {

    private String uidFacultad;
    private String nombre;
    private String calle;
    private long numeroTelefono;
    private String correo;
    private String sitioWeb;
    private String urlImagenFacultad;
    private String uidColonia;

    public Facultad() {

    }

    public Facultad(String nombre, String calle, long numeroTelefono, String correo, String sitioWeb, String urlImagenFacultad, String uidColonia) {
        this.nombre = nombre;
        this.calle = calle;
        this.numeroTelefono = numeroTelefono;
        this.correo = correo;
        this.sitioWeb = sitioWeb;
        this.urlImagenFacultad = urlImagenFacultad;
        this.uidColonia = uidColonia;
    }

    public String getUidFacultad() {
        return uidFacultad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCalle() {
        return calle;
    }

    public long getNumeroTelefono() {
        return numeroTelefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public String getUrlImagenFacultad() {
        return urlImagenFacultad;
    }

    public String getUidColonia() {
        return uidColonia;
    }

    public void setUidFacultad(String uidFacultad) {
        this.uidFacultad = uidFacultad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setNumeroTelefono(long numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public void setUrlImagenFacultad(String urlImagenFacultad) {
        this.urlImagenFacultad = urlImagenFacultad;
    }

    public void setUidColonia(String uidColonia) {
        this.uidColonia = uidColonia;
    }

    @NonNull
    @Override
    public String toString() {
        return nombre;
    }
}
