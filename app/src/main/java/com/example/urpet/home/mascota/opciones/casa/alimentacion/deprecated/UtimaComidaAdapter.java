package com.example.urpet.home.mascota.opciones.casa.alimentacion.deprecated;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urpet.R;
import com.example.urpet.database.entity.UltimaComidaEntity;

import java.util.ArrayList;

public class UtimaComidaAdapter extends RecyclerView.Adapter<UtimaComidaAdapter.UltimaComidaViewHolder> {

    private ArrayList<UltimaComidaEntity> listadoComida;

    private Context mContext;

    private onReAddcomida listener;

    public interface onReAddcomida{
        void reAddComida(UltimaComidaEntity comida);
    }

    public void setListener(onReAddcomida listener) {
        this.listener = listener;
    }

    public void setListadoComida(ArrayList<UltimaComidaEntity> listadoComid) {
        listadoComida.clear();
        listadoComida = listadoComid;
        notifyDataSetChanged();
    }

    public UtimaComidaAdapter(Context mContext) {
        listadoComida = new ArrayList<>();
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public UltimaComidaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_comida_reciente, parent, false);
        return new UltimaComidaViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull UltimaComidaViewHolder holder, int position) {
        UltimaComidaEntity comida = listadoComida.get(position);
        holder.mDescripcion.setText(comida.getDescripcion());
        holder.fecha.setText(mContext.getString(R.string.adquirido, comida.getFecha()));
        holder.presentacion.setText(mContext.getResources().getString(R.string.edicion_de, comida.getPresentacion()));
        holder.mTitulo.setText(comida.getNombre());
        holder.mButtonAddcomida.setOnClickListener(v-> listener.reAddComida(listadoComida.get(position)));
    }

    @Override
    public int getItemCount() {
        return listadoComida.size();
    }

    static class UltimaComidaViewHolder extends RecyclerView.ViewHolder{
        TextView mTitulo, mDescripcion, presentacion, fecha;
        ImageView mButtonAddcomida;
        public UltimaComidaViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitulo = itemView.findViewById(R.id.item_comida_titulo);
            presentacion = itemView.findViewById(R.id.item_comida_tamano);
            fecha = itemView.findViewById(R.id.item_comida_fecha);
            mDescripcion = itemView.findViewById(R.id.item_comida_descripcion);
            mButtonAddcomida = itemView.findViewById(R.id.item_comida_add_elemento);
        }
    }
}
