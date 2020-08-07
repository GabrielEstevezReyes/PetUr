package com.example.urpet.home.grupos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.urpet.PersonalInfo;
import com.example.urpet.R;
import com.example.urpet.connections.Post;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;

public class RealizarPublicacionActivity extends AppCompatActivity {

    public EditText titlePost =  null;
    public EditText descriptionPost =  null;
    public ImageButton imageB = null;
    public boolean selectedImage = false;
    public String encodedImage = "";
    public CheckBox isForSale = null;
    public  EditText price = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        titlePost = findViewById(R.id.namePostCreate);
        descriptionPost = findViewById(R.id.descriptionPostCreate);
        imageB = findViewById(R.id.imgePostCreate);
        isForSale = findViewById(R.id.saledPstCreate);
        price = findViewById(R.id.pricePostCreate);
        price.setVisibility(View.INVISIBLE);
        price.setText("0");
    }

    public void post(View view){
        if(allFieldsClean()) {
            Post newGroup = new Post(PersonalInfo.selectedGroup.getID());
            int itIsClosed;
            if(isForSale.isChecked()){
                itIsClosed = 1;
                newGroup.setForSale(1);
            }
            else{
                itIsClosed = 0;
            }
            Log.d("crear", "checked: " + itIsClosed);
            newGroup.setName(titlePost.getText().toString());
            newGroup.setDescription(descriptionPost.getText().toString());
            newGroup.setPrice(Float.parseFloat(price.getText().toString()));
            if(selectedImage && !encodedImage.isEmpty()){
                newGroup.setImage(encodedImage);
            }
            if(newGroup.create()) {
                Toast toast = Toast.makeText(this, "Grupo Creado", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                Toast toast = Toast.makeText(this, "Error, intente de nuevo más tarde", Toast.LENGTH_LONG);
                toast.show();
            }
            Intent siguiente = new Intent(RealizarPublicacionActivity.this, DetalleGrupoActivity.class);
            startActivity(siguiente);
            finish();
        }
    }

    public void showPrice(View view){
        if(isForSale.isChecked()){
            price.setVisibility(View.VISIBLE);
        }
        else{
            price.setVisibility(View.INVISIBLE);
        }
        price.setVisibility(View.VISIBLE);
        price.setText("0");
    }

    private Boolean allFieldsClean(){
        return !titlePost.getText().toString().isEmpty() && !descriptionPost.getText().toString().isEmpty();
    }

    @Override
    public void onBackPressed() {
        Intent siguiente = new Intent(RealizarPublicacionActivity.this, DetalleGrupoActivity.class);
        startActivity (siguiente);
        finish();
    }

    public void updateImage(View v){
        String[] mimes = {"image/png", "image/jpg", "image/jpeg"};
        ImagePicker.Companion.with(this).crop(16,9).compress(1024).maxResultSize(720,480).galleryMimeTypes(mimes).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_OK) {
            Uri fileUri = data.getData();
            imageB.setImageURI(fileUri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);
                byte[] byteArray;
                if (bitmap != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byteArray = stream.toByteArray();
                    encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    System.out.println("Guardando: " + encodedImage);
                    Toast.makeText(RealizarPublicacionActivity.this, "Conversion Done", Toast.LENGTH_SHORT).show();
                    selectedImage = true;
                }
            } catch (Exception e) {
            }
        }
    }
}