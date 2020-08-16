package com.example.urpet.home.alimentos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.urpet.AnadirCompra;
import com.example.urpet.home.mascota.detallesMascota.DetalleMascotaActivity;
import com.example.urpet.home.medico.MenuCuidados;
import com.example.urpet.R;

public class AnadirAlimento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_alimento);
    }

    public void anadirCom(View view){
        Intent siguiente = new Intent(AnadirAlimento.this, AnadirCompra.class);
        startActivity (siguiente);
        finish();
    }

    public void btn_atr(View view){
        Intent siguiente = new Intent(AnadirAlimento.this, MenuCuidados.class);
        startActivity (siguiente);

        finish();
    }

    @Override
    public void onBackPressed() {
        Intent siguiente = new Intent(AnadirAlimento.this, DetalleMascotaActivity.class);
        startActivity (siguiente);
        finish();
    }
}
