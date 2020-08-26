package com.example.urpet.home.mascota.opciones.medico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.urpet.R;
import com.example.urpet.home.MainActivity;

public class Clinicas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinicas);
    }

    public void lis_care(View view){
        Intent siguiente = new Intent(Clinicas.this, ListaVisitasMedicas.class);
        startActivity (siguiente);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent siguiente = new Intent(Clinicas.this, MainActivity.class);
        startActivity (siguiente);
        finish();
    }
}