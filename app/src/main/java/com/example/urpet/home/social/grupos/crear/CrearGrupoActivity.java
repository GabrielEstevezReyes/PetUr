package com.example.urpet.home.social.grupos.crear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.urpet.R;
import com.example.urpet.Utils.LoaderFragment;
import com.example.urpet.Utils.alert.AlertFragment;
import com.example.urpet.Utils.alert.AlertManager;
import com.example.urpet.connections.Group;
import com.example.urpet.home.social.grupos.listado.ListadoGruposActivity;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;

public class CrearGrupoActivity extends AppCompatActivity implements CrearGrupoView, AlertFragment.onAceptarClick{

    private LoaderFragment mLoader;
    private Button mCrearGrupoBtn;
    private EditText nameGroup =  null;
    private EditText descriptionGroup =  null;
    private CheckBox isClosedCheckbox =  null;
    private ImageButton imageB = null;
    private boolean selectedImage = false;
    private String encodedImage = "";
    private CrearGrupoPresenter mPresenter;

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
        mLoader = LoaderFragment.newInstance();
        mPresenter = new CrearGrupoPresenter(this, new CrearGrupoInteractor());
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

            mPresenter.onCrearGrupo(newGroup);
        }
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
                Toast.makeText(CrearGrupoActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public void onGrupoCreado() {
        AlertManager.muestraMensaje(getSupportFragmentManager(), "sucess de grupo", AlertFragment.EnumTipoMensaje.EXITO,
                getResources().getString(R.string.grupo_creado), getResources().getString(R.string.grupo_creado_desc), false,this);
    }

    @Override
    public void onGrupoError() {
        AlertManager.muestraMensaje(getSupportFragmentManager(), "Error de grupo", AlertFragment.EnumTipoMensaje.ERROR,
                getResources().getString(R.string.error_al_publicar), getResources().getString(R.string.error_grupo_desc), false,this);
    }

    @Override
    public void onShowLoader() {
        mLoader.show(getSupportFragmentManager(),"Loader");
    }

    @Override
    public void onHideLoader() {
        mLoader.dismiss();
    }

    @Override
    public void onAceptado(boolean exito) {
        if(exito){
            Intent siguiente = new Intent(this, ListadoGruposActivity.class);
            startActivity(siguiente);
            finish();
        }
    }
}
