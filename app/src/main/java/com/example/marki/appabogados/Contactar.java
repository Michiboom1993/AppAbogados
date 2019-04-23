package com.example.marki.appabogados;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Contactar extends AppCompatActivity {              //Clase que permite al usuario contactar con el abogado, no incluye llamada
    private FirebaseDatabase database;
    public String llave;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactar);


        Bundle bundle= new Bundle(getIntent().getExtras());     //bundle que recoge el telefono y el nombre del usuario para mandarselo al abogado

       final EditText problema=(EditText) findViewById(R.id.problema);
       final String nombre= bundle.getString("nombre");
       final String telefono=bundle.getString("telf");
       final String nombreAbogado=bundle.getString("nombreAbogado");
        database = FirebaseDatabase.getInstance();
        Button enviar= findViewById(R.id.enviar);

        final DatabaseReference reference=database.getReference("Miembros");

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Query q=reference.orderByChild("nombre").equalTo(nombreAbogado);           //Se crea una query donde se comprueba si el nombre es igual al nombre

                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String texto= problema.getText().toString();

                        for (DataSnapshot datasnapshot:dataSnapshot.getChildren()){
                           // String mGroupId = reference.push().getKey();
                            int cont=((int)datasnapshot.getChildrenCount())-8;

                            llave= datasnapshot.getKey();
                            reference.child(llave).child("problema"+cont).child("incidencia").setValue(texto);
                            reference.child(llave).child("problema"+cont).child("usuario").setValue(nombre);
                            reference.child(llave).child("problema"+cont).child("telefono").setValue(telefono);




                            cont++;

                        }


                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Toast.makeText(getApplicationContext(),"Mensaje enviado correctamente",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Contactar.this,IniciarSesionUsuario.class));
                finish();

            }
        });

    }
}
