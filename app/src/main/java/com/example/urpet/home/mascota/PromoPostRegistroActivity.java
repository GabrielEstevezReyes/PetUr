package com.example.urpet.home.mascota;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.example.urpet.PersonalInfo;
import com.example.urpet.R;
import com.example.urpet.connections.Marketing;

public class PromoPostRegistroActivity extends AppCompatActivity {

    public CheckBox all;
    public CheckBox vet;
    public CheckBox food;
    public CheckBox nearby;
    public CheckBox stores;
    public CheckBox events;
    public CheckBox tips;
    private Marketing mark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_publicidad);
        all = findViewById(R.id.allCheckbox);
        vet = findViewById(R.id.vetCheckbox);
        food = findViewById(R.id.foodCheckbox);
        nearby = findViewById(R.id.nearbyCheckbox);
        stores = findViewById(R.id.storesCheckbox);
        events = findViewById(R.id.eventsCheckbox);
        tips = findViewById(R.id.tipsCheckbox);
        findViewById(R.id.promo_activity_aceptar_btn).setOnClickListener(v-> aceptarPromo());
        mark = new Marketing(PersonalInfo.currentUser.getID());
        mark.get();
    }

    public void aceptarPromo(){
        if(all.isChecked()){
            mark.turnAll();
        }
        else{
            if(vet.isChecked()){
                mark.setVet(1);
            }
            if(food.isChecked()){
                mark.setFood(1);
            }
            if(nearby.isChecked()){
                mark.setNearbyP(1);
            }
            if(stores.isChecked()){
                mark.setStores(1);
            }
            if(events.isChecked()){
                mark.setEvents(1);
            }
            if(tips.isChecked()){
                mark.setTips(1);
            }
        }
        if(mark.getID()!=-1) {
            mark.update();
        }
        else
        {
            mark.create();
        }
        Intent siguiente = new Intent(PromoPostRegistroActivity.this, SuccesRegPet.class);
        startActivity (siguiente);
        finish();
    }

}
