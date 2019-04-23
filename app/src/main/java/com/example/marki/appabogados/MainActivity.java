package com.example.marki.appabogados;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;




public class MainActivity extends AppCompatActivity {



    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();

        final EditText correo= findViewById(R.id.textcorreo);
        final EditText contraseña=findViewById(R.id.contrasena);


        Button btnini = findViewById(R.id.iniciar);
        Button btnregu= findViewById(R.id.regusuario);
        Button btnrega= findViewById(R.id.regabogado);

        final Intent intent1=new Intent(this,RegUsuario.class);
        final Intent intent2= new Intent(this, RegAbogado.class);
        final Intent intent3= new Intent();
        mAuth=FirebaseAuth.getInstance();




        btnini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= correo.getText().toString();
                if(isValidEmail(email)){
                    String password= contraseña.getText().toString();
                    mAuth.signInWithEmailAndPassword(email, password)                   //metodo que nos ofrece firebase database para la autentificacion, el correo y  la contraseña han de ser validos
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information


                                        FirebaseUser currentUser=mAuth.getCurrentUser();
                                        DatabaseReference reference=database.getReference("Miembros/"+currentUser.getUid());
                                        Toast.makeText(MainActivity.this, "Iniciando sesion...", Toast.LENGTH_SHORT).show();

                                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {          //En caso de iniciar sesion correctamente, debemos saber si se ha registrado un usuario o un abogado, y realizamos la comparación
                                                                                                                        //El siguiente codigo es valido gracias a la structura json

                                                Usuario usuario=new Usuario();

                                               usuario=dataSnapshot.getValue(Usuario.class);


                                                if(usuario.getRol().equalsIgnoreCase("abogado")){
                                                    startActivity(new Intent(MainActivity.this, IniciarSesionAbogado.class));
                                                }
                                                else if(usuario.getRol().equalsIgnoreCase("usuario")){
                                                    startActivity(new Intent(MainActivity.this, IniciarSesionUsuario.class));
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });


                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(getApplicationContext(), "no se ha logueado", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                }
                else{
                    Toast.makeText(getApplicationContext(),"Introduzca bien los campos",Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnregu.setOnClickListener(new View.OnClickListener() {     //boton que manda a la actividad de registro de usuario
            @Override
            public void onClick(View view) {
                startActivity(intent1);
            }
        });


        btnrega.setOnClickListener(new View.OnClickListener() {     //boton que manda a la actividad de registro de abogado
            @Override
            public void onClick(View v) {

                startActivity(intent2);

            }
        });

    }
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }






}

