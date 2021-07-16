package com.android.woonga.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.woonga.R;

import butterknife.ButterKnife;

public class FragmentSignin extends Fragment {

    public static FragmentSignin newInstance() {
        FragmentSignin fragmentSignin = new FragmentSignin();
        return fragmentSignin;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        ButterKnife.bind(this, view);


        return view;

    }
}
