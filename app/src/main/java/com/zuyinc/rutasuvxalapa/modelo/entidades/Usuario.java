package com.zuyinc.rutasuvxalapa.modelo.entidades;

public class Usuario {

    private String uidUsuario;
    private String correo;
    private String nombreUsuario;
    private String password;
    private String tipoUsuario;
    private String uidImagenUsuario;

    public Usuario() {
    }

    public Usuario(String nombreUsuario, String tipoUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.tipoUsuario = tipoUsuario;
    }

    public Usuario(String correo, String nombreUsuario, String password, String tipoUsuario) {
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.tipoUsuario = tipoUsuario;
    }

    public Usuario(String correo, String nombreUsuario, String password, String tipoUsuario, String uidImagenUsuario) {
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.tipoUsuario = tipoUsuario;
        this.uidImagenUsuario = uidImagenUsuario;
    }

    public String getUidUsuario() {
        return uidUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public String getUidImagenUsuario() {
        return uidImagenUsuario;
    }

    public void setUidUsuario(String uidUsuario) {
        this.uidUsuario = uidUsuario;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public void setUidImagenUsuario(String uidImagenUsuario) {
        this.uidImagenUsuario = uidImagenUsuario;
    }
}
