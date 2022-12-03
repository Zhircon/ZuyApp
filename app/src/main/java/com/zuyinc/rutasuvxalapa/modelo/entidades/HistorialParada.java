package com.zuyinc.rutasuvxalapa.modelo.entidades;

import java.sql.Date;

public class HistorialParada {

    private String uidHistorialParada;
    private String fechaUsoParada;
    private String uidUsuario;
    private String uidItinerario;

    public HistorialParada() {
    }

    public HistorialParada(String uidHistorialParada, String fechaUsoParada, String uidUsuario, String uidItinerario) {
        this.uidHistorialParada = uidHistorialParada;
        this.fechaUsoParada = fechaUsoParada;
        this.uidUsuario = uidUsuario;
        this.uidItinerario = uidItinerario;
    }

    public String getUidHistorialParada() {
        return uidHistorialParada;
    }

    public String getFechaUsoParada() {
        return fechaUsoParada;
    }

    public String getUidUsuario() {
        return uidUsuario;
    }

    public String getUidItinerario() {
        return uidItinerario;
    }

    public void setUidHistorialParada(String uidHistorialParada) {
        this.uidHistorialParada = uidHistorialParada;
    }

    public void setFechaUsoParada(String fechaUsoParada) {
        this.fechaUsoParada = fechaUsoParada;
    }

    public void setUidUsuario(String uidUsuario) {
        this.uidUsuario = uidUsuario;
    }

    public void setUidItinerario(String uidItinerario) {
        this.uidItinerario = uidItinerario;
    }
}
