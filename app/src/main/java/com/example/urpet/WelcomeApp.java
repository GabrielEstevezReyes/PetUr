package com.example.urpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WelcomeApp extends AppCompatActivity {

    public TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_app);
        name = findViewById(R.id.nombreBienvenido);
        name.setText("Genial, " + PersonalInfo.registeredName);
    }
    public void btn_sig(View view){
        PersonalInfo.registeredName = "";
        Intent siguiente = new Intent(WelcomeApp.this, MainActivity.class);
        startActivity (siguiente);
        finish();
    }

    public void btn_sig2(View view){
        Intent siguiente = new Intent(WelcomeApp.this, SMS.class);
        startActivity (siguiente);
        finish();
    }
}
