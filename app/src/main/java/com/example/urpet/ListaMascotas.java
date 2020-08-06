package com.example.urpet;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.urpet.connections.Pet;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.text.ParseException;
import java.util.ArrayList;

public class ListaMascotas extends AppCompatActivity {

    LinearLayout scroll = null;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mascotas);
        PersonalInfo.clickedPet = null;
        scroll = findViewById(R.id.ListaMascotasScroll);
        Pet obtener = new Pet();
        ArrayList<Pet> allPets = null;
        try {
            allPets = obtener.read();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i=0; i<allPets.size(); i++){
            scroll.addView(buildPetLayout(allPets.get(i)));
        }
    }

    public void getBitmapFromURL(String src, final CircularImageView circ) {
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(src);

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] b) {
                Drawable image = new BitmapDrawable(getResources(),BitmapFactory.decodeByteArray(b, 0, b.length));
                circ.setImageDrawable(image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    LinearLayout buildPetLayout(final Pet petName){
        LinearLayout parentPet = new LinearLayout(this);
        parentPet.setPadding(0,25,0,25);
        parentPet.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        parentPet.setOrientation(LinearLayout.HORIZONTAL);

        CircularImageView petI = new CircularImageView(this);
        if(!petName.getBase64Image().isEmpty()) {
            getBitmapFromURL(petName.getBase64Image(), petI);
        }
        else{
            petI.setImageResource(R.drawable.ic_mascotas_1);
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(125, 125);
        petI.setLayoutParams(params);
        parentPet.addView(petI);

        LinearLayout textsPet = new LinearLayout(this);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        textsPet.setLayoutParams(params2);
        textsPet.setOrientation(LinearLayout.VERTICAL);
        textsPet.setPadding(15,0,0,0);

        Typeface typeface = ResourcesCompat.getFont(this, R.font.sf_bold);
        Typeface typeface3 = ResourcesCompat.getFont(this, R.font.sf_regular);

        TextView nameOfPet = new TextView(this);
        nameOfPet.setText(petName.getName());
        nameOfPet.setTextSize(14);
        nameOfPet.setTextColor(Color.parseColor("#FF193247"));
        nameOfPet.setTypeface(typeface);

        TextView typeOfPet = new TextView(this);
        typeOfPet.setText(petName.getType());
        typeOfPet.setTextSize(12);
        typeOfPet.setTextColor(Color.parseColor("#80193247"));
        typeOfPet.setTypeface(typeface3);

        TextView ageOfPet = new TextView(this);
        ageOfPet.setText(petName.getAge() + " años");
        ageOfPet.setTextSize(12);
        ageOfPet.setTextColor(Color.parseColor("#80193247"));
        ageOfPet.setTypeface(typeface3);

        textsPet.addView(nameOfPet);
        textsPet.addView(typeOfPet);
        textsPet.addView(ageOfPet);

        TextView detailsOfPet = new TextView(this);
        detailsOfPet.setText("Ver detalles");
        detailsOfPet.setTextSize(10);
        detailsOfPet.setTextColor(Color.parseColor("#FF193247"));
        detailsOfPet.setTypeface(typeface);
        nameOfPet.setTypeface(typeface);
        textsPet.addView(detailsOfPet);
        textsPet.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                btn_edit(v, petName);
            }
        });
        textsPet.setId(petName.getID());
        parentPet.addView(textsPet);

        return parentPet;
    }

    public void btn_sig(View view){
        Intent siguiente = new Intent(ListaMascotas.this, RegistroMascota.class);
        startActivity (siguiente);
        finish();
    }

    public void btn_edit(View view, Pet petToEdit){
        PersonalInfo.clickedPet = petToEdit;
        Log.println(Log.INFO, "kek2", PersonalInfo.clickedPet.toString());
        Intent siguiente = new Intent(ListaMascotas.this, Carnet.class);
        startActivity (siguiente);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent siguiente = new Intent(ListaMascotas.this, MainActivity.class);
        startActivity (siguiente);
        finish();
    }
}
