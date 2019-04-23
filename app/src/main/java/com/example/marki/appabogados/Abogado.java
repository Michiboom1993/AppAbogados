package com.example.marki.appabogados;

public class Abogado {

    String rol, nombre, email, categoria, contraseña,problema;
    int edad, telefono, experiencia;

    public Abogado(String rol, String nombre, String email, String categoria, String contraseña, int edad, int telefono, int experiencia) {         //Se crea la clase abogado en la cual vamos
        this.rol = rol;                                                                                                                                   //Donde guardaremos los datos correspodientes a los abogados
        this.nombre = nombre;
        this.email = email;
        this.categoria = categoria;
        this.contraseña = contraseña;
        this.edad = edad;
        this.telefono = telefono;
        this.experiencia = experiencia;
        //this.problema=problema;
    }

    public Abogado() {
         }

    public String getProblema() {
        return problema;
    }

    public String getRol() {
        return rol;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getContraseña() {
        return contraseña;
    }

    public int getEdad() {
        return edad;
    }

    public int getTelefono() {
        return telefono;
    }

    public int getExperiencia() {
        return experiencia;
    }
}
