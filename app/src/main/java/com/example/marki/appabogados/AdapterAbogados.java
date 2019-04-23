package com.example.marki.appabogados;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class AdapterAbogados extends RecyclerView.Adapter<HolderAbogado> {                          //Esta clase permite utilizar el recyclerView, un layout donde almacenaremos los datos de los abogados cuando sean introducidos

    private List<Abogado> listAbogado= new ArrayList<>();
    private Context c;

    public AdapterAbogados(Context c) {

        this.c = c;
    }

    public void addAbogado(Abogado a){
        listAbogado.add(a);
        notifyItemInserted(listAbogado.size());
    }

    @Override
    public HolderAbogado onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.cardview_abogados,parent,false);       //relacionamos el recyclerView con el xml cardvew_abogados
        return new HolderAbogado(v);
    }

    @Override
    public void onBindViewHolder(HolderAbogado holder, int position) {              //Damos valor al recycler View
        holder.getNombre().setText("Nombre: "+listAbogado.get(position).getNombre());
        holder.getCategoria().setText("Categoria: "+listAbogado.get(position).getCategoria());
        holder.getExperiencia().setText("Años de experiencia: "+String.valueOf(listAbogado.get(position).getExperiencia()));
        holder.getCorreo().setText("Correo: "+listAbogado.get(position).getEmail());
        holder.getEdad().setText("Edad: "+String.valueOf(listAbogado.get(position).getEdad()));
        holder.getTelefono().setText("Telefono: "+String.valueOf(listAbogado.get(position).getTelefono()));
    }

    @Override

    public int getItemCount() {

        return listAbogado.size();
    }

}
