package com.example.notwall;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.aplication.NotWall.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

public class SecondFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private ViewPager2 viewPager;
    private int currentPosition = 0;



    private ImageButton boton_cerrarSesion;
    private ImageButton actual;
    private ImageButton note;
    private ImageButton calc;



    public SecondFragment() {
    }
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
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
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new ImageAdapter());

        // Crear un Handler para cambiar la imagen cada 5 segundos
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                viewPager.setCurrentItem((currentItem + 1) % new ImageAdapter().getItemCount());
                handler.postDelayed(this, 5000); // Agregar esta línea para repetir el Runnable cada 5 segundos
            }
        };

        // Iniciar el cambio de imagen cada 5 segundos
        handler.postDelayed(update, 5000);

        boton_cerrarSesion = view.findViewById(R.id.btnCerrarSesion);
        actual = view.findViewById(R.id.btnActualiz);
        note = view.findViewById(R.id.btnNote);
        calc = view.findViewById(R.id.btnCalc);


        actual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), actuali.class);
                startActivity(intent);
            }
        });

        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirtsFragment fragment1 = new FirtsFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment1);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThirdFragment fragment3 = new ThirdFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment3);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        boton_cerrarSesion.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            // Redirect the user to the login screen after logging out
            startActivity(new Intent(getActivity(), Login.class));
            getActivity().finish();
            Toast.makeText(getActivity(), "¡Hasta Pronto!", Toast.LENGTH_SHORT).show();
        });


        return view;
    }

    public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
        private int[] images = new int[] {
                R.drawable.uno, R.drawable.dos, R.drawable.tres, R.drawable.cuatro,
                R.drawable.cinco, R.drawable.seis, R.drawable.siete, R.drawable.ocho,
                R.drawable.nueve, R.drawable.diez, R.drawable.once, R.drawable.doce
        };

        public ImageAdapter() {
        }

        public ImageAdapter(List<Integer> images) {
            this.images = images.stream().mapToInt(i->i).toArray();
        }

        @NonNull
        @Override
        public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ImageView imageView = new ImageView(parent.getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            return new ImageViewHolder(imageView);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
            holder.imageView.setImageResource(images[position]);
        }

        @Override
        public int getItemCount() {
            return images.length;
        }

        class ImageViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public ImageViewHolder(@NonNull ImageView imageView) {
                super(imageView);
                this.imageView = imageView;
            }
        }
    }

}