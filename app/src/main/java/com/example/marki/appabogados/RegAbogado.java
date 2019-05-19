package com.example.marki.appabogados;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegAbogado extends AppCompatActivity {

    String categoria;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_abogado);

        database = FirebaseDatabase.getInstance();
        mAuth= FirebaseAuth.getInstance();

        final   EditText nombre=findViewById(R.id.nombre);
        final   EditText correo=findViewById(R.id.correo);
        final  EditText contraseña=findViewById(R.id.contraseña);
        final   EditText edad=findViewById(R.id.edad);
        final  EditText experiencia=findViewById(R.id.experiencia);
        final   EditText telefono=findViewById(R.id.telefono);
        final Intent intent= new Intent(this, MainActivity.class);
        final Button registrar= findViewById(R.id.btnRegistrar);

        Spinner spinner= findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this, R.array.categoria, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoria=parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String correo1=correo.getText().toString();

                if (isValidEmail(correo1)){
                    final String contraseña1=contraseña.getText().toString();
                    mAuth.createUserWithEmailAndPassword(correo1, contraseña1)
                            .addOnCompleteListener(RegAbogado.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                       // Toast.makeText(getApplicationContext(),"Master!!!!", Toast.LENGTH_SHORT).show();
                                        Abogado abogado =new Abogado("abogado",nombre.getText().toString(),correo.getText().toString(),categoria,contraseña.getText().toString(),Integer.parseInt(edad.getText().toString()),Integer.parseInt(telefono.getText().toString()),Integer.parseInt(experiencia.getText().toString()));
                                        FirebaseUser currentUser=mAuth.getCurrentUser();
                                        DatabaseReference reference=database.getReference("Miembros/"+currentUser.getUid());
                                        reference.setValue(abogado);
                                        Toast.makeText(getApplicationContext(),"Te has registrado correctamente",Toast.LENGTH_LONG ).show();
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
