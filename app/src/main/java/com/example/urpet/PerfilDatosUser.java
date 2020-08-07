package com.example.urpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.urpet.home.MainActivity;
import com.example.urpet.home.perfil.EditPerfilUserData;
import com.google.firebase.auth.FirebaseAuth;

public class PerfilDatosUser extends AppCompatActivity implements View.OnClickListener {

    public TextView nameI =  null;
    public TextView cellI =  null;
    public TextView mailI =  null;
    public TextView addrI =  null;
    private FirebaseAuth mAuth;
    private Button mEditarbtn;

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
        bindviews();
        configureViews();
    }

    private void bindviews(){
        mEditarbtn = findViewById(R.id.editar_perfil_editar_btn);
    }

    private void configureViews(){
        mEditarbtn.setOnClickListener(this);
    }

    public void irAEditarPerfil(){
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.editar_perfil_editar_btn:
                irAEditarPerfil();
            break;
        }
    }
}
