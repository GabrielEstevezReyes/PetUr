package com.example.urpet.home.mascota.opciones.medico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.urpet.home.mascota.HistorialRecorridos;
import com.example.urpet.home.mascota.detallesMascota.DetalleMascotaActivity;
import com.example.urpet.R;
import com.example.urpet.home.mascota.opciones.casa.vacunas.Vacunas;
import com.example.urpet.home.MainActivity;
import com.example.urpet.home.mascota.opciones.casa.alimentacion.ListadoComidasActivity;

public class MenuCuidados extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cuidados);
        findViewById(R.id.cuidados_activity_vacunas_et).setOnClickListener(v-> irAVacunas());
        findViewById(R.id.cuidados_activity_aliments_tv).setOnClickListener(v-> iraAlimentos());
        findViewById(R.id.cuidados_activity_recorrido_et).setOnClickListener(v-> irAHistorial());
    }

    public void irAVacunas(){
        Intent siguiente = new Intent(MenuCuidados.this, Vacunas.class);
        startActivity (siguiente);
    }

    public void btn_atras(View view){
        Intent siguiente = new Intent(MenuCuidados.this, MainActivity.class);
        startActivity (siguiente);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent siguiente = new Intent(MenuCuidados.this, DetalleMascotaActivity.class);
        startActivity (siguiente);
        finish();
    }

    public void iraAlimentos(){
        Intent siguiente = new Intent(MenuCuidados.this, ListadoComidasActivity.class);
        startActivity (siguiente);
    }

    public void irAHistorial(){
        Intent siguiente = new Intent(MenuCuidados.this, HistorialRecorridos.class);
        startActivity (siguiente);
    }
}
