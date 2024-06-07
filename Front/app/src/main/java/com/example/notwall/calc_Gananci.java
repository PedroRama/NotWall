package com.example.notwall;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aplication.NotWall.R;

public class calc_Gananci extends AppCompatActivity {

    // Declaración de variables para los campos de entrada y los botones
    private EditText cantidadInvertida;
    private EditText precioCompra;
    private EditText precioVenta;
    private Button calcular;
    private TextView ganancia;
    private TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_gananci);

        // Inicialización de las variables con los elementos de la interfaz de usuario
        cantidadInvertida = findViewById(R.id.cantidadInvertida);
        precioCompra = findViewById(R.id.precioCompra);
        precioVenta = findViewById(R.id.precioVenta);
        calcular = findViewById(R.id.btnCalcular);
        ganancia = findViewById(R.id.Resultado);
        total = findViewById(R.id.Total);

        // Establecimiento del evento de clic en el botón calcular
        calcular.setOnClickListener(v -> calcularGanancia());
    }

    private void calcularGanancia() {
        // Conversión de los valores de entrada a números decimales
        double cantidadInvertida = Double.parseDouble(this.cantidadInvertida.getText().toString());
        double precioCompra = Double.parseDouble(this.precioCompra.getText().toString());
        double precioVenta = Double.parseDouble(this.precioVenta.getText().toString());

        // Cálculo de las ganancias
        double ganancias = cantidadInvertida * precioVenta / precioCompra - cantidadInvertida;

        // Formateo del número de ganancias a 4 decimales
        String formattedGanancia = String.format("%.4f", ganancias);

        // Si el número de ganancias es un entero, se eliminan los decimales
        if (ganancias == Math.floor(ganancias)) {
            formattedGanancia = String.format("%.0f", ganancias);
        }

        // Se muestra la ganancia en la interfaz de usuario
        ganancia.setText(formattedGanancia);
        System.out.println(formattedGanancia + "$");

        // Cálculo del valor total
        double totalValue = ganancias + cantidadInvertida;

        // Formateo del número total a 4 decimales
        String formattedTotal = String.format("%.4f", totalValue);

        // Si el número total es un entero, se eliminan los decimales
        if (totalValue == Math.floor(totalValue)) {
            formattedTotal = String.format("%.0f", totalValue);
        }

        // Se muestra el total en la interfaz de usuario
        total.setText(formattedTotal);
        System.out.println(formattedTotal + "$");
    }
}