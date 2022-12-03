package com.zuyinc.rutasuvxalapa.modelo.entidades;

import com.google.firebase.database.Exclude;

import java.sql.Date;

public class Comentario {

    private String uidComentario;
    private String descripcionComentario;
    private String tipoComentario;
    private String fechaComentario;
    private String uidUsuario;

    public Comentario() {
    }

    public Comentario(String descripcionComentario, String tipoComentario, String fechaComentario, String uidUsuario) {
        this.descripcionComentario = descripcionComentario;
        this.tipoComentario = tipoComentario;
        this.fechaComentario = fechaComentario;
        this.uidUsuario = uidUsuario;
    }

    public String getUidComentario() {
        return uidComentario;
    }

    public String getDescripcionComentario() {
        return descripcionComentario;
    }

    public String getTipoComentario() {
        return tipoComentario;
    }

    public String getFechaComentario() {
        return fechaComentario;
    }

    public String getUidUsuario() {
        return uidUsuario;
    }

    public void setUidComentario(String uidComentario) {
        this.uidComentario = uidComentario;
    }

    public void setDescripcionComentario(String descripcionComentario) {
        this.descripcionComentario = descripcionComentario;
    }

    public void setTipoComentario(String tipoComentario) {
        this.tipoComentario = tipoComentario;
    }

    public void setFechaComentario(String fechaComentario) {
        this.fechaComentario = fechaComentario;
    }

    public void setUidUsuario(String uidUsuario) {
        this.uidUsuario = uidUsuario;
    }

}
