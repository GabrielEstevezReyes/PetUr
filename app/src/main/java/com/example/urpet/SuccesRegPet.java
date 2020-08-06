package com.example.urpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.urpet.home.MainActivity;
import com.mikhaellopez.circularimageview.CircularImageView;

public class SuccesRegPet extends AppCompatActivity {

    public TextView newPetName;
    public CircularImageView petI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succes_reg_pet);
        newPetName = findViewById(R.id.NewPetName);
        petI = findViewById(R.id.petSuccesImageEdit);
        if(!PersonalInfo.registedPetImage.isEmpty()) {
            //petI.setImageDrawable(PersonalInfo.decodeDrawable(this, PersonalInfo.registedPetImage));
        }
        else{
            petI.setImageResource(R.drawable.ic_pet_success_1);
        }
        newPetName.setText(PersonalInfo.registedPetName);
    }

    public void usccPurc(View view){
        PersonalInfo.registedPetName = "";
        PersonalInfo.registedPetImage = "";
        Intent siguiente = new Intent(SuccesRegPet.this, MainActivity.class);
        startActivity (siguiente);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent siguiente = new Intent(SuccesRegPet.this, MainActivity.class);
        startActivity (siguiente);
        finish();
    }
}
