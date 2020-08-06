package com.example.urpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.urpet.home.MainActivity;
import com.example.urpet.home.perfil.EditPerfilUserData;
import com.google.firebase.auth.FirebaseAuth;

public class PerfilDatosUser extends AppCompatActivity {

    public TextView nameI =  null;
    public TextView cellI =  null;
    public TextView mailI =  null;
    public TextView addrI =  null;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_datos_user);
        nameI = findViewById(R.id.fullNameInfo);
        cellI = findViewById(R.id.cellphoneInfo);
        mailI = findViewById(R.id.mailInfo);
        addrI = findViewById(R.id.addressInfo);
        nameI.setText(PersonalInfo.currentUser.getName());
        mailI.setText(PersonalInfo.currentUser.getMail());
        cellI.setText(PersonalInfo.currentUser.getPhoneNumber());
        addrI.setText(PersonalInfo.currentUser.getAddr());
        mAuth = FirebaseAuth.getInstance();
    }

    public void btn_sig(View view){
        Intent siguiente = new Intent(PerfilDatosUser.this, EditPerfilUserData.class);
        startActivity (siguiente);
        finish();
    }

    public void btn_sig2(View view){
        mAuth.signOut();
        Intent siguiente = new Intent(PerfilDatosUser.this, LoginActivity.class);
        startActivity (siguiente);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent siguiente = new Intent(PerfilDatosUser.this, MainActivity.class);
        startActivity (siguiente);
        finish();
    }

    public void volver(View view){
        onBackPressed();
    }
}
