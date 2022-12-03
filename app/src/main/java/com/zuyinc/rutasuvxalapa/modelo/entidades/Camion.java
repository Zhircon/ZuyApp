package com.zuyinc.rutasuvxalapa.modelo.entidades;

import androidx.annotation.NonNull;

public class Camion {

    private String uidCamion;
    private int numero;
    private String color;
    private String urlImagen;

    public Camion() {

    }

    public Camion(int numero, String color, String urlImagen) {
        this.numero = numero;
        this.color = color;
        this.urlImagen = urlImagen;
    }

    public String getUidCamion() {
        return uidCamion;
    }

    public int getNumero() {
        return numero;
    }

    public String getColor() {
        return color;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUidCamion(String uidCamion) {
        this.uidCamion = uidCamion;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    @NonNull
    @Override
    public String toString() {
        return "Camion numero : " + numero + " " + color;
    }

}
