package com.android.woonga.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.TextView;

import com.android.woonga.R;
import com.android.woonga.views.fragments.FragmentProfile;
import com.android.woonga.views.fragments.FragmentRefer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        tv_title.setText("Profile");

        FragmentProfile fragmentProfile = FragmentProfile.newInstance();
        FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
        fts.replace(R.id.fragmentHolder, fragmentProfile);
        fts.addToBackStack(null);
        fts.commit();

    }

    @OnClick(R.id.iv_back)
    void iv_back() {
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}