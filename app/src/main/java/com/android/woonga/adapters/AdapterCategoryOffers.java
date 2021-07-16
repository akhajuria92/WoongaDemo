package com.android.woonga.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.woonga.R;
import com.android.woonga.interfaces.OnAdCardClick;
import com.android.woonga.response.DashboardOffersResponse;
import com.android.woonga.views.activities.OfferDetailActivity;
import com.android.woonga.views.activities.OffersActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.ads.AdView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterCategoryOffers extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    LayoutInflater inflater;
    List<DashboardOffersResponse.Offers> offersList;
    List<Object> recyclerViewItems;
    OnAdCardClick onAdCardClick;
    int uniqueUser;
    int categoryId;

    // A menu item view type.
    private static final int MENU_ITEM_VIEW_TYPE = 0;

    // The banner ad view type.
    private static final int BANNER_AD_VIEW_TYPE = 1;


    public AdapterCategoryOffers(Context context, List<Object> recyclerViewItems, int uniqueUser, int categoryId, OnAdCardClick onAdCardClick) {
        this.context = context;
        this.recyclerViewItems = recyclerViewItems;
        this.uniqueUser = uniqueUser;
        this.categoryId = categoryId;
        this.onAdCardClick = onAdCardClick;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case MENU_ITEM_VIEW_TYPE:
                View v = inflater.inflate(R.layout.adapter_cashback_offers, parent, false);
                AdapterCategoryOffers.RecyclerViewHolder viewHolder = new AdapterCategoryOffers.RecyclerViewHolder(v);
                return viewHolder;
            case BANNER_AD_VIEW_TYPE:
                // fall through
            default:
                View bannerLayoutView = inflater.inflate(R.layout.banner_ad_container,
                        parent, false);
                return new AdViewHolder(bannerLayoutView);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case MENU_ITEM_VIEW_TYPE:
                RecyclerViewHolder holder = (RecyclerViewHolder) viewHolder;
                DashboardOffersResponse.Offers offers = (DashboardOffersResponse.Offers) recyclerViewItems.get(position);
                if (offers.getIcon() != null) {
                    Glide.with(context).load(offers.getIcon()).into(holder.iv);
                } else {
                    Glide.with(context).load(R.mipmap.ic_logo_round).into(holder.iv);
                }
                /*if (offers.getDisplayImage() != null) {
                    Glide.with(context)
                            .asBitmap()
                            .load(offers.getDisplayImage())
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
                if (offers.getTitle() != null) {
                    holder.title.setText(offers.getTitle());
                }
                if (offers.getPayout() != null) {
                    holder.payOut.setVisibility(View.VISIBLE);
                    holder.payOut.setText("Get ₹ " + offers.getPayout());
                } else {
                    holder.payOut.setVisibility(View.VISIBLE);
                    holder.payOut.setText("Get " + offers.getPercentage() + " %");
                    holder.tv_bottom.setText("Install and get " + offers.getPercentage() + "% " + offers.getMessage());

                }

                if (offers.getShortDescription() != null) {
                    holder.tv_bottom.setText(offers.getShortDescription());
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (offers.getVideoCategories() != null) {
                            if (offers.getVideoCategories().equalsIgnoreCase("Adcolony")) {
                                onAdCardClick.onAdColonyClick(offers.getId(), offers.getPayout());
                            } else if (offers.getVideoCategories().equalsIgnoreCase("Unity")) {
//                        context.startActivity(new Intent(context, YoutubeTrendingActivity.class));
                                onAdCardClick.onUnityClick(offers.getId(), offers.getPayout());
                            } else if (offers.getVideoCategories().equalsIgnoreCase("Vungle")) {
                                onAdCardClick.onVungleClick(offers.getId(), offers.getPayout());
                            }
                        } else {
                            context.startActivity(new Intent(context, OfferDetailActivity.class).
                                    putExtra("offerData", offers).putExtra("uniqueUser", uniqueUser).
                                    putExtra("fromCategories", true)
                                    .putExtra("categoryId", categoryId));
                        }
                    }
                });

                break;
            case BANNER_AD_VIEW_TYPE:
                // fall through

            default:
                AdViewHolder bannerHolder = (AdViewHolder) viewHolder;
                AdView adView = (AdView) recyclerViewItems.get(position);
                RelativeLayout adCardView = (RelativeLayout) bannerHolder.ad_card_view;
                ViewGroup viewGroup = (ViewGroup) bannerHolder.itemView;
                viewGroup.setVisibility(View.GONE);
                viewGroup.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                /*if (position == 0) {
                    viewGroup.setVisibility(View.GONE);
                    viewGroup.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                } else {
                    viewGroup.setVisibility(View.VISIBLE);
                    int height = context.getResources().getDimensionPixelSize(R.dimen.ad_height);
                    int width = context.getResources().getDimensionPixelSize(R.dimen.ad_width);
                    // int margin = context.getResources().getDimensionPixelSize(R.dimen._20sdp);
                    RelativeLayout.LayoutParams feedCommentParams = new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


                    // feedCommentParams.setMargins(margin, 0, margin, 0);
                    viewGroup.setLayoutParams(feedCommentParams);
                    // The AdViewHolder recycled by the RecyclerView may be a different
                    // instance than the one used previously for this position. Clear the
                    // AdViewHolder of any subviews in case it has a different
                    // AdView associated with it, and make sure the AdView for this position doesn't
                    // already have a parent of a different recycled AdViewHolder.
                    if (adCardView.getChildCount() > 0) {
                        adCardView.removeAllViews();
                    }
                    if (adView.getParent() != null) {
                        ((RelativeLayout) adView.getParent()).removeView(adView);
                    }

                    // Add the banner ad to the ad view.
                    adCardView.addView(adView);
                }*/

        }

    }

    @Override
    public int getItemCount() {
        return recyclerViewItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position % OffersActivity.ITEMS_PER_AD == 0) ? BANNER_AD_VIEW_TYPE
                : MENU_ITEM_VIEW_TYPE;
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

    public class AdViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ad_card_view)
        RelativeLayout ad_card_view;

        AdViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}