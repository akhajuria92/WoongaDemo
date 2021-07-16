package com.android.woonga.views.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;
import com.adcolony.sdk.AdColonyZone;
import com.android.woonga.R;
import com.android.woonga.adapters.AdapterCategoryOffers;
import com.android.woonga.interfaces.OnAdCardClick;
import com.android.woonga.response.AddVideoPointsResponse;
import com.android.woonga.response.CategoryOffersResponse;
import com.android.woonga.response.DashboardOffersResponse;
import com.android.woonga.utils.Constant;
import com.android.woonga.utils.ProgressDialog;
import com.android.woonga.utils.Utility;
import com.android.woonga.views.activities.OfferDetailActivity;
import com.android.woonga.webapi.APICallBack;
import com.android.woonga.webapi.RestClient;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;
import com.vungle.warren.AdConfig;
import com.vungle.warren.LoadAdCallback;
import com.vungle.warren.PlayAdCallback;
import com.vungle.warren.Vungle;
import com.vungle.warren.error.VungleException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentOffers extends Fragment implements OnAdCardClick, IUnityAdsListener {

    @BindView(R.id.offersRecycler)
    RecyclerView offersRecycler;

    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

    @BindView(R.id.rl_no_data)
    RelativeLayout rl_no_data;

    private AdapterCategoryOffers adapterCategoryOffers;
    private List<DashboardOffersResponse.Offers> offersList;
    private int categoryId, uniqueUser = 0;
    private AdColonyInterstitial adColonyAdd;
    private AdColonyInterstitialListener adColonyInterstitialListener;
    private Boolean testMode = true;
    private String unityPlacementId = "video";
    int offerId = 0;
    String payout = "";

    public static FragmentOffers newInstance() {
        return new FragmentOffers();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers, container, false);
        ButterKnife.bind(this, view);

        UnityAds.initialize(getActivity(), Constant.UNITY_GAME_ID, this, testMode);

        if (getArguments() != null) {
            categoryId = getArguments().getInt("categoryId");
        }
        setAdapter();
        getOffers(categoryId + "", true);

        processVungleAd();


        return view;
    }


    private void setAdapter() {
        offersRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        offersList = new ArrayList<>();
       // adapterCategoryOffers = new AdapterCategoryOffers(getActivity(), offersList, uniqueUser, categoryId, this);
        offersRecycler.setAdapter(adapterCategoryOffers);
    }

    @Override
    public void onResume() {
        super.onResume();
        getOffers(categoryId + "", false);
    }

    private void getOffers(String categoryId, boolean showProgress) {
        if (Utility.getInstance().isNetworkAvailable(getActivity())) {
            if (showProgress) {
                ProgressDialog.showDialog(getActivity());
            }
            HashMap hashMap = new HashMap();
            hashMap.put("security_token", Constant.getInstance().getSecurityToken());
            hashMap.put("category_id", categoryId);
            hashMap.put("state", Constant.getInstance().getCurrentState());
            hashMap.put("city", Constant.getInstance().getCurrentCity());

            RestClient.getInstance().getBaseUrl().getOffers(hashMap).enqueue(new APICallBack<CategoryOffersResponse>() {
                @Override
                protected void success(CategoryOffersResponse response) {
                    if (getActivity() != null) {
                        ProgressDialog.dismissDialog(getActivity());
                    }
                    if (response != null) {
                        if (response.getSuccess().equals(Constant.FLAG_TRUE)) {
                            if (response.getOffers() != null) {
                                if (response.getOffers().size() > 0) {
                                    offersRecycler.setVisibility(View.VISIBLE);
                                    rl_no_data.setVisibility(View.GONE);
                                    offersList.clear();
                                    offersList.addAll(response.getOffers());
                                    adapterCategoryOffers.notifyDataSetChanged();
                                } else {
                                    offersRecycler.setVisibility(View.GONE);
                                    rl_no_data.setVisibility(View.VISIBLE);
                                }

                            }

                        } else if (response.getSuccess().equals(10)) {
                            if (getActivity() != null) {
                                Utility.getInstance().logout(getActivity());
                            }
                        } else {
                            Utility.getInstance().showSnackBar(mainLayout, response.getMessage());
                        }
                    }
                }

                @Override
                protected void failure(String errorMsg) {
                    if (getActivity() != null) {
                        ProgressDialog.dismissDialog(getActivity());
                    }
                }

                @Override
                protected void onFailure(String failureReason) {
                    if (getActivity() != null) {
                        ProgressDialog.dismissDialog(getActivity());
                    }
                }
            });
        } else {
            Utility.getInstance().showSnackBar(mainLayout, "Please check your Internet Connection");
        }
    }

    @OnClick(R.id.btn_earn)
    void earn() {
        getOffers("10", true);
    }

    @Override
    public void onAdColonyClick(int offerId, String payout) {
        this.offerId = offerId;
        this.payout = payout;
        processAdColonyRequestToViewAdd();
    }

    @Override
    public void onVungleClick(int offerId, String payout) {
        this.offerId = offerId;
        this.payout = payout;
        loadVungleAd();
    }

    @Override
    public void onUnityClick(int offerId, String payout) {
        this.offerId = offerId;
        this.payout = payout;
        playUnityAd();
    }


    private void processAdColonyRequestToViewAdd() {
        adColonyInterstitialListener = new AdColonyInterstitialListener() {
            @Override
            public void onRequestFilled(AdColonyInterstitial ad) {
                // Ad passed back in request filled callback, ad can now be shown
                FragmentOffers.this.adColonyAdd = ad;
                if (getActivity() != null) {
                    ProgressDialog.dismissDialog(getActivity());
                }
                adColonyAdd.show();
            }

            @Override
            public void onRequestNotFilled(AdColonyZone zone) {
                // Ad request was not filled
                if (getActivity() != null) {
                    ProgressDialog.dismissDialog(getActivity());
                }

                Utility.getInstance().showSnackBar(mainLayout, "Ad not available yet!");
            }

            @Override
            public void onOpened(AdColonyInterstitial ad) {
                // Ad opened, reset UI to reflect state change
                if (getActivity() != null) {
                    ProgressDialog.dismissDialog(getActivity());
                }
            }

            @Override
            public void onClosed(AdColonyInterstitial ad) {
                if (ad.isExpired()) {
                    addVideoPoints(offerId + "", payout);
                }
            }


            @Override
            public void onExpiring(AdColonyInterstitial ad) {
                // Request a new ad if ad is expiring
                Log.e("unityfinish", placementId);
            }
        };


        if (adColonyAdd == null || adColonyAdd.isExpired()) {
            if (getActivity() != null) {
                ProgressDialog.showDialog(getActivity());
            }
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
        if (getActivity() != null) {
            ProgressDialog.showDialog(getActivity());
        }
        Vungle.loadAd(placementId, new LoadAdCallback() {
            @Override
            public void onAdLoad(String id) {
                playVungleAd();
            }

            @Override
            public void onError(String id, VungleException e) {
                if (getActivity() != null) {
                    ProgressDialog.dismissDialog(getActivity());
                }
                Utility.getInstance().showSnackBar(mainLayout, e.getLocalizedMessage());
                Log.e("AdLoadError", e.getLocalizedMessage());
            }
        });
    }

    private void playVungleAd() {
        Vungle.playAd(placementId, new AdConfig(), new PlayAdCallback() {
            @Override
            public void onAdStart(String id) {
                //
                if (getActivity() != null) {
                    ProgressDialog.dismissDialog(getActivity());
                }
            }

            @Override
            public void onAdEnd(String id, boolean completed, boolean isCTAClicked) {
                if (completed) {
                    addVideoPoints(offerId + "", payout);
                }
            }

            @Override
            public void onError(String id, VungleException e) {

            }
        });
    }

    private void playUnityAd() {
        if (UnityAds.isReady(unityPlacementId)) {
            UnityAds.show(getActivity(), unityPlacementId);
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
            addVideoPoints(offerId + "", payout);
        }
    }

    @Override
    public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {
        Log.e("unityerror", message);
    }


    private void addVideoPoints(String offerId, String payout) {
        if (Utility.getInstance().isNetworkAvailable(getActivity())) {
            HashMap hashMap = new HashMap();
            hashMap.put("security_token", Constant.getInstance().getSecurityToken());
            hashMap.put("payout", payout);
            hashMap.put("offer_id", offerId);

            RestClient.getInstance().getBaseUrl().addVideoPoints(hashMap).enqueue(new APICallBack<AddVideoPointsResponse>() {
                @Override
                protected void success(AddVideoPointsResponse addVideoPointsResponse) {
                    if (addVideoPointsResponse != null) {
                        if (addVideoPointsResponse.getSuccess() == 1) {

                        }
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

}
