package com.android.woonga.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.woonga.R;
import com.android.woonga.response.CategoriesResponse;
import com.android.woonga.views.activities.HomeActivity;
import com.android.woonga.views.activities.OffersActivity;
import com.android.woonga.views.fragments.FragmentOffers;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.RecyclerViewHolder> {

    Context context;
    LayoutInflater inflater;
    List<CategoriesResponse.Category> categoryList;


    public AdapterCategories(Context context, List<CategoriesResponse.Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public AdapterCategories.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.adapter_categories, parent, false);
        AdapterCategories.RecyclerViewHolder viewHolder = new AdapterCategories.RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterCategories.RecyclerViewHolder holder, int position) {
        if (categoryList.get(position).getColor() != null) {
            holder.card.setBackgroundColor(Color.parseColor(categoryList.get(position).getColor()));
        }
        if (categoryList.get(position).getIcon() != null) {
            Glide.with(context).load(categoryList.get(position).getIcon()).into(holder.iv);
        }

        if (categoryList.get(position).getName() != null) {
            holder.title.setText(categoryList.get(position).getName());
        }

        if (categoryList.get(position).getShortDescription() != null) {
            holder.subTitle.setText(categoryList.get(position).getShortDescription());
        }

       /* if (categoryList.get(position).getCategoryId() == 14) {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        } else {
            holder.itemView.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams feedCommentParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, 150);
            holder.itemView.setLayoutParams(feedCommentParams);
        }*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, OffersActivity.class).putExtra("categoryId", categoryList.get(position).getCategoryId())
                        .putExtra("title", categoryList.get(position).getName()));
              /*  FragmentOffers fragmentOffers = FragmentOffers.newInstance();
                FragmentTransaction fts = ((HomeActivity) v.getContext()).getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putSerializable("categoryId", categoryList.get(position).getCategoryId());
                bundle.putInt("uniqueUser", categoryList.get(position).getUniqueUser());
                fragmentOffers.setArguments(bundle);
                fts.replace(R.id.fragmentHolder, fragmentOffers);
                fts.addToBackStack(fragmentOffers.getClass().getSimpleName());
                fts.commit();*/

            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv)
        ImageView iv;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.subTitle)
        TextView subTitle;

        @BindView(R.id.card)
        RelativeLayout card;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}