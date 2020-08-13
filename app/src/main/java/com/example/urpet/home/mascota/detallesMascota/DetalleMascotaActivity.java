package com.example.urpet.home.mascota.detallesMascota;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.urpet.PersonalInfo;
import com.example.urpet.PlacesM;
import com.example.urpet.R;
import com.example.urpet.Utils.GeneralUtils;
import com.example.urpet.Utils.alert.SpinnerFechasDialogFragment;
import com.example.urpet.connections.Pet;
import com.example.urpet.home.social.grupos.listado.ListadoGruposActivity;
import com.example.urpet.home.medico.Clinicas;
import com.example.urpet.home.medico.MenuCuidados;
import com.google.firebase.storage.FirebaseStorage;
import com.mikhaellopez.circularimageview.CircularImageView;

public class DetalleMascotaActivity extends AppCompatActivity implements View.OnClickListener, SpinnerFechasDialogFragment.onDateSelected {

    private CardView mMedicoBtnCV, mSocialBtnCV, mUbicacionBtnCV, mCasaBtnCV, mPerfilCV;
    private Button mEditarPesoBtn, mEditarComidaBtn, mAgendarBtn;
    private CircularImageView mFotoMascotaCIV;
    private EditText mPesoEt, mComidaEt, mGramosEt, mHoraEt, mFechaEt, mCitaEt;
    private TextView mNombreMasacotaTv;


    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_mascota);
        bindViews();
        configureViews();
    }

    private void bindViews(){
        mNombreMasacotaTv = findViewById(R.id.activity_detalle_mascota_nombre_et);

        mMedicoBtnCV = findViewById(R.id.activity_detalle_mascota_medico_btn);
        mSocialBtnCV = findViewById(R.id.activity_detalle_mascota_social_btn);
        mUbicacionBtnCV = findViewById(R.id.activity_detalle_mascota_ubicacion_btn);
        mCasaBtnCV = findViewById(R.id.activity_detalle_mascota_casa_btn);
        mPerfilCV = findViewById(R.id.activity_detalle_mascota_editar_btn);

        mEditarPesoBtn = findViewById(R.id.activity_detalle_mascota_peso_btn);
        mEditarComidaBtn = findViewById(R.id.activity_detalle_mascota_comida_btn);
        mAgendarBtn = findViewById(R.id.activity_detalle_mascota_cita_btn);

        mFotoMascotaCIV = findViewById(R.id.activity_detalle_mascota_foto);

        mPesoEt = findViewById(R.id.activity_detalle_mascota_peso_et);
        mComidaEt = findViewById(R.id.activity_detalle_mascota_comida_et);
        mGramosEt = findViewById(R.id.activity_detalle_mascota_comida_gramos_et);
        mHoraEt = findViewById(R.id.activity_detalle_mascota_comida_hora_et);
        mFechaEt = findViewById(R.id.activity_detalle_mascota_comida_dia_et);
        mCitaEt = findViewById(R.id.activity_detalle_mascota_cita_et);
    }

    private void configureViews(){
        mMedicoBtnCV.setOnClickListener(this);
        mSocialBtnCV.setOnClickListener(this);
        mUbicacionBtnCV.setOnClickListener(this);
        mCasaBtnCV.setOnClickListener(this);
        mPerfilCV.setOnClickListener(this);

        mEditarPesoBtn.setOnClickListener(this);
        mEditarComidaBtn.setOnClickListener(this);
        mAgendarBtn.setOnClickListener(this);

        mHoraEt.setOnClickListener(this);
        mFechaEt.setOnClickListener(this);

        mNombreMasacotaTv.setText(PersonalInfo.clickedPet.getName());

        if(!PersonalInfo.clickedPet.getBase64Image().isEmpty() && !PersonalInfo.clickedPet.getBase64Image().equals("no-image.png")) {
            getBitmapFromURL(PersonalInfo.clickedPet.getBase64Image(), mFotoMascotaCIV);
        }
        else{
            mFotoMascotaCIV.setImageResource(R.drawable.ic_mascotas_1);
        }
    }

    public void getBitmapFromURL(String src, final CircularImageView circ) {
        GeneralUtils.getBitmapFromURL(src, circ, storage, this);
    }

    public void irAgrupos() {
        Intent siguiente = new Intent(this, ListadoGruposActivity.class);
        startActivity (siguiente);
    }

    public void irASitios() {
        Intent siguiente = new Intent(this, PlacesM.class);
        startActivity (siguiente);
        finish();
    }

    public void irACasa() {
        Intent siguiente = new Intent(this, MenuCuidados.class);
        startActivity (siguiente);
    }

    public void agendarCita() {
        Intent siguiente = new Intent(DetalleMascotaActivity.this, Clinicas.class);
        startActivity (siguiente);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_detalle_mascota_medico_btn:
                abrirCarnet(PersonalInfo.clickedPet);
            break;
            case R.id.activity_detalle_mascota_social_btn:
                irAgrupos();
            break;
            case R.id.activity_detalle_mascota_ubicacion_btn:
                irASitios();
            break;
            case R.id.activity_detalle_mascota_casa_btn:
                irACasa();
            break;
            case R.id.activity_detalle_mascota_peso_btn:
                editarpeso();
            break;
            case R.id.activity_detalle_mascota_comida_btn:
                editarComida();
            break;
            case R.id.activity_detalle_mascota_cita_btn:
                agendarCita();
            break;
            case R.id.activity_detalle_mascota_comida_hora_et:
            break;
            case R.id.activity_detalle_mascota_comida_dia_et:
                abrirCalendarioFecha();
            break;
            case R.id.activity_detalle_mascota_editar_btn:
                irAEditarPerfil();
            break;
            default:
        }
    }

    private void abrirCarnet(Pet petToEdit){
        PersonalInfo.clickedPet = petToEdit;
        Intent siguiente = new Intent(this, CarnetMascotaActivity.class);
        startActivity (siguiente);
    }

    private void editarpeso(){
        boolean esEditable = mPesoEt.isEnabled();
        mPesoEt.setEnabled(!esEditable);
        mEditarPesoBtn.setText(esEditable ? R.string.editar : R.string.guardar);
    }

    private void editarComida(){
        boolean esEditable = mComidaEt.isEnabled();
        mComidaEt.setEnabled(!esEditable);
        mGramosEt.setEnabled(!esEditable);
        mFechaEt.setEnabled(!esEditable);
        mHoraEt.setEnabled(!esEditable);
        mEditarComidaBtn.setText(esEditable ? R.string.editar : R.string.guardar);
    }

    private void abrirCalendarioHora(){

    }

    private void irAEditarPerfil(){

    }

    private void abrirCalendarioFecha(){
        GeneralUtils.abrirCalendarioFecha(getSupportFragmentManager(), this, "horas");
    }

    @Override
    public void onFechaSelected(SpinnerFechasDialogFragment view, String fecha) {
        mFechaEt.setText(fecha);
    }
}