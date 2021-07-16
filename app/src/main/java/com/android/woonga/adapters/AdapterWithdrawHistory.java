package com.android.woonga.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.woonga.R;
import com.android.woonga.response.WithdrawHistoryResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterWithdrawHistory extends RecyclerView.Adapter<AdapterWithdrawHistory.RecyclerViewHolder> {

    Context context;
    LayoutInflater inflater;
    List<WithdrawHistoryResponse.Datum> historyList;


    public AdapterWithdrawHistory(Context context, List<WithdrawHistoryResponse.Datum> historyList) {
        this.context = context;
        this.historyList = historyList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public AdapterWithdrawHistory.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.adapter_withdraw_history, parent, false);
        AdapterWithdrawHistory.RecyclerViewHolder viewHolder = new AdapterWithdrawHistory.RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterWithdrawHistory.RecyclerViewHolder holder, int position) {
        if (historyList.get(position).getId() != null) {
            holder.tv_id.setText("#REQ" + historyList.get(position).getId());
        }

        if (historyList.get(position).getStatus() == 1) {
            holder.status.setText("Status: Approved");
            holder.status.setTextColor(Color.parseColor("#43A047"));
            if (historyList.get(position).getAmount() != null) {
                holder.tv_title.setText("Your ₹ " + historyList.get(position).getAmount() + " is transferred to your bank details successfully.");
            }
        } else {
            holder.status.setText("Status: Pending");
            holder.status.setTextColor(Color.parseColor("#2378FA"));
            if (historyList.get(position).getAmount() != null) {
                holder.tv_title.setText("You have requested ₹ " + historyList.get(position).getAmount() + " for withdrawl.");
            }
        }


        if (historyList.get(position).getUpdatedAt() != null) {
            String input = historyList.get(position).getUpdatedAt();

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
                                holder.tv_time.setText(time_format(historyList.get(position).getUpdatedAt()));
                            } else {
                                holder.tv_time.setText(time_format1(historyList.get(position).getUpdatedAt()));
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
        return historyList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_id)
        TextView tv_id;

        @BindView(R.id.tv_title)
        TextView tv_title;

        @BindView(R.id.tv_time)
        TextView tv_time;

        @BindView(R.id.status)
        TextView status;


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