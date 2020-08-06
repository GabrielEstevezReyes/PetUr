package com.example.urpet.home.medico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.urpet.home.perfil.EditPerfilPet;
import com.example.urpet.home.mascota.ListaMascotas;
import com.example.urpet.PersonalInfo;
import com.example.urpet.R;

public class Carnet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carnet);
    }

    public void btn_edit2(View view){
        Log.println(Log.INFO, "kek2", PersonalInfo.clickedPet.toString());
        Intent siguiente = new Intent(Carnet.this, EditPerfilPet.class);
        startActivity (siguiente);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent siguiente = new Intent(Carnet.this, ListaMascotas.class);
        startActivity (siguiente);
        finish();
    }
}