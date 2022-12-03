/*
LEER LA CABECERA DEL ITINERARIO (CUANDO SE ESCRIBIERON ETAS NOTAS NO SE SABIAN TRAZAR VARIOS PUNTOS EN RUTA
DE GOOGLE MAPS POR SI LLEGARA A SER SENCILLO Y APRENDIERA XD) 18/09/2022
 */

package com.zuyinc.rutasuvxalapa.modelo.entidades;

import androidx.annotation.NonNull;

public class Parada {

    private String uidParada;
    private String nombreCalle;
    private float latitud;
    private float longitud;
    private String urlImagenParada;
    private String uidColonia;

    public Parada() {
    }

    public Parada(String nombreCalle, float latitud, float longitud, String urlImagenParada, String uidColonia) {
        this.nombreCalle = nombreCalle;
        this.latitud = latitud;
        this.longitud = longitud;
        this.urlImagenParada = urlImagenParada;
        this.uidColonia = uidColonia;
    }

    public String getUidParada() {
        return uidParada;
    }

    public String getNombreCalle() {
        return nombreCalle;
    }

    public float getLatitud() {
        return latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public String getUrlImagenParada() {
        return urlImagenParada;
    }

    public String getUidColonia() {
        return uidColonia;
    }

    public void setUidParada(String uidParada) {
        this.uidParada = uidParada;
    }

    public void setNombreCalle(String nombreCalle) {
        this.nombreCalle = nombreCalle;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public void setUrlImagenParada(String urlImagenParada) {
        this.urlImagenParada = urlImagenParada;
    }

    public void setUidColonia(String uidColonia) {
        this.uidColonia = uidColonia;
    }

    @NonNull
    @Override
    public String toString() {
        return "Parada de la calle : " + nombreCalle;
    }
}
