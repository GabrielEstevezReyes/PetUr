package com.example.urpet.home.mascota.detallesMascota;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.urpet.PersonalInfo;
import com.example.urpet.PlacesM;
import com.example.urpet.R;
import com.example.urpet.connections.Pet;
import com.example.urpet.home.MainActivity;
import com.example.urpet.home.grupos.ListadoGruposActivity;
import com.example.urpet.home.medico.Clinicas;
import com.example.urpet.home.medico.MenuCuidados;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;

public class DetalleMascotaActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mMedicoBTN, mSocialBtn;

    CircularImageView petImage = null;
    TextView petName = null;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_card_pet);
        bindViews();
        configureViews();
        petName = findViewById(R.id.nombreInfoCard);
        petImage = findViewById(R.id.imageView6);
        petName.setText(PersonalInfo.clickedPet.getName());
        if(!PersonalInfo.currentUser.getBase64Image().isEmpty() && !PersonalInfo.currentUser.getBase64Image().equals("no-image.png")) {
            getBitmapFromURL(PersonalInfo.currentUser.getBase64Image(), petImage);
        }
        else{
            petImage.setImageResource(R.drawable.ic_user);
        }
    }

    private void bindViews(){
        mMedicoBTN = findViewById(R.id.detalle_mascota_medico_btn);
        mSocialBtn = findViewById(R.id.detalle_mascota_social_btn);
    }

    private void configureViews(){
        mMedicoBTN.setOnClickListener(this);
        mSocialBtn.setOnClickListener(this);
    }

    public void getBitmapFromURL(String src, final CircularImageView circ) {
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(src);

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(b -> {
            Drawable image = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(b, 0, b.length));
            circ.setImageDrawable(image);
        }).addOnFailureListener(exception -> {
            // Handle any errors
        });
    }

    public void grupos() {
        Intent siguiente = new Intent(this, ListadoGruposActivity.class);
        startActivity (siguiente);
    }

    public void carnet(View view) {
        Intent siguiente = new Intent(DetalleMascotaActivity.this, CarnetMascotaActivity.class);
        startActivity (siguiente);
    }

    public void sitios(View view) {
        Intent siguiente = new Intent(DetalleMascotaActivity.this, PlacesM.class);
        startActivity (siguiente);
        finish();
    }

    public void casa(View view) {
        Intent siguiente = new Intent(DetalleMascotaActivity.this, MenuCuidados.class);
        startActivity (siguiente);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent siguiente = new Intent(DetalleMascotaActivity.this, MainActivity.class);
        startActivity (siguiente);
        finish();
    }

    public void citas(View view) {
        Intent siguiente = new Intent(DetalleMascotaActivity.this, Clinicas.class);
        startActivity (siguiente);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.detalle_mascota_medico_btn:
                abrirCarnet(null);
            break;
            case R.id.detalle_mascota_social_btn:
                grupos();
            break;
        }
    }

    public void abrirCarnet(Pet petToEdit){
        //PersonalInfo.clickedPet = petToEdit;
        Intent siguiente = new Intent(this, CarnetMascotaActivity.class);
        startActivity (siguiente);
    }
}