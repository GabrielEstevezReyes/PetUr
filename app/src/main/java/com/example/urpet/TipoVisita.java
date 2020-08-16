package com.example.urpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.urpet.home.medico.ListaVisitasMedicas;

public class TipoVisita extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_visita);
    }

    @Override
    public void onBackPressed() {
        Intent siguiente = new Intent(TipoVisita.this, ListaVisitasMedicas.class);
        startActivity (siguiente);
        finish();
    }
}