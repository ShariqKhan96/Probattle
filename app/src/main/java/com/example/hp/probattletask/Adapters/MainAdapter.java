package com.example.hp.probattletask.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.probattletask.LDB.LDAL.KryptoAccessLayer;
import com.example.hp.probattletask.R;
import com.example.hp.probattletask.Wrappers.AllCryptosEnt;
import com.example.hp.probattletask.Wrappers.CryptoList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 3/30/2018.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    Context context;
    ArrayList<CryptoList> cryptoLists;

    public MainAdapter(Context context, ArrayList<CryptoList> cryptoLists) {
        this.cryptoLists = cryptoLists;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvPrice, tvMarket, tvVolume;
        TextView tvHourly, tv24, tv_seven_day;
        ImageView ivFavourite;


        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvMarket = (TextView) itemView.findViewById(R.id.tvMarket);
            tvVolume = (TextView) itemView.findViewById(R.id.tvVolume);
            tvHourly = (TextView) itemView.findViewById(R.id.tvHourly);
            tv24 = (TextView) itemView.findViewById(R.id.tv24);
            tv_seven_day = (TextView) itemView.findViewById(R.id.tv_seven_day);
            ivFavourite = (ImageView) itemView.findViewById(R.id.ivFavourite);
        }
    }

    @NonNull
    @Override
    public MainAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainAdapter.MyViewHolder holder, final int position) {
        final CryptoList cryptoListItem = cryptoLists.get(position);

        holder.tvTitle.setText(cryptoListItem.getName());
        holder.tvPrice.setText(cryptoListItem.getPriceUsd());
        holder.tvMarket.setText(cryptoListItem.getMarketCapUsd());
        holder.tvVolume.setText(cryptoListItem.get24hVolumeUsd());
        holder.tvHourly.setText(cryptoListItem.getPercentChange1h());
        holder.tv24.setText(cryptoListItem.getPercentChange24h());
        holder.tv_seven_day.setText(cryptoListItem.getPercentChange7d());
        boolean isFavourite = cryptoListItem.getIsFavourite().equals("1");
        holder.ivFavourite.setImageResource(isFavourite ? android.R.drawable.star_big_on : android.R.drawable.star_big_off);
        holder.ivFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markFavourite(cryptoListItem);
                holder.ivFavourite.setImageResource(android.R.drawable.star_big_on );
                Log.d("adapter", "clicked position = " + position);
                Toast.makeText(context, "Added to favorites!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void markFavourite(CryptoList cryptoList) {

        KryptoAccessLayer accessLayer = new KryptoAccessLayer(context);
        try {
            accessLayer.open();
            accessLayer.markFavourite(cryptoList);
            accessLayer.close();
        } catch (Exception xe) {
            xe.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return cryptoLists.size();
    }
}


