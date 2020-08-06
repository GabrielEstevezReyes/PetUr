package com.example.urpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SOS extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_o_s);
    }

    @Override
    public void onBackPressed() {
        Intent siguiente = new Intent(SOS.this, MainActivity.class);
        startActivity (siguiente);
        finish();
    }
}
