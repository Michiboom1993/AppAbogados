package com.example.marki.appabogados;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegUsuario extends AppCompatActivity {

    private FirebaseDatabase database;
    private FirebaseAuth mAuth;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_usuario);


        database = FirebaseDatabase.getInstance();
        mAuth= FirebaseAuth.getInstance();

         final  EditText correo= findViewById(R.id.correo);
         final  EditText contraseña= findViewById(R.id.contraseña);
         final EditText nombre= findViewById(R.id.nombre);
         final EditText telefono= findViewById(R.id.telefono);
         final EditText edad= findViewById(R.id.edad);
         final Intent intent=new  Intent(this, MainActivity.class);
         final Button registrar= findViewById(R.id.btnRegistrar);




        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String correo1=correo.getText().toString();


                if (isValidEmail(correo1)){
                    final String contraseña1=contraseña.getText().toString();
                    mAuth.createUserWithEmailAndPassword(correo1, contraseña1)      //Contraseña entre 6 y 8 caracteres y correo tiene que ser valido
                            .addOnCompleteListener(RegUsuario.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(getApplicationContext(),"Registrando...", Toast.LENGTH_SHORT).show();
                                        Usuario usuario =new Usuario("usuario",correo.getText().toString(),nombre.getText().toString(),contraseña.getText().toString(),Integer.parseInt(telefono.getText().toString()),Integer.parseInt(edad.getText().toString()));

                                    // Se crea el objeto usuario y se añade a la base de datos, teniendo como padre su id y a su vez como padre Miembros

                                        FirebaseUser currentUser=mAuth.getCurrentUser();
                                        DatabaseReference reference=database.getReference("Miembros/"+currentUser.getUid());

                                       reference.setValue(usuario);


                                       startActivity(intent);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(getApplicationContext(),"error al registrarse", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                }
                else{
                    Toast.makeText(getApplicationContext(),"Validaciones funcionando", Toast.LENGTH_SHORT).show();
                }




            }
        });
    }
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
