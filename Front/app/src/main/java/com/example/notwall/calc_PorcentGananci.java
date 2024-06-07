package com.example.notwall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aplication.NotWall.R;

public class calc_PorcentGananci extends AppCompatActivity {

    // Declaración de variables para los campos de entrada y los botones
    private EditText precioCompra;
    private EditText precioVenta;
    private Button calcular;
    private TextView porcentGanancia;
    private TextView porcent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_porcent_gananci);

        // Inicialización de las variables con los elementos de la interfaz de usuario
        precioCompra = findViewById(R.id.precioCompra);
        precioVenta = findViewById(R.id.precioVenta);
        calcular = findViewById(R.id.btnCalcular);
        porcentGanancia = findViewById(R.id.Resultado);
        porcent = findViewById(R.id.Porcent);

        // Establecimiento del evento de clic en el botón calcular
        calcular.setOnClickListener(v -> calcularPorcentGanancia());
    }

    private void calcularPorcentGanancia() {
        // Establecemos el valor que queremos que represente porcent
        porcent.setText("%");

        // Conversión de los valores de entrada a números decimales
        double precioCompra = Double.parseDouble(this.precioCompra.getText().toString());
        double precioVenta = Double.parseDouble(this.precioVenta.getText().toString());

        // Cálculo del porcentaje de ganancia
        double porcentganancia = (precioVenta - precioCompra) / precioCompra * 100;

        // Formateo del número de porcentaje de ganancia a 2 decimales
        String formattedGanancia = String.format("%.2f", porcentganancia);

        // Si el número de porcentaje de ganancia es un entero, se eliminan los decimales
        if (porcentganancia == Math.floor(porcentganancia)) {
            formattedGanancia = String.format("%.0f", porcentganancia);
        }

        // Se muestra el porcentaje de ganancia en la interfaz de usuario
        porcentGanancia.setText(formattedGanancia);
        System.out.println(formattedGanancia + porcent);
    }
}