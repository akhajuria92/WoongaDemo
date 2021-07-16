package com.android.woonga.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.woonga.R;
import com.android.woonga.response.HistoryResponse;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterApprovedHistory extends RecyclerView.Adapter<AdapterApprovedHistory.RecyclerViewHolder> {

    Context context;
    LayoutInflater inflater;
    List<HistoryResponse.Log> approvedList;
    AdapterPayout adapterPayout;


    public AdapterApprovedHistory(Context context, List<HistoryResponse.Log> approvedList) {
        this.context = context;
        this.approvedList = approvedList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public AdapterApprovedHistory.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.adapter_logs_history, parent, false);
        AdapterApprovedHistory.RecyclerViewHolder viewHolder = new AdapterApprovedHistory.RecyclerViewHolder(v);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(AdapterApprovedHistory.RecyclerViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        if (approvedList.get(position).getDetails() != null) {
            if (approvedList.get(position).getDetails().getIcon() != null) {
                Glide.with(context).asBitmap().load(approvedList.get(position).getDetails().getIcon()).addListener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        holder.iv.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_logo_round));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).into(holder.iv);
            }
            if (approvedList.get(position).getDetails().getTitle() != null) {
                holder.tv_title.setText(approvedList.get(position).getDetails().getTitle());
            }

            holder.status.setText("Status: Approved");
            holder.status.setTextColor(context.getResources().getColor(R.color.green));

            if (approvedList.get(position).getIsSplit() != null &&
                    approvedList.get(position).getIsSplit() == 1 && approvedList.get(position).getOfferPayoutDetail() != null
                    && approvedList.get(position).getOfferPayoutDetail().size() > 0) {
                holder.iv_arrow.setVisibility(View.VISIBLE);
                adapterPayout = new AdapterPayout(context, approvedList.get(position).getOfferPayoutDetail());
                holder.payoutRecycler.setAdapter(adapterPayout);
            } else {
                holder.iv_arrow.setVisibility(View.GONE);
                holder.payoutRecycler.setVisibility(View.GONE);
            }

            if (approvedList.get(position).getPayOut() != null) {
                holder.payOut.setText(approvedList.get(position).getDetails().getPayout());
            } else {
                if (approvedList.get(position).getDetails().getPayout() != null) {
                    holder.payOut.setText(approvedList.get(position).getDetails().getPayout());
                } else {
                    holder.payOut.setText(approvedList.get(position).getDetails().getPercentage() + " %");
                }
            }
        }

        holder.itemView.setOnClickListener(view -> {
            if (holder.iv_arrow.getVisibility() == View.VISIBLE) {
                if (holder.payoutRecycler.getVisibility() == View.VISIBLE) {
                    holder.iv_arrow.setImageDrawable(context.getDrawable(R.drawable.ic_down_arrow));
                    holder.payoutRecycler.setVisibility(View.GONE);
                } else {
                    holder.iv_arrow.setImageDrawable(context.getDrawable(R.drawable.ic_up_arrow));
                    holder.payoutRecycler.setVisibility(View.VISIBLE);
                }
            }
        });

        if (approvedList.get(position).getCreatedAt() != null) {
            String input = approvedList.get(position).getCreatedAt();

            if (input != null)
                if (input.length() > 0) {
                    Date value = null;
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                        value = formatter.parse(input);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    try {
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //this format changeable
                        dateFormatter.setTimeZone(TimeZone.getDefault());
                        input = dateFormatter.format(value);
                        if (input != null) {
                            Date past = dateFormatter.parse(input);
                            long timeInMilliseconds = past.getTime();
                            if (DateUtils.isToday(timeInMilliseconds)) {
                                holder.tv_time.setText(time_format(approvedList.get(position).getCreatedAt()));
                            } else {
                                holder.tv_time.setText(time_format1(approvedList.get(position).getCreatedAt()));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

        }


    }

    @Override
    public int getItemCount() {
        return approvedList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv)
        CircleImageView iv;
        @BindView(R.id.iv_arrow)
        ImageView iv_arrow;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.payOut)
        TextView payOut;
        @BindView(R.id.payoutRecycler)
        RecyclerView payoutRecycler;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            payoutRecycler.setLayoutManager(new LinearLayoutManager(context));
        }
    }

    public String time_format(String time) {
        try {
            final SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            curFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
            final Date dateObj = curFormater.parse(time);

            SimpleDateFormat dateFormatter = new SimpleDateFormat("h:mm a"); //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault());
            time = dateFormatter.format(dateObj);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return time;
    }

    public String time_format1(String time) {
        try {
            final SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            curFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
            final Date dateObj = curFormater.parse(time);

            SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM d, HH:MM"); //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault());
            time = dateFormatter.format(dateObj);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return time;
    }
}