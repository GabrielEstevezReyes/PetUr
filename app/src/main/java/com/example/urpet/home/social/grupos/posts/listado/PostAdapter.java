package com.example.urpet.home.social.grupos.posts.listado;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urpet.R;
import com.example.urpet.Utils.GeneralUtils;
import com.example.urpet.connections.Post;
import com.example.urpet.home.social.grupos.posts.DetallePostActivity;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private ArrayList<Post> mListadoPosts;
    private Context mContext;
    private FirebaseStorage storage;
    public static final String POST_KEY = "post";

    public PostAdapter(Context mContext, FirebaseStorage mstorage) {
        this.mContext = mContext;
        mListadoPosts = new ArrayList<>();
        storage = mstorage;
    }

    public void setmListadoPosts(ArrayList<Post> listadoPosts) {
        mListadoPosts.clear();
        mListadoPosts.addAll(listadoPosts);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_post, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.mTitulo.setText(mListadoPosts.get(position).getName());
        holder.mDescr.setText(mListadoPosts.get(position).getDescription());
        holder.mFecha.setText(mListadoPosts.get(position).getFecha());
        boolean isVenta = mListadoPosts.get(position).getForSale() == 1;
        holder.mPrecio.setVisibility(isVenta ? View.VISIBLE : View.INVISIBLE);
        holder.mPrecio.setText(isVenta ? String.valueOf(mListadoPosts.get(position).getPrice()) : "0");
        holder.mIconVenta.setVisibility(isVenta ? View.VISIBLE : View.INVISIBLE);
        if(!mListadoPosts.get(position).getImage().isEmpty()){
            GeneralUtils.getBitmapFromURL(mListadoPosts.get(position).getImage(),
                    holder.mImagen, storage, mContext);
        }
        holder.mRootCV.setOnClickListener(v->{
            Intent post = new Intent(mContext, DetallePostActivity.class);
            post.putExtra(POST_KEY, mListadoPosts.get(position));
            mContext.startActivity(post);
        });
    }

    @Override
    public int getItemCount() {
        return mListadoPosts.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder{
        TextView mTitulo, mDescr, mPrecio, mFecha;
        ImageView mImagen, mIconVenta;
        CardView mRootCV;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
                mTitulo = itemView.findViewById(R.id.item_post_titulo_tv);
                mDescr = itemView.findViewById(R.id.item_post_descripcion_tv);
                mPrecio = itemView.findViewById(R.id.item_post_precio_tv);
                mFecha = itemView.findViewById(R.id.item_post_fecha_tv);
                mImagen = itemView.findViewById(R.id.item_post_img_iv);
                mIconVenta = itemView.findViewById(R.id.item_post_precio_iv);
                mRootCV = itemView.findViewById(R.id.item_post_root_cv);
        }
    }
}
