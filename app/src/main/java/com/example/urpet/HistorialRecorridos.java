package com.example.urpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HistorialRecorridos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_recorridos);
    }

    public void btn_sig(View view){
        Intent siguiente = new Intent(HistorialRecorridos.this, MenuCuidados.class);
        startActivity (siguiente);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent siguiente = new Intent(HistorialRecorridos.this, InfoCardPet.class);
        startActivity (siguiente);
        finish();
    }
}
