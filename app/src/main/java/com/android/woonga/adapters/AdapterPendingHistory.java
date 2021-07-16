package com.android.woonga.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
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

public class AdapterPendingHistory extends RecyclerView.Adapter<AdapterPendingHistory.RecyclerViewHolder> {

    Context context;
    LayoutInflater inflater;
    List<HistoryResponse.Log> pendingList;


    public AdapterPendingHistory(Context context, List<HistoryResponse.Log> pendingList) {
        this.context = context;
        this.pendingList = pendingList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public AdapterPendingHistory.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.adapter_logs_history, parent, false);
        AdapterPendingHistory.RecyclerViewHolder viewHolder = new AdapterPendingHistory.RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterPendingHistory.RecyclerViewHolder holder, int position) {
        if (pendingList.get(position).getDetails() != null) {
            if (pendingList.get(position).getDetails().getIcon() != null) {
                Glide.with(context).asBitmap().load(pendingList.get(position).getDetails().getIcon()).addListener(new RequestListener<Bitmap>() {
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
            if (pendingList.get(position).getDetails().getTitle() != null) {
                holder.tv_title.setText(pendingList.get(position).getDetails().getTitle());
            }

            holder.status.setText("Status: Pending");
            holder.status.setTextColor(context.getResources().getColor(R.color.blue));

            if (pendingList.get(position).getIsSplit() != null &&
                    pendingList.get(position).getIsSplit() == 1 && pendingList.get(position).getOfferPayoutDetail() != null
                    && pendingList.get(position).getOfferPayoutDetail().size() > 0) {
                holder.payOut.setText(pendingList.get(position).getOfferPayoutDetail().get(0).getPayout());
            } else {
                if (pendingList.get(position).getPayOut() != null) {
                    holder.payOut.setText(pendingList.get(position).getDetails().getPayout());
                } else {
                    if (pendingList.get(position).getDetails().getPayout() != null) {
                        holder.payOut.setText(pendingList.get(position).getDetails().getPayout());
                    } else {
                        holder.payOut.setText(pendingList.get(position).getDetails().getPercentage() + " %");
                    }
                }
            }
        }

        if (pendingList.get(position).getCreatedAt() != null) {
            String input = pendingList.get(position).getCreatedAt();

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
                                holder.tv_time.setText(time_format(pendingList.get(position).getCreatedAt()));
                            } else {
                                holder.tv_time.setText(time_format1(pendingList.get(position).getCreatedAt()));
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
        return pendingList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv)
        CircleImageView iv;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.payOut)
        TextView payOut;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

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