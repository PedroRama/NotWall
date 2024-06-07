package com.example.notwall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.aplication.NotWall.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView botonNavegacionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar BottomNavigationView
        botonNavegacionView = findViewById(R.id.bottom_navigation);

        // Configurar el listener de selección de items en BottomNavigationView
        botonNavegacionView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;

                int itemId = item.getItemId();
                if (itemId == R.id.firstFragment) {
                    selectedFragment = new FirtsFragment();
                } else if (itemId == R.id.secondFragment) {
                    selectedFragment = new SecondFragment();
                } else if (itemId == R.id.thirdFragment) {
                    selectedFragment = new ThirdFragment();
                }
                // Reemplazar el fragmento actual con el fragmento seleccionado
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, selectedFragment).commit();

                return true;
            }
        });

        // Establecer el SecondFragment como el fragmento inicial
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new SecondFragment()).commit();
        botonNavegacionView.setSelectedItemId(R.id.secondFragment);
    }
}