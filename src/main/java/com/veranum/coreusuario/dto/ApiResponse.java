package com.veranum.coreusuario.dto;

public class ApiResponse<T> {

    private Integer codigo;
    private String mensaje;
    private Boolean error;
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(Integer codigo, String mensaje, Boolean error, T data) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.error = error;
        this.data = data;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
