package com.example.urpet.home.marketplace;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.urpet.R;

public class MarketplaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mkp_main);
        findViewById(R.id.marketplace_activity_perros_civ).setOnClickListener(v->startActivity(new Intent(this, MkpCatMascota.class)));
    }
}