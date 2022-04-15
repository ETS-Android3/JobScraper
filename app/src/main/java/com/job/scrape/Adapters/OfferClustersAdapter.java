package com.job.scrape.Adapters;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.job.scrape.Models.Cluster;
import com.job.scrape.Models.Offer;
import com.job.scrape.R;

import java.util.List;

public class OfferClustersAdapter extends RecyclerView.Adapter<OfferClustersAdapter.MyViewHolder> {

    private List<Cluster> clusterList;
    private OfferClustersClickListener clickListener;

    public OfferClustersAdapter(List<Cluster> clusterList, OfferClustersClickListener clickListener) {
        this.clusterList = clusterList;
        this.clickListener = clickListener;
    }

    public void updateData(List<Cluster> clusterList) {
        this.clusterList = clusterList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OfferClustersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.cluster_recycler_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferClustersAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.clusterNum.setText("Cluster number : "+clusterList.get(position).getClusterNum());
        holder.offerClusterCount.setText("Offers count : "+clusterList.get(position).getOfferList().size());
        holder.viewOffersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cluster cluster = clusterList.get(position);
                clickListener.onClusterBtnClick(cluster);
            }
        });
        Glide.with(holder.thumbImage)
                .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTnf7qOzDg4o3h9vgnkbIHM5jVSCFGK9WMiTQ&usqp=CAU")
                .into(holder.thumbImage);
    }

    @Override
    public int getItemCount() {
        return clusterList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView clusterNum;
        TextView offerClusterCount;
        TextView viewOffersBtn;
        ImageView thumbImage;

        public MyViewHolder(View view) {
            super(view);
            clusterNum = view.findViewById(R.id.clusterNum);
            offerClusterCount = view.findViewById(R.id.offerClusterCount);
            viewOffersBtn = view.findViewById(R.id.viewOffersBtn);
            thumbImage = view.findViewById(R.id.thumbImage);
        }
    }

    public interface OfferClustersClickListener {
        public void onClusterBtnClick(Cluster cluster);
    }
}