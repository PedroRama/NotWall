package com.example.notwall;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.aplication.NotWall.R;

public class calc_MediaCompras extends AppCompatActivity {

    // Declaración de variables para los campos de entrada y los botones
    private EditText activos;
    private EditText dineroInvertido;
    private Button calcular;
    private TextView mediaCompras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_media_compras);

        // Inicialización de las variables con los elementos de la interfaz de usuario
        activos = findViewById(R.id.cantidadActivos);
        dineroInvertido = findViewById(R.id.cantidadInvertida);
        calcular = findViewById(R.id.btnCalcular);
        mediaCompras = findViewById(R.id.Resultado);

        // Establecimiento del evento de clic en el botón calcular
        calcular.setOnClickListener(v -> calcularMediaCompras());
    }

    private void calcularMediaCompras() {
        // Conversión de los valores de entrada a números decimales
        double activos = Double.parseDouble(this.activos.getText().toString());
        double dineroInvertido = Double.parseDouble(this.dineroInvertido.getText().toString());

        // Cálculo de la media de compras
        double mediacompras = dineroInvertido / activos;

        // Formateo del número de la media de compras a 4 decimales
        String formattedMediaCompras = String.format("%.4f", mediacompras);

        // Si el número de la media de compras es un entero, se eliminan los decimales
        if (mediacompras == Math.floor(mediacompras)) {
            formattedMediaCompras = String.format("%.0f", mediacompras);
        }

        // Se muestra la media de compras en la interfaz de usuario
        mediaCompras.setText(formattedMediaCompras);
        System.out.println(formattedMediaCompras);
    }
}