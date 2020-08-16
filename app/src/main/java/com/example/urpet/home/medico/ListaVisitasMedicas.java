package com.example.urpet.home.medico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.urpet.R;
import com.example.urpet.TipoVisita;
import com.example.urpet.VisitaVeterinario;

public class ListaVisitasMedicas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_visitas_medicas);
    }

    public void lis_care(View view){
        Intent siguiente = new Intent(ListaVisitasMedicas.this, VisitaVeterinario.class);
        startActivity (siguiente);
        finish();
    }

    public void lis_care2(View view){
        Intent siguiente = new Intent(ListaVisitasMedicas.this, TipoVisita.class);
        startActivity (siguiente);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent siguiente = new Intent(ListaVisitasMedicas.this, Clinicas.class);
        startActivity (siguiente);
        finish();
    }
}