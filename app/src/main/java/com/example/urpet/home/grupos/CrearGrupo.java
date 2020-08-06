package com.example.urpet.home.grupos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.urpet.R;
import com.example.urpet.connections.Group;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;

public class CrearGrupo extends AppCompatActivity {

    public EditText nameGroup =  null;
    public EditText descriptionGroup =  null;
    public CheckBox isClosedCheckbox =  null;
    public ImageButton imageB = null;
    public boolean selectedImage = false;
    public String encodedImage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_grupo);
        nameGroup = findViewById(R.id.nameGroupCreate);
        descriptionGroup = findViewById(R.id.descriptionGroupCreate);
        isClosedCheckbox = findViewById(R.id.closedGroupCreate);
        imageB = findViewById(R.id.imgeGroupCreate);
    }

    public void btn_sig(View view) throws JSONException {
        if(allFieldsClean()) {
            int itIsClosed;
            if(isClosedCheckbox.isChecked()){
                itIsClosed = 1;
            }
            else{
                itIsClosed = 0;
            }
            Log.d("crear", "checked: " + itIsClosed);
            Group newGroup = new Group(nameGroup.getText().toString(), descriptionGroup.getText().toString(), itIsClosed);
            if(!encodedImage.isEmpty()){
                newGroup.setImage(encodedImage);
            }
            newGroup.create();
            Intent siguiente = new Intent(CrearGrupo.this, Grupos.class);
            startActivity(siguiente);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent siguiente = new Intent(CrearGrupo.this, Grupos.class);
        startActivity (siguiente);
        finish();
    }

    private Boolean allFieldsClean(){
        return !nameGroup.getText().toString().isEmpty() && !descriptionGroup.getText().toString().isEmpty();
    }

    public void updateImage(View v){
        String[] mimes = {"image/png", "image/jpg", "image/jpeg"};
        ImagePicker.Companion.with(this).crop(16,9).compress(1024).maxResultSize(720,480).galleryMimeTypes(mimes).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            final Uri fileUri = data.getData();
            imageB.setImageURI(fileUri);
            StorageReference Folder = FirebaseStorage.getInstance().getReference();
            final StorageReference file_name = Folder.child("GRP" + fileUri.getLastPathSegment());
            file_name.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    encodedImage = "GRP" + fileUri.getLastPathSegment();
                    Toast.makeText(CrearGrupo.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
