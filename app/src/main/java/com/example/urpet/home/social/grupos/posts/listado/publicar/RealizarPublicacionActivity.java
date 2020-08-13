package com.example.urpet.home.social.grupos.posts.listado.publicar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.urpet.PersonalInfo;
import com.example.urpet.R;
import com.example.urpet.Utils.LoaderFragment;
import com.example.urpet.Utils.alert.AlertFragment;
import com.example.urpet.Utils.alert.AlertManager;
import com.example.urpet.home.social.grupos.listado.ListadoGruposActivity;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class RealizarPublicacionActivity extends AppCompatActivity implements PostView, AlertFragment.onAceptarClick {

    public EditText titlePost =  null;
    public EditText descriptionPost =  null;
    public ImageButton imageB = null;
    private Button mPostearBtn;
    public boolean selectedImage = false;
    public String encodedImage = "";
    public CheckBox isForSale = null;
    public  EditText price = null;
    private LoaderFragment mLoader;
    private PostPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        bindviews();
        configureViews();
    }

    private void bindviews(){
        titlePost = findViewById(R.id.namePostCreate);
        descriptionPost = findViewById(R.id.descriptionPostCreate);
        imageB = findViewById(R.id.imgePostCreate);
        isForSale = findViewById(R.id.saledPstCreate);
        mPostearBtn = findViewById(R.id.crear_post_activity_aceptar_btn);
        price = findViewById(R.id.pricePostCreate);
        mPresenter = new PostPresenter(this, new PostInteractor());
        mLoader = LoaderFragment.newInstance();
    }

    private void configureViews(){
        mPostearBtn.setOnClickListener(v-> onHacerPost());
        price.setVisibility(View.INVISIBLE);
        price.setText("0");
    }

    public void onHacerPost(){
        if(allFieldsClean()) {
            mPresenter.onHacerPost(PersonalInfo.selectedGroup.getID(), isForSale.isChecked(), titlePost.getText().toString(),
            descriptionPost.getText().toString(), Float.parseFloat(price.getText().toString()), encodedImage, PersonalInfo.clickedPet.getID());
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


    public void updateImage(View v){
        String[] mimes = {"image/png", "image/jpg", "image/jpeg"};
        ImagePicker.Companion.with(this).crop(16,9).compress(1024).maxResultSize(720,480).galleryMimeTypes(mimes).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
//            Uri fileUri = data.getData();
//            imageB.setImageURI(fileUri);
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);
//                byte[] byteArray;
//                if (bitmap != null) {
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                    byteArray = stream.toByteArray();
//                    encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
//                    System.out.println("Guardando: " + encodedImage);
//
//                    selectedImage = true;
//                }
//            } catch (Exception ignored) {
//            }
            final Uri fileUri = data.getData();
            imageB.setImageURI(fileUri);
            StorageReference Folder = FirebaseStorage.getInstance().getReference();
            assert fileUri != null;
            final StorageReference file_name = Folder.child("POST" + fileUri.getLastPathSegment());
            file_name.putFile(fileUri).addOnSuccessListener(taskSnapshot -> {
                encodedImage = "POST" + fileUri.getLastPathSegment();
            });
        }
    }

    @Override
    public void onPostCreado() {
        AlertManager.muestraMensaje(getSupportFragmentManager(), "Error de post", AlertFragment.EnumTipoMensaje.EXITO,
                getResources().getString(R.string.post_creado), getResources().getString(R.string.post_creado_desc), false,this);
    }

    @Override
    public void onPostError() {
        AlertManager.muestraMensaje(getSupportFragmentManager(), "Error de post", AlertFragment.EnumTipoMensaje.ERROR,
        getResources().getString(R.string.error_al_publicar), getResources().getString(R.string.error_post_desc), false,this);
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