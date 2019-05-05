package com.example.marki.appabogados;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marki.appabogados.Chat.Main;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class IniciarSesionAbogado extends AppCompatActivity {
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    public String usuario, incidencia, telefono;
    public String [] lista= new String [100];
    Bundle bundle= new Bundle();
    public String nombre1;
    public String nombre2;


    public String [][] lista1= new String[100][3];
    public ListView list;




    Boolean ok = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sesion_abogado);




        list = findViewById(R.id.list11);

        ImageButton cerrarSesion=findViewById(R.id.button);
        Button comprobar=findViewById(R.id.comprobar);

        database = FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=mAuth.getCurrentUser();


        /////////////////////////////////////////////////////*********************************************************///////////////////////////////////////////////////////
        final DatabaseReference reference1= database.getReference("Miembros/"+currentUser.getUid());

       final DatabaseReference reference=database.getReference("Miembros/"+currentUser.getUid()).child("problema0");     //buscamos la referencia del usuario que esta iniciado sesion

                                                                                                                         //de esta forma sabemos donde tenemos que leer los datos, para saber si nos han enviado algun mensaje

        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int cont=0;
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    if(dataSnapshot1.getChildrenCount()==3){
                        lista[cont]=dataSnapshot1.getKey();
                        lista1[cont][0]=dataSnapshot1.child("incidencia").getValue(String.class);
                        lista1[cont][1]=dataSnapshot1.child("telefono").getValue(String.class);
                        lista1[cont][2]=dataSnapshot1.child("usuario").getValue(String.class);

                        cont++;
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(IniciarSesionAbogado.this, android.R.layout.simple_expandable_list_item_1, contar(lista));
                list.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });




      cerrarSesion.setOnClickListener(new View.OnClickListener() {          //Permite cerrar la sesion iniciada
           @Override
           public void onClick(View v) {
               FirebaseAuth.getInstance().signOut();
               finish();
           }
       });

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Problema problema1 =dataSnapshot.getValue(Problema.class);
                    nombre2=problema1.getUsuario();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nombre1=dataSnapshot.child("nombre").getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Intent intent= new Intent(IniciarSesionAbogado.this, Main.class);
        bundle.putString("nombre1",nombre2);
        bundle.putString("nombre2",nombre1);
        intent.putExtras(bundle);
        startActivity(intent);
    }
});
            comprobar.setOnClickListener(new View.OnClickListener() {        //listener del boton comprobar


                @Override
                public void onClick(View v) {

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {                   //Llamamos al listener, el cual si ha cambiado alguno de los valores, se produce la lectura
                            if (dataSnapshot.exists()) {
                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        TextView nombre = findViewById(R.id.nombreProblema);
                                        TextView inc = findViewById(R.id.problemaProblema);
                                        TextView telf = findViewById(R.id.telefonoProblema);

                                        Problema problema = dataSnapshot.getValue(Problema.class);
                                        usuario = problema.getUsuario();
                                        incidencia = problema.getIncidencia();
                                        telefono = problema.getTelefono();

                                        nombre.setText("Usuario: " + usuario);
                                        inc.setText("Problema: " + incidencia);
                                        telf.setText("Telefono :" + telefono);


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            } else {
                                Toast.makeText(getApplicationContext(), "No tienes ningun contacto en la bandeja de entrada", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            });
       // }

    }
    public String [] contar(String [] lista){
        int cont=0;
        for(int i=0; i<lista.length;i++){
            if(lista[i]!=null){
                cont++;
            }
        }
        String []prueba= new String[cont];
        for(int i=0; i<prueba.length;i++){
            prueba[i]=lista[i];
        }
        return prueba;
    }
}
