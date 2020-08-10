package com.example.urpet.home.perfil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

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

import java.util.Calendar;

public class EditPerfilUserData extends AppCompatActivity {

    public EditText nameI =  null;
    public EditText cellI =  null;
    public EditText mailI =  null;
    public EditText addrI =  null;
    CircularImageView petI = null;
    public String encodedImage = "";
    public boolean selectedImage = false;
    public EditText ageInput =  null;
    DatePickerDialog picker = null;

    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_perfil_user_data);
        nameI = findViewById(R.id.fullnameInfoEdit);
        cellI = findViewById(R.id.cellphoneInfoEdit);
        mailI = findViewById(R.id.MailInfoEdit);
        addrI = findViewById(R.id.addressInfoEdit);
        petI = findViewById(R.id.userImageEdit);
        mailI.setInputType(InputType.TYPE_NULL);
        mailI.setText(PersonalInfo.currentUser.getMail());
        nameI.setText(PersonalInfo.currentUser.getName());
        cellI.setText(PersonalInfo.currentUser.getPhoneNumber());
        addrI.setText(PersonalInfo.currentUser.getAddr());
        ageInput = findViewById(R.id.userAgeEdit);
        ageInput.setText(PersonalInfo.currentUser.getBirthDate());
        ageInput.setInputType(InputType.TYPE_NULL);
        ageInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate(v);
            }
        });
        if(!PersonalInfo.currentUser.getBase64Image().isEmpty() && PersonalInfo.currentUser.getBase64Image() != "no-image.png") {
            getBitmapFromURL(PersonalInfo.currentUser.getBase64Image(), petI);
        }
        else{
            petI.setImageResource(R.drawable.ic_user);
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

    public void updateImage(View v){
        String[] mimes = {"image/png", "image/jpg", "image/jpeg"};
        ImagePicker.Companion.with(this).cropSquare().compress(1024).maxResultSize(480,480).galleryMimeTypes(mimes).start();
    }

    @Override
    public void onBackPressed() {
        Intent siguiente = new Intent(EditPerfilUserData.this, MiPerfilActivity.class);
        startActivity (siguiente);
        finish();
    }

    public void btn_sig(View view) throws JSONException {
        if(!nameI.getText().toString().isEmpty()){
            PersonalInfo.currentUser.setName(nameI.getText().toString());
        }
        if(!cellI.getText().toString().isEmpty()){
            PersonalInfo.currentUser.setPhoneNumber(cellI.getText().toString());
        }
        if(!addrI.getText().toString().isEmpty()){
            PersonalInfo.currentUser.setAddr(addrI.getText().toString());
        }
        PersonalInfo.currentUser.setBirthDate(ageInput.getText().toString());
        PersonalInfo.currentUser.update(selectedImage);
        onBackPressed();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            Uri fileUri = data.getData();
            petI.setImageURI(fileUri);

            StorageReference Folder = FirebaseStorage.getInstance().getReference();
            final StorageReference file_name = Folder.child("USR" + PersonalInfo.currentUser.getID());
            file_name.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    PersonalInfo.currentUser.setBase64Image("USR" + PersonalInfo.currentUser.getID());
                    Toast.makeText(EditPerfilUserData.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
