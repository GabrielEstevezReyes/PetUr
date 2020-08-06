package com.example.urpet;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.OperationApplicationException;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.IDNA;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.urpet.connections.Pet;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.text.ParseException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    CircularImageView user;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    LinearLayout cardPets = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView name = findViewById(R.id.nombrePrincipal);
        name.setText(PersonalInfo.currentUser.getName());
        user = findViewById(R.id.imageView4);
        cardPets = findViewById(R.id.scrollPets);
        if(!PersonalInfo.currentUser.getBase64Image().isEmpty() && PersonalInfo.currentUser.getBase64Image() != "no-image.png") {
            getBitmapFromURL(PersonalInfo.currentUser.getBase64Image(), user);
        }
        else{
            user.setImageResource(R.drawable.ic_user);
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
        viewN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent siguiente = new Intent(MainActivity.this, RegistroMascota.class);
                startActivity (siguiente);
                finish();
            }
        });
        cardPets.addView(viewN);
    }

    View buildPetLayout(final Pet petName){
        View view = getLayoutInflater().inflate(R.layout.petcard, null);
        CircularImageView petI = view.findViewById(R.id.petCardImage);
        if(!petName.getBase64Image().isEmpty() && petName.getBase64Image() != "no-image.png") {
            getBitmapFromURL(petName.getBase64Image(), petI);
        }
        else{
            petI.setImageResource(R.drawable.ic_mascotas_1);
        }
        TextView petN = view.findViewById(R.id.petCardName);
        petN.setText(petName.getName());
        view.setPadding(10,0,10,0);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalInfo.clickedPet = petName;
                Intent siguiente = new Intent(MainActivity.this, InfoCardPet.class);
                startActivity (siguiente);
                finish();
            }
        });
        ImageView sett = view.findViewById(R.id.petCardSettings);
        sett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent siguiente = new Intent(MainActivity.this, OpcCardMascota.class);
                startActivity (siguiente);
                finish();
            }
        });
        return view;
    }

    public void linker(View view){
        String url1 = "https://www.purina-latam.com/mx/proplan";
        openURL(url1);
    }

    public void openURL(String url){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
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

    public void btn_sig(View view){
        Intent siguiente = new Intent(MainActivity.this, PerfilDatosUser.class);
        startActivity (siguiente);
        finish();
    }

    public void lis_mas(View view){
        Intent siguiente = new Intent(MainActivity.this, ListaMascotas.class);
        startActivity (siguiente);
        finish();
    }

    public void lis_care(View view){
        Intent siguiente = new Intent(MainActivity.this, Clinicas.class);
        startActivity (siguiente);
        finish();
    }

    public void lis_grupos(View view){
        Intent siguiente = new Intent(MainActivity.this, Grupos.class);
        startActivity (siguiente);
        finish();
    }

    public void lis_cuidad(View view){
        Intent siguiente = new Intent(MainActivity.this, PlacesM.class);
        startActivity (siguiente);
        finish();
    }

    public void lis_SOS(View view){
        Intent siguiente = new Intent(MainActivity.this, SOS.class);
        startActivity (siguiente);
        finish();
    }
}
