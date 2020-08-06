package com.example.urpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;

public class InfoCardPet extends AppCompatActivity {

    CircularImageView petImage = null;
    TextView petName = null;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_card_pet);
        petName = findViewById(R.id.nombreInfoCard);
        petImage = findViewById(R.id.imageView6);
        petName.setText(PersonalInfo.clickedPet.getName());
        if(!PersonalInfo.currentUser.getBase64Image().isEmpty() && PersonalInfo.currentUser.getBase64Image() != "no-image.png") {
            getBitmapFromURL(PersonalInfo.currentUser.getBase64Image(), petImage);
        }
        else{
            petImage.setImageResource(R.drawable.ic_user);
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

    public void grupos(View view) {
        Intent siguiente = new Intent(InfoCardPet.this, Grupos.class);
        startActivity (siguiente);
        finish();
    }

    public void carnet(View view) {
        Intent siguiente = new Intent(InfoCardPet.this, Carnet.class);
        startActivity (siguiente);
        finish();
    }

    public void sitios(View view) {
        Intent siguiente = new Intent(InfoCardPet.this, PlacesM.class);
        startActivity (siguiente);
        finish();
    }

    public void casa(View view) {
        Intent siguiente = new Intent(InfoCardPet.this, MenuCuidados.class);
        startActivity (siguiente);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent siguiente = new Intent(InfoCardPet.this, MainActivity.class);
        startActivity (siguiente);
        finish();
    }

    public void citas(View view) {
        Intent siguiente = new Intent(InfoCardPet.this, Clinicas.class);
        startActivity (siguiente);
        finish();
    }
}