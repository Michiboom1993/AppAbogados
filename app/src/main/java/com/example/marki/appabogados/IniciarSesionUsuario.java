package com.example.marki.appabogados;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class IniciarSesionUsuario extends AppCompatActivity {


    private FirebaseDatabase database;
    private RecyclerView rvAbogados;
    private AdapterAbogados adapter;
    public String nombres [];
    public String nombre;
    public String telefono;
    private String nombreAbogado;
    private Spinner spinner;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sesion_usuario);

        Button contactar=findViewById(R.id.contactar);
        Button llamar= findViewById(R.id.llamar);
        Button buscar= findViewById(R.id.buscador);
        rvAbogados=findViewById(R.id.rvAbogados);
        ImageButton cerrarSesion=findViewById(R.id.button);
        adapter=new AdapterAbogados(this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        rvAbogados.setLayoutManager(l);
        rvAbogados.setAdapter(adapter);


        mAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();




       final DatabaseReference reference=database.getReference("Miembros");
       final DatabaseReference reference2=database.getReference("Miembros/"+currentUser.getUid());      //buscamos ambas referencias tanto la del usuario que esta identificado, como la normal

       cerrarSesion.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {            //Boton que permite cerrar sesion
               FirebaseAuth.getInstance().signOut();
               finish();
           }
       });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Query q=reference.orderByChild("rol").equalTo("abogado");           //Para buscar utilizaremos una query donde se comprueba si el rol coincide con abogado
                                                                                    //de esta forma podemos mostrar en nuestro recyclerlayout los abogados que se han encontrado
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        int cont = 0;
                        nombres = new String[(int) (dataSnapshot.getChildrenCount())];
                        String nombre4, rol4, email4,categoria4,contraseña4, edad4, telefono4,experiencia4;
                        //for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {

                           for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                                nombre4 =String.valueOf(dataSnapshot1.child("nombre").getValue(String.class));
                                rol4 =String.valueOf(dataSnapshot1.child("rol").getValue(String.class));
                                email4 =String.valueOf(dataSnapshot1.child("email").getValue(String.class));
                                categoria4 =String.valueOf(dataSnapshot1.child("categoria").getValue(String.class));
                                contraseña4 =String.valueOf(dataSnapshot1.child("contraseña").getValue(String.class));
                                edad4 =String.valueOf(dataSnapshot1.child("edad").getValue(Integer.class));
                                telefono4 =String.valueOf(dataSnapshot1.child("telefono").getValue(Integer.class));
                                experiencia4 =String.valueOf(dataSnapshot1.child("experiencia").getValue(Integer.class));

                               Abogado b= new Abogado(rol4,nombre4,email4,categoria4,contraseña4,Integer.valueOf(edad4),Integer.valueOf(telefono4),Integer.valueOf(experiencia4));
                               nombres[cont]=nombre4;
                               adapter.addAbogado(b);
                               //cont++;
                         //  }
                            //Abogado a = datasnapshot.getValue(Abogado.class);
                            //nombres[cont] = "" + a.getNombre();
                            //adapter.addAbogado(a);

                            cont++;

                            if (cont == (int) dataSnapshot.getChildrenCount()) {
                                InicializarSpinner();

                            }

                        }

                    }




                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        contactar.setOnClickListener(new View.OnClickListener() {                   //utilizamos la referencia del usuario identificado para leer y mandar los datos del usuario,
            @Override                                                               //así el abogado tendrá mas información sobre quien contacta con el
            public void onClick(View v) {

                reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        nombre= dataSnapshot.child("nombre").getValue().toString();
                        telefono= dataSnapshot.child("telefono").getValue().toString();

                        Intent intent= new Intent(IniciarSesionUsuario.this, Contactar.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("nombre",nombre);
                        bundle.putString("telf",telefono);
                        bundle.putString("nombreAbogado",nombreAbogado);

                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });
        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query q=reference.orderByChild("nombre").equalTo(nombreAbogado);            //Lanzamos una query donde el nombre del spinner seleccionado coincida con alguna de los nombres y leemos su numero de telefono
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int cont=0;

                        for (DataSnapshot datasnapshot:dataSnapshot.getChildren()){
                            cont++;

                            Abogado a = datasnapshot.getValue(Abogado.class);
                            telefono=String.valueOf(a.getTelefono());


                        }
                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+telefono)));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });

    }
    public void InicializarSpinner(){
        spinner=findViewById(R.id.spin);
        spinner.setAdapter(new ArrayAdapter<String>(IniciarSesionUsuario.this, android.R.layout.simple_spinner_item,nombres));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nombreAbogado=parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
