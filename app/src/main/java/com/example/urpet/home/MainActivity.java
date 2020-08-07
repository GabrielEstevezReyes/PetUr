package com.example.urpet.home;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.urpet.home.marketplace.MarketplaceActivity;
import com.example.urpet.home.medico.Clinicas;
import com.example.urpet.home.mascota.detallesMascota.DetalleMascotaActivity;
import com.example.urpet.home.mascota.ListaMascotas;
import com.example.urpet.home.mascota.MascotaSettingActivity;
import com.example.urpet.PerfilDatosUser;
import com.example.urpet.PersonalInfo;
import com.example.urpet.PlacesM;
import com.example.urpet.R;
import com.example.urpet.home.mascota.RegistroMascota;
import com.example.urpet.SOS;
import com.example.urpet.connections.Pet;
import com.example.urpet.home.grupos.ListadoGruposActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.text.ParseException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mSOSBtn, mBtnTiendaBTN;

    CircularImageView mFotoPerfilCIV;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    LinearLayout cardPets = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView name = findViewById(R.id.nombrePrincipal);
        name.setText(
                (PersonalInfo.currentUser != null ? (PersonalInfo.currentUser.getName().isEmpty() ? "" : PersonalInfo.currentUser.getName()) : ""));
        bindviews();
        configureViews();
        cardPets = findViewById(R.id.scrollPets);
        if(PersonalInfo.currentUser != null && !PersonalInfo.currentUser.getBase64Image().isEmpty() && !PersonalInfo.currentUser.getBase64Image().equals("no-image.png")) {
            getBitmapFromURL(PersonalInfo.currentUser.getBase64Image(), mFotoPerfilCIV);
        }
        else{
            mFotoPerfilCIV.setImageResource(R.drawable.ic_user);
        }
        Pet obtener = new Pet();

        ArrayList<Pet> allPets = null;
        try {
            allPets = obtener.read();
        } catch (ParseException e) {
            e.printStackTrace();
            //TODO: mandar mensaje de alerta y volvel a intentarlo
        }
        for (int i=0; i<allPets.size(); i++){
            cardPets.addView(buildPetLayout(allPets.get(i)));
        }
        View viewN = getLayoutInflater().inflate(R.layout.newpetcard, null);
        viewN.setPadding(10,0,10,0);
        viewN.setOnClickListener(v -> {
            Intent siguiente = new Intent(MainActivity.this, RegistroMascota.class);
            startActivity (siguiente);
            finish();
        });
        cardPets.addView(viewN);
    }

    View buildPetLayout(final Pet petName){
        View view = getLayoutInflater().inflate(R.layout.petcard, null);
        CircularImageView petI = view.findViewById(R.id.petCardImage);
        if(!petName.getBase64Image().isEmpty() && !petName.getBase64Image().equals("no-image.png")) {
            getBitmapFromURL(petName.getBase64Image(), petI);
        }
        else{
            petI.setImageResource(R.drawable.ic_mascotas_1);
        }
        TextView petN = view.findViewById(R.id.petCardName);
        petN.setText(petName.getName());
        view.setPadding(10,0,10,0);
        view.setOnClickListener(v -> {
            PersonalInfo.clickedPet = petName;
            Intent siguiente = new Intent(this, DetalleMascotaActivity.class);
            startActivity (siguiente);
            finish();
        });

        ImageView sett = view.findViewById(R.id.item_home_mascota_setting_iv);
        sett.setOnClickListener(v -> {
            Intent siguiente = new Intent(this, MascotaSettingActivity.class);
            startActivity (siguiente);
            finish();
        });
        return view;
    }

    private void bindviews(){
        mFotoPerfilCIV = findViewById(R.id.home_activity_foto_perfil_civ);
        mBtnTiendaBTN = findViewById(R.id.home_activity_tienda_btn);
        mSOSBtn = findViewById(R.id.home_activity_sos_btn);
    }

    private void configureViews(){
        mFotoPerfilCIV.setOnClickListener(this);
        mBtnTiendaBTN.setOnClickListener(this);
        mSOSBtn.setOnClickListener(this);
    }

    public void linker(View v){
        String url1 = "amzn://apps/android?";
        openURL(url1);
    }

    public void openURL(String url){
        try{
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (ActivityNotFoundException ex){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.amazon.com/gp/mas/dl/android?s=purina%20pro")));
        }
    }

    public void getBitmapFromURL(String src, final CircularImageView circ) {
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(src);

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] b) {
                Drawable image = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(b, 0, b.length));
                circ.setImageDrawable(image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    public void itAPerfil(){
        startActivity(new Intent(this, PerfilDatosUser.class));
    }

    public void lis_mas(View view){
        Intent siguiente = new Intent(this, ListaMascotas.class);
        startActivity (siguiente);
        finish();
    }

    public void lis_care(View view){
        Intent siguiente = new Intent(MainActivity.this, Clinicas.class);
        startActivity (siguiente);
        finish();
    }

    public void lis_grupos(View view){
        Intent siguiente = new Intent(MainActivity.this, ListadoGruposActivity.class);
        startActivity (siguiente);
        finish();
    }

    public void lis_cuidad(View view){
        Intent siguiente = new Intent(MainActivity.this, PlacesM.class);
        startActivity (siguiente);
        finish();
    }

    public void irASoS(){
        Intent siguiente = new Intent(MainActivity.this, SOS.class);
        startActivity (siguiente);
        finish();
    }

    private void irAMarket(){
        Intent siguiente = new Intent(this, MarketplaceActivity.class);
        startActivity (siguiente);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_activity_foto_perfil_civ:
                itAPerfil();
            break;
            case R.id.home_activity_tienda_btn:
                irAMarket();
            break;
            case R.id.home_activity_sos_btn:
                irASoS();
            break;
        }
    }
}
