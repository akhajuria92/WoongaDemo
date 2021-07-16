package com.android.woonga.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;
import com.adcolony.sdk.AdColonyZone;
import com.android.woonga.R;
import com.android.woonga.WoongaApplication;
import com.android.woonga.adapters.AdapterCategoryOffers;
import com.android.woonga.interfaces.OnAdCardClick;
import com.android.woonga.response.AddVideoPointsResponse;
import com.android.woonga.response.CategoryOffersResponse;
import com.android.woonga.response.DashboardOffersResponse;
import com.android.woonga.response.VerifyOtpResponse;
import com.android.woonga.utils.Constant;
import com.android.woonga.utils.MessageEvent;
import com.android.woonga.utils.ProgressDialog;
import com.android.woonga.utils.Utility;
import com.android.woonga.views.fragments.FragmentOffers;
import com.android.woonga.webapi.APICallBack;
import com.android.woonga.webapi.RestClient;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;
import com.vungle.warren.AdConfig;
import com.vungle.warren.LoadAdCallback;
import com.vungle.warren.PlayAdCallback;
import com.vungle.warren.Vungle;
import com.vungle.warren.error.VungleException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OffersActivity extends AppCompatActivity implements OnAdCardClick, IUnityAdsListener {
    @BindView(R.id.offersRecycler)
    RecyclerView offersRecycler;

    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

    @BindView(R.id.rl_no_data)
    RelativeLayout rl_no_data;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.frame)
    FrameLayout frame;

    @BindView(R.id.priceOut)
    TextView priceOut;

    @BindView(R.id.ad_view_container)
    FrameLayout adContainerView;


    private AdapterCategoryOffers adapterCategoryOffers;
    private List<DashboardOffersResponse.Offers> offersList;
    public int categoryId, uniqueUser = 0;
    private AdColonyInterstitial adColonyAdd;
    private AdColonyInterstitialListener adColonyInterstitialListener;
    private Boolean testMode = true;
    private String unityPlacementId = "video";
    int offerId = 0;
    String payout = "";
    String title;
    VerifyOtpResponse.Data userData;
    boolean pointsAdded = false;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        ButterKnife.bind(this);

        UnityAds.initialize(this, Constant.UNITY_GAME_ID, this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            categoryId = bundle.getInt("categoryId");
            title = bundle.getString("title");
        }

        if (title != null) {
            tv_title.setText(title);
        }
        userData = new Gson().fromJson(WoongaApplication.getAppPreferences().getString(Constant.USER_DATA), VerifyOtpResponse.Data.class);
        frame.setVisibility(View.VISIBLE);
        if (userData.getApprovedPoints() != null) {
            priceOut.setText("₹ " + userData.getApprovedPoints());
        }

        setAdapter();
        getOffers(categoryId + "", true);

        processVungleAd();

      /*  adContainerView.post(new Runnable() {
            @Override
            public void run() {
                loadBanner();
            }
        });*/
    }


    private void setAdapter() {
        offersRecycler.setLayoutManager(new LinearLayoutManager(this));
        offersList = new ArrayList<>();
//        adapterCategoryOffers = new AdapterCategoryOffers(this, offersList, uniqueUser, categoryId, this);
//        offersRecycler.setAdapter(adapterCategoryOffers);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
        getOffers(categoryId + "", false);
    }

    private void getOffers(String categoryId, boolean showProgress) {
        if (Utility.getInstance().isNetworkAvailable(this)) {
            if (showProgress) {
                ProgressDialog.showDialog(this);
            }
            HashMap hashMap = new HashMap();
            hashMap.put("security_token", Constant.getInstance().getSecurityToken());
            hashMap.put("category_id", categoryId);
            hashMap.put("state", Constant.getInstance().getCurrentState());
            hashMap.put("city", Constant.getInstance().getCurrentCity());

            RestClient.getInstance().getBaseUrl().getOffers(hashMap).enqueue(new APICallBack<CategoryOffersResponse>() {
                @Override
                protected void success(CategoryOffersResponse response) {
                    ProgressDialog.dismissDialog(OffersActivity.this);
                    if (response != null) {
                        if (response.getSuccess().equals(Constant.FLAG_TRUE)) {
                            if (response.getOffers() != null) {
                                if (response.getOffers().size() > 0) {
                                    offersRecycler.setVisibility(View.VISIBLE);
                                    rl_no_data.setVisibility(View.GONE);
                                    offersList.clear();
                                    recyclerViewItems.clear();
                                    offersList.addAll(response.getOffers());
                                    recyclerViewItems.addAll(offersList);
                                    addBannerAds();
                                    loadBannerAds();

                                    adapterCategoryOffers = new AdapterCategoryOffers(OffersActivity.this, recyclerViewItems, uniqueUser, OffersActivity.this.categoryId, OffersActivity.this);
                                    offersRecycler.setAdapter(adapterCategoryOffers);
                                   // adapterCategoryOffers.notifyDataSetChanged();
                                } else {
                                    offersRecycler.setVisibility(View.GONE);
                                    rl_no_data.setVisibility(View.VISIBLE);
                                }

                            }

                        } else if (response.getSuccess().equals(10)) {
                            Utility.getInstance().logout(OffersActivity.this);
                        } else {
                            Utility.getInstance().showSnackBar(mainLayout, response.getMessage());
                        }
                    }
                }

                @Override
                protected void failure(String errorMsg) {
                    ProgressDialog.dismissDialog(OffersActivity.this);
                }

                @Override
                protected void onFailure(String failureReason) {
                    ProgressDialog.dismissDialog(OffersActivity.this);
                }
            });
        } else {
            Utility.getInstance().showSnackBar(mainLayout, "Please check your Internet Connection");
        }
    }

    @OnClick(R.id.btn_earn)
    void earn() {
        tv_title.setText("Cashback Offers");
        getOffers("10", true);
    }

    @Override
    public void onAdColonyClick(int offerId, String payout) {
        this.offerId = offerId;
        this.payout = payout;
        this.pointsAdded = false;
        processAdColonyRequestToViewAdd();
    }

    @Override
    public void onVungleClick(int offerId, String payout) {
        this.offerId = offerId;
        this.payout = payout;
        this.pointsAdded = false;
        loadVungleAd();
    }

    @Override
    public void onUnityClick(int offerId, String payout) {
        this.offerId = offerId;
        this.payout = payout;
        this.pointsAdded = false;
        playUnityAd();
    }


    private void processAdColonyRequestToViewAdd() {
        adColonyInterstitialListener = new AdColonyInterstitialListener() {
            @Override
            public void onRequestFilled(AdColonyInterstitial ad) {
                // Ad passed back in request filled callback, ad can now be shown
                OffersActivity.this.adColonyAdd = ad;
                ProgressDialog.dismissDialog(OffersActivity.this);
                adColonyAdd.show();
            }

            @Override
            public void onRequestNotFilled(AdColonyZone zone) {
                // Ad request was not filled
                ProgressDialog.dismissDialog(OffersActivity.this);

                Utility.getInstance().showSnackBar(mainLayout, "Ad not available yet!");
            }

            @Override
            public void onOpened(AdColonyInterstitial ad) {
                // Ad opened, reset UI to reflect state change
                ProgressDialog.dismissDialog(OffersActivity.this);
            }

            @Override
            public void onClosed(AdColonyInterstitial ad) {
                if (ad.isExpired()) {
                    if (!pointsAdded) {
                        pointsAdded = true;
                        addVideoPoints(offerId + "", payout);
                    }
                }
            }


            @Override
            public void onExpiring(AdColonyInterstitial ad) {
                // Request a new ad if ad is expiring
                Log.e("unityfinish", placementId);
            }
        };


        if (adColonyAdd == null || adColonyAdd.isExpired()) {
            ProgressDialog.showDialog(this);
            AdColony.requestInterstitial(Constant.AD_COLONY_ZONE_ID_1, adColonyInterstitialListener);
        }
    }

    String placementId;

    private void processVungleAd() {
        Collection<String> placements = Vungle.getValidPlacements();
        String[] placementsArray = placements.toArray(new String[0]);
        if (placementsArray != null) {
            for (int i = 0; i < placementsArray.length; i++) {
                if (placementsArray[i].contains("INTERSTITIAL")) {
                    placementId = placementsArray[i];
                }
            }
        }


    }

    private void loadVungleAd() {
        ProgressDialog.showDialog(this);
        Vungle.loadAd(placementId, new LoadAdCallback() {
            @Override
            public void onAdLoad(String id) {
                playVungleAd();
            }

            @Override
            public void onError(String id, VungleException e) {
                ProgressDialog.dismissDialog(OffersActivity.this);
                Utility.getInstance().showSnackBar(mainLayout, e.getLocalizedMessage());
                Log.e("AdLoadError", e.getLocalizedMessage());
            }
        });
    }

    private void playVungleAd() {
        Vungle.playAd(placementId, new AdConfig(), new PlayAdCallback() {
            @Override
            public void onAdStart(String id) {
                ProgressDialog.dismissDialog(OffersActivity.this);
            }

            @Override
            public void onAdEnd(String id, boolean completed, boolean isCTAClicked) {
                if (completed) {
                    if (!pointsAdded) {
                        pointsAdded = true;
                        addVideoPoints(offerId + "", payout);
                    }
                }
            }

            @Override
            public void onError(String id, VungleException e) {

            }
        });
    }

    private void playUnityAd() {
        if (UnityAds.isReady(unityPlacementId)) {
            UnityAds.show(this, unityPlacementId);
        }
    }

    @Override
    public void onUnityAdsReady(String placementId) {
        Log.e("unity", placementId);

    }


    @Override
    public void onUnityAdsStart(String placementId) {

    }

    @Override
    public void onUnityAdsFinish(String placementId, UnityAds.FinishState result) {
        if (result.name().equalsIgnoreCase("COMPLETED")) {
            if (!pointsAdded) {
                pointsAdded = true;
                addVideoPoints(offerId + "", payout);
            }
        }
    }

    @Override
    public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {
        Log.e("unityerror", message);
    }


    private void addVideoPoints(String offerId, String payout) {
        if (Utility.getInstance().isNetworkAvailable(this)) {
            HashMap hashMap = new HashMap();
            hashMap.put("security_token", Constant.getInstance().getSecurityToken());
            hashMap.put("payout", payout);
            hashMap.put("offer_id", offerId);

            RestClient.getInstance().getBaseUrl().addVideoPoints(hashMap).enqueue(new APICallBack<AddVideoPointsResponse>() {
                @Override
                protected void success(AddVideoPointsResponse addVideoPointsResponse) {
                    if (addVideoPointsResponse != null) {

                    }
                }

                @Override
                protected void failure(String errorMsg) {

                }

                @Override
                protected void onFailure(String failureReason) {

                }
            });
        }
    }

    @OnClick(R.id.iv_back)
    void back() {
        onBackPressed();
    }


    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adView != null) {
            adView.destroy();
        }
        ProgressDialog.dismissDialog(this);
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MessageEvent event) {

        if (event.message.contains("UserDataUpdated")) {
            EventBus.getDefault().removeStickyEvent(event);

            userData = new Gson().fromJson(WoongaApplication.getAppPreferences().getString(Constant.USER_DATA), VerifyOtpResponse.Data.class);
            frame.setVisibility(View.VISIBLE);
            if (userData.getApprovedPoints() != null) {
                priceOut.setText("₹ " + userData.getApprovedPoints());
            }
        }
    }

    @OnClick(R.id.frame)
    void frame() {
        startActivity(new Intent(this, HistoryActivity.class));
    }

    /**
     * Called when leaving the activity
     */
    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
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
        for (int i = 0; i <= recyclerViewItems.size(); i += ITEMS_PER_AD) {
            final AdView adView = new AdView(OffersActivity.this);
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
