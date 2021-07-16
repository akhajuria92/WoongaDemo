package com.android.woonga.views.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.woonga.R;
import com.android.woonga.utils.Constant;
import com.android.woonga.utils.Utility;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentSettings extends Fragment {

    public static FragmentSettings newInstance() {
        return new FragmentSettings();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);


        return view;

    }

    @OnClick(R.id.rl_frequently)
    void frequently() {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(requireActivity(), R.color.colorPrimary));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.intent.setPackage("com.android.chrome");
        customTabsIntent.launchUrl(requireActivity(), Uri.parse("http://docs.google.com/gview?embedded=true&url=" + Constant.getInstance().FREQUENTYLY_ASKED_QUESTIONS_URL));
        //startActivity(new Intent(getActivity(), ViewDocumentActivity.class).putExtra("url", Constant.getInstance().FREQUENTYLY_ASKED_QUESTIONS_URL));
    }

    @OnClick(R.id.rl_privacy)
    void privacy() {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(requireActivity(), R.color.colorPrimary));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.intent.setPackage("com.android.chrome");
        customTabsIntent.launchUrl(requireActivity(), Uri.parse("http://docs.google.com/gview?embedded=true&url=" + Constant.getInstance().PRIVACY_URL));
        // startActivity(new Intent(getActivity(), ViewDocumentActivity.class).putExtra("url", Constant.getInstance().PRIVACY_URL));
    }

    @OnClick(R.id.rl_terms)
    void terms() {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(requireActivity(), R.color.colorPrimary));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.intent.setPackage("com.android.chrome");
        customTabsIntent.launchUrl(requireActivity(), Uri.parse("http://docs.google.com/gview?embedded=true&url=" + Constant.getInstance().TERMS_URL));
        // startActivity(new Intent(getActivity(), ViewDocumentActivity.class).putExtra("url", Constant.getInstance().TERMS_URL));
    }

    @OnClick(R.id.rl_logout)
    void logout() {
        Utility.getInstance().logout(getActivity());
    }
}
