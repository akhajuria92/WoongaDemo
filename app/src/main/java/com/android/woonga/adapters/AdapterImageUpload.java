package com.android.woonga.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.woonga.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterImageUpload extends RecyclerView.Adapter<AdapterImageUpload.RecyclerViewHolder> {
    Context context;
    LayoutInflater inflater;
    private ArrayList<String> data;


    public AdapterImageUpload(Context context, ArrayList<String > data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public AdapterImageUpload.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.adapterimage, parent, false);
        AdapterImageUpload.RecyclerViewHolder viewHolder = new AdapterImageUpload.RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterImageUpload.RecyclerViewHolder holder, final int position) {


        if (data.get(position) != null) {
            holder.progressbar.setVisibility(View.VISIBLE);
            holder.alertimage.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(data.get(position))
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressbar.setVisibility(View.GONE);
                            return false;

                        }
                    })
                    .into(holder.alertimage);
        } else {
            holder.progressbar.setVisibility(View.GONE);
            holder.alertimage.setVisibility(View.GONE);

        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.alertimage)
        ImageView alertimage;

        @BindView(R.id.delete)
        ImageView delete;

        @BindView(R.id.progressbar)
        ProgressBar progressbar;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}