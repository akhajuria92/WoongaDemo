
package com.android.woonga.views.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.android.woonga.R;
import com.android.woonga.views.fragments.FragmentRefer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReferralActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral);
        ButterKnife.bind(this);
        tv_title.setText("Refer & Earn");

        FragmentRefer fragmentRefer = FragmentRefer.newInstance();
        FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
        fts.replace(R.id.fragmentHolder, fragmentRefer);
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
