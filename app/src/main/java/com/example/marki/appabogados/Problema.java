package com.example.marki.appabogados;

public class Problema {

    String telefono, usuario, incidencia;


    public Problema( String usuario, String incidencia,String telefono) {

        this.usuario = usuario;
        this.incidencia = incidencia;
        this.telefono=telefono;
    }
    public Problema(){

    }

    public String getTelefono() {
        return telefono;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getIncidencia() {
        return incidencia;
    }
    public String toString() {
        return "Problema{" +
                "incidencia='" + this.incidencia + '\'' +
                ", usuario='" + this.usuario +"telefono='" + this.telefono+
        '}';
    }
}