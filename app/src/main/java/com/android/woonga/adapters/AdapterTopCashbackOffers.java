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

public class AdapterTopCashbackOffers extends RecyclerView.Adapter<AdapterTopCashbackOffers.RecyclerViewHolder> {

    Context context;
    LayoutInflater inflater;
    List<DashboardOffersResponse.Offers> topOfferList;


    public AdapterTopCashbackOffers(Context context, List<DashboardOffersResponse.Offers> topOfferList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.topOfferList = topOfferList;
    }

    @Override
    public AdapterTopCashbackOffers.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.adapter_cashback_offers, parent, false);
        AdapterTopCashbackOffers.RecyclerViewHolder viewHolder = new AdapterTopCashbackOffers.RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterTopCashbackOffers.RecyclerViewHolder holder, int position) {
        if (topOfferList.get(position).getIcon() != null) {
            Glide.with(context).load(topOfferList.get(position).getIcon()).into(holder.iv);
        }
        if (topOfferList.get(position).getTitle() != null) {
            holder.title.setText(topOfferList.get(position).getTitle());
        }
        if (topOfferList.get(position).getPayout() != null) {
            holder.payOut.setVisibility(View.VISIBLE);
            holder.payOut.setText("Get ₹ " + topOfferList.get(position).getPayout());

        } else {
            holder.payOut.setVisibility(View.VISIBLE);
            holder.payOut.setText("Get " + topOfferList.get(position).getPercentage() + " %");
            holder.tv_bottom.setText("Install and get " + topOfferList.get(position).getPercentage() + "% " + topOfferList.get(position).getMessage());

        }
        if (topOfferList.get(position).getShortDescription() != null) {
            holder.tv_bottom.setText(topOfferList.get(position).getShortDescription());
        }

      /*  if (topOfferList.get(position).getDisplayImage() != null) {
            Glide.with(context)
                    .asBitmap()
                    .load(topOfferList.get(position).getDisplayImage())
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // onCashBackOfferClicked(v);
                context.startActivity(new Intent(context, OfferDetailActivity.class).putExtra("offerData", topOfferList.get(position)).putExtra("fromCashback", true));
            }
        });
    }

   /* public void onCashBackOfferClicked(View view) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("category", "cashback_offer");
        AdGyde.onCountingEvent("cashback_offer", params);
    }*/

    @Override
    public int getItemCount() {
        return topOfferList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv)
        CircleImageView iv;
        /*  @BindView(R.id.icon)
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