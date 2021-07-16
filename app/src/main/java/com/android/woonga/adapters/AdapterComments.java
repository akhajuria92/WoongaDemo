package com.android.woonga.adapters;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.woonga.R;
import com.android.woonga.response.TicketDetailsResponse;
import com.android.woonga.utils.Constant;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterComments extends RecyclerView.Adapter<AdapterComments.RecyclerViewHolder> {

    Context context;
    LayoutInflater inflater;
    List<TicketDetailsResponse.Comment> comments;


    public AdapterComments(Context context, List<TicketDetailsResponse.Comment> comments) {
        this.context = context;
        this.comments = comments;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public AdapterComments.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.adapter_comments, parent, false);
        AdapterComments.RecyclerViewHolder viewHolder = new AdapterComments.RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterComments.RecyclerViewHolder holder, int position) {
        if (comments.get(position).getCommentBy() != null) {
            if (comments.get(position).getCommentBy() == 0) {
                holder.admin_comment_view.setVisibility(View.VISIBLE);
                if(comments.get(position).getComment()!=null){
                    holder.tv_comment.setText(comments.get(position).getComment());
                }
                if(comments.get(position).getCreatedAt()!=null){
                    String input = comments.get(position).getCreatedAt();

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
                                        holder.tv_time.setText(time_format(comments.get(position).getCreatedAt()));
                                    } else {
                                        holder.tv_time.setText(time_format1(comments.get(position).getCreatedAt()));
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                }
            } else {
                holder.user_comment_view.setVisibility(View.VISIBLE);
                if(comments.get(position).getComment()!=null){
                    holder.tv_comment_user.setText(comments.get(position).getComment());
                }

                if (!Constant.getInstance().getProfilePic().equalsIgnoreCase("") && !Constant.getInstance().getProfilePic().equalsIgnoreCase("null")) {
                    Glide.with(context).load(Constant.getInstance().getProfilePic()).into(holder.iv_user_image);
                }
                if(comments.get(position).getCreatedAt()!=null){
                    String input = comments.get(position).getCreatedAt();

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
                                        holder.tv_time_user.setText(time_format(comments.get(position).getCreatedAt()));
                                    } else {
                                        holder.tv_time_user.setText(time_format1(comments.get(position).getCreatedAt()));
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.admin_comment_view)
        RelativeLayout admin_comment_view;

        @BindView(R.id.user_comment_view)
        RelativeLayout user_comment_view;

        @BindView(R.id.iv_admin_image)
        CircleImageView iv_admin_image;

        @BindView(R.id.iv_user_image)
        CircleImageView iv_user_image;

        @BindView(R.id.tv_time)
        TextView tv_time;

        @BindView(R.id.tv_time_user)
        TextView tv_time_user;

        @BindView(R.id.tv_comment_user)
        TextView tv_comment_user;

        @BindView(R.id.tv_comment)
        TextView tv_comment;

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

            SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM d"); //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault());
            time = dateFormatter.format(dateObj);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return time;
    }
}
