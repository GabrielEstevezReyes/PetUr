package com.example.urpet.home.mascota.listadoMascotas;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urpet.PersonalInfo;
import com.example.urpet.R;
import com.example.urpet.Utils.GeneralUtils;
import com.example.urpet.connections.Pet;
import com.example.urpet.home.mascota.anadirMascota.RegistroMascota;
import com.example.urpet.home.mascota.detallesMascota.MascotaSettingActivity;
import com.example.urpet.home.mascota.detallesMascota.DetalleMascotaActivity;
import com.google.firebase.storage.FirebaseStorage;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class MascotaAdapter extends RecyclerView.Adapter<MascotaAdapter.MascotaViewHolder> {

    private FirebaseStorage mFirebaseStora;

    private ArrayList<Pet> mListadoMascotas;

    private int lastCardPos;

    private boolean noPets = false;

    private Context mContext;

    private onItemClicked mListener;

    public MascotaAdapter(Context c) {
        mListadoMascotas = new ArrayList<>();
        mContext = c;
    }
    public interface onItemClicked{
        void onFlechaClicked(boolean esSiguiente, int position);
    }

    public void setmFirebaseStora(FirebaseStorage mFirebaseStora) {
        this.mFirebaseStora = mFirebaseStora;
    }

    public void setmListadoMascotas(ArrayList<Pet> mascotas) {
        mListadoMascotas.clear();
        mListadoMascotas = mascotas;
        if(mListadoMascotas.size() == 0){
            noPets = true;
        } else{
            lastCardPos = mListadoMascotas.size();
        }
        notifyDataSetChanged();
    }

    public void setmListener(onItemClicked mListener) {
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public MascotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_mascota_home, parent, false);
        return new MascotaViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MascotaViewHolder holder, int position) {
        if(noPets || position == lastCardPos){
            holder.mSiPetsCpontainerCV.setVisibility(View.GONE);
            holder.mNoPetsAdviceCV.setVisibility(View.VISIBLE);
            holder.mNoPetsAdviceCV.setOnClickListener(v->{
                Intent siguiente = new Intent(mContext, RegistroMascota.class);
                mContext.startActivity(siguiente);
            });
            if(position == lastCardPos){
                holder.addPetTitulo.setText(R.string.agregar_mascota_titulo);
            }
        }
        else {
            holder.mSiPetsCpontainerCV.setVisibility(View.VISIBLE);
            holder.mNoPetsAdviceCV.setVisibility(View.GONE);
            Pet mascota = mListadoMascotas.get(position);
            holder.mascotaNombreTV.setText(mascota.getName());
            if (!mascota.getBase64Image().isEmpty() && !mascota.getBase64Image().equals("no-image.png")) {
                GeneralUtils.getBitmapFromURL(mascota.getBase64Image(), holder.fotoPerfilMascota, mFirebaseStora, mContext);
            } else {
                holder.fotoPerfilMascota.setImageResource(R.drawable.ic_mascotas_1);
            }
            holder.settingsMascota.setOnClickListener(V -> {
                Intent siguiente = new Intent(mContext, MascotaSettingActivity.class);
                mContext.startActivity(siguiente);
            });

            holder.contenedorPrincipal.setOnClickListener(v -> {
                PersonalInfo.clickedPet = mascota;
                Intent siguiente = new Intent(mContext, DetalleMascotaActivity.class);
                mContext.startActivity(siguiente);
            });

            if (position == 0) {
                holder.mAnterior.setVisibility(View.GONE);
                holder.mSiguiente.setVisibility(View.VISIBLE);
            } else if (position == mListadoMascotas.size() - 1) {
                holder.mAnterior.setVisibility(View.VISIBLE);
                holder.mSiguiente.setVisibility(View.GONE);
            } else {
                holder.mAnterior.setVisibility(View.VISIBLE);
                holder.mSiguiente.setVisibility(View.VISIBLE);
            }

            holder.mSiguiente.setOnClickListener(v -> mListener.onFlechaClicked(true, position));
            holder.mAnterior.setOnClickListener(v -> mListener.onFlechaClicked(false, position));
        }
    }

    @Override
    public int getItemCount() {
        return noPets ? 1 : mListadoMascotas.size() + 1;
    }

    static class MascotaViewHolder extends RecyclerView.ViewHolder{
        TextView mascotaNombreTV, addPetTitulo;
        CircularImageView fotoPerfilMascota;
        RecyclerView listadoEventosRV;
        ImageView settingsMascota;
        ImageView mSiguiente, mAnterior;
        ConstraintLayout contenedorPrincipal;
        CardView mNoPetsAdviceCV,mSiPetsCpontainerCV;

        public MascotaViewHolder(@NonNull View itemView) {
            super(itemView);
            mascotaNombreTV = itemView.findViewById(R.id.item_mascota_nombre_tv);
            fotoPerfilMascota = itemView.findViewById(R.id.item_mascota_foto_civ);
            listadoEventosRV = itemView.findViewById(R.id.item_mascota_listado_eventos_rv);
            settingsMascota = itemView.findViewById(R.id.item_mascota_settings_iv);
            contenedorPrincipal = itemView.findViewById(R.id.item_mascota_full_item_cl);
            mSiguiente = itemView.findViewById(R.id.item_home_next_arrow_iv);
            mAnterior = itemView.findViewById(R.id.item_home_back_arrow_iv);
            mNoPetsAdviceCV = itemView.findViewById(R.id.item_mascota_no_pet_cv);
            mSiPetsCpontainerCV = itemView.findViewById(R.id.item_mascota_si_pet_cv);
            addPetTitulo = itemView.findViewById(R.id.item_mascota_no_pet_titulo);
        }
    }
}
