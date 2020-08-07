package com.example.urpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.urpet.home.MainActivity;
import com.example.urpet.home.mascota.SuccesRegPet;

public class AnadirCompra extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_compra);
    }

    public void success(View view){
        Intent siguiente = new Intent(AnadirCompra.this, SuccesRegPet.class);
        startActivity (siguiente);
        finish();
    }

    public void btn_atr(View view){
        Intent siguiente = new Intent(AnadirCompra.this, MainActivity.class);
        startActivity (siguiente);
        finish();
    }

}
