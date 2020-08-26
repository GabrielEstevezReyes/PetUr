package com.example.urpet.home.mascota.opciones.casa.vacunas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.urpet.R;
import com.example.urpet.home.mascota.opciones.medico.MenuCuidados;
import com.example.urpet.home.mascota.detallesMascota.DetalleMascotaActivity;

public class Vacunas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacunas);

    }
    public void btn_sig(View view){
        Intent siguiente = new Intent(Vacunas.this, MenuCuidados.class);
        startActivity (siguiente);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent siguiente = new Intent(Vacunas.this, DetalleMascotaActivity.class);
        startActivity (siguiente);
        finish();
    }
}
