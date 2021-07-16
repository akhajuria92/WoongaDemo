package com.android.woonga.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.woonga.R;
import com.android.woonga.response.YoutubeTrendingResponse;
import com.android.woonga.views.activities.YoutubePlayerActivity;
import com.android.woonga.views.activities.YoutubeTrendingActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterYoutube extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    LayoutInflater inflater;
    List<Object> recyclerViewItems;

    // A menu item view type.
    private static final int MENU_ITEM_VIEW_TYPE = 0;

    // The banner ad view type.
    private static final int BANNER_AD_VIEW_TYPE = 1;

    public AdapterYoutube(Context context, List<Object> recyclerViewItems) {
        this.context = context;
        this.recyclerViewItems = recyclerViewItems;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case MENU_ITEM_VIEW_TYPE:
                View v = inflater.inflate(R.layout.adapter_youtube, parent, false);
                AdapterYoutube.RecyclerViewHolder viewHolder = new AdapterYoutube.RecyclerViewHolder(v);
                return viewHolder;
            case BANNER_AD_VIEW_TYPE:
                // fall through
            default:
                View bannerLayoutView = inflater.inflate(R.layout.banner_ad_container,
                        parent, false);
                return new AdapterYoutube.AdViewHolder(bannerLayoutView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case MENU_ITEM_VIEW_TYPE:
                RecyclerViewHolder holder = (RecyclerViewHolder) viewHolder;
                YoutubeTrendingResponse.Item video = (YoutubeTrendingResponse.Item) recyclerViewItems.get(position);
                if (video.getSnippet().getThumbnails().getMaxres() != null) {
                    Glide.with(context).asBitmap().load(video.getSnippet().getThumbnails().getMaxres().getUrl()).addListener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            //holder.play_button.setVisibility(View.VISIBLE);
                            return false;
                        }
                    }).into(holder.iv);
                } else if (video.getSnippet().getThumbnails().getHigh() != null) {
                    Glide.with(context).asBitmap().load(video.getSnippet().getThumbnails().getHigh().getUrl()).addListener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            //  holder.play_button.setVisibility(View.VISIBLE);
                            return false;
                        }
                    }).into(holder.iv);
                }

             /*   holder.rl_iv.setVisibility(View.GONE);
                holder.play_button.setVisibility(View.GONE);
                holder.youtube_player_view.setVisibility(View.VISIBLE);

                holder.youtube_player_view.initialize(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(YouTubePlayer youTubePlayer) {
                        youTubePlayer.loadVideo(video.getId(), 0);
                    }
                });*/

               /* holder.play_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.rl_iv.setVisibility(View.GONE);
                        holder.play_button.setVisibility(View.GONE);
                        holder.youtube_player_view.setVisibility(View.VISIBLE);

                        holder.youtube_player_view.initialize(new AbstractYouTubePlayerListener() {
                            @Override
                            public void onReady(YouTubePlayer youTubePlayer) {
                                youTubePlayer.loadVideo(video.getId(), 0);
                                youTubePlayer.pause();
                            }
                        });
                    }
                });*/

                if (video.getSnippet().getTitle() != null) {
                    holder.title.setText(video.getSnippet().getTitle());
                }

                if (video.getSnippet().getChannelTitle() != null) {
                    holder.channel_name.setText(video.getSnippet().getChannelTitle());
                }

                if (video.getContentDetails().getDuration() != null) {
                    try {
                        String result = video.getContentDetails().getDuration().replace("PT", "").replace("H", ":").replace("M", ":").replace("S", "");
                        String arr[] = result.split(":");
                        String timeString = "";
                        if (arr.length == 1) {
                            timeString = String.format("%d:%02d:%02d", 0, 0, Integer.parseInt(arr[0]));

                        } else if (arr.length == 2) {
                            timeString = String.format("%d:%02d", Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));

                        } else if (arr.length == 3) {
                            timeString = String.format("%d:%02d:%02d", Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));

                        }
                        holder.duration.setText(timeString);
                    } catch (NumberFormatException e) {

                    }

                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.startActivity(new Intent(context, YoutubePlayerActivity.class).putExtra("videoId", video.getId()));
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
        return (position % YoutubeTrendingActivity.ITEMS_PER_AD == 0) ? BANNER_AD_VIEW_TYPE
                : MENU_ITEM_VIEW_TYPE;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv)
        ImageView iv;

       /* @BindView(R.id.channel_logo)
        ImageView channel_logo;*/

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.channel_name)
        TextView channel_name;

        @BindView(R.id.duration)
        TextView duration;

        @BindView(R.id.progressBar)
        ProgressBar progressBar;

        @BindView(R.id.play_button)
        ImageView play_button;

        @BindView(R.id.youtube_player_view)
        YouTubePlayerView youtube_player_view;

        @BindView(R.id.rl_top)
        RelativeLayout rl_top;

        @BindView(R.id.rl_iv)
        RelativeLayout rl_iv;

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