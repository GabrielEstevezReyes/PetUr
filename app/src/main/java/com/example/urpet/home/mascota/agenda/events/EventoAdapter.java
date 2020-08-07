package com.example.urpet.home.mascota.agenda.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urpet.R;

import java.util.ArrayList;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.EventoViewHolder> {

    private ArrayList<EventoDTO> mListadoEventos;

    private Context mContext;

    public EventoAdapter(Context c) {
        mListadoEventos = new ArrayList<>();
        mContext = c;
    }

    public void setData(ArrayList<EventoDTO> eventos){
        mListadoEventos.clear();
        mListadoEventos.addAll(eventos);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_evento, parent, false);
        return new EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
        holder.mFinTV.setText(mListadoEventos.get(position).getmFechaFin());
        holder.mInicioTv.setText(mListadoEventos.get(position).getmFechaInicio());
        holder.mNombreTv.setText(mListadoEventos.get(position).getmNombreEvento());
    }

    @Override
    public int getItemCount() {
        return mListadoEventos.size();
    }

    static class EventoViewHolder extends RecyclerView.ViewHolder{

        TextView mNombreTv, mInicioTv, mFinTV;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);
            mNombreTv = itemView.findViewById(R.id.item_evento_nombre);
            mInicioTv = itemView.findViewById(R.id.item_evento_inicio_tv);
            mFinTV = itemView.findViewById(R.id.item_evento_fin_tv);

        }
    }
}
