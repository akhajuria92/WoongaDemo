package com.android.woonga.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.android.woonga.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

public class AdapterEnlargeImages extends PagerAdapter {

    Context context;
    private LayoutInflater layoutInflater;
    ArrayList<String> attachmentImages;


    public AdapterEnlargeImages(Context context, ArrayList<String> attachmentImages) {
        this.context = context;
        this.attachmentImages = attachmentImages;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = layoutInflater.inflate(R.layout.adapter_enlarge_images, container, false);
        final ImageView imageView = (ImageView) view.findViewById(R.id.iv);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        if (attachmentImages.get(position).equalsIgnoreCase("")) {
            imageView.setVisibility(View.GONE);
        } else {
            Glide.with(context)
                    .load(attachmentImages.get(position))
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(imageView);

            container.addView(view);

        }


        return view;
    }

    @Override
    public int getCount() {
        return attachmentImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == ((RelativeLayout) obj);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}