package com.example.urpet.home.social.grupos.posts.comentarios;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urpet.R;
import com.example.urpet.connections.social.Comentario;
import com.example.urpet.home.mascota.listadoMascotas.MascotaAdapter;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.ComentarioViewHolder> {

    private ArrayList<Comentario> mListadoComentarios;
    private Context mContext;

    public ComentarioAdapter(Context mContext) {
        this.mContext = mContext;
        mListadoComentarios = new ArrayList<>();
    }

    public void setmListadoComentarios(ArrayList<Comentario> listadoComentarios) {
        mListadoComentarios.clear();
        mListadoComentarios.addAll(listadoComentarios);
    }

    @NonNull
    @Override
    public ComentarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_comentario, parent, false);
        return new ComentarioViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ComentarioViewHolder holder, int position) {
        holder.mDescripcion.setText(mListadoComentarios.get(position).getComentario());
        holder.mFecha.setText(mListadoComentarios.get(position).getFecha());
        holder.mTitulo.setText(mContext.getString(R.string.coment_dice, "Nombre mascota"));
    }

    @Override
    public int getItemCount() {
        return mListadoComentarios.size();
    }

    static class ComentarioViewHolder extends RecyclerView.ViewHolder{

        TextView mTitulo, mDescripcion, mFecha;
        CircularImageView mFoto;

        public ComentarioViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitulo = itemView.findViewById(R.id.item_comentario_titulo_tv);
            mDescripcion = itemView.findViewById(R.id.item_comentario_descripcion_tv);
            mFecha = itemView.findViewById(R.id.item_comentario_fecha_tv);
            mFoto = itemView.findViewById(R.id.item_comentario_img_iv);
        }
    }
}
