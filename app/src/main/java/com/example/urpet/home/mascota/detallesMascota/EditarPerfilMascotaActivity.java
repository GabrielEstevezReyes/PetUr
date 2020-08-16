package com.example.urpet.home.mascota.detallesMascota;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.urpet.Utils.GeneralUtils;
import com.example.urpet.home.mascota.ListaMascotas;
import com.example.urpet.PersonalInfo;
import com.example.urpet.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONException;

import java.text.ParseException;
import java.util.Calendar;

public class EditarPerfilMascotaActivity extends AppCompatActivity {
    public EditText namePet =  null;
    public EditText typePet =  null;
    public EditText racePet =  null;
    public EditText colorPet =  null;
    public EditText ageInput =  null;
    public EditText descriptionPet =  null;
    CircularImageView petI = null;
    public boolean selectedImage = false;
    public String encodedImage = "";
    DatePickerDialog picker = null;

    FirebaseStorage storage = FirebaseStorage.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_perfil_pet);
        namePet = findViewById(R.id.petNameEdit);
        typePet = findViewById(R.id.petTypeEdit);
        racePet = findViewById(R.id.petRaceEdit);
        colorPet = findViewById(R.id.petColorEdit);
        descriptionPet = findViewById(R.id.petDescriptionEdit);
        ageInput = findViewById(R.id.petAgeEdit);
        ageInput.setInputType(InputType.TYPE_NULL);
        ageInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate(v);
            }
        });
        petI = findViewById(R.id.petImageEdit);
        if(!PersonalInfo.clickedPet.getBase64Image().isEmpty()) {
            GeneralUtils.getBitmapFromURL(PersonalInfo.clickedPet.getBase64Image(), petI, storage, this);
            PersonalInfo.registedPetImage = PersonalInfo.clickedPet.getBase64Image();
        }
        else{
            petI.setImageResource(R.drawable.ic_mascotas_1);
        }
        namePet.setText(PersonalInfo.clickedPet.getName());
        ageInput.setText(PersonalInfo.clickedPet.getBornDate());
        typePet.setText(PersonalInfo.clickedPet.getType());
        racePet.setText(PersonalInfo.clickedPet.getRace());
        colorPet.setText(PersonalInfo.clickedPet.getColor());
        descriptionPet.setText(PersonalInfo.clickedPet.getDescription());
    }

    @Override
    public void onBackPressed() {
        Intent siguiente = new Intent(EditarPerfilMascotaActivity.this, ListaMascotas.class);
        startActivity (siguiente);
        finish();
    }

    public void updateImage(View v){
        String[] mimes = {"image/png", "image/jpg", "image/jpeg"};
        ImagePicker.Companion.with(this).cropSquare().compress(1024).maxResultSize(480,480).galleryMimeTypes(mimes).start();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void save(View view) throws ParseException, JSONException {
        PersonalInfo.clickedPet.setName(namePet.getText().toString());
        PersonalInfo.clickedPet.setType(typePet.getText().toString());
        PersonalInfo.clickedPet.setRace(racePet.getText().toString());
        PersonalInfo.clickedPet.setColor(colorPet.getText().toString());
        PersonalInfo.clickedPet.setDescription(descriptionPet.getText().toString());
        PersonalInfo.clickedPet.setBornDate(ageInput.getText().toString());
        if(selectedImage && !encodedImage.isEmpty()){
            PersonalInfo.clickedPet.setBase64Image(encodedImage);
        }
        PersonalInfo.clickedPet.update(selectedImage);
        Intent siguiente = new Intent(EditarPerfilMascotaActivity.this, ListaMascotas.class);
        startActivity (siguiente);
        finish();
    }

    public void deletePet(View v){
        PersonalInfo.clickedPet.delete();
        Intent siguiente = new Intent(EditarPerfilMascotaActivity.this, ListaMascotas.class);
        startActivity (siguiente);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            Uri fileUri = data.getData();
            petI.setImageURI(fileUri);
            StorageReference Folder = FirebaseStorage.getInstance().getReference();
            final StorageReference file_name = Folder.child("PET" + PersonalInfo.clickedPet.getID());
            file_name.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    PersonalInfo.clickedPet.setBase64Image("PET" + PersonalInfo.clickedPet.getID());
                    Toast.makeText(EditarPerfilMascotaActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void selectDate(View v){
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        ageInput.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        picker.show();
    }
}
