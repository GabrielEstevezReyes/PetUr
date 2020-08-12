package com.example.urpet.home.ads.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urpet.R;
import com.example.urpet.home.ads.listado.AdsDTO;

import java.util.ArrayList;

public class AdsAdapter extends RecyclerView.Adapter<AdsAdapter.AdsViewHolder> {

    private Context mContext;

    private ArrayList<AdsDTO> mListadoAds;

    public AdsAdapter(Context mContext) {
        mListadoAds = new ArrayList<>();
        this.mContext = mContext;
    }

    public void setmListadoAds(ArrayList<AdsDTO> mListado) {
        mListadoAds.clear();
        mListadoAds.addAll(mListado);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_ads, parent, false);
        return new AdsViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdsViewHolder holder, int position) {
        holder.mImagenAd.setOnClickListener(v->{
            openAmazon(mListadoAds.get(position).getNombre());
        });
    }

    public void openAmazon(String busqueda){
        String url1 = "amzn://apps/android?s=" + busqueda;
        openURL(url1, busqueda);
    }

    public void openURL(String url, String busqueda){
        try{
            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (ActivityNotFoundException ex){
            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.amazon.com/gp/mas/dl/android?s="+busqueda)));
        }
    }

    @Override
    public int getItemCount() {
        return mListadoAds.size();
    }

    static class AdsViewHolder extends RecyclerView.ViewHolder{
        ImageView mImagenAd;
        public AdsViewHolder(@NonNull View itemView) {
            super(itemView);
            mImagenAd = itemView.findViewById(R.id.item_ads_image_iv);
        }
    }
}
