package com.example.urpet.home.social.grupos.detalle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urpet.PersonalInfo;
import com.example.urpet.R;
import com.example.urpet.Utils.GeneralUtils;
import com.example.urpet.Utils.LoaderFragment;
import com.example.urpet.Utils.alert.AlertFragment;
import com.example.urpet.Utils.alert.AlertManager;
import com.example.urpet.connections.BelongGroup;
import com.example.urpet.connections.social.Post;
import com.example.urpet.home.social.grupos.posts.listado.ListaPostInteractor;
import com.example.urpet.home.social.grupos.posts.listado.ListaPostPresenter;
import com.example.urpet.home.social.grupos.posts.listado.ListaPostView;
import com.example.urpet.home.social.grupos.posts.listado.PostAdapter;
import com.example.urpet.home.social.grupos.posts.listado.publicar.RealizarPublicacionActivity;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class DetalleGrupoActivity extends AppCompatActivity implements View.OnClickListener, ListaPostView {

    private TextView mTituloTv, mCerradoTv, mDescTv;
    private Button mEditarBtn;
    private CardView mMiembrosBtn, mPublicarBtn;
    private ImageView mLockIv, mFotoGrupoIv;
    private RecyclerView mListadoPostsRV;
    private PostAdapter mPostsAdapter;
    private ListaPostPresenter mPresenter;
    private LoaderFragment mLoader;

    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_grupo);
        bindviews();
        configureviews();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_detalle_grupo_miembros_cv:
                verListadoMiembros();
            break;
            case R.id.activity_detalle_grupo_publicar_cv:
                onRealizarPublicacion();
            break;
        }
    }

    private void bindviews(){
        mTituloTv = findViewById(R.id.activity_detalle_grupo_nomrbe_grupo_tv);
        mCerradoTv = findViewById(R.id.activity_detalle_grupo_lock_tv);
        mDescTv = findViewById(R.id.activity_detalle_grupo_descripcipn_tv);

        mEditarBtn = findViewById(R.id.activity_detalle_grupo_editar_btn);
        mMiembrosBtn = findViewById(R.id.activity_detalle_grupo_miembros_cv);
        mPublicarBtn = findViewById(R.id.activity_detalle_grupo_publicar_cv);
        mFotoGrupoIv = findViewById(R.id.activity_detalle_grupo_imagen_bg);

        mLockIv = findViewById(R.id.activity_detalle_grupo_lock_img_iv);

        mListadoPostsRV = findViewById(R.id.activity_detalle_grupo_listado_posts_rv);

        mLoader = LoaderFragment.newInstance();

        mPresenter = new ListaPostPresenter(this, new ListaPostInteractor());
    }

    private void configureviews(){
        mMiembrosBtn.setOnClickListener(this);
        mPublicarBtn.setOnClickListener(this);

        mTituloTv.setText(PersonalInfo.selectedGroup.getName());
        mDescTv.setText(PersonalInfo.selectedGroup.getDescription());

        mCerradoTv.setText(PersonalInfo.selectedGroup.getIsClosed() == 0 ? getResources().getString(R.string.grupo_abierto) : getResources().getString(R.string.grupo_cerrado));

        PersonalInfo.selectedGroup.getImageSafe();
        if(!PersonalInfo.selectedGroup.getImage().isEmpty()) {
            GeneralUtils.getBitmapFromURL(PersonalInfo.selectedGroup.getImage(), mFotoGrupoIv, storage, this);
        }
        else{
            mFotoGrupoIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.peluqueria_canina));
        }

        if(PersonalInfo.selectedGroup.getIDMascota() == PersonalInfo.clickedPet.getID()){
            mEditarBtn.setText(R.string.editar);
            mEditarBtn.setOnClickListener(v -> editarGrupo());
        }
        else
            {
                if(PersonalInfo.belong.contains(PersonalInfo.selectedGroup.getID())){
                    mEditarBtn.setVisibility(View.GONE);
                }
                else{
                    mEditarBtn.setText(R.string.unirse);
                    if(PersonalInfo.selectedGroup.getIsClosed() == 0) {
                        mEditarBtn.setOnClickListener(v -> unirseAGrupo());
                    }
                    else{
                        mEditarBtn.setOnClickListener(v -> solicitarUnirseAGrupo());
                    }
                }
            }

        mPostsAdapter = new PostAdapter(this, storage);

        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        mListadoPostsRV.setAdapter(mPostsAdapter);
        mListadoPostsRV.setLayoutManager(manager);

        mPresenter.onTraerPosts(PersonalInfo.selectedGroup.getID());
    }

    private void verListadoMiembros(){

    }

    public void editarGrupo(){
        Intent siguiente = new Intent(this, EditarGrupoActivity.class);
        startActivity (siguiente);
    }

    public void onRealizarPublicacion(){
        Intent siguiente = new Intent(this, RealizarPublicacionActivity.class);
        startActivity (siguiente);
    }

    public void unirseAGrupo(){
        BelongGroup toJoin = new BelongGroup(PersonalInfo.currentUser.getID(), PersonalInfo.selectedGroup.getID());
        toJoin.create();
        AlertManager.muestraMensaje(getSupportFragmentManager(), "Unirse", AlertFragment.EnumTipoMensaje.EXITO,
                PersonalInfo.selectedGroup.getName(), getResources().getString(R.string.union_exitosa), true, null);
        reload();
    }

    private void reload(){
        BelongGroup myBelongs = new BelongGroup(PersonalInfo.currentUser.getID(), -1);
        PersonalInfo.belong = myBelongs.getFromUser();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    public void solicitarUnirseAGrupo(){
        BelongGroup toJoin = new BelongGroup(PersonalInfo.currentUser.getID(), PersonalInfo.selectedGroup.getID());
        toJoin.requestJoin();
        AlertManager.muestraMensaje(getSupportFragmentManager(), "Unirse", AlertFragment.EnumTipoMensaje.EXITO,
                PersonalInfo.selectedGroup.getName(), getResources().getString(R.string.solicitud_union), true, null);
        reload();
    }


    @Override
    public void muestraListado(ArrayList<Post> data) {
        mPostsAdapter.setmListadoPosts(data);
    }

    @Override
    public void errorListado() {
        AlertManager.muestraMensaje(getSupportFragmentManager(), "Error de post", AlertFragment.EnumTipoMensaje.ERROR,
                getResources().getString(R.string.error_de_conexion), getResources().getString(R.string.error_conexion_desc), true,null);
    }

    @Override
    public void onShowLoader() {
        mLoader.show(getSupportFragmentManager(), "Loader");
    }

    @Override
    public void onHideLoader() {
        mLoader.dismiss();
    }
}