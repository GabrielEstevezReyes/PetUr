package com.example.urpet.home.mascota.detallesMascota;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.urpet.home.mascota.agenda.AgendarDialogFragment;
import com.example.urpet.home.mascota.agenda.events.EventoAdapter;
import com.example.urpet.home.mascota.agenda.events.EventoDTO;
import com.example.urpet.R;
import com.example.urpet.home.mascota.opciones.perfil.EditarPerfilMascotaActivity;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class CarnetMascotaActivity extends AppCompatActivity implements View.OnClickListener, AgendarDialogFragment.onEventoAdd {

    private Button mAgregarEventoBTN;
    private CircularImageView mVeterinarioCiv, mEscanerCiv, mAjustesCiv;
    private EventoAdapter mAdapter;
    private RecyclerView mListadoEventosRV;

    private ArrayList<EventoDTO> mListadoEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carnet);
        bindviews();
        configureViews();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.carnet_activity_veterinario_civ:

            break;
            case R.id.carnet_activity_escaner_civ:

            break;
            case R.id.carnet_activity_ajustes_civ:
                abrirAjustes();
            break;
            case R.id.carnet_activity_agregar_evento_btn:
                agregarEvento();
            break;
        }
    }
    private void bindviews(){
        mVeterinarioCiv = findViewById(R.id.carnet_activity_veterinario_civ);
        mEscanerCiv = findViewById(R.id.carnet_activity_escaner_civ);
        mAjustesCiv = findViewById(R.id.carnet_activity_ajustes_civ);
        mListadoEventosRV = findViewById(R.id.carnet_activity_listado_eventos_rv);
        mAgregarEventoBTN = findViewById(R.id.carnet_activity_agregar_evento_btn);
    }

    private void configureViews(){
        mVeterinarioCiv.setOnClickListener(this);
        mEscanerCiv.setOnClickListener(this);
        mAjustesCiv.setOnClickListener(this);
        mAgregarEventoBTN.setOnClickListener(this);

        mListadoEventos = new ArrayList<>();
        mAdapter = new EventoAdapter(this);
        mAdapter.setData(mListadoEventos);

        LinearLayoutManager mManager= new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        mListadoEventosRV = findViewById(R.id.carnet_activity_listado_eventos_rv);
        mListadoEventosRV.setAdapter(mAdapter);
        mListadoEventosRV.setLayoutManager(mManager);
    }

    private void abrirAjustes(){
        Intent siguiente = new Intent(this, EditarPerfilMascotaActivity.class);
        startActivity (siguiente);
    }

    private void agregarEvento(){
        AgendarDialogFragment fr = AgendarDialogFragment.newInstance();
        fr.setmListener(this);
        fr.show(getSupportFragmentManager(), "Agenda");
    }

    @Override
    public void agregarEvento(String nombre, String fechaInicio, String fechaFin) {
        findViewById(R.id.item_evento_titulos).setVisibility(View.VISIBLE);
        mListadoEventos.add(new EventoDTO(fechaInicio, fechaFin, nombre));
        mAdapter.setData(mListadoEventos);
    }
}