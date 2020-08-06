package com.example.urpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
        Intent siguiente = new Intent(AnadirAlimento.this, InfoCardPet.class);
        startActivity (siguiente);
        finish();
    }
}
