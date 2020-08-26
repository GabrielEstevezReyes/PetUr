package com.example.urpet.home.mascota.opciones.casa.alimentacion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.urpet.AnadirCompra;
import com.example.urpet.home.mascota.detallesMascota.DetalleMascotaActivity;
import com.example.urpet.home.mascota.opciones.medico.MenuCuidados;
import com.example.urpet.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListadoComidasActivity extends AppCompatActivity {

    private FloatingActionButton mAddFoodFAB;
    private RecyclerView mListadoComidasRV;
    private TextView mUltimaComidaTv, mUltimaDescrTV, mPresentacionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_alimento);
        bindviews();
        configureViews();
    }

    private void bindviews(){
        mAddFoodFAB = findViewById(R.id.agregar_comida_activity_fab);
        mUltimaComidaTv = findViewById(R.id.agregar_comida_activity_ultima_compra_titulo_tv);
        mUltimaDescrTV = findViewById(R.id.agregar_comida_activity_ultima_compra_descri_tv);
        mPresentacionTv = findViewById(R.id.agregar_comida_activity_ultima_compra_presentacion_tv);
    }

    private void configureViews(){
        mAddFoodFAB.setOnClickListener(v-> addComida());
    }

    private void addComida(){
        DialogFragmentAgregarComida addFood = DialogFragmentAgregarComida.newInstance();
        addFood.show(getSupportFragmentManager(), "addComidas");
    }

    private void getUltimaComida(){

    }




}
