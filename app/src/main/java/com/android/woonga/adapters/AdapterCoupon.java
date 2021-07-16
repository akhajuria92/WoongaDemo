package com.android.woonga.adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.woonga.R;
import com.android.woonga.response.DashboardOffersResponse;
import com.android.woonga.utils.Utility;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterCoupon extends RecyclerView.Adapter<AdapterCoupon.RecyclerViewHolder> {

    Context context;
    LayoutInflater inflater;
    List<DashboardOffersResponse.OfferCoupon> couponList;


    public AdapterCoupon(Context context, List<DashboardOffersResponse.OfferCoupon> couponList) {
        this.context = context;
        this.couponList = couponList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public AdapterCoupon.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.adapter_coupons, parent, false);
        AdapterCoupon.RecyclerViewHolder viewHolder = new AdapterCoupon.RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterCoupon.RecyclerViewHolder holder, int position) {
        if (couponList.get(position).getCouponDescription() != null) {
            holder.title.setText(couponList.get(position).getCouponDescription());
        }

        if (couponList.get(position).getCouponCode() != null) {
            holder.coupon.setText(couponList.get(position).getCouponCode());
        }

        holder.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("couponCode", couponList.get(position).getCouponCode());
                clipboard.setPrimaryClip(clip);
                Utility.getInstance().showToast(context, "Coupon Copied!!");
            }
        });

        if(position==couponList.size()-1){
            holder.viewBottom.setVisibility(View.VISIBLE);
        }else {
            holder.viewBottom.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return couponList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.coupon)
        TextView coupon;

        @BindView(R.id.copy)
        ImageView copy;

        @BindView(R.id.viewBottom)
        View viewBottom;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}