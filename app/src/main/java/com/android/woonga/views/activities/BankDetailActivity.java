package com.android.woonga.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.woonga.BuildConfig;
import com.android.woonga.R;
import com.android.woonga.WoongaApplication;
import com.android.woonga.response.BankDetails;
import com.android.woonga.response.SuccessBean;
import com.android.woonga.response.VerifyOtpResponse;
import com.android.woonga.utils.Constant;
import com.android.woonga.utils.MessageEvent;
import com.android.woonga.utils.ProgressDialog;
import com.android.woonga.utils.Utility;
import com.android.woonga.webapi.APICallBack;
import com.android.woonga.webapi.RestClient;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BankDetailActivity extends AppCompatActivity {

    @BindView(R.id.edtBankName)
    EditText edtBankName;

    @BindView(R.id.edtAccountHolderName)
    EditText edtAccountHolderName;

    @BindView(R.id.edtIfsc)
    EditText edtIfsc;

    @BindView(R.id.edtAccountNumber)
    EditText edtAccountNumber;

    @BindView(R.id.edtBankAddress)
    EditText edtBankAddress;

    @BindView(R.id.edtupi)
    EditText edtupi;

    @BindView(R.id.edtpaytm)
    EditText edtpaytm;

    @BindView(R.id.ll_bank_save)
    LinearLayout ll_bank_save;

    @BindView(R.id.ll_upi_save)
    LinearLayout ll_upi_save;

    @BindView(R.id.ll_paytm_save)
    LinearLayout ll_paytm_save;

    @BindView(R.id.iv_bank_edit)
    ImageView iv_bank_edit;

    @BindView(R.id.iv_upi_edit)
    ImageView iv_upi_edit;

    @BindView(R.id.iv_paytm_edit)
    ImageView iv_paytm_edit;

    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.ad_view_container)
    FrameLayout adContainerView;

    @BindView(R.id.ad_view_container1)
    RelativeLayout ad_view_container1;


    List<VerifyOtpResponse.UserAccountDetail> userAccountDetail;
    private AdView adView;
    private AdView rectangularAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_detail);
        ButterKnife.bind(this);
        setUi();
      /*  adContainerView.post(new Runnable() {
            @Override
            public void run() {
                loadBanner();
            }
        });*/
        ad_view_container1.post(new Runnable() {
            @Override
            public void run() {
                loadRectangularBanner();
            }
        });

    }

    private void loadRectangularBanner() {
        rectangularAdView = new AdView(this);
        rectangularAdView.setAdUnitId(Constant.AD_UNIT_ID_ADMOBS);
        ad_view_container1.removeAllViews();
        ad_view_container1.addView(rectangularAdView);

        rectangularAdView.setAdSize(AdSize.MEDIUM_RECTANGLE);

        AdRequest adRequest = new AdRequest.Builder().build();
        // Start loading the ad in the background.
        rectangularAdView.loadAd(adRequest);

        rectangularAdView.setAdListener(new AdListener() {
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


    private void setUi() {
        tv_title.setText("Bank Details");
        userAccountDetail = new Gson().fromJson(WoongaApplication.getAppPreferences().getString(Constant.USER_DATA), VerifyOtpResponse.Data.class).getUserAccountDetails();

        if (userAccountDetail != null) {
            for (int i = 0; i < userAccountDetail.size(); i++) {
                if (userAccountDetail.get(i).getType() == 1) {
                    setBankDetails(userAccountDetail.get(i));
                } else if (userAccountDetail.get(i).getType() == 2) {
                    setUpiDetails(userAccountDetail.get(i));
                } else if (userAccountDetail.get(i).getType() == 3) {
                    setPaytmDetails(userAccountDetail.get(i));
                }
            }
        }


        unFocusBankDetails();
        unFocusUpiDetails();
        unFocusPaytmDetails();
    }

    private void setBankDetails(VerifyOtpResponse.UserAccountDetail userAccountDetail) {
        if (userAccountDetail.getDetails() != null) {
            BankDetails bankDetails = new Gson().fromJson(userAccountDetail.getDetails(), BankDetails.class);
            if (bankDetails != null) {
                if (bankDetails.getBankName() != null) {
                    edtBankName.setText(bankDetails.getBankName());
                }
                if (bankDetails.getAccountHolderName() != null) {
                    edtAccountHolderName.setText(bankDetails.getAccountHolderName());
                }
                if (bankDetails.getAccountNumber() != null) {
                    edtAccountNumber.setText(bankDetails.getAccountNumber());
                }
                if (bankDetails.getIFSCCode() != null) {
                    edtIfsc.setText(bankDetails.getIFSCCode());
                }
                if (bankDetails.getBankAddress() != null) {
                    edtBankAddress.setText(bankDetails.getBankAddress());
                }
            }

        }
    }

    private void setUpiDetails(VerifyOtpResponse.UserAccountDetail userAccountDetail) {
        if (userAccountDetail.getDetails() != null) {
            edtupi.setText(userAccountDetail.getDetails());
        }
    }

    private void setPaytmDetails(VerifyOtpResponse.UserAccountDetail userAccountDetail) {
        if (userAccountDetail.getDetails() != null) {
            edtpaytm.setText(userAccountDetail.getDetails());
        }
    }

    private void unFocusBankDetails() {
        edtAccountHolderName.setEnabled(false);
        edtAccountNumber.setEnabled(false);
        edtBankAddress.setEnabled(false);
        edtBankName.setEnabled(false);
        edtIfsc.setEnabled(false);
    }

    private void focusBankDetails() {
        edtAccountHolderName.setEnabled(true);
        edtAccountHolderName.setFocusableInTouchMode(true);
        edtAccountNumber.setEnabled(true);
        edtAccountNumber.setFocusableInTouchMode(true);
        edtBankAddress.setEnabled(true);
        edtBankAddress.setFocusableInTouchMode(true);
        edtBankName.setEnabled(true);
        edtBankName.setFocusableInTouchMode(true);
        edtIfsc.setEnabled(true);
        edtIfsc.setFocusableInTouchMode(true);
    }

    private void unFocusUpiDetails() {
        edtupi.setEnabled(false);
    }

    private void focusUpiDetails() {
        edtupi.setEnabled(true);
        edtupi.setFocusableInTouchMode(true);
    }

    private void unFocusPaytmDetails() {
        edtpaytm.setEnabled(false);
    }

    private void focusPaytmDetails() {
        edtpaytm.setEnabled(true);
        edtpaytm.setFocusableInTouchMode(true);
    }

    @OnClick(R.id.iv_paytm_edit)
    void iv_paytm_edit() {
        iv_paytm_edit.setVisibility(View.GONE);
        ll_paytm_save.setVisibility(View.VISIBLE);
        focusPaytmDetails();

        iv_upi_edit.setVisibility(View.VISIBLE);
        ll_upi_save.setVisibility(View.GONE);
        unFocusUpiDetails();

        iv_bank_edit.setVisibility(View.VISIBLE);
        ll_bank_save.setVisibility(View.GONE);
        focusPaytmDetails();
    }

    @OnClick(R.id.btn_paytm_cancel)
    void btn_paytm_cancel() {
        iv_paytm_edit.setVisibility(View.VISIBLE);
        ll_paytm_save.setVisibility(View.GONE);
        unFocusPaytmDetails();
    }

    @OnClick(R.id.iv_upi_edit)
    void iv_upi_edit() {
        iv_upi_edit.setVisibility(View.GONE);
        ll_upi_save.setVisibility(View.VISIBLE);
        focusUpiDetails();

        iv_paytm_edit.setVisibility(View.VISIBLE);
        ll_paytm_save.setVisibility(View.GONE);
        unFocusUpiDetails();

        iv_bank_edit.setVisibility(View.VISIBLE);
        ll_bank_save.setVisibility(View.GONE);
        focusUpiDetails();
    }

    @OnClick(R.id.btn_upi_cancel)
    void btn_upi_cancel() {
        iv_upi_edit.setVisibility(View.VISIBLE);
        ll_upi_save.setVisibility(View.GONE);
        unFocusUpiDetails();
    }

    @OnClick(R.id.iv_bank_edit)
    void bankEdit() {
        ll_bank_save.setVisibility(View.VISIBLE);
        iv_bank_edit.setVisibility(View.GONE);
        focusBankDetails();

        iv_upi_edit.setVisibility(View.VISIBLE);
        ll_upi_save.setVisibility(View.GONE);
        unFocusUpiDetails();

        iv_paytm_edit.setVisibility(View.VISIBLE);
        ll_paytm_save.setVisibility(View.GONE);
        focusBankDetails();
    }

    @OnClick(R.id.btn_bank_cancel)
    void btn_bank_cancel() {
        ll_bank_save.setVisibility(View.GONE);
        iv_bank_edit.setVisibility(View.VISIBLE);
        unFocusBankDetails();
    }

    @OnClick(R.id.btn_bank_save)
    void btn_bank_save() {
        if (edtBankName.getText().toString().equalsIgnoreCase("")) {
            Utility.getInstance().showSnackBar(mainLayout, "Please enter bank name");
        } else if (edtAccountHolderName.getText().toString().equalsIgnoreCase("")) {
            Utility.getInstance().showSnackBar(mainLayout, "Please enter account holder name");
        } else if (edtIfsc.getText().toString().equalsIgnoreCase("")) {
            Utility.getInstance().showSnackBar(mainLayout, "Please enter IFSC code");
        } else if (edtAccountNumber.getText().toString().equalsIgnoreCase("")) {
            Utility.getInstance().showSnackBar(mainLayout, "Please enter account number");
        } else if (edtBankAddress.getText().toString().equalsIgnoreCase("")) {
            Utility.getInstance().showSnackBar(mainLayout, "Please enter bank address");
        } else {
            updateBankDetails("1");
        }
    }

    @OnClick(R.id.btn_upi_save)
    void btn_upi_save() {
        if (edtupi.getText().toString().equalsIgnoreCase("")) {
            Utility.getInstance().showSnackBar(mainLayout, "Please enter upi id");
        } else {
            updateBankDetails("2");
        }
    }

    @OnClick(R.id.btn_paytm_save)
    void btn_paytm_save() {
        if (edtpaytm.getText().toString().equalsIgnoreCase("")) {
            Utility.getInstance().showSnackBar(mainLayout, "Please enter paytm number");
        } else {
            updateBankDetails("3");
        }
    }

    private void updateBankDetails(String type) {
        Utility.getInstance().hideKeyBoard(this);
        if (Utility.getInstance().isNetworkAvailable(this)) {

            ProgressDialog.showDialog(this);

            HashMap hashMap = new HashMap();
            hashMap.put("security_token", Constant.getInstance().getSecurityToken());
            hashMap.put("type", type);

            if (type.equalsIgnoreCase("1")) {
                HashMap details = new HashMap();
                details.put("bank_name", edtBankName.getText().toString());
                details.put("account_holder_name", edtAccountHolderName.getText().toString());
                details.put("IFSC_Code", edtIfsc.getText().toString());
                details.put("account_number", edtAccountNumber.getText().toString());
                details.put("bank_address", edtBankAddress.getText().toString());
                hashMap.put("details", details);
            } else if (type.equalsIgnoreCase("2")) {
                hashMap.put("details", edtupi.getText().toString());
            } else if (type.equalsIgnoreCase("3")) {
                hashMap.put("details", edtpaytm.getText().toString());
            }

            RestClient.getInstance().getBaseUrl().updateAccountDetails(hashMap).enqueue(new APICallBack<VerifyOtpResponse>() {
                @Override
                protected void success(VerifyOtpResponse response) {
                    ProgressDialog.dismissDialog(BankDetailActivity.this);
                    if (response != null) {
                        if (response.getSuccess().equals(Constant.FLAG_TRUE)) {
                            WoongaApplication.getAppPreferences().putString(Constant.USER_DATA, new Gson().toJson(response.getData()));
                            EventBus.getDefault().postSticky(new MessageEvent("UserDataUpdated"));
                            if (type.equalsIgnoreCase("1")) {
                                ll_bank_save.setVisibility(View.GONE);
                                iv_bank_edit.setVisibility(View.VISIBLE);
                                unFocusBankDetails();
                            } else if (type.equalsIgnoreCase("2")) {
                                ll_upi_save.setVisibility(View.GONE);
                                iv_upi_edit.setVisibility(View.VISIBLE);
                                unFocusUpiDetails();
                            } else if (type.equalsIgnoreCase("3")) {
                                ll_paytm_save.setVisibility(View.GONE);
                                iv_paytm_edit.setVisibility(View.VISIBLE);
                                unFocusPaytmDetails();
                            }
                            setUi();

                        } else {
                            Utility.getInstance().showSnackBar(mainLayout, response.getMessage());
                        }
                    }
                }

                @Override
                protected void failure(String errorMsg) {
                    ProgressDialog.dismissDialog(BankDetailActivity.this);
                }

                @Override
                protected void onFailure(String failureReason) {
                    ProgressDialog.dismissDialog(BankDetailActivity.this);
                }
            });
        } else {
            Utility.getInstance().showSnackBar(mainLayout, "Please check your Internet Connection");
        }

    }

    @OnClick(R.id.iv_back)
    void back() {
        onBackPressed();
    }

    private void submitReview(String review) {
        if (Utility.getInstance().isNetworkAvailable(this)) {
            ProgressDialog.showDialog(this);

            HashMap hashMap = new HashMap();
            hashMap.put("description", review);
            hashMap.put("security_token", Constant.getInstance().getSecurityToken());

            RestClient.getInstance().getBaseUrl().submitReview(hashMap).enqueue(new APICallBack<SuccessBean>() {
                @Override
                protected void success(SuccessBean response) {
                    ProgressDialog.dismissDialog(BankDetailActivity.this);
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
                    ProgressDialog.dismissDialog(BankDetailActivity.this);

                }

                @Override
                protected void onFailure(String failureReason) {
                    ProgressDialog.dismissDialog(BankDetailActivity.this);

                }
            });
        } else {
            Utility.getInstance().showSnackBar(mainLayout, "Please check your Internet Connection");
        }

    }

    private void showRatingDialog() {
        final Dialog dialog;
        dialog = new Dialog(this, android.R.style.Theme_Material_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_rating);
        dialog.setCancelable(true);

        LayoutInflater inflater = (LayoutInflater) this
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
                    Utility.getInstance().showToast(BankDetailActivity.this, "Please enter your review.");
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


}
