package com.android.woonga.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

import com.android.woonga.R;
import com.android.woonga.WoongaApplication;
import com.android.woonga.response.ResendOtpResponse;
import com.android.woonga.response.VerifyOtpResponse;
import com.android.woonga.utils.Constant;
import com.android.woonga.utils.ProgressDialog;
import com.android.woonga.utils.Utility;
import com.android.woonga.views.activities.MainActivity;
import com.android.woonga.webapi.APICallBack;
import com.android.woonga.webapi.RestClient;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmOtpFragment extends Fragment {

    @BindView(R.id.phoneNumber)
    TextView phoneNumber;

    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

    @BindView(R.id.resend)
    TextView resend;

    @BindView(R.id.no1)
    EditText no1;

    @BindView(R.id.no2)
    EditText no2;

    @BindView(R.id.no3)
    EditText no3;

    @BindView(R.id.no4)
    EditText no4;

    private EditText[] editTexts;
    private String otp;
    VerifyOtpResponse verifyOtpResponse;


    public static ConfirmOtpFragment newInstance() {
        return new ConfirmOtpFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm_otp, container, false);
        ButterKnife.bind(this, view);
        setUi();


        return view;

    }


    private void setOtp(String otp) {
        no1.setText(otp.substring(0, 1));
        no2.setText(otp.substring(1, 2));
        no3.setText(otp.substring(2, 3));
        no4.setText(otp.substring(3, 4));
    }

    private void setUi() {
        if (getArguments() != null) {
            phoneNumber.setText(getArguments().getString("phoneNumber"));
            otp = getArguments().getString("otp");
        }
       /* if (otp != null) {
            if (!otp.equalsIgnoreCase("")) {
                setOtp(otp);
            }
        }*/


      /*  SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                String code = messageText.substring(4, 10);//Parse verification code

                no1.setText(code.substring(0, 1));
                no2.setText(code.substring(1, 2));
                no3.setText(code.substring(2, 3));
                no4.setText(code.substring(3, 4));
            }
        });*/

        resend.setText(HtmlCompat.fromHtml(getString(R.string.otp_resend), HtmlCompat.FROM_HTML_MODE_LEGACY));


        editTexts = new EditText[]{no1, no2, no3, no4};
        no1.addTextChangedListener(new PinTextWatcher(0));
        no2.addTextChangedListener(new PinTextWatcher(1));
        no3.addTextChangedListener(new PinTextWatcher(2));
        no4.addTextChangedListener(new PinTextWatcher(3));


        no1.setOnKeyListener(new PinOnKeyListener(0));
        no2.setOnKeyListener(new PinOnKeyListener(1));
        no3.setOnKeyListener(new PinOnKeyListener(2));
        no4.setOnKeyListener(new PinOnKeyListener(3));
    }


    public class PinTextWatcher implements TextWatcher {

        private int currentIndex;
        private boolean isFirst = false, isLast = false;
        private String newTypedString = "";

        PinTextWatcher(int currentIndex) {
            this.currentIndex = currentIndex;

            if (currentIndex == 0)
                this.isFirst = true;
            else if (currentIndex == editTexts.length - 1)
                this.isLast = true;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            newTypedString = s.subSequence(start, start + count).toString().trim();
        }

        @Override
        public void afterTextChanged(Editable s) {

            String text = newTypedString;

            /* Detect paste event and set first char */
            if (text.length() > 1)
                text = String.valueOf(text.charAt(0)); // TODO: We can fill out other EditTexts

            editTexts[currentIndex].removeTextChangedListener(this);
            editTexts[currentIndex].setText(text);
            editTexts[currentIndex].setSelection(text.length());
            editTexts[currentIndex].addTextChangedListener(this);

            if (text.length() == 1)
                moveToNext();
            else if (text.length() == 0)
                moveToPrevious();
        }

        private void moveToNext() {
            if (!isLast)
                editTexts[currentIndex + 1].requestFocus();

            if (isAllEditTextsFilled() && isLast) { // isLast is optional
                // Click();
            }
        }

        private void moveToPrevious() {
            if (!isFirst)
                editTexts[currentIndex - 1].requestFocus();
        }

        private boolean isAllEditTextsFilled() {
            for (EditText editText : editTexts)
                if (editText.getText().toString().trim().length() == 0)
                    return false;
            return true;
        }

    }

    public class PinOnKeyListener implements View.OnKeyListener {

        private int currentIndex;

        PinOnKeyListener(int currentIndex) {
            this.currentIndex = currentIndex;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (editTexts[currentIndex].getText().toString().isEmpty() && currentIndex != 0)
                    editTexts[currentIndex - 1].requestFocus();
            }
            return false;
        }
    }


    @OnClick(R.id.btn_verify)
    void btn_verify() {
        String otp = no1.getText().toString() + no2.getText().toString() + no3.getText().toString() + no4.getText().toString();
        if (otp.equalsIgnoreCase("") || otp.length() < 4) {
            Utility.getInstance().showSnackBar(mainLayout, "Please enter OTP");
        } else {
            verifyOtp(otp);
        }
    }


    private void verifyOtp(String otp) {
        Utility.getInstance().hideKeyBoard(getActivity());
        if (Utility.getInstance().isNetworkAvailable(getActivity())) {

            ProgressDialog.showDialog(getActivity());

            HashMap hashMap = new HashMap();
            hashMap.put("phone_number", phoneNumber.getText().toString());
            hashMap.put("OTP", otp);
            hashMap.put("referral_code", Constant.getInstance().getReferralCode());
            hashMap.put("first_time", Constant.getInstance().getFirstTimeUser() + "");
            RestClient.getInstance().getBaseUrl().verifyOtp(hashMap).enqueue(new APICallBack<VerifyOtpResponse>() {
                @Override
                protected void success(VerifyOtpResponse response) {
                    if (getActivity() != null) {
                        ProgressDialog.dismissDialog(getActivity());
                    }

                    if (response != null) {
                        verifyOtpResponse = response;
                        if (response.getSuccess().equals(Constant.FLAG_TRUE)) {
                            WoongaApplication.getAppPreferences().putBoolean(Constant.LOGIN, true);
                            WoongaApplication.getAppPreferences().putString(Constant.USER_DATA, new Gson().toJson(response.getData()));
                            startActivity(new Intent(getActivity(), MainActivity.class));
                            getActivity().finish();
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


    @OnClick(R.id.resend)
    void resend() {
        resendOtpRequest();
    }


    private void resendOtpRequest() {
        Utility.getInstance().hideKeyBoard(getActivity());
        if (Utility.getInstance().isNetworkAvailable(getActivity())) {

            ProgressDialog.showDialog(getActivity());

            HashMap hashMap = new HashMap();
            hashMap.put("phone_number", phoneNumber.getText().toString());
            RestClient.getInstance().getBaseUrl().resendOtp(hashMap).enqueue(new APICallBack<ResendOtpResponse>() {
                @Override
                protected void success(ResendOtpResponse response) {
                    if (getActivity() != null) {
                        ProgressDialog.dismissDialog(getActivity());
                    }

                    if (response != null) {
                        if (response.getSuccess().equals(Constant.FLAG_TRUE)) {
                            Utility.getInstance().showSnackBar(mainLayout, response.getMessage());
                            /*setOtp(response.getOtp());*/
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
