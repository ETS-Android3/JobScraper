package com.job.scrape.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.job.scrape.R;
//import com.bumptech.glide.Glide;
import com.job.scrape.Models.Offer;

import java.util.List;

public class OfferListAdapter extends RecyclerView.Adapter<OfferListAdapter.MyViewHolder> {

    private List<Offer> offerModelList;
    private OfferListClickListener clickListener;

    public OfferListAdapter(List<Offer> offerModelList, OfferListClickListener clickListener) {
        this.offerModelList = offerModelList;
        this.clickListener = clickListener;
    }

    public void updateData(List<Offer> offerModelList) {
        this.offerModelList = offerModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OfferListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.offerTitle.setText(offerModelList.get(position).getTitle());
        holder.offerCompanyName.setText("Company : "+offerModelList.get(position).getCompany().getName());
        holder.offerPostType.setText("Type : " + offerModelList.get(position).getPostType());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(offerModelList.get(position));
            }
        });
        Glide.with(holder.thumbImage)
                .load(offerModelList.get(position).getCompany().getImage())
                .into(holder.thumbImage);

    }

    @Override
    public int getItemCount() {
        return offerModelList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  offerTitle;
        TextView  offerCompanyName;
        TextView  offerPostType;
        ImageView thumbImage;

        public MyViewHolder(View view) {
            super(view);
            offerTitle = view.findViewById(R.id.offerTitle);
            offerCompanyName = view.findViewById(R.id.offerCompanyName);
            offerPostType = view.findViewById(R.id.offerPostType);
            thumbImage = view.findViewById(R.id.thumbImage);

        }
    }

    public interface OfferListClickListener {
        public void onItemClick(Offer offer);
    }
}

