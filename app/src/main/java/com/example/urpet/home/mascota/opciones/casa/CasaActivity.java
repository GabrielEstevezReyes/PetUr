package com.example.urpet.home.mascota.opciones.casa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.urpet.home.mascota.opciones.casa.recorridos.HistorialRecorridos;
import com.example.urpet.R;
import com.example.urpet.home.mascota.opciones.casa.vacunas.Vacunas;
import com.example.urpet.home.mascota.opciones.casa.alimentacion.ListadoComidasActivity;

public class CasaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cuidados);
        findViewById(R.id.cuidados_activity_vacunas_et).setOnClickListener(v-> irAVacunas());
        findViewById(R.id.cuidados_activity_aliments_tv).setOnClickListener(v-> iraAlimentos());
        findViewById(R.id.cuidados_activity_recorrido_et).setOnClickListener(v-> irAHistorial());
    }

    public void irAVacunas(){
        Intent siguiente = new Intent(CasaActivity.this, Vacunas.class);
        startActivity (siguiente);
    }


    public void iraAlimentos(){
        Intent siguiente = new Intent(CasaActivity.this, ListadoComidasActivity.class);
        startActivity (siguiente);
    }

    public void irAHistorial(){
        Intent siguiente = new Intent(CasaActivity.this, HistorialRecorridos.class);
        startActivity (siguiente);
    }
}
