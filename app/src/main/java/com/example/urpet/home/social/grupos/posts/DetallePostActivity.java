package com.example.urpet.home.social.grupos.posts;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urpet.R;
import com.example.urpet.Utils.GeneralUtils;
import com.example.urpet.Utils.LoaderFragment;
import com.example.urpet.Utils.alert.AlertFragment;
import com.example.urpet.Utils.alert.AlertManager;
import com.example.urpet.connections.social.Comentario;
import com.example.urpet.connections.social.Post;
import com.example.urpet.home.social.grupos.posts.comentarios.ComentarioAdapter;
import com.example.urpet.home.social.grupos.posts.comentarios.CrearComentarioDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import static android.view.View.GONE;
import static com.example.urpet.home.social.grupos.posts.listado.PostAdapter.POST_KEY;

public class DetallePostActivity extends AppCompatActivity implements View.OnClickListener, CrearComentarioDialogFragment.OnComentarioListener, PostView {

    private TextView mTitulo, mDescr, mPrecio, mFecha;
    private ImageView mImagen, mIconVenta;
    private Post mPost;
    private LoaderFragment mLoader;
    private ComentarioAdapter mAdapterComent;
    private FloatingActionButton mFab;
    private ArrayList<Comentario> mListadoComentarios;
    private PostPresenter mPresenter;
    private RecyclerView mComentariosRecycler;
    private CardView mCartaSinComentariosCV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_post);
        mPost = (Post) getIntent().getSerializableExtra(POST_KEY);
        bindviews();
        configureViews();
        getComentarios();
    }

    private void bindviews(){
        mFab = findViewById(R.id.activity_detalkle_post_add_coment_fab);
        mTitulo = findViewById(R.id.activity_detalle_post_titulo_tv);
        mDescr = findViewById(R.id.activity_detalle_post_descripcion_tv);
        mPrecio = findViewById(R.id.activity_detalle_post_precio_tv);
        mFecha = findViewById(R.id.activity_detalle_post_fecha_tv);
        mImagen = findViewById(R.id.activity_detalle_post_img_iv);
        mIconVenta = findViewById(R.id.activity_detalle_post_precio_iv);
        mAdapterComent = new ComentarioAdapter(this);
        mPresenter = new PostPresenter(this, new PostInteractor());
        mListadoComentarios = new ArrayList<>();
        mLoader = LoaderFragment.newInstance();
        mCartaSinComentariosCV = findViewById(R.id.activity_detalle_post_sin_coments_cv);
        mComentariosRecycler = findViewById(R.id.activity_detalle_post_listado_comentarios_rv);
    }

    private void configureViews(){
        mFab.setOnClickListener(this);

        if(mPost != null){
            mTitulo.setText(mPost.getName());
            mDescr.setText(mPost.getDescription());
            boolean isForSale = mPost.getForSale() == 1;
            mPrecio.setVisibility(isForSale ? View.VISIBLE : GONE);
            mPrecio.setText(isForSale ? String.valueOf(mPost.getPrice()) : "-");
            mIconVenta.setVisibility(isForSale ? View.VISIBLE : GONE);
            if(!mPost.getImage().isEmpty()){
                GeneralUtils.getBitmapFromURL(mPost.getImage(),
                        mImagen, FirebaseStorage.getInstance(), this);
            }
            mFecha.setText(getString(R.string.publicado_el, mPost.getFecha()));
        }

        mAdapterComent.setmListadoComentarios(mListadoComentarios);
        LinearLayoutManager mManager  = new LinearLayoutManager(this, RecyclerView.VERTICAL, true);
        mComentariosRecycler.setAdapter(mAdapterComent);
        mComentariosRecycler.setNestedScrollingEnabled(false);
        mComentariosRecycler.setLayoutManager(mManager);

    }

    private void crearComentario(){
        CrearComentarioDialogFragment mComent = CrearComentarioDialogFragment.newInstance();
        mComent.setData(this, this, mPost);
        mComent.show(getSupportFragmentManager(), "Comentar");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_detalkle_post_add_coment_fab:
                crearComentario();
            break;
        }
    }

    private void getComentarios(){
        mAdapterComent.setmListadoComentarios(new ArrayList<>());
        mPresenter.onCallComentarios(mPost.getIdPost());
    }

    @Override
    public void comentarioEnviado() {
        getComentarios();
    }

    @Override
    public void onGetListadoComentarios(ArrayList<Comentario> mComentarios) {
        mListadoComentarios.clear();
        mListadoComentarios.addAll(mComentarios);
        mAdapterComent.setmListadoComentarios(mListadoComentarios);
        boolean hayComments = mListadoComentarios.size() > 0;
        mCartaSinComentariosCV.setVisibility(hayComments ? GONE : View.VISIBLE);
        mComentariosRecycler.setVisibility(hayComments ? View.VISIBLE : GONE);
    }

    @Override
    public void onError() {
        AlertManager.muestraMensaje(getSupportFragmentManager(), "Error", AlertFragment.EnumTipoMensaje.ERROR,
                getResources().getString(R.string.error_de_conexion), getResources().getString(R.string.error_conexion_desc), true,null);
    }

    @Override
    public void onShowLoader() {
        mLoader = LoaderFragment.newInstance();
        mLoader.show(getSupportFragmentManager(), "Loader");
    }

    @Override
    public void onHideLoader() {
        mLoader.dismissAllowingStateLoss();
    }
}
