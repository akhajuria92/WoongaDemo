package com.android.woonga.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.woonga.R;
import com.android.woonga.WoongaApplication;
import com.android.woonga.adapters.AdapterYoutube;
import com.android.woonga.response.VerifyOtpResponse;
import com.android.woonga.response.YoutubeTrendingResponse;
import com.android.woonga.utils.Constant;
import com.android.woonga.utils.ProgressDialog;
import com.android.woonga.webapi.APICallBack;
import com.android.woonga.webapi.RestClient;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class YoutubeTrendingActivity extends AppCompatActivity {
    @BindView(R.id.youtubeListView)
    RecyclerView youtubeListView;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.priceOut)
    TextView priceOut;

    @BindView(R.id.frame)
    FrameLayout frame;

    @BindView(R.id.ad_view_container)
    FrameLayout adContainerView;

    AdapterYoutube adapterYoutube;
    VerifyOtpResponse.Data userData;
    private AdView adView;
    List<YoutubeTrendingResponse.Item> videosList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_trending);
        ButterKnife.bind(this);
        tv_title.setText("Trending Videos");
        userData = new Gson().fromJson(WoongaApplication.getAppPreferences().getString(Constant.USER_DATA), VerifyOtpResponse.Data.class);
        frame.setVisibility(View.VISIBLE);
        if (userData.getApprovedPoints() != null) {
            priceOut.setText("₹ " + userData.getApprovedPoints());
        }
        setAdapter();
        getYoutubeVideos();

       /* adContainerView.post(new Runnable() {
            @Override
            public void run() {
                loadBanner();
            }
        });*/
    }


    @OnClick(R.id.frame)
    void frame() {
        startActivity(new Intent(this, HistoryActivity.class));
    }

    private void setAdapter() {
        youtubeListView.setLayoutManager(new LinearLayoutManager(this));

    }


    private void getYoutubeVideos() {
        ProgressDialog.showDialog(this);
        RestClient.getInstance().getYoutubeBaseUrl().getTrendingVideos("snippet,contentDetails", "mostPopular", "IN",
                "25", "AIzaSyCjTvzU8NBIW9Lw9x410pA8WVZhf_BPw4o").enqueue(new APICallBack<YoutubeTrendingResponse>() {
            @Override
            protected void success(YoutubeTrendingResponse model) {
                ProgressDialog.dismissDialog(YoutubeTrendingActivity.this);
                videosList.clear();
                recyclerViewItems.clear();
                videosList.addAll(model.getItems());
                recyclerViewItems.addAll(videosList);
                addBannerAds();
                loadBannerAds();

                adapterYoutube = new AdapterYoutube(YoutubeTrendingActivity.this, recyclerViewItems);
                youtubeListView.setAdapter(adapterYoutube);
            }

            @Override
            protected void failure(String errorMsg) {
                ProgressDialog.dismissDialog(YoutubeTrendingActivity.this);
            }

            @Override
            protected void onFailure(String failureReason) {
                ProgressDialog.dismissDialog(YoutubeTrendingActivity.this);
            }
        });
    }

    @OnClick(R.id.iv_back)
    void back() {
        onBackPressed();
    }


    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adView != null) {
            adView.destroy();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }


    private void loadBanner() {
        // Create an ad request. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        adView = new AdView(this);
        adView.setAdUnitId(Constant.AD_UNIT_ID_ADMOBS);
        adContainerView.removeAllViews();
        adContainerView.addView(adView);

        AdSize adSize = getAdSize();
        adView.setAdSize(adSize);

        AdRequest adRequest = new AdRequest.Builder().build();
        // Start loading the ad in the background.
        adView.loadAd(adRequest);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.e("AdLoaded", "AdLoaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

                Log.e("AdFailed", "AdFailed");// Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        });
    }

    private AdSize getAdSize() {
        // Determine the screen width (less decorations) to use for the ad width.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = outMetrics.density;

        float adWidthPixels = adContainerView.getWidth();

        // If the ad hasn't been laid out, default to the full screen width.
        if (adWidthPixels == 0) {
            adWidthPixels = outMetrics.widthPixels;
        }

        int adWidth = (int) (adWidthPixels / density);

        return AdSize.getCurrentOrientationBannerAdSizeWithWidth(this, adWidth);
    }

    List<Object> recyclerViewItems = new ArrayList<>();
    public static final int ITEMS_PER_AD = 4;

    private void addBannerAds() {
        // Loop through the items array and place a new banner ad in every ith position in
        // the items List.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = outMetrics.density;

        float adWidthPixels = adContainerView.getWidth();

        // If the ad hasn't been laid out, default to the full screen width.
        if (adWidthPixels == 0) {
            adWidthPixels = outMetrics.widthPixels;
        }

        int adWidth = (int) (adWidthPixels / density);

        for (int i = 0; i <= recyclerViewItems.size(); i += ITEMS_PER_AD) {
            final AdView adView = new AdView(YoutubeTrendingActivity.this);
            adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
            adView.setAdUnitId(Constant.AD_UNIT_ID_ADMOBS);
            recyclerViewItems.add(i, adView);
        }
    }

    private void loadBannerAds() {
        // Load the first banner ad in the items list (subsequent ads will be loaded automatically
        // in sequence).
        loadBannerAd(0);
    }

    /**
     * Loads the banner ads in the items list.
     */
    private void loadBannerAd(final int index) {

        if (index >= recyclerViewItems.size()) {
            return;
        }

        Object item = recyclerViewItems.get(index);
        if (!(item instanceof AdView)) {
            throw new ClassCastException("Expected item at index " + index + " to be a banner ad"
                    + " ad.");
        }

        final AdView adView = (AdView) item;

        // Set an AdListener on the AdView to wait for the previous banner ad
        // to finish loading before loading the next ad in the items list.
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                // The previous banner ad loaded successfully, call this method again to
                // load the next ad in the items list.
                loadBannerAd(index + ITEMS_PER_AD);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // The previous banner ad failed to load. Call this method again to load
                // the next ad in the items list.
                Log.e("MainActivity", "The previous banner ad failed to load. Attempting to"
                        + " load the next banner ad in the items list.");
                loadBannerAd(index + ITEMS_PER_AD);
            }
        });

        // Load the banner ad.
        adView.loadAd(new AdRequest.Builder().build());
    }
}
