/*
LEER LA CABECERA DEL ITINERARIO (CUANDO SE ESCRIBIERON ETAS NOTAS NO SE SABIAN TRAZAR VARIOS PUNTOS EN RUTA
DE GOOGLE MAPS POR SI LLEGARA A SER SENCILLO Y APRENDIERA XD) 18/09/2022
 */

package com.zuyinc.rutasuvxalapa.modelo.entidades;

import androidx.annotation.NonNull;

public class Ruta {

    private String uidRuta;
    private int numeroRuta;
    private String nombreCallePtoInicial;
    private String nombreCallePtoFinal;
    private float latitudPtoInicial;
    private float longitudPtoInicial;
    private float latitudPtoFinal;
    private float longitudPtoFinal;
    private String urlImagenRuta;
    private String uidCamion;

    public Ruta() {
    }

    public Ruta(int numeroRuta, String nombreCallePtoInicial, String nombreCallePtoFinal, float latitudPtoInicial, float longitudPtoInicial, float latitudPtoFinal, float longitudPtoFinal, String urlImagenRuta, String uidCamion) {
        this.numeroRuta = numeroRuta;
        this.nombreCallePtoInicial = nombreCallePtoInicial;
        this.nombreCallePtoFinal = nombreCallePtoFinal;
        this.latitudPtoInicial = latitudPtoInicial;
        this.longitudPtoInicial = longitudPtoInicial;
        this.latitudPtoFinal = latitudPtoFinal;
        this.longitudPtoFinal = longitudPtoFinal;
        this.urlImagenRuta = urlImagenRuta;
        this.uidCamion = uidCamion;
    }

    public String getUidRuta() {
        return uidRuta;
    }

    public int getNumeroRuta() {
        return numeroRuta;
    }

    public String getNombreCallePtoInicial() {
        return nombreCallePtoInicial;
    }

    public String getNombreCallePtoFinal() {
        return nombreCallePtoFinal;
    }

    public float getLatitudPtoInicial() {
        return latitudPtoInicial;
    }

    public float getLongitudPtoInicial() {
        return longitudPtoInicial;
    }

    public float getLatitudPtoFinal() {
        return latitudPtoFinal;
    }

    public float getLongitudPtoFinal() {
        return longitudPtoFinal;
    }

    public String getUrlImagenRuta() {
        return urlImagenRuta;
    }

    public String getUidCamion() {
        return uidCamion;
    }

    public void setUidRuta(String uidRuta) {
        this.uidRuta = uidRuta;
    }

    public void setNumeroRuta(int numeroRuta) {
        this.numeroRuta = numeroRuta;
    }

    public void setNombreCallePtoInicial(String nombreCallePtoInicial) {
        this.nombreCallePtoInicial = nombreCallePtoInicial;
    }

    public void setNombreCallePtoFinal(String nombreCallePtoFinal) {
        this.nombreCallePtoFinal = nombreCallePtoFinal;
    }

    public void setLatitudPtoInicial(float latitudPtoInicial) {
        this.latitudPtoInicial = latitudPtoInicial;
    }

    public void setLongitudPtoInicial(float longitudPtoInicial) {
        this.longitudPtoInicial = longitudPtoInicial;
    }

    public void setLatitudPtoFinal(float latitudPtoFinal) {
        this.latitudPtoFinal = latitudPtoFinal;
    }

    public void setLongitudPtoFinal(float longitudPtoFinal) {
        this.longitudPtoFinal = longitudPtoFinal;
    }

    public void setUrlImagenRuta(String urlImagenRuta) {
        this.urlImagenRuta = urlImagenRuta;
    }

    public void setUidCamion(String uidCamion) {
        this.uidCamion = uidCamion;
    }

    @NonNull
    @Override
    public String toString() {
        return "Ruta : " + numeroRuta + " | " + nombreCallePtoInicial + " - " + nombreCallePtoFinal;
    }
}
