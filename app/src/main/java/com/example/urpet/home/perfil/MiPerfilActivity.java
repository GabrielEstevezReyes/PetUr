package com.example.urpet.home.perfil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.urpet.PersonalInfo;
import com.example.urpet.R;
import com.example.urpet.Utils.SharedPreferencesUtil;
import com.example.urpet.home.MainActivity;
import com.example.urpet.login.LoginActivity;
import com.example.urpet.login.LoginActivityJava;
import com.google.firebase.auth.FirebaseAuth;

public class MiPerfilActivity extends AppCompatActivity implements View.OnClickListener {

    public TextView nameI =  null;
    public TextView cellI =  null;
    public TextView mailI =  null;
    public TextView addrI =  null;
    private FirebaseAuth mAuth;
    private Button mEditarbtn, mCerrarsesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_datos_user);
        bindviews();
        configureViews();
    }

    private void bindviews(){
        mEditarbtn = findViewById(R.id.editar_perfil_editar_btn);
        nameI = findViewById(R.id.fullNameInfo);
        cellI = findViewById(R.id.cellphoneInfo);
        mailI = findViewById(R.id.mailInfo);
        addrI = findViewById(R.id.addressInfo);
        mCerrarsesion = findViewById(R.id.perfil_activity_cerrar_btn);
    }

    private void configureViews(){
        mEditarbtn.setOnClickListener(this);
        nameI.setText(PersonalInfo.currentUser.getName());
        mailI.setText(PersonalInfo.currentUser.getMail());
        cellI.setText(PersonalInfo.currentUser.getPhoneNumber());
        addrI.setText(PersonalInfo.currentUser.getAddr());
        mCerrarsesion.setOnClickListener(v-> onCerrarSesion());
        mAuth = FirebaseAuth.getInstance();
    }

    public void irAEditarPerfil(){
        Intent siguiente = new Intent(MiPerfilActivity.this, EditarPerfilActivity.class);
        startActivity (siguiente);
        finish();
    }

    public void onCerrarSesion(){
        mAuth.signOut();
        SharedPreferencesUtil.getInstance().closeSession();
        Intent siguiente = new Intent(this, LoginActivity.class);
        startActivity (siguiente);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent siguiente = new Intent(MiPerfilActivity.this, MainActivity.class);
        startActivity (siguiente);
        finish();
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
