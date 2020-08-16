package com.example.urpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.urpet.home.medico.ListaVisitasMedicas;

public class VisitaVeterinario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visita_veterinario);
    }

    public void lis_care2(View view){
        Intent siguiente = new Intent(VisitaVeterinario.this, ListaVisitasMedicas.class);
        startActivity (siguiente);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent siguiente = new Intent(VisitaVeterinario.this, ListaVisitasMedicas.class);
        startActivity (siguiente);
        finish();
    }
}