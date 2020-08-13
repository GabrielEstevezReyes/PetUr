package com.example.urpet.home;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urpet.PersonalInfo;
import com.example.urpet.PlacesM;
import com.example.urpet.R;
import com.example.urpet.SOS;
import com.example.urpet.Utils.SharedPreferencesUtil;
import com.example.urpet.connections.Pet;
import com.example.urpet.home.ads.adapter.AdsAdapter;
import com.example.urpet.home.ads.listado.AdsDTO;
import com.example.urpet.home.social.ListadoGruposActivity;
import com.example.urpet.home.marketplace.MarketplaceActivity;
import com.example.urpet.home.mascota.ListaMascotas;
import com.example.urpet.home.mascota.anadirMascota.RegistroMascota;
import com.example.urpet.home.mascota.listadoMascotas.MascotaAdapter;
import com.example.urpet.home.medico.Clinicas;
import com.example.urpet.home.perfil.MiPerfilActivity;
import com.example.urpet.login.LoginFingerprintAlert;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoginFingerprintAlert.initLogin, MascotaAdapter.onItemClicked{

    private Button mSOSBtn, mBtnTiendaBTN;
    private CardView mAddPetBtn;
    private ImageView mSettingBtn, mNotifBtn;

    private RecyclerView mListadoMascotasRV, mListadoPublicidadRV;

    private MascotaAdapter mAdapterMascotas;

    private AdsAdapter madapterAds;

    private TextView mNombreTV;

    private CircularImageView mFotoPerfilCIV;

    FirebaseStorage storage = FirebaseStorage.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        bindviews();
        configureViews();
        bindData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(PersonalInfo.currentUser != null){
            if(!PersonalInfo.currentUser.getBase64Image().isEmpty() && !PersonalInfo.currentUser.getBase64Image().equals("no-image.png")) {
                getBitmapFromURL(PersonalInfo.currentUser.getBase64Image(), mFotoPerfilCIV);
            }
            else{
                mFotoPerfilCIV.setImageResource(R.drawable.ic_user);
            }
            mNombreTV.setText(PersonalInfo.currentUser.getName().isEmpty() ? "" : PersonalInfo.currentUser.getName());
        }

    }

    private void bindviews(){
        mNombreTV = findViewById(R.id.home_activity_nombre_et);
        mListadoMascotasRV = findViewById(R.id.home_activity_listado_mascota_rv);
        mListadoPublicidadRV = findViewById(R.id.home_activity_listado_publicidad_rv);

        mFotoPerfilCIV = findViewById(R.id.home_activity_foto_perfil_civ);
        mBtnTiendaBTN = findViewById(R.id.home_activity_tienda_btn);
        mSOSBtn = findViewById(R.id.home_activity_sos_btn);
        mAddPetBtn = findViewById(R.id.home_activity_add_pet_cv);
        mSettingBtn = findViewById(R.id.home_activity_settings_iv);
        mNotifBtn = findViewById(R.id.home_activity_notificaciones_iv);

        bindbiometric();
    }

    private void bindbiometric(){
        if(!SharedPreferencesUtil.getInstance().isBiometricenabled()){
            LoginFingerprintAlert fr = LoginFingerprintAlert.newInstance(LoginFingerprintAlert.TiposAlert.ACTIVAR);
            fr.setmListener(this);
            fr.show(getSupportFragmentManager(), "Alert Fingerprint");
        }
    }

    private void configureViews(){
        mFotoPerfilCIV.setOnClickListener(this);
        mBtnTiendaBTN.setOnClickListener(this);
        mSOSBtn.setOnClickListener(this);
        mAddPetBtn.setOnClickListener(this);
        mSettingBtn.setOnClickListener(this);
        mNotifBtn.setOnClickListener(this);
    }

    private void bindData(){

        madapterAds = new AdsAdapter(this);
        ArrayList<AdsDTO> mListadoAds = new ArrayList<>();
        mListadoAds.add(new AdsDTO("Pedigree chico"));
        mListadoAds.add(new AdsDTO("Pedigree cachorro"));
        mListadoAds.add(new AdsDTO("Pedigree grande"));

        mAdapterMascotas = new MascotaAdapter(this);
        mAdapterMascotas.setmFirebaseStora(storage);
        mAdapterMascotas.setmListener(this);

        Pet obtener = new Pet();
        ArrayList<Pet> allPets;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                allPets = obtener.read();
               mAdapterMascotas.setmListadoMascotas(allPets);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        LinearLayoutManager mL = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        mListadoMascotasRV.setAdapter(mAdapterMascotas);
        mListadoMascotasRV.setLayoutManager(mL);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mListadoMascotasRV);

        madapterAds.setmListadoAds(mListadoAds);
        LinearLayoutManager mL2 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        mListadoPublicidadRV.setAdapter(madapterAds);
        mListadoPublicidadRV.setLayoutManager(mL2);
    }

    @Override
    public void onFlechaClicked(boolean esSiguiente, int position) {
        if(esSiguiente){
            toRight(position);
        }
        else{
            toLeft(position);
        }
    }

    /**
     * <p>Método que te permiete recorrer la lista a la derecha</p>
     */
    public void toRight(int position) {
        int pos = position + 1;
        mListadoMascotasRV.scrollToPosition(pos >= mAdapterMascotas.getItemCount() ? mAdapterMascotas.getItemCount()-1: pos);
    }

    /**
     * <p>Metodo que te permite recorrer la lista a la izquierda</p>
     */

    public void toLeft(int position) {
        int pos = position - 1;
        mListadoMascotasRV.scrollToPosition(pos >= 0 ? pos: position);
    }

    private void addMascota(){
        Intent siguiente = new Intent(this, RegistroMascota.class);
        startActivity (siguiente);
    }

    public void getBitmapFromURL(String src, final CircularImageView circ) {
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(src);

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(b -> {
            Drawable image = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(b, 0, b.length));
            circ.setImageDrawable(image);
        }).addOnFailureListener(exception -> {
            // Handle any errors
        });
    }

    public void itAPerfil(){
        startActivity(new Intent(this, MiPerfilActivity.class));
    }

    public void lis_mas(View view){
        Intent siguiente = new Intent(this, ListaMascotas.class);
        startActivity (siguiente);
        finish();
    }

    public void lis_care(View view){
        Intent siguiente = new Intent(MainActivity.this, Clinicas.class);
        startActivity (siguiente);
        finish();
    }

    public void lis_grupos(View view){
        Intent siguiente = new Intent(MainActivity.this, ListadoGruposActivity.class);
        startActivity (siguiente);
        finish();
    }

    public void lis_cuidad(View view){
        Intent siguiente = new Intent(MainActivity.this, PlacesM.class);
        startActivity (siguiente);
        finish();
    }

    public void irASoS(){
        Intent siguiente = new Intent(MainActivity.this, SOS.class);
        startActivity (siguiente);
        finish();
    }

    private void irAMarket(){
        Intent siguiente = new Intent(this, MarketplaceActivity.class);
        startActivity (siguiente);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_activity_foto_perfil_civ:
                itAPerfil();
            break;
            case R.id.home_activity_tienda_btn:
                irAMarket();
            break;
            case R.id.home_activity_sos_btn:
                irASoS();
            break;
            case R.id.home_activity_add_pet_cv:
                addMascota();
            break;
            case R.id.home_activity_settings_iv:

            break;
            case R.id.home_activity_notificaciones_iv:

            break;
        }
    }

    @Override
    public void onSetFingerprint() {
        validaStatusBiometrico();
    }

    @Override
    public void onIrAConfig() {
        abrirconfiguracion();
    }


    private void validaStatusBiometrico(){
        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                registrarFingerprint();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                LoginFingerprintAlert fr = LoginFingerprintAlert.newInstance(LoginFingerprintAlert.TiposAlert.IR_A_CONFIG);
                fr.setmListener(this);
                fr.show(getSupportFragmentManager(), "Config Fingerprint");
            break;
        }
    }

    private void abrirconfiguracion(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            startActivity(new Intent(Settings.ACTION_FINGERPRINT_ENROLL));
        }
        else{
            startActivity(new Intent(Settings.ACTION_SETTINGS));
        }
    }

    /**
     * <p>Método para</p>
     * */
    private void registrarFingerprint(){
        if(!SharedPreferencesUtil.getInstance().isBiometricenabled() && SharedPreferencesUtil.getInstance().isLoginClasico()){
            SharedPreferencesUtil.getInstance().createBiometricUser(
                    SharedPreferencesUtil.getInstance().getCorreo(),
                    SharedPreferencesUtil.getInstance().getPassword()
            );
            LoginFingerprintAlert fr = LoginFingerprintAlert.newInstance(LoginFingerprintAlert.TiposAlert.EXITO);
            fr.setmListener(this);
            fr.show(getSupportFragmentManager(), "Exito Fingerprint");
        }
    }


}
