package com.example.notwall;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.aplication.NotWall.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;

public class FirtsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private EditText texto;
    private ImageButton guardar;
    private ImageButton restaurar;
    private ImageButton eliminar;

    private FirebaseDatabase database;
    private DatabaseReference notasReferencia;
    private FirebaseAuth mAuth;

    public static FirtsFragment newInstance(String param1, String param2) {
        FirtsFragment fragment = new FirtsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_firts, container, false);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        notasReferencia = database.getReference("txt");

        texto = view.findViewById(R.id.escribeNota);
        guardar = view.findViewById(R.id.guardar);
        restaurar = view.findViewById(R.id.restaurar);
        eliminar = view.findViewById(R.id.eliminar);

        guardar.setOnClickListener(v -> guardar());
        restaurar.setOnClickListener(v -> restaurar());
        eliminar.setOnClickListener(v -> eliminar());

        // Agregar el TextWatcher aquí
        texto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No se necesita hacer nada aquí
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    // Si el usuario ha empezado a escribir, establece la opacidad al 100%
                    texto.setAlpha(1.0f);
                } else {
                    // Si el EditText está vacío, establece la opacidad al 25%
                    texto.setAlpha(0.25f);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No se necesita hacer nada aquí
            }
        });

        return view;
    }

    private void eliminar() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(getActivity(), "Usuario no autenticado", Toast.LENGTH_SHORT).show();
            return;
        }

        notasReferencia.child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Note noteData = snapshot.getValue(Note.class);
                        if (noteData != null) {
                            String noteId = noteData.getNoteId();
                            notasReferencia.child(currentUser.getUid()).child(noteId).removeValue()
                                    .addOnSuccessListener(aVoid -> {
                                        texto.setText("");
                                        Toast.makeText(getActivity(), "Nota eliminada", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> Toast.makeText(getActivity(), "Error al eliminar la Nota: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                            break; // Asumimos que solo queremos eliminar la primera nota
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "No se encontró ninguna Nota para eliminar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Error al eliminar la Nota: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void restaurar() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(getActivity(), "Usuario no autenticado", Toast.LENGTH_SHORT).show();
            return;
        }
        restoreNote(currentUser.getUid());
    }

    private void restoreNote(String userId) {
        notasReferencia.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Note noteData = snapshot.getValue(Note.class);
                        if (noteData != null) {
                            texto.setText(noteData.getNoteText());
                            Toast.makeText(getActivity(), "Nota Restaurada", Toast.LENGTH_SHORT).show();
                            break; // Asumimos que solo queremos la primera nota
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "No se encontró ninguna Nota", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Error al restaurar la Nota: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void guardar() {
        String noteText = texto.getText().toString();
        if (!noteText.isEmpty()) {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser == null) {
                Toast.makeText(getActivity(), "Usuario no autenticado", Toast.LENGTH_SHORT).show();
                return;
            }
            String userId = currentUser.getUid();
            String noteId = notasReferencia.push().getKey();
            Note note = new Note(noteId, userId, noteText);
            notasReferencia.child(userId).child(noteId).setValue(note)
                    .addOnSuccessListener(aVoid -> {
                        texto.setText("");
                        Toast.makeText(getActivity(), "Nota guardada", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> Toast.makeText(getActivity(), "Error al guardar la Nota", Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(getActivity(), "Por favor escriba una Nota", Toast.LENGTH_SHORT).show();
        }
    }
}

class Note {
    private String notaId;
    private String usuarioId;
    private String notaTxt;

    public Note() {
        // Default constructor required for calls to DataSnapshot.getValue(Note.class)
    }

    public Note(String noteId, String userId, String noteText) {
        this.notaId = noteId;
        this.usuarioId = userId;
        this.notaTxt = noteText;
    }

    public String getNoteId() {
        return notaId;
    }

    public void setNoteId(String noteId) {
        this.notaId = noteId;
    }

    public String getUserId() {
        return usuarioId;
    }

    public void setUserId(String userId) {
        this.usuarioId = userId;
    }

    public String getNoteText() {
        return notaTxt;
    }

    public void setNoteText(String noteText) {
        this.notaTxt = noteText;
    }
}
