package com.example.urpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class OpcCardMascota extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opc_card_mascota);
    }

    @Override
    public void onBackPressed() {
        Intent siguiente = new Intent(OpcCardMascota.this, MainActivity.class);
        startActivity (siguiente);
        finish();
    }
}