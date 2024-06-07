package com.example.notwall;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aplication.NotWall.R;

public class ThirdFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private Button mediaCompras;
    private Button ganancias;
    private Button porcentajeGancia;


    public ThirdFragment() {

    }


    public static ThirdFragment newInstance(String param1, String param2) {
        ThirdFragment fragment = new ThirdFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);

        mediaCompras = view.findViewById(R.id.btnMediaCompras);
        ganancias = view.findViewById(R.id.btnGanancias);
        porcentajeGancia = view.findViewById(R.id.btnPorcentajeCompras);

        mediaCompras.setOnClickListener(v -> medCompr());
        ganancias.setOnClickListener(v -> gananci());
        porcentajeGancia.setOnClickListener(v -> porcGananci());

        return view;
    }

    private void medCompr() {
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), calc_MediaCompras.class);
            startActivity(intent);
        }
    }

    private void gananci() {
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), calc_Gananci.class); // Intent para iniciar la actividad
            startActivity(intent);
        }
    }

    private void porcGananci() {
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), calc_PorcentGananci.class); // Intent para iniciar la actividad
            startActivity(intent);
        }
    }
}