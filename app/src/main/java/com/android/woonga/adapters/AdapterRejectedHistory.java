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

public class AdapterRejectedHistory extends RecyclerView.Adapter<AdapterRejectedHistory.RecyclerViewHolder> {

    Context context;
    LayoutInflater inflater;
    List<HistoryResponse.Log> rejectedList;


    public AdapterRejectedHistory(Context context, List<HistoryResponse.Log> rejectedList) {
        this.context = context;
        this.rejectedList = rejectedList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public AdapterRejectedHistory.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.adapter_logs_history, parent, false);
        AdapterRejectedHistory.RecyclerViewHolder viewHolder = new AdapterRejectedHistory.RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterRejectedHistory.RecyclerViewHolder holder, int position) {
        if (rejectedList.get(position).getDetails() != null) {
            if (rejectedList.get(position).getDetails().getIcon() != null) {
                Glide.with(context).asBitmap().load(rejectedList.get(position).getDetails().getIcon()).addListener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        holder.iv.setImageResource(R.mipmap.ic_logo_round);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).into(holder.iv);
            }
            if (rejectedList.get(position).getDetails().getTitle() != null) {
                holder.tv_title.setText(rejectedList.get(position).getDetails().getTitle());
            }

            holder.status.setText("Status: Rejected");
            holder.status.setTextColor(context.getResources().getColor(R.color.colorRed));

            if (rejectedList.get(position).getPayOut() != null) {
                holder.payOut.setText(rejectedList.get(position).getDetails().getPayout());
            }else {
                if (rejectedList.get(position).getDetails().getPayout() != null) {
                    holder.payOut.setText(rejectedList.get(position).getDetails().getPayout());
                } else {
                    holder.payOut.setText(rejectedList.get(position).getDetails().getPercentage() + " %");
                }
            }
        }

        if (rejectedList.get(position).getCreatedAt() != null) {
            String input = rejectedList.get(position).getCreatedAt();

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
                                holder.tv_time.setText(time_format(rejectedList.get(position).getCreatedAt()));
                            } else {
                                holder.tv_time.setText(time_format1(rejectedList.get(position).getCreatedAt()));
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
        return rejectedList.size();
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