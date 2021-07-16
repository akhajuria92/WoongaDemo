package com.android.woonga.views.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.woonga.R;
import com.android.woonga.WoongaApplication;
import com.android.woonga.response.LoginWithOtpResponse;
import com.android.woonga.response.SuccessBean;
import com.android.woonga.utils.Constant;
import com.android.woonga.utils.ProgressDialog;
import com.android.woonga.utils.Utility;
import com.android.woonga.views.activities.ViewDocumentActivity;
import com.android.woonga.webapi.APICallBack;
import com.android.woonga.webapi.RestClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class FragmentOtp extends Fragment {

    @BindView(R.id.text)
    TextView text;

    @BindView(R.id.termsDialog)
    TextView termsDialog;

    @BindView(R.id.signingUp)
    TextView signingUp;

    @BindView(R.id.privacyPolicy)
    TextView privacyPolicy;

    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

    @BindView(R.id.edt_phoneNumber)
    EditText edt_phoneNumber;


    private String deviceToken;
    LoginWithOtpResponse loginWithOtpResponse;

    public static FragmentOtp newInstance() {
        return new FragmentOtp();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_otp, container, false);
        ButterKnife.bind(this, view);
        long tsLong = System.currentTimeMillis();
        deviceToken = Long.toString(tsLong);
        setUi();
        getDeviceToken();
        getGoogleAdvertisingId();

        return view;

    }


    @SuppressLint("StaticFieldLeak")
    private void getGoogleAdvertisingId() {
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                AdvertisingIdClient.Info idInfo = null;
                try {
                    idInfo = AdvertisingIdClient.getAdvertisingIdInfo(getActivity());
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String advertId = null;
                try {
                    advertId = idInfo.getId();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

                return advertId;
            }

            @Override
            protected void onPostExecute(String advertId) {
                WoongaApplication.getAppPreferences().putString(Constant.GOOGLE_ID, advertId);
            }

        };
        task.execute();
    }

    private void setUi() {
        text.setText(HtmlCompat.fromHtml(getString(R.string.otp_string), HtmlCompat.FROM_HTML_MODE_LEGACY));
        termsDialog.setText(Html.fromHtml(getString(R.string.terms)));
        privacyPolicy.setText(Html.fromHtml(getString(R.string.privacy)));
        signingUp.setText(Html.fromHtml(getString(R.string.signing_up)));
    }

    @OnClick(R.id.btn_getOtp)
    void btn_getOtp() {
        if (Utility.getInstance().isBlankString(edt_phoneNumber.getText().toString())) {
            Utility.getInstance().showSnackBar(mainLayout, getResources().getString(R.string.enter_phone_number));
        } else if (edt_phoneNumber.getText().toString().length() != 10) {
            Utility.getInstance().showSnackBar(mainLayout, getResources().getString(R.string.enter_valid_number));
        } else {
            loginWithOtp(true);
        }
    }

    private void changeViewToConfirmOtpScreen(String otp) {
        ConfirmOtpFragment confirmOtpFragment = ConfirmOtpFragment.newInstance();
        FragmentTransaction fts = getActivity().getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("phoneNumber", edt_phoneNumber.getText().toString());
        bundle.putString("otp", otp);
        confirmOtpFragment.setArguments(bundle);
        fts.replace(R.id.fragmentHolder, confirmOtpFragment);
        fts.addToBackStack(null);
        fts.commit();
    }

    @OnClick(R.id.btn_signin)
    void btn_signin() {
        FragmentSignin fragmentSignin = FragmentSignin.newInstance();
        FragmentTransaction fts = getActivity().getSupportFragmentManager().beginTransaction();
        fts.replace(R.id.fragmentHolder, fragmentSignin);
        fts.addToBackStack(fragmentSignin.getClass().getSimpleName());
        fts.commit();
    }

    private void loginWithOtp(boolean isProgress) {
        Utility.getInstance().hideKeyBoard(getActivity());
        if (Utility.getInstance().isNetworkAvailable(getActivity())) {
            if (isProgress) {
                ProgressDialog.showDialog(getActivity());
            }

            HashMap hashMap = new HashMap();
            hashMap.put("phone_number", edt_phoneNumber.getText().toString());
            hashMap.put("device_type", Constant.OS_TYPE);
            hashMap.put("device_token", deviceToken);
            hashMap.put("google_id", Constant.getInstance().getGoogleId());

            RestClient.getInstance().getBaseUrl().loginWithOtp(hashMap).enqueue(new APICallBack<LoginWithOtpResponse>() {
                @Override
                protected void success(LoginWithOtpResponse response) {
                    if (getActivity() != null) {
                        ProgressDialog.dismissDialog(getActivity());
                    }

                    if (response != null) {
                        if(response.getFirstTime()!=null){
                            WoongaApplication.getAppPreferences().putInt(Constant.FIRST_TIME, response.getFirstTime());
                        }
                        loginWithOtpResponse = response;
                        if (response.getSuccess().equals(Constant.FLAG_TRUE)) {
                            changeViewToConfirmOtpScreen(response.getOtp());
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

    private void getDeviceToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new Instance ID token
                        deviceToken = task.getResult().getToken();

                    }
                });
    }

    @OnClick(R.id.termsDialog)
    void terms() {
        startActivity(new Intent(getActivity(), ViewDocumentActivity.class).putExtra("url", Constant.getInstance().TERMS_URL));
    }

    @OnClick(R.id.privacyPolicy)
    void privacyPolicy() {
        startActivity(new Intent(getActivity(), ViewDocumentActivity.class).putExtra("url", Constant.getInstance().PRIVACY_URL));
    }

    @OnClick(R.id.referralCode)
    void referral() {
        showReferralDialog();
    }


    private void showReferralDialog() {
        final Dialog dialog;
        dialog = new Dialog(getActivity(), android.R.style.Theme_Material_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_referral_code);
        dialog.setCancelable(true);

        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_referral_code, null);

        Button btnSubmit = layout.findViewById(R.id.btn_submit);
        EditText edtCode = layout.findViewById(R.id.edt_code);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtCode.getText().toString().equalsIgnoreCase("")) {
                    verifyReferralCode(edtCode.getText().toString().trim());
                    dialog.dismiss();
                } else {
                    Utility.getInstance().showSnackBar(mainLayout, "Please enter referral code");
                }
            }
        });

        dialog.setContentView(layout);
        dialog.show();

    }


    private void verifyReferralCode(String code) {
        if (Utility.getInstance().isNetworkAvailable(getActivity())) {
            ProgressDialog.showDialog(getActivity());

            HashMap hashMap = new HashMap();
            hashMap.put("referral_code", code);

            RestClient.getInstance().getBaseUrl().verifyReferralCode(hashMap).enqueue(new APICallBack<SuccessBean>() {
                @Override
                protected void success(SuccessBean response) {
                    if (getActivity() != null) {
                        ProgressDialog.dismissDialog(getActivity());
                    }
                    if (response != null) {
                        if (response.getSuccess().equals(Constant.FLAG_TRUE)) {
                            WoongaApplication.getAppPreferences().putString(Constant.REFERRAL_CODE, code);
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

}
