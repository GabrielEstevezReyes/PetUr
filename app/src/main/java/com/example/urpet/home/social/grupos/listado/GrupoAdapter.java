package com.example.urpet.home.social.grupos.listado;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urpet.PersonalInfo;
import com.example.urpet.R;
import com.example.urpet.Utils.GeneralUtils;
import com.example.urpet.connections.social.Group;
import com.example.urpet.home.social.grupos.detalle.DetalleGrupoActivity;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class GrupoAdapter extends RecyclerView.Adapter<GrupoAdapter.GrupoViewHolder> {

    private ArrayList<Group> mListadoGrupos;
    private Context mContext;
    private FirebaseStorage storage;

    public GrupoAdapter(Context mContext, FirebaseStorage storage) {
        this.mContext = mContext;
        this.storage = storage;
        mListadoGrupos = new ArrayList<>();
    }

    public void setmListadoGrupos(ArrayList<Group> listadoGrupos) {
        mListadoGrupos.clear();
        mListadoGrupos.addAll(listadoGrupos);
        notifyDataSetChanged();
    }

    public void clearData(){
        mListadoGrupos.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GrupoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GrupoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_grupo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GrupoViewHolder holder, int position) {
        holder.mTitulo.setText(mListadoGrupos.get(position).getName());
        holder.mDescr.setText(mListadoGrupos.get(position).getDescription());
        boolean isCerrado = mListadoGrupos.get(position).getIsClosed() == 1;
        holder.mTipo.setText(isCerrado ? R.string.grupo_cerrado : R.string.grupo_abierto);
        if(!mListadoGrupos.get(position).getImage().isEmpty()){
            GeneralUtils.getBitmapFromURL(mListadoGrupos.get(position).getImage(),
                    holder.mImagen, storage, mContext);
        } else{
            holder.mImagen.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_mascotas_1));
        }
        holder.cv.setOnClickListener(v-> abrirDetalleGrupo(mListadoGrupos.get(position)));
    }

    private void abrirDetalleGrupo(Group grupo){
        PersonalInfo.selectedGroup = grupo;
        Intent siguiente = new Intent(mContext, DetalleGrupoActivity.class);
        mContext.startActivity (siguiente);
    }

    @Override
    public int getItemCount() {
        return mListadoGrupos.size();
    }

    static class GrupoViewHolder extends RecyclerView.ViewHolder{
        TextView mTitulo, mDescr, mTipo;
        ImageView mImagen;
        CardView cv;
        public GrupoViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.item_grupo_contenedor_principal_cv);
            mTitulo = itemView.findViewById(R.id.item_grupo_titulo_tv);
            mDescr = itemView.findViewById(R.id.item_grupo_descripcion_tv);
            mTipo = itemView.findViewById(R.id.item_grupo_cerrado_tv);
            mImagen = itemView.findViewById(R.id.item_grupo_img_iv);
        }
    }
}
