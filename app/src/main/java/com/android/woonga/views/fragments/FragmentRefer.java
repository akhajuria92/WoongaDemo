package com.android.woonga.views.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.woonga.R;
import com.android.woonga.WoongaApplication;
import com.android.woonga.response.VerifyOtpResponse;
import com.android.woonga.utils.Constant;
import com.android.woonga.utils.Utility;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentRefer extends Fragment {

    @BindView(R.id.referralCode)
    TextView referralCode;

    @BindView(R.id.tv_2)
    TextView tv_2;

    VerifyOtpResponse.Data userData;
    String code, referralText = "";

    public static FragmentRefer newInstance() {
        return new FragmentRefer();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_refer, container, false);
        ButterKnife.bind(this, view);

        userData = new Gson().fromJson(WoongaApplication.getAppPreferences().getString(Constant.USER_DATA), VerifyOtpResponse.Data.class);

        if (userData != null) {
            if (userData.getReferralCode() != null) {
                code = userData.getReferralCode();
                referralCode.setText(userData.getReferralCode());
            }
        }
        int amount = WoongaApplication.getAppPreferences().getInt(Constant.AMOUNT_WHO_REFER);

        tv_2.setText("Both get ₹" + amount + " when your friend earn after installing 1st app from WOONGA");
        String appPackageName = getActivity().getPackageName();
        referralText = "Use this referral code to login for the first time in the Woonga app and get upto ₹50 daily.\n\n" +
                "The Referral Code is: " + code + "\n\nThe Google Playstore link to download the app is :\nhttps://play.google.com/store/apps/details?id=" + appPackageName;

        return view;
    }

    @OnClick(R.id.referralCode)
    void copy() {
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("referralCode", code);
        clipboard.setPrimaryClip(clip);
        Utility.getInstance().showToast(getActivity(), "Referral Code Copied!!");
    }


    @OnClick(R.id.whatsapp)
    void whatsapp() {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, referralText);
        try {
            startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Utility.getInstance().showToast(getActivity(), "Whatsapp have not been installed.");
        }
    }

    @OnClick(R.id.share)
    void share() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                referralText);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

}
