package com.example.urpet.home.mascota.opciones.casa.alimentacion;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urpet.R;
import com.example.urpet.Utils.LoaderFragment;
import com.example.urpet.database.entity.UltimaComidaEntity;
import com.example.urpet.database.tasks.GetAllComidaTask;
import com.example.urpet.database.tasks.GetUltimaComidaTask;
import com.example.urpet.database.tasks.InsertComidaAsync;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ListadoComidasActivity extends AppCompatActivity implements DialogFragmentAgregarComida.onAddComida, UtimaComidaAdapter.onReAddcomida {

    private FloatingActionButton mAddFoodFAB;
    private RecyclerView mListadoComidasRV;
    private TextView mUltimaComidaTv, mUltimaDescrTV, mPresentacionTv;
    private UtimaComidaAdapter mAdapter;
    private LoaderFragment mLoader;
    private CardView mSinCompraCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_alimento);
        bindviews();
        configureViews();
    }

    private void bindviews() {
        mAddFoodFAB = findViewById(R.id.agregar_comida_activity_fab);
        mUltimaComidaTv = findViewById(R.id.agregar_comida_activity_ultima_compra_titulo_tv);
        mUltimaDescrTV = findViewById(R.id.agregar_comida_activity_ultima_compra_descri_tv);
        mPresentacionTv = findViewById(R.id.agregar_comida_activity_ultima_compra_presentacion_tv);
        mListadoComidasRV = findViewById(R.id.agregar_comida_activity_historial_rv);
        mLoader = LoaderFragment.newInstance();
        mSinCompraCV = findViewById(R.id.sin_compra_cv);
    }

    private void configureViews() {
        mAddFoodFAB.setOnClickListener(v -> addComida());
        mAdapter = new UtimaComidaAdapter(this);
        mAdapter.setListener(this);
        getUltimaComida();
        getAllComida();
        mListadoComidasRV.setAdapter(mAdapter);
        mListadoComidasRV.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, true));
    }

    private void getUltimaComida(){
        new GetUltimaComidaTask(data -> {
            if(data != null){
                mUltimaComidaTv.setText(getResources().getString(R.string.ultima_compra, data.getNombre(), data.getFecha()));
                mUltimaDescrTV.setText(data.getDescripcion());
                mPresentacionTv.setText(getResources().getString(R.string.ultima_compra_presentacion, data.getPresentacion()));
                mSinCompraCV.setVisibility(View.GONE);

            } else{
                mUltimaComidaTv.setText("");
                mUltimaDescrTV.setText("");
                mPresentacionTv.setText("");
                mSinCompraCV.setVisibility(View.VISIBLE);
            }
        }).execute();
    }

    private void addComida() {
        DialogFragmentAgregarComida addFood = DialogFragmentAgregarComida.newInstance();
        addFood.setmListener(this);
        addFood.show(getSupportFragmentManager(), "addComidas");
    }

    private void getAllComida() {
        showLoader();
        new GetAllComidaTask(this::setData).execute();
    }

    private void setData(List<UltimaComidaEntity> list) {
        hideLoader();
        if (list != null) {
            ArrayList<UltimaComidaEntity> data = new ArrayList<>(list);
            mAdapter.setListadoComida(data);
        }
    }

    private void showLoader(){
        mLoader.show(getSupportFragmentManager(), "Loader");
    }

    private void hideLoader(){
        new CountDownTimer(1600, 800) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                mLoader.dismissAllowingStateLoss();
            }
        }.start();
    }

    @Override
    public void onAddcomida(String nombre, String desc, String presentacion) {
        new InsertComidaAsync(this::getAllComida).execute(new UltimaComidaEntity(null, nombre, desc, getFechaHoy(), presentacion));;
        getUltimaComida();
    }

    @Override
    public void reAddComida(UltimaComidaEntity comida) {
        new InsertComidaAsync(this::getAllComida).execute(new UltimaComidaEntity(null, comida.getNombre(), comida.getDescripcion(), getFechaHoy(), comida.getPresentacion()));
        getUltimaComida();
    }

    private String getFechaHoy(){
        SimpleDateFormat date = new SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault());
        return date.format(Calendar.getInstance().getTime()).toString();
    }
}
