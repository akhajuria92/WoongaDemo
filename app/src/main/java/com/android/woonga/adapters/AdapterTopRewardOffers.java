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

public class AdapterTopRewardOffers extends RecyclerView.Adapter<AdapterTopRewardOffers.RecyclerViewHolder> {

    Context context;
    LayoutInflater inflater;
    List<DashboardOffersResponse.Offers> topRewardsOfferList;


    public AdapterTopRewardOffers(Context context, List<DashboardOffersResponse.Offers> topRewardsOfferList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.topRewardsOfferList = topRewardsOfferList;
    }

    @Override
    public AdapterTopRewardOffers.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.adapter_cashback_offers, parent, false);
        AdapterTopRewardOffers.RecyclerViewHolder viewHolder = new AdapterTopRewardOffers.RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterTopRewardOffers.RecyclerViewHolder holder, int position) {

        if (topRewardsOfferList.get(position).getIcon() != null) {
            Glide.with(context).load(topRewardsOfferList.get(position).getIcon()).into(holder.iv);
        }
        if (topRewardsOfferList.get(position).getTitle() != null) {
            holder.title.setText(topRewardsOfferList.get(position).getTitle());
        }
        if (topRewardsOfferList.get(position).getPayout() != null) {
            holder.payOut.setText("Get ₹ " + topRewardsOfferList.get(position).getPayout());
        }
        if (topRewardsOfferList.get(position).getShortDescription() != null) {
            holder.tv_bottom.setText(topRewardsOfferList.get(position).getShortDescription());
        }
      /*  if (topRewardsOfferList.get(position).getDisplayImage() != null) {
            Glide.with(context)
                    .asBitmap()
                    .load(topRewardsOfferList.get(position).getDisplayImage())
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
               // onRewardOfferClicked(v);
                context.startActivity(new Intent(context, OfferDetailActivity.class).putExtra("offerData", topRewardsOfferList.get(position)).putExtra("fromRewards", true));
            }
        });
    }

  /*  public void onRewardOfferClicked(View view) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("category", "reward_offer");
        AdGyde.onCountingEvent("reward_offer", params);
    }*/

    @Override
    public int getItemCount() {
        return topRewardsOfferList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv)
        CircleImageView iv;
        /*   @BindView(R.id.icon)
           RoundedImageView icon;*/
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.priceOut)
        TextView payOut;
        @BindView(R.id.tv_bottom)
        TextView tv_bottom;
      /*  @BindView(R.id.progressBar)
        ProgressBar progressBar;*/

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}