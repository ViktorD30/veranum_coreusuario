package com.veranum.coreusuario.dto;

public class UsuarioResponse {

    private Long id;
    private String rut;
    private String nombre;
    private String email;
    private String rol;
    private boolean bloqueado;
    private int intentosFallidos;

    public UsuarioResponse() {
    }

    public UsuarioResponse(
            Long id,
            String rut,
            String nombre,
            String email,
            String rol,
            boolean bloqueado,
            int intentosFallidos) {

        this.id = id;
        this.rut = rut;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
        this.bloqueado = bloqueado;
        this.intentosFallidos = intentosFallidos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public int getIntentosFallidos() {
        return intentosFallidos;
    }

    public void setIntentosFallidos(int intentosFallidos) {
        this.intentosFallidos = intentosFallidos;
    }
}
