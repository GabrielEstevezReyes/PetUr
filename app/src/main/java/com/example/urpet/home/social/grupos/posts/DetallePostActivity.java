package com.example.urpet.home.social.grupos.posts;

import android.mtp.MtpConstants;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.urpet.R;
import com.example.urpet.Utils.GeneralUtils;
import com.example.urpet.connections.Post;
import com.google.firebase.storage.FirebaseStorage;

import static android.view.View.GONE;
import static com.example.urpet.home.social.grupos.posts.listado.PostAdapter.POST_KEY;

public class DetallePostActivity extends AppCompatActivity {

    private TextView mTitulo, mDescr, mPrecio, mFecha;
    private ImageView mImagen, mIconVenta;
    private Post mPost;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_post);
        mPost = (Post) getIntent().getSerializableExtra(POST_KEY);
        bindviews();
        configureViews();
    }

    private void bindviews(){
        mTitulo = findViewById(R.id.activity_detalle_post_titulo_tv);
        mDescr = findViewById(R.id.activity_detalle_post_descripcion_tv);
        mPrecio = findViewById(R.id.activity_detalle_post_precio_tv);
        mFecha = findViewById(R.id.activity_detalle_post_fecha_tv);
        mImagen = findViewById(R.id.activity_detalle_post_img_iv);
        mIconVenta = findViewById(R.id.activity_detalle_post_precio_iv);
    }

    private void configureViews(){
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
    }
}
