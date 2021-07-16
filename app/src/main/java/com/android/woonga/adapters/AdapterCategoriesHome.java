package com.android.woonga.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.woonga.R;
import com.android.woonga.response.CategoriesResponse;
import com.android.woonga.utils.Constant;
import com.android.woonga.views.activities.HomeActivity;
import com.android.woonga.views.activities.OffersActivity;
import com.android.woonga.views.activities.YoutubeTrendingActivity;
import com.android.woonga.views.fragments.FragmentOffers;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterCategoriesHome extends RecyclerView.Adapter<AdapterCategoriesHome.RecyclerViewHolder> {

    Context context;
    LayoutInflater inflater;
    List<CategoriesResponse.Category> categoryList;


    public AdapterCategoriesHome(Context context, List<CategoriesResponse.Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public AdapterCategoriesHome.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.adapter_categories_home, parent, false);
        AdapterCategoriesHome.RecyclerViewHolder viewHolder = new AdapterCategoriesHome.RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterCategoriesHome.RecyclerViewHolder holder, int position) {

        if (categoryList.get(position).getName() != null) {
            holder.title.setText(categoryList.get(position).getName());
        }

        if (categoryList.get(position).getName().equalsIgnoreCase("WOONGA GAMES")) {
            holder.iv.setImageResource(R.drawable.ic_gamezop_icon);
        } else {
            if (categoryList.get(position).getIcon() != null) {
                Glide.with(context).load(categoryList.get(position).getIcon()).into(holder.iv);
            }
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categoryList.get(position).getCategoryId() == 14) {
                    context.startActivity(new Intent(context, YoutubeTrendingActivity.class));
                } else if (categoryList.get(position).getName().equalsIgnoreCase("WOONGA GAMES")) {
                    openPortal();
                } else {
                    context.startActivity(new Intent(context, OffersActivity.class).putExtra("categoryId", categoryList.get(position).getCategoryId())
                            .putExtra("title", categoryList.get(position).getName()));
                    /*FragmentOffers fragmentOffers = FragmentOffers.newInstance();
                    FragmentTransaction fts = ((HomeActivity) v.getContext()).getSupportFragmentManager().beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("categoryId", categoryList.get(position).getCategoryId());
                    fragmentOffers.setArguments(bundle);
                    fts.replace(R.id.fragmentHolder, fragmentOffers);
                    fts.addToBackStack(fragmentOffers.getClass().getSimpleName());
                    fts.commit();*/
                }
            }
        });
    }

    private void openPortal() {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.intent.setPackage("com.android.chrome");
        customTabsIntent.launchUrl(context, Uri.parse(Constant.GAMEZOP_URL));
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv)
        CircleImageView iv;

        @BindView(R.id.title)
        TextView title;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}