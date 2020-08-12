package com.example.urpet.home.perfil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.urpet.PersonalInfo;
import com.example.urpet.R;
import com.example.urpet.Utils.GeneralUtils;
import com.example.urpet.Utils.LoaderFragment;
import com.example.urpet.Utils.alert.SpinnerFechasDialogFragment;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONException;

public class EditarPerfilActivity extends AppCompatActivity implements SpinnerFechasDialogFragment.onDateSelected {

    private EditText mNombreEt, mTelefonoEt, mCorreoEt, mFechaEt,mDireccionEt;
    private CircularImageView mFotoPerilCiv;
    private ImageView mEditarFotoIv;
    private LoaderFragment mLoader;
    private Button mGuardarBtn;
    public boolean selectedImage = false;

    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_perfil_user_data);
        bindviews();
        configureViews();
    }

    private void bindviews(){
        mNombreEt = findViewById(R.id.editar_perfil_activity_nombre_et);
        mTelefonoEt = findViewById(R.id.editar_perfil_activity_telefono_et);
        mCorreoEt = findViewById(R.id.editar_perfil_activity_mail_et);
        mFechaEt = findViewById(R.id.editar_perfil_activity_fecha_nacimiento_et);
        mDireccionEt = findViewById(R.id.editar_perfil_activity_addr_et);
        mFotoPerilCiv = findViewById(R.id.editar_perfil_activity_foto_civ);
        mEditarFotoIv = findViewById(R.id.editar_perfil_activity_edit_foto_iv);
        mGuardarBtn = findViewById(R.id.editar_perfil_activity_save_btn);
        mLoader = LoaderFragment.newInstance();
    }

    private void configureViews(){
        mCorreoEt.setText(PersonalInfo.currentUser.getMail());
        mNombreEt.setText(PersonalInfo.currentUser.getName());
        mTelefonoEt.setText(PersonalInfo.currentUser.getPhoneNumber());
        mDireccionEt.setText(PersonalInfo.currentUser.getAddr());

        mFechaEt.setText(PersonalInfo.currentUser.getBirthDate());
        mFechaEt.setOnClickListener(v -> GeneralUtils.abrirCalendarioFecha(getSupportFragmentManager(), this, "Fecha de nacimiento"));
        if(!PersonalInfo.currentUser.getBase64Image().isEmpty() && !PersonalInfo.currentUser.getBase64Image().equals("no-image.png")) {
            GeneralUtils.getBitmapFromURL(PersonalInfo.currentUser.getBase64Image(), mFotoPerilCiv, storage, this);
        }
        else{
            mFotoPerilCiv.setImageResource(R.drawable.ic_user);
        }

        mEditarFotoIv.setOnClickListener(v-> updateImage());

        mGuardarBtn.setOnClickListener(v-> {
            try {
                onGuardarCambios();
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }


    public void updateImage(){
        String[] mimes = {"image/png", "image/jpg", "image/jpeg"};
        ImagePicker.Companion.with(this).cropSquare().compress(1024).maxResultSize(480,480).galleryMimeTypes(mimes).start();
    }


    public void onGuardarCambios() throws JSONException {
        mLoader = LoaderFragment.newInstance();
        mLoader.show(getSupportFragmentManager(), "Guardando");
        if(!mNombreEt.getText().toString().isEmpty()){
            PersonalInfo.currentUser.setName(mNombreEt.getText().toString());
        }
        if(!mTelefonoEt.getText().toString().isEmpty()){
            PersonalInfo.currentUser.setPhoneNumber(mTelefonoEt.getText().toString());
        }
        if(!mDireccionEt.getText().toString().isEmpty()){
            PersonalInfo.currentUser.setAddr(mDireccionEt.getText().toString());
        }
        PersonalInfo.currentUser.setBirthDate(mFechaEt.getText().toString());
        PersonalInfo.currentUser.update(selectedImage);
        onBackPressed();
        mLoader.dismiss();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLoader.show(getSupportFragmentManager(), "Fragment Imagen");
        if (resultCode == RESULT_OK) {
            Uri fileUri = data.getData();
            mFotoPerilCiv.setImageURI(fileUri);

            StorageReference Folder = FirebaseStorage.getInstance().getReference();
            final StorageReference file_name = Folder.child("USR" + PersonalInfo.currentUser.getID());
            assert fileUri != null;
            file_name.putFile(fileUri).addOnSuccessListener(taskSnapshot ->
                PersonalInfo.currentUser.setBase64Image("USR" + PersonalInfo.currentUser.getID())
            );
        }
        mLoader.dismiss();
    }

    @Override
    public void onFechaSelected(SpinnerFechasDialogFragment view, String fecha) {
        mFechaEt.setText(fecha);
    }
}
