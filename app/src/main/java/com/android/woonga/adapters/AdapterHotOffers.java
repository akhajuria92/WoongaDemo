package com.android.woonga.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.android.woonga.R;
import com.android.woonga.response.DashboardOffersResponse;
import com.android.woonga.views.activities.OfferDetailActivity;
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterHotOffers extends RecyclerView.Adapter<AdapterHotOffers.RecyclerViewHolder> {

    Context context;
    LayoutInflater inflater;
    List<DashboardOffersResponse.Offers> offersList;
    int uniqueUser;
    int categoryId;


    public AdapterHotOffers(Context context, List<DashboardOffersResponse.Offers> offersList, int uniqueUser, int categoryId) {
        this.context = context;
        this.offersList = offersList;
        this.uniqueUser = uniqueUser;
        this.categoryId = categoryId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public AdapterHotOffers.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.adapter_cashback_offers, parent, false);
        AdapterHotOffers.RecyclerViewHolder viewHolder = new AdapterHotOffers.RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterHotOffers.RecyclerViewHolder holder, int position) {
        if (offersList.get(position).getIcon() != null) {
            Glide.with(context).load(offersList.get(position).getIcon()).into(holder.iv);
        } else {
            Glide.with(context).load(R.mipmap.ic_logo_round).into(holder.iv);
        }
       /* if (offersList.get(position).getDisplayImage() != null) {
            Glide.with(context)
                    .asBitmap()
                    .load(offersList.get(position).getDisplayImage())
                    .addListener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(holder.iv);
        }*/
        if (offersList.get(position).getTitle() != null) {
            holder.title.setText(offersList.get(position).getTitle());
        }
        if (offersList.get(position).getPayout() != null) {
            holder.payOut.setVisibility(View.VISIBLE);
            holder.payOut.setText("Get ₹ " + offersList.get(position).getPayout());
        } else {
            holder.payOut.setVisibility(View.VISIBLE);
            holder.payOut.setText("Get " + offersList.get(position).getPercentage() + " %");
            holder.tv_bottom.setText("Install and get " + offersList.get(position).getPercentage() + "% " + offersList.get(position).getMessage());

        }

        if (offersList.get(position).getShortDescription() != null) {
            holder.tv_bottom.setText(offersList.get(position).getShortDescription());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // onHotOfferClicked(v);
                context.startActivity(new Intent(context, OfferDetailActivity.class).
                        putExtra("offerData", offersList.get(position)).putExtra("uniqueUser", uniqueUser).
                        putExtra("fromCategories", true)
                        .putExtra("categoryId", categoryId));
            }
        });
    }

   /* public void onHotOfferClicked(View view) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("category", "hot_offer");
        AdGyde.onCountingEvent("hot_offer", params);
    }*/

    @Override
    public int getItemCount() {
        return offersList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv)
        CircleImageView iv;
        /* @BindView(R.id.icon)
         RoundedImageView icon;*/
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.priceOut)
        TextView payOut;
        @BindView(R.id.tv_bottom)
        TextView tv_bottom;
       /* @BindView(R.id.progressBar)
        ProgressBar progressBar;*/

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}