package com.example.urpet.home.mascota.listadoMascotas;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urpet.PersonalInfo;
import com.example.urpet.R;
import com.example.urpet.Utils.GeneralUtils;
import com.example.urpet.connections.Pet;
import com.example.urpet.home.mascota.detallesMascota.MascotaSettingActivity;
import com.example.urpet.home.mascota.detallesMascota.DetalleMascotaActivity;
import com.google.firebase.storage.FirebaseStorage;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class MascotaAdapter extends RecyclerView.Adapter<MascotaAdapter.MascotaViewHolder> {

    private FirebaseStorage mFirebaseStora;

    private ArrayList<Pet> mListadoMascotas;

    private Context mContext;

    public MascotaAdapter(Context c) {
        mListadoMascotas = new ArrayList<>();
        mContext = c;
    }

    public void setmFirebaseStora(FirebaseStorage mFirebaseStora) {
        this.mFirebaseStora = mFirebaseStora;
    }

    public void setmListadoMascotas(ArrayList<Pet> mascotas) {
        mListadoMascotas.clear();
        mListadoMascotas = mascotas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MascotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_mascota_home, parent, false);
        return new MascotaViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MascotaViewHolder holder, int position) {
        Pet mascota = mListadoMascotas.get(position);
        holder.mascotaNombreTV.setText(mascota.getName());
        if(!mascota.getBase64Image().isEmpty() && !mascota.getBase64Image().equals("no-image.png")) {
            GeneralUtils.getBitmapFromURL(mascota.getBase64Image(), holder.fotoPerfilMascota, mFirebaseStora, mContext);
        }
        else{
            holder.fotoPerfilMascota.setImageResource(R.drawable.ic_mascotas_1);
        }
        holder.settingsMascota.setOnClickListener(V->{
            Intent siguiente = new Intent(mContext, MascotaSettingActivity.class);
            mContext.startActivity(siguiente);
        });

        holder.contenedorPrincipal.setOnClickListener(v->{
            PersonalInfo.clickedPet = mascota;
            Intent siguiente = new Intent(mContext, DetalleMascotaActivity.class);
            mContext.startActivity(siguiente);
        });
    }

    @Override
    public int getItemCount() {
        return mListadoMascotas.size();
    }

    static class MascotaViewHolder extends RecyclerView.ViewHolder{
        TextView mascotaNombreTV;
        CircularImageView fotoPerfilMascota;
        RecyclerView listadoEventosRV;
        ImageView settingsMascota;
        ConstraintLayout contenedorPrincipal;

        public MascotaViewHolder(@NonNull View itemView) {
            super(itemView);
            mascotaNombreTV = itemView.findViewById(R.id.item_mascota_nombre_tv);
            fotoPerfilMascota = itemView.findViewById(R.id.item_mascota_foto_civ);
            listadoEventosRV = itemView.findViewById(R.id.item_mascota_listado_eventos_rv);
            settingsMascota = itemView.findViewById(R.id.item_mascota_settings_iv);
            contenedorPrincipal = itemView.findViewById(R.id.item_mascota_full_item_cl);
        }
    }
}
