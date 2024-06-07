package com.example.notwall;

import androidx.appcompat.app.AppCompatActivity;

import com.aplication.NotWall.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    private EditText editTxtNombre, editTxtCorreo, editTxtContraseña;
    Button botonRegister, botonVolver;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        editTxtNombre = findViewById(R.id.NameUsu);
        editTxtCorreo = findViewById(R.id.corr_elec);
        editTxtContraseña = findViewById(R.id.password);
        botonRegister = findViewById(R.id.btnRegister);
        botonVolver = findViewById(R.id.btnBack);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);

        botonRegister.setOnClickListener(v -> registrarUsuario());
        botonVolver.setOnClickListener(v -> backToLogin());
    }

    private void registrarUsuario() {

        progressBar.setVisibility(View.VISIBLE);

        String nombre = editTxtNombre.getText().toString().trim();
        String email = editTxtCorreo.getText().toString().trim();
        String password = editTxtContraseña.getText().toString().trim();

        // Validaciones
        if (TextUtils.isEmpty(nombre)) {
            editTxtNombre.setError("Ingresa tu nombre");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            editTxtCorreo.setError("Ingresa tu correo electrónico");
            return;
        }

        if (TextUtils.isEmpty(password) || password.length() < 6) {
            editTxtContraseña.setError("Ingresa una contraseña válida (mínimo 6 caracteres)");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "El usuario se registró exitosamente", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Register.this, MainActivity.class));
                        finish();
                    } else {
                        Exception e = task.getException();
                        if (e instanceof FirebaseAuthUserCollisionException) {
                            progressBar.setVisibility(View.INVISIBLE);
                            // El correo electrónico ya está en uso
                            Toast.makeText(getApplicationContext(), "El correo electrónico ya está en uso", Toast.LENGTH_SHORT).show();
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            // Otro error ocurrió
                            Toast.makeText(getApplicationContext(), "Error de registro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
        class Usuario {
            public String nombre, correo;
            public Usuario(String nombre, String correo) {
                this.nombre = nombre;
                this.correo = correo;
            }
        }
    private void backToLogin() {
        startActivity(new Intent(Register.this, Login.class));
        finish();
    }
}