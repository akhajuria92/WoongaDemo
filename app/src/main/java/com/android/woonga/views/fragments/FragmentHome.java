package com.android.woonga.views.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.android.woonga.BuildConfig;
import com.android.woonga.R;
import com.android.woonga.WoongaApplication;
import com.android.woonga.adapters.AdapterBanners;
import com.android.woonga.adapters.AdapterCategoriesHome;
import com.android.woonga.adapters.AdapterTopCashbackOffers;
import com.android.woonga.adapters.AdapterTopRewardOffers;
import com.android.woonga.response.CategoriesResponse;
import com.android.woonga.response.CheckAppVersionResponse;
import com.android.woonga.response.DashboardBannersResponse;
import com.android.woonga.response.DashboardOffersResponse;
import com.android.woonga.response.ReferralDetailsResponse;
import com.android.woonga.response.SuccessBean;
import com.android.woonga.utils.Constant;
import com.android.woonga.utils.MessageEvent;
import com.android.woonga.utils.ProgressDialog;
import com.android.woonga.utils.Utility;
import com.android.woonga.views.activities.OfferDetailActivity;
import com.android.woonga.views.activities.OffersActivity;
import com.android.woonga.views.activities.ReferralActivity;
import com.android.woonga.views.activities.YoutubeTrendingActivity;
import com.android.woonga.webapi.APICallBack;
import com.android.woonga.webapi.RestClient;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class FragmentHome extends Fragment implements LocationListener {

    @BindView(R.id.bannerRecycler)
    ViewPager bannerRecycler;

    @BindView(R.id.topCashbackRecycler)
    RecyclerView topCashbackRecycler;

    @BindView(R.id.topRewardsRecycler)
    RecyclerView topRewardsRecycler;

    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

    @BindView(R.id.earnDefaultBanner)
    RelativeLayout earnDefaultBanner;

    @BindView(R.id.rl_categories)
    RelativeLayout rl_categories;

    @BindView(R.id.earnImage)
    RoundedImageView earnImage;

    @BindView(R.id.image1)
    RoundedImageView image1;

    @BindView(R.id.image2)
    RoundedImageView image2;

    @BindView(R.id.image3)
    RoundedImageView image3;

    @BindView(R.id.image4)
    RoundedImageView image4;

    @BindView(R.id.cardRewardOffers)
    CardView cardRewardOffers;

    @BindView(R.id.iv_reward)
    ImageView iv_reward;

    @BindView(R.id.tv_reward)
    TextView tv_reward;

    @BindView(R.id.cardCashbackOffers)
    CardView cardCashbackOffers;

    @BindView(R.id.iv_cashback)
    ImageView iv_cashback;

    @BindView(R.id.tv_cashback)
    TextView tv_cashback;

    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout pullToRefresh;

    @BindView(R.id.layoutDots)
    TabLayout layoutDots;

    @BindView(R.id.background_view)
    View background_view;

    @BindView(R.id.categoriesListView)
    RecyclerView categoriesListView;

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;


    private AdapterTopCashbackOffers adapterTopCashbackOffers;
    AdapterTopRewardOffers adapterTopRewardOffers;
    DashboardBannersResponse dashboardBannersResponse;
    private AdapterBanners adapterBanners;
    AdapterCategoriesHome adapterCategoriesHome;
    private List<DashboardBannersResponse.TopBanner> topBannerList;
    List<DashboardOffersResponse.Offers> topRewardsOfferList;
    List<DashboardOffersResponse.Offers> topOfferList;
    List<CategoriesResponse.Category> categoryList;

    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 1000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        setUi();
        ProgressDialog.showDialog(getActivity());
        if (!checkPermissions()) {
            requestPermission();
        } else {
            final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                buildAlertMessageNoGps();
            } else {
                WoongaApplication.getAppPreferences().putString(Constant.CURRENT_STATE, "Punjab");
                WoongaApplication.getAppPreferences().putString(Constant.CURRENT_CITY, "Chandigarh");
                getDashboardBanners(true);
                getDashboardOffers();
                getCurrentAddress();
                getReferralDetails();
            }
        }
        return view;
    }


    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
        builder.setMessage("Your GPS seems to be disabled, Please enable it to access the app")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                        getActivity().finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    private void permissionCompulsoryAlert() {
        AlertDialog.Builder builderAlert;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builderAlert = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builderAlert = new AlertDialog.Builder(getActivity());
        }
        builderAlert.setMessage("You need to allow permission to access the app").setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
                requestPermission();
                dialog.dismiss();


            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getActivity().finish();
            }
        }).setCancelable(false).show();
    }


    private void requestPermission() {
        Dexter.withActivity(getActivity()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                //  mRequestingLocationUpdates = true;

                getCurrentAddress();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                if (response.isPermanentlyDenied()) {
                    // open device settings when the permission is
                    // denied permanently
                    Utility.getInstance().openSettings(getActivity());
                } else {
                    permissionCompulsoryAlert();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }

        }).check();
    }


    LocationManager locationManager;
    Location location;

    public void getCurrentAddress() {
        // Get the location manager
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (locationManager != null) {

            try {

                if (Build.VERSION.SDK_INT >= 23 && getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            } catch (Exception ex) {
                Log.i("msg", "fail to request location update, ignore", ex);
            }
            if (locationManager != null) {
                location = locationManager
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            Geocoder gcd = new Geocoder(getActivity().getBaseContext(),
                    Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(location.getLatitude(),
                        location.getLongitude(), 1);
                if (addresses.size() > 0) {
                    String state = addresses.get(0).getAdminArea() != null ? addresses.get(0).getAdminArea() : "punjab";
                    String city = addresses.get(0).getLocality() != null ? addresses.get(0).getLocality() : "Chandigarh";
                    Log.e("CurrentLocation>>>", state);
                    WoongaApplication.getAppPreferences().putString(Constant.CURRENT_STATE, state);
                    WoongaApplication.getAppPreferences().putString(Constant.CURRENT_CITY, city);
                    getDashboardBanners(false);
                    getDashboardOffers();
                    getReferralDetails();
                } else {
                    WoongaApplication.getAppPreferences().putString(Constant.CURRENT_STATE, "Punjab");
                    WoongaApplication.getAppPreferences().putString(Constant.CURRENT_CITY, "Chandigarh");
                    getDashboardBanners(false);
                    getDashboardOffers();
                    getReferralDetails();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            if (getActivity() != null) {
                Geocoder gcd = new Geocoder(getActivity().getBaseContext(),
                        Locale.getDefault());
                List<Address> addresses;
                try {
                    addresses = gcd.getFromLocation(location.getLatitude(),
                            location.getLongitude(), 1);
                    if (addresses.size() > 0) {
                        String state = addresses.get(0).getAdminArea() != null ? addresses.get(0).getAdminArea() : "punjab";
                        String city = addresses.get(0).getLocality() != null ? addresses.get(0).getLocality() : "Chandigarh";
                        Log.e("LocationUpdate>>>", state);
                        WoongaApplication.getAppPreferences().putString(Constant.CURRENT_STATE, state);
                        WoongaApplication.getAppPreferences().putString(Constant.CURRENT_CITY, city);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!Constant.getInstance().getCurrentState().equalsIgnoreCase("")) {
            getDashboardBanners(false);
            getDashboardOffers();
            EventBus.getDefault().postSticky(new MessageEvent("NeedToRefreshHistory"));
        }
        checkAppVersion();
    }

    private void checkAppVersion() {
        if (Utility.getInstance().isNetworkAvailable(getActivity())) {
            String version = BuildConfig.VERSION_NAME;
            RestClient.getInstance().getBaseUrl().checkAppVersion().enqueue(new APICallBack<CheckAppVersionResponse>() {
                @Override
                protected void success(CheckAppVersionResponse checkAppVersionResponse) {
                    if (checkAppVersionResponse != null) {
                        if (!checkAppVersionResponse.getVersionDetails().getVersion().equalsIgnoreCase(version)) {
                            showUpdateDialog();
                        } else {
                            WoongaApplication.getAppPreferences().putBoolean("isLaterClicked", false);
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

    private void setUi() {
        topOfferList = new ArrayList<>();
        topCashbackRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterTopCashbackOffers = new AdapterTopCashbackOffers(getActivity(), topOfferList);
        topCashbackRecycler.setAdapter(adapterTopCashbackOffers);
        topCashbackRecycler.setNestedScrollingEnabled(false);

        topRewardsOfferList = new ArrayList<>();
        topRewardsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterTopRewardOffers = new AdapterTopRewardOffers(getActivity(), topRewardsOfferList);
        topRewardsRecycler.setAdapter(adapterTopRewardOffers);
        topRewardsRecycler.setNestedScrollingEnabled(false);

        categoryList = new ArrayList<>();
        categoriesListView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        adapterCategoriesHome = new AdapterCategoriesHome(getActivity(), categoryList);
        categoriesListView.setAdapter(adapterCategoriesHome);
        categoriesListView.setNestedScrollingEnabled(false);

        topBannerList = new ArrayList<>();
        adapterBanners = new AdapterBanners(getActivity(), topBannerList);
        layoutDots.setupWithViewPager(bannerRecycler);
        bannerRecycler.setAdapter(adapterBanners);
/*        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == topBannerList.size()) {
                    currentPage = 0;
                }
                bannerRecycler.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);*/

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDashboardBanners(true);
                getDashboardOffers();
            }
        });
    }

    private void getDashboardBanners(boolean showProgress) {
        if (Utility.getInstance().isNetworkAvailable(getActivity())) {
            if (showProgress) {
                ProgressDialog.showDialog(getActivity());
            }

            HashMap hashMap = new HashMap();
            hashMap.put("security_token", Constant.getInstance().getSecurityToken());
            hashMap.put("state", Constant.getInstance().getCurrentState());
            hashMap.put("city", Constant.getInstance().getCurrentCity());
            RestClient.getInstance().getBaseUrl().getDashboardBanners(hashMap).enqueue(new APICallBack<DashboardBannersResponse>() {
                @Override
                protected void success(DashboardBannersResponse response) {
                    if (getActivity() != null) {
                        ProgressDialog.dismissDialog(getActivity());
                    }
                    if (response != null) {
                        dashboardBannersResponse = response;
                        if (response.getSuccess().equals(Constant.FLAG_TRUE)) {
                            topBannerList.clear();
                            topBannerList.addAll(response.getTopBanners());
                            adapterBanners.notifyDataSetChanged();
                            setCollage();

                            if (response.getPromotionalBanner() != null) {
                                if (response.getPromotionalBanner().getIcon() != null) {
                                    earnImage.setVisibility(View.VISIBLE);
                                    if (getActivity() != null) {
                                        Glide.with(getActivity()).load(response.getPromotionalBanner().getIcon()).into(earnImage);
                                    }
                                    return;
                                }
                            }
                            earnImage.setVisibility(View.GONE);

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

                }

                @Override
                protected void onFailure(String failureReason) {
                }
            });
        } else {
            pullToRefresh.setRefreshing(false);
            Utility.getInstance().showSnackBar(mainLayout, "Please check your Internet Connection");
        }

    }


    private void getDashboardOffers() {
        if (Utility.getInstance().isNetworkAvailable(getActivity())) {

            HashMap hashMap = new HashMap();
            hashMap.put("security_token", Constant.getInstance().getSecurityToken());
            hashMap.put("state", Constant.getInstance().getCurrentState());
            hashMap.put("city", Constant.getInstance().getCurrentCity());
            RestClient.getInstance().getBaseUrl().getDashboardOffers(hashMap).enqueue(new APICallBack<DashboardOffersResponse>() {
                @Override
                protected void success(DashboardOffersResponse response) {
                    if (response != null) {
                        if (response.getSuccess().equals(Constant.FLAG_TRUE)) {
                            coordinatorLayout.setVisibility(View.VISIBLE);
                            topOfferList.clear();
                            topRewardsOfferList.clear();
                            topOfferList.addAll(response.getTopOffers());
                            topRewardsOfferList.addAll(response.getTopRewardsOffers());
                            adapterTopCashbackOffers.notifyDataSetChanged();
                            adapterTopRewardOffers.notifyDataSetChanged();
                            if (tv_cashback.getCurrentTextColor() == Color.WHITE) {
                                topCashbackRecycler.setVisibility(View.VISIBLE);
                                topRewardsRecycler.setVisibility(View.GONE);
                            } else {
                                topRewardsRecycler.setVisibility(View.VISIBLE);
                                topCashbackRecycler.setVisibility(View.GONE);
                            }
                            pullToRefresh.setRefreshing(false);

                            if (response.getBgColor() != null && !response.getBgColor().equalsIgnoreCase("")) {
                                background_view.setBackgroundColor(Color.parseColor(response.getBgColor()));
                            } else {
                                background_view.setBackgroundColor(Color.WHITE);
                            }

                            if (response.getData() != null) {
                                WoongaApplication.getAppPreferences().putString(Constant.USER_DATA, new Gson().toJson(response.getData()));
                                EventBus.getDefault().postSticky(new MessageEvent("UserDataUpdated"));

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
            pullToRefresh.setRefreshing(false);
            Utility.getInstance().showSnackBar(mainLayout, "Please check your Internet Connection");
        }
    }


    private void setCollage() {
        if (getActivity() != null) {
            if (dashboardBannersResponse != null) {
                if (dashboardBannersResponse.getBottomBanners() != null) {
                    for (int i = 0; i < dashboardBannersResponse.getBottomBanners().size(); i++) {
                        if (dashboardBannersResponse.getBottomBanners().get(i).getImagePosition() != null) {
                            if (dashboardBannersResponse.getBottomBanners().get(i).getImagePosition().equalsIgnoreCase("1")) {
                                if (dashboardBannersResponse.getBottomBanners().get(i).getImage() != null) {
                                    Glide.with(getActivity()).load(dashboardBannersResponse.getBottomBanners().get(i).getImage()).into(image1);
                                }
                                setClickOnImage(dashboardBannersResponse.getBottomBanners().get(i), image1);
                            }
                            if (dashboardBannersResponse.getBottomBanners().get(i).getImagePosition().equalsIgnoreCase("2")) {
                                if (dashboardBannersResponse.getBottomBanners().get(i).getImage() != null) {
                                    Glide.with(getActivity()).load(dashboardBannersResponse.getBottomBanners().get(i).getImage()).into(image2);
                                }
                                setClickOnImage(dashboardBannersResponse.getBottomBanners().get(i), image2);
                            }
                            if (dashboardBannersResponse.getBottomBanners().get(i).getImagePosition().equalsIgnoreCase("3")) {
                                if (dashboardBannersResponse.getBottomBanners().get(i).getImage() != null) {
                                    Glide.with(getActivity()).load(dashboardBannersResponse.getBottomBanners().get(i).getImage()).into(image3);
                                }
                                setClickOnImage(dashboardBannersResponse.getBottomBanners().get(i), image3);
                            }
                            if (dashboardBannersResponse.getBottomBanners().get(i).getImagePosition().equalsIgnoreCase("4")) {
                                if (dashboardBannersResponse.getBottomBanners().get(i).getImage() != null) {
                                    Glide.with(getActivity()).load(dashboardBannersResponse.getBottomBanners().get(i).getImage()).into(image4);
                                }
                                setClickOnImage(dashboardBannersResponse.getBottomBanners().get(i), image4);
                            }
                        }
                    }
                }
            }
        }
    }

    private void setClickOnImage(DashboardBannersResponse.BottomBanner bottomBanner, ImageView imageView) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // onBottomBannerClicked(v);
                if (bottomBanner.getIsReferal() == 1) {
                    startActivity(new Intent(getActivity(), ReferralActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), OfferDetailActivity.class).putExtra("bottomBannerData", bottomBanner));
                }
            }
        });
    }

   /* public void onBottomBannerClicked(View view) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("category", "bottom_banner");
        AdGyde.onCountingEvent("selected_category", params);
    }*/

    @OnClick(R.id.earnImage)
    void setEarnImage() {
        if (dashboardBannersResponse != null) {
            if (dashboardBannersResponse.getPromotionalBanner() != null) {
                if (dashboardBannersResponse.getPromotionalBanner().getTrackingLink() != null) {
                    String url = dashboardBannersResponse.getPromotionalBanner().getTrackingLink();

                    if (url.contains("{log_id}")) {
                        url.replace("{log_id}", Constant.getInstance().getUserId());
                    }

                    if (url.contains("{google_id}")) {
                        url.replace("{google_id}", Constant.getInstance().getGoogleId());
                    }

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(dashboardBannersResponse.getPromotionalBanner().getTrackingLink()));
                    startActivity(browserIntent);
                    return;
                }
            }
        }

        Utility.getInstance().showSnackBar(mainLayout, "No link found!");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.cardCashbackOffers)
    void setCardCashbackOffers() {
        cardCashbackOffers.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        iv_cashback.setImageTintList(ColorStateList.valueOf(Color.WHITE));
        tv_cashback.setTextColor(ColorStateList.valueOf(Color.WHITE));

        cardRewardOffers.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        iv_reward.setImageTintList(ColorStateList.valueOf(Color.BLACK));
        tv_reward.setTextColor(ColorStateList.valueOf(Color.BLACK));

        topCashbackRecycler.setVisibility(View.VISIBLE);
        topRewardsRecycler.setVisibility(View.GONE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.cardRewardOffers)
    void setCardRewardOffers() {
        cardRewardOffers.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        iv_reward.setImageTintList(ColorStateList.valueOf(Color.WHITE));
        tv_reward.setTextColor(ColorStateList.valueOf(Color.WHITE));

        cardCashbackOffers.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        iv_cashback.setImageTintList(ColorStateList.valueOf(Color.BLACK));
        tv_cashback.setTextColor(ColorStateList.valueOf(Color.BLACK));

        topCashbackRecycler.setVisibility(View.GONE);
        topRewardsRecycler.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.rl_earnBanner)
    void rl_earnBanner() {
        startActivity(new Intent(getActivity(), OffersActivity.class).putExtra("categoryId", 2)
                .putExtra("title", "Reward Offers"));
       /* FragmentOffers fragmentOffers = FragmentOffers.newInstance();
        FragmentTransaction fts = getActivity().getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("categoryId", 2);
        fragmentOffers.setArguments(bundle);
        fts.replace(R.id.fragmentHolder, fragmentOffers);
        fts.addToBackStack(fragmentOffers.getClass().getSimpleName());
        fts.commit();*/
    }

    @OnClick(R.id.btn_viewAll)
    void viewAll() {
        startActivity(new Intent(getActivity(), OffersActivity.class).putExtra("categoryId", 2)
                .putExtra("title", "Reward Offers"));
    }


    private void showUpdateDialog() {
        final Dialog dialog;
        dialog = new Dialog(getActivity(), android.R.style.Theme_Material_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_version);
        dialog.setCancelable(false);

        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_update_version, null);

        Button updateBtn = layout.findViewById(R.id.btn_update);
        Button laterBtn = layout.findViewById(R.id.btn_later);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String appPackageName = getActivity().getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                } finally {
                    getActivity().finishAffinity();
                }
                dialog.dismiss();
            }
        });

        laterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WoongaApplication.getAppPreferences().putBoolean("isLaterClicked", true);
                WoongaApplication.getAppPreferences().putLong("dialogLaunchTime", System.currentTimeMillis() + DateUtils.DAY_IN_MILLIS);
                dialog.dismiss();
            }
        });

        dialog.setContentView(layout);
        if (WoongaApplication.getAppPreferences().getBoolean("isLaterClicked")) {
            if (System.currentTimeMillis() > WoongaApplication.getAppPreferences().getLong("dialogLaunchTime")) {
                dialog.show();
            }
        } else {
            dialog.show();
        }
    }

    private void showWelcomeDialog(String amount) {
        final Dialog dialog;
        dialog = new Dialog(getActivity(), android.R.style.Theme_Material_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_welcome);
        dialog.setCancelable(true);

        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_welcome, null);

        TextView tv = layout.findViewById(R.id.tv);
        TextView amount_tv = layout.findViewById(R.id.amount);
        Button btnOkay = layout.findViewById(R.id.btn_okay);

        amount_tv.setText("₹ " + amount);
        tv.setText("has been credited in your approved points as a joining bonus.\nHappy earnings!");

        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(layout);
        dialog.show();

    }

    private void submitReview(String review) {
        if (Utility.getInstance().isNetworkAvailable(getActivity())) {
            ProgressDialog.showDialog(getActivity());

            HashMap hashMap = new HashMap();
            hashMap.put("description", review);
            hashMap.put("security_token", Constant.getInstance().getSecurityToken());

            RestClient.getInstance().getBaseUrl().submitReview(hashMap).enqueue(new APICallBack<SuccessBean>() {
                @Override
                protected void success(SuccessBean response) {
                    if (getActivity() != null) {
                        ProgressDialog.dismissDialog(getActivity());
                    }
                    if (response != null) {
                        if (response.getSuccess().equals(Constant.FLAG_TRUE)) {
                            Utility.getInstance().showSnackBar(mainLayout, response.getMessage());
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

    private void showRatingDialog() {
        final Dialog dialog;
        dialog = new Dialog(getActivity(), android.R.style.Theme_Material_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_rating);
        dialog.setCancelable(true);

        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_rating, null);

        EditText edtReview = layout.findViewById(R.id.edt_review);
        Button btnSubmit = layout.findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtReview.getText().toString().equalsIgnoreCase("")) {
                    submitReview(edtReview.getText().toString().trim());
                    dialog.dismiss();
                } else {
                    Utility.getInstance().showToast(getActivity(), "Please enter your review.");
                }
            }
        });

        dialog.setContentView(layout);
        dialog.show();

    }

    @OnClick({R.id.ll_rating1, R.id.ll_rating2, R.id.ll_rating3})
    void rating() {
        showRatingDialog();
    }

    @OnClick({R.id.ll_rating4, R.id.ll_rating5})
    void directToPlayStore() {
        Uri uri = Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)));
        }
    }

    int newUserAmount = 0;

    private void getReferralDetails() {
        if (Utility.getInstance().isNetworkAvailable(getActivity())) {
            RestClient.getInstance().getBaseUrl().getReferralDetails().enqueue(new APICallBack<ReferralDetailsResponse>() {
                @Override
                protected void success(ReferralDetailsResponse response) {
                    if (response != null) {
                        if (response.getSuccess().equals(Constant.FLAG_TRUE)) {
                            if (response.getData() != null) {
                                if (response.getData().getAmountWhoRefer() != null) {
                                    WoongaApplication.getAppPreferences().putInt(Constant.AMOUNT_WHO_REFER, (int) Float.parseFloat(response.getData().getAmountWhoRefer()));
                                }
                                if (response.getData().getAmountNewUser() != null) {
                                    newUserAmount = (int) Float.parseFloat(response.getData().getAmountNewUser());
                                    if (newUserAmount > 0) {
                                        if (Constant.getInstance().getFirstTimeUser() == 1) {
                                            Map<String, String> params = new HashMap<String, String>();
                                            params.put("category", "new_registration");
                                           // AdGyde.onCountingEvent("new_registration", params);
                                            showWelcomeDialog(newUserAmount + "");
                                            WoongaApplication.getAppPreferences().putInt(Constant.FIRST_TIME, 0);
                                        }
                                    }
                                }
                            }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(List<CategoriesResponse.Category> categoryList) {
        this.categoryList.clear();
        this.categoryList.addAll(categoryList);
        CategoriesResponse.Category game = new CategoriesResponse.Category();
        game.setCategoryId(0);
        game.setName("WOONGA GAMES");
        this.categoryList.add(game);
        CategoriesResponse.Category youTubeCategory = new CategoriesResponse.Category();
        youTubeCategory.setCategoryId(14);
        youTubeCategory.setName("YOUTUBE TRENDING");
        youTubeCategory.setIcon("https://woonga.s3.ap-south-1.amazonaws.com/CategoriesIcon/5ea68015dc36c-1587970069.png");
        this.categoryList.add(youTubeCategory);
        rl_categories.setVisibility(View.VISIBLE);
        adapterCategoriesHome.notifyDataSetChanged();
    }
}
