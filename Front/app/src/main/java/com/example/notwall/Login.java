package com.example.notwall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.widget.ProgressBar;
import android.view.View;

import com.aplication.NotWall.R;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText editTextCorreo, editTextCotraseña;
    private Button botonLogin, botonRegiser;

    private ProgressBar progressBar;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        editTextCorreo = findViewById(R.id.Correo_electrónico);
        editTextCotraseña = findViewById(R.id.Contraseña);
        botonLogin = findViewById(R.id.btnIniciarSesion);
        botonRegiser = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);

        botonLogin.setOnClickListener(v -> login());
        botonRegiser.setOnClickListener(v -> register());
    }

    private void login() {

        progressBar.setVisibility(View.VISIBLE);

        String email = editTextCorreo.getText().toString().trim();
        String password = editTextCotraseña.getText().toString().trim();

        // Validaciones de los campos...

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.INVISIBLE);
                        // Iniciar Sesion exitoso, navegar a la pantalla principal
                        startActivity(new Intent(Login.this, MainActivity.class));
                        finish();
                    } else {

                        progressBar.setVisibility(View.INVISIBLE);
                        // Error al iniciar sesión
                        Toast.makeText(Login.this, "Error al iniciar sesión: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        // Imprimir el error completo en la consola de depuración
                        task.getException().printStackTrace();
                    }
                });
    }

    private void register() {
        startActivity(new Intent(Login.this, Register.class));
    }
}