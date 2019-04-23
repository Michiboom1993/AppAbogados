package com.example.marki.appabogados;

public class Usuario {

    String email,nombre,contraseña, rol ;
    int telefono, edad;

    public Usuario(String rol, String email, String nombre, String contraseña,int telefono, int edad) {

        this.rol=rol;
        this.email=email;
        this.nombre=nombre;
        this.contraseña=contraseña;
        this.telefono= telefono;
        this.edad=edad;
    }
    public Usuario(){

    }

    public String getRol() {
        return rol;
    }

    public String getEmail() {
        return email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public int getTelefono() {
        return telefono;
    }

    public int getEdad() {
        return edad;
    }
}
