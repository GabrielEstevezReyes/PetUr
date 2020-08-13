package com.example.urpet.home.social;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    private Button mCrearGrupoBtn;
    private EditText nameGroup =  null;
    private EditText descriptionGroup =  null;
    private CheckBox isClosedCheckbox =  null;
    private ImageButton imageB = null;
    private boolean selectedImage = false;
    private String encodedImage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_grupo);
        bindviews();
        configureviews();
    }

    private void bindviews(){
        mCrearGrupoBtn = findViewById(R.id.crear_grupo_activity_crear_btn);
        nameGroup = findViewById(R.id.nameGroupCreate);
        descriptionGroup = findViewById(R.id.descriptionGroupCreate);
        isClosedCheckbox = findViewById(R.id.closedGroupCreate);
        imageB = findViewById(R.id.imgeGroupCreate);
    }

    private void configureviews(){
        mCrearGrupoBtn.setOnClickListener(v-> {
            try {
                onCrearGrupo();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void onCrearGrupo() throws JSONException {
        if(allFieldsClean()) {
            int itIsClosed;
            if(isClosedCheckbox.isChecked()){
                itIsClosed = 1;
            }
            else{
                itIsClosed = 0;
            }
            Group newGroup = new Group(nameGroup.getText().toString(), descriptionGroup.getText().toString(), itIsClosed);
            if(!encodedImage.isEmpty()){
                newGroup.setImage(encodedImage);
            }
            newGroup.create();
            Intent siguiente = new Intent(CrearGrupo.this, ListadoGruposActivity.class);
            startActivity(siguiente);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent siguiente = new Intent(CrearGrupo.this, ListadoGruposActivity.class);
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
        if (resultCode == RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            final Uri fileUri = data.getData();
            imageB.setImageURI(fileUri);
            StorageReference Folder = FirebaseStorage.getInstance().getReference();
            assert fileUri != null;
            final StorageReference file_name = Folder.child("GRP" + fileUri.getLastPathSegment());
            file_name.putFile(fileUri).addOnSuccessListener(taskSnapshot -> {
                encodedImage = "GRP" + fileUri.getLastPathSegment();
                Toast.makeText(CrearGrupo.this, "File Uploaded", Toast.LENGTH_SHORT).show();
            });
        }
    }
}
