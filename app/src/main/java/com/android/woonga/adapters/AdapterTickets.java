package com.android.woonga.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.woonga.R;
import com.android.woonga.response.TicketsResponse;
import com.android.woonga.views.activities.TicketDetailActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterTickets extends RecyclerView.Adapter<AdapterTickets.RecyclerViewHolder> {

    Context context;
    LayoutInflater inflater;
    List<TicketsResponse.Datum> tickets;


    public AdapterTickets(Context context, List<TicketsResponse.Datum> tickets) {
        this.context = context;
        this.tickets = tickets;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public AdapterTickets.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.adapter_tickets, parent, false);
        AdapterTickets.RecyclerViewHolder viewHolder = new AdapterTickets.RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterTickets.RecyclerViewHolder holder, int position) {
        if (tickets.get(position).getId() != null) {
            holder.tv_id.setText("#TKT" + tickets.get(position).getId());
        }
        if (tickets.get(position).getSubject() != null) {
            holder.tv_title.setText(tickets.get(position).getSubject());
        }
        if (tickets.get(position).getIsActive() == 1) {
            holder.status.setText("Status: Open");
            holder.status.setTextColor(Color.parseColor("#43A047"));
        } else {
            holder.status.setText("Status: Closed");
            holder.status.setTextColor(Color.RED);
        }

        if (tickets.get(position).getUnreadCount() > 0) {
            holder.new_comment.setVisibility(View.VISIBLE);
        } else {
            holder.new_comment.setVisibility(View.GONE);
        }

        if (tickets.get(position).getCreatedAt() != null) {
            String input = tickets.get(position).getCreatedAt();

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
                                holder.tv_time.setText(time_format(tickets.get(position).getCreatedAt()));
                            } else {
                                holder.tv_time.setText(time_format1(tickets.get(position).getCreatedAt()));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, TicketDetailActivity.class).putExtra("ticketId", tickets.get(position).getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return tickets.size();
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

        @BindView(R.id.new_comment)
        TextView new_comment;

        @BindView(R.id.card)
        RelativeLayout card;

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