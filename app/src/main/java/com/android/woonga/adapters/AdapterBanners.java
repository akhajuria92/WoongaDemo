package com.android.woonga.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.android.woonga.R;
import com.android.woonga.response.DashboardBannersResponse;
import com.android.woonga.views.activities.OfferDetailActivity;
import com.android.woonga.views.activities.ReferralActivity;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterBanners extends PagerAdapter {

    Context context;
    private LayoutInflater layoutInflater;
    List<DashboardBannersResponse.TopBanner> topBannerList;


    public AdapterBanners(Context context, List<DashboardBannersResponse.TopBanner> topBannerList) {
        this.context = context;
        this.topBannerList = topBannerList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View view = layoutInflater.inflate(R.layout.adapter_banner, container, false);
        final ImageView imageView = (RoundedImageView) view.findViewById(R.id.iv);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        final ImageView refreshIcon = (ImageView) view.findViewById(R.id.refresh);

        if (topBannerList.get(position).getImage() != null) {
            if (topBannerList.get(position).getImage().equalsIgnoreCase("")) {
                imageView.setVisibility(View.GONE);
            } else {
               /* Glide.with(context)
                        .load(topBannerList.get(position).getImage())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .addListener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                refreshIcon.setVisibility(View.VISIBLE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                refreshIcon.setVisibility(View.GONE);
                                imageView.setImageDrawable(resource);
                                return false;
                            }
                        })
                        .into(imageView);*/

                Picasso.get().load(
                        topBannerList.get(position).getImage()
                ).into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                        refreshIcon.setVisibility(View.GONE);
                        //imageView.setImageDrawable(resource);
                    }

                    @Override
                    public void onError(Exception e) {
                        progressBar.setVisibility(View.GONE);
                        refreshIcon.setVisibility(View.VISIBLE);
                    }
                });
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // onTopBannerClicked(v);
                        if (topBannerList.get(position).getIsReferal() == 1) {
                            context.startActivity(new Intent(context, ReferralActivity.class));
                        } else {
                            context.startActivity(new Intent(context, OfferDetailActivity.class).putExtra("topBannerData", topBannerList.get(position)));
                        }
                    }
                });

                refreshIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        refreshIcon.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
/*                        Glide.with(context)
                                .load(topBannerList.get(position).getImage())
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .addListener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        progressBar.setVisibility(View.GONE);
                                        refreshIcon.setVisibility(View.VISIBLE);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        progressBar.setVisibility(View.GONE);
                                        refreshIcon.setVisibility(View.GONE);
                                        imageView.setImageDrawable(resource);
                                        return false;
                                    }
                                })
                                .into(imageView);*/

                        Picasso.get().load(
                                topBannerList.get(position).getImage()
                        ).into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                progressBar.setVisibility(View.GONE);
                                refreshIcon.setVisibility(View.GONE);
                                //imageView.setImageDrawable(resource);
                            }

                            @Override
                            public void onError(Exception e) {
                                progressBar.setVisibility(View.GONE);
                                refreshIcon.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });
                container.addView(view);

            }
        } else {
            imageView.setVisibility(View.GONE);
        }


        return view;
    }

    /*public void onTopBannerClicked(View view) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("category", "top_banner");
        AdGyde.onCountingEvent("top_banner", params);
    }*/

    @Override
    public int getCount() {
        return topBannerList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == ((RelativeLayout) obj);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }


}