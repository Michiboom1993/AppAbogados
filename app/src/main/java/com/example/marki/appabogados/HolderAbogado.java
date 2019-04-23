package com.example.marki.appabogados;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class HolderAbogado extends RecyclerView.ViewHolder {

    private TextView contraseña;
    private TextView correo;
    private TextView nombre;
    private TextView categoria;
    private TextView experiencia;
    private TextView edad;
    private TextView telefono;
    private TextView rol;

    public HolderAbogado(View itemView) {
        super(itemView);

        nombre= (TextView) itemView.findViewById(R.id.nombreAbogado);
        categoria=(TextView) itemView.findViewById(R.id.categoriaAbogado);
        experiencia= (TextView)itemView.findViewById(R.id.experienciaAbogado);
        //contraseña= (TextView)itemView.findViewById(R.id.contraseñaAbogado);
        correo=(TextView) itemView.findViewById(R.id.correoAbogado);
        edad= (TextView)itemView.findViewById(R.id.edadAbogado);
        telefono = (TextView)itemView.findViewById(R.id.telefonoAbogado);
        //rol=(TextView) itemView.findViewById(R.id.rolAbogado);

    }

    public TextView getContraseña() {
        return contraseña;
    }

    public void setContraseña(TextView contraseña) {
        this.contraseña = contraseña;
    }

    public TextView getCorreo() {
        return correo;
    }

    public void setCorreo(TextView correo) {
        this.correo = correo;
    }

    public TextView getEdad() {
        return edad;
    }

    public void setEdad(TextView edad) {
        this.edad = edad;
    }

    public TextView getTelefono() {
        return telefono;
    }

    public void setTelefono(TextView telefono) {
        this.telefono = telefono;
    }

    public TextView getRol() {
        return rol;
    }

    public void setRol(TextView rol) {
        this.rol = rol;
    }

    public TextView getNombre() {
        return nombre;
    }

    public void setNombre(TextView nombre) {
        this.nombre = nombre;
    }

    public TextView getCategoria() {
        return categoria;
    }

    public void setCategoria(TextView categoria) {
        this.categoria = categoria;
    }

    public TextView getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(TextView experiencia) {
        this.experiencia = experiencia;
    }
}
