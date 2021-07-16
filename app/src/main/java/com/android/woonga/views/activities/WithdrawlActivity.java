package com.android.woonga.views.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.woonga.R;
import com.android.woonga.WoongaApplication;
import com.android.woonga.adapters.AdapterWithdrawHistory;
import com.android.woonga.response.AddVisitedResponse;
import com.android.woonga.response.SuccessBean;
import com.android.woonga.response.VerifyOtpResponse;
import com.android.woonga.response.WithdrawHistoryResponse;
import com.android.woonga.utils.Constant;
import com.android.woonga.utils.MessageEvent;
import com.android.woonga.utils.ProgressDialog;
import com.android.woonga.utils.Utility;
import com.android.woonga.webapi.APICallBack;
import com.android.woonga.webapi.RestClient;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WithdrawlActivity extends AppCompatActivity {

    @BindView(R.id.tv_available_amount)
    TextView tv_available_amount;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.nothing)
    TextView nothing;

    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

    @BindView(R.id.withdrawHistoryList)
    RecyclerView withdrawHistoryList;

    @BindView(R.id.button)
    Button button;

    VerifyOtpResponse.Data userData;
    AdapterWithdrawHistory adapterWithdrawHistory;
    int withdrawAmount = 0;
    List<VerifyOtpResponse.UserAccountDetail> userAccountDetail;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawl);
        ButterKnife.bind(this);
        setUi();
        getWithdrawHistory(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setUi() {
        tv_title.setText("Withdrawl History");
        withdrawHistoryList.setLayoutManager(new LinearLayoutManager(this));
        userData = new Gson().fromJson(WoongaApplication.getAppPreferences().getString(Constant.USER_DATA), VerifyOtpResponse.Data.class);
        userAccountDetail = new Gson().fromJson(WoongaApplication.getAppPreferences().getString(Constant.USER_DATA), VerifyOtpResponse.Data.class).getUserAccountDetails();


        if (userData != null) {
            if (userData.getApprovedPoints() != null) {
                tv_available_amount.setText("₹ " + userData.getApprovedPoints());
                withdrawAmount = (int) Float.parseFloat(userData.getApprovedPoints());
            }
        }

        if (withdrawAmount >= 50) {
            button.setEnabled(true);
        } else {
            button.setEnabled(false);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorLightGrey)));
        }
    }

    @OnClick(R.id.iv_back)
    void button() {
        onBackPressed();
    }

    @OnClick(R.id.button)
    void withdraw() {
        if (userData.getEmail() == null || userData.getEmail().equalsIgnoreCase("")) {
            Utility.getInstance().showToast(this, "Please complete your Profile!");
            startActivity(new Intent(this, ProfileActivity.class));
            return;
        }
        if (userAccountDetail != null) {
            if (userAccountDetail.size() > 0) {
                sendWithdrawRequest(withdrawAmount);
                return;
            }
        }
        Utility.getInstance().showToast(this, "Please update your bank details!");
        startActivity(new Intent(this, BankDetailActivity.class));

    }

    private void sendWithdrawRequest(int withdrawAmount) {
        if (Utility.getInstance().isNetworkAvailable(this)) {
            ProgressDialog.showDialog(this);
            HashMap hashMap = new HashMap();
            hashMap.put("security_token", Constant.getInstance().getSecurityToken());
            hashMap.put("amount", withdrawAmount + "");

            RestClient.getInstance().getBaseUrl().sendWithdrawRequest(hashMap).enqueue(new APICallBack<SuccessBean>() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                protected void success(SuccessBean successBean) {
                    ProgressDialog.dismissDialog(WithdrawlActivity.this);
                    if (successBean != null) {
                        if (successBean.getSuccess() == Constant.FLAG_TRUE) {
                            if (successBean.getData() != null) {
                                WoongaApplication.getAppPreferences().putString(Constant.USER_DATA, new Gson().toJson(successBean.getData()));
                                EventBus.getDefault().postSticky(new MessageEvent("UserDataUpdated"));
                            }
                            getWithdrawHistory(false);
                            showDialog(successBean.getMessage());
                            setUi();
                        } else {
                            Utility.getInstance().showSnackBar(mainLayout, successBean.getMessage());
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
            Utility.getInstance().showSnackBar(mainLayout, "Please check your Internet Connection");
        }
    }


    private void getWithdrawHistory(boolean showProgress) {
        if (Utility.getInstance().isNetworkAvailable(this)) {
            if (showProgress) {
                ProgressDialog.showDialog(this);
            }
            HashMap hashMap = new HashMap();
            hashMap.put("security_token", Constant.getInstance().getSecurityToken());

            RestClient.getInstance().getBaseUrl().getWithdrawHistory(hashMap).enqueue(new APICallBack<WithdrawHistoryResponse>() {
                @Override
                protected void success(WithdrawHistoryResponse withdrawHistoryResponse) {
                    ProgressDialog.dismissDialog(WithdrawlActivity.this);
                    if (withdrawHistoryResponse != null) {
                        if (withdrawHistoryResponse.getData() != null) {
                            if (withdrawHistoryResponse.getData().size() > 0) {
                                nothing.setVisibility(View.GONE);
                                adapterWithdrawHistory = new AdapterWithdrawHistory(WithdrawlActivity.this, withdrawHistoryResponse.getData());
                                withdrawHistoryList.setAdapter(adapterWithdrawHistory);
                            } else {
                                nothing.setVisibility(View.VISIBLE);
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
        } else {
            Utility.getInstance().showSnackBar(mainLayout, "Please check your Internet Connection");
        }
    }


    private void showDialog(String message) {
        final Dialog dialog;
        dialog = new Dialog(this, android.R.style.Theme_Material_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_withdraw);
        dialog.setCancelable(false);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_withdraw, null);

        Button okBtn = layout.findViewById(R.id.btn_okay);
        TextView tv = layout.findViewById(R.id.tv);
        tv.setText(message);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(layout);
        dialog.show();
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
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MessageEvent event) {

        if (event.message.contains("UserDataUpdated")) {
            EventBus.getDefault().removeStickyEvent(event);
            setUi();

        }
    }

}
