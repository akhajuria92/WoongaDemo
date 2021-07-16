package com.android.woonga.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.woonga.R;
import com.android.woonga.adapters.AdapterEnlargeImages;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EnlargeImageActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.layoutDots)
    TabLayout layoutDots;

    @BindView(R.id.iv_cancel)
    ImageView iv_cancel;

    ArrayList<String> images = new ArrayList<>();
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlarge_image);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            images = (ArrayList<String>) extras.getSerializable("images");
            position = extras.getInt("position");
        }

        if (images != null) {
            setUi(images, position);
        }
    }

    private void setUi(ArrayList<String> images, int position) {
        AdapterEnlargeImages adapterEnlargeImages = new AdapterEnlargeImages(this, images);
        layoutDots.setupWithViewPager(viewpager);
        viewpager.setAdapter(adapterEnlargeImages);
        viewpager.setCurrentItem(position);
    }

    @OnClick(R.id.iv_cancel)
    void cancel() {
        onBackPressed();
    }
}
