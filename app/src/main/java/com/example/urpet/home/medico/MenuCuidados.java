package com.example.urpet.home.medico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.urpet.home.mascota.HistorialRecorridos;
import com.example.urpet.home.perfil.InfoCardPet;
import com.example.urpet.R;
import com.example.urpet.Vacunas;
import com.example.urpet.home.MainActivity;
import com.example.urpet.home.alimentos.AnadirAlimento;

public class MenuCuidados extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cuidados);
    }

    public void btn_sig(View view){
        Intent siguiente = new Intent(MenuCuidados.this, Vacunas.class);
        startActivity (siguiente);
        finish();
    }

    public void btn_atras(View view){
        Intent siguiente = new Intent(MenuCuidados.this, MainActivity.class);
        startActivity (siguiente);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent siguiente = new Intent(MenuCuidados.this, InfoCardPet.class);
        startActivity (siguiente);
        finish();
    }

    public void btn_alimentos(View view){
        Intent siguiente = new Intent(MenuCuidados.this, AnadirAlimento.class);
        startActivity (siguiente);
        finish();
    }

    public void btn_recorr(View view){
        Intent siguiente = new Intent(MenuCuidados.this, HistorialRecorridos.class);
        startActivity (siguiente);
        finish();
    }
}
