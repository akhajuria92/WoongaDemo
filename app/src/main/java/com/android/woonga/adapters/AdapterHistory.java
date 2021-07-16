/*
package com.android.woonga.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.woonga.R;
import com.android.woonga.interfaces.OnLoadMoreListener;
import com.android.woonga.response.HistoryResponse;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterHistory extends RecyclerView.Adapter {

    Context context;
    LayoutInflater inflater;
    HistoryResponse historyResponse;
    private final int VIEW_TYPE_NOTI = 1;
    private final int VIEW_TYPE_LOADING = 0;
    private OnLoadMoreListener onLoadMoreListener;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    boolean isLoading, isEnd;

    public AdapterHistory(Context context, HistoryResponse historyResponse, RecyclerView recyclerView) {
        this.context = context;
        this.historyResponse = historyResponse;
        inflater = LayoutInflater.from(context);

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();

            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                        }

                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            visibleItemCount = linearLayoutManager.getChildCount();
                            totalItemCount = linearLayoutManager.getItemCount();
                            pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                            if (dy > 0) {
                                if ((!isLoading
                                        && (visibleItemCount + pastVisiblesItems) >= totalItemCount)) {
                                    // End has been reached
                                    // Do something
                                    if (onLoadMoreListener != null) {
                                        onLoadMoreListener.onLoadMore();
                                    }
                                    isLoading = true;

                                }
                            }
                        }
                    });
        }

    }

    public void setLoaded() {
        isLoading = false;
    }

    public void isEnd() {
        isLoading = true;
        isEnd = true;
    }

    @Override
    public int getItemViewType(int position) {
        return historyResponse.getLogsHistory().get(position) != null ?
                VIEW_TYPE_NOTI : VIEW_TYPE_LOADING;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        if (viewType == VIEW_TYPE_NOTI) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_history, parent, false);
            // set the view's size, margins, paddings and layout parameter
            viewHolder = new AdapterHistory.RecyclerViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.layout_loading_item, parent, false);

            viewHolder = new AdapterHistory.ProgressViewHolder(v);
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof RecyclerViewHolder) {
            if (historyResponse.getLogsHistory() != null) {
                if (historyResponse.getLogsHistory().get(position).getStatus().equals(0)) {
                    GradientDrawable drawable = (GradientDrawable) ((RecyclerViewHolder) holder).card.getBackground();
                    drawable.setStroke(5, Color.parseColor("#246CFC"));
                    ((RecyclerViewHolder) holder).status.setText("Pending");
                    ((RecyclerViewHolder) holder).status.setTextColor(Color.parseColor("#246CFC"));
                } else if (historyResponse.getLogsHistory().get(position).getStatus().equals(1)) {
                    GradientDrawable drawable = (GradientDrawable) ((RecyclerViewHolder) holder).card.getBackground();
                    drawable.setStroke(5, Color.parseColor("#43A047"));
                    ((RecyclerViewHolder) holder).status.setText("Approved");
                    ((RecyclerViewHolder) holder).status.setTextColor(Color.parseColor("#43A047"));
                } else if (historyResponse.getLogsHistory().get(position).getStatus().equals(2)) {
                    GradientDrawable drawable = (GradientDrawable) ((RecyclerViewHolder) holder).card.getBackground();
                    drawable.setStroke(5, Color.RED);
                    ((RecyclerViewHolder) holder).status.setText("Rejected");
                    ((RecyclerViewHolder) holder).status.setTextColor(Color.RED);
                }
                if (historyResponse.getLogsHistory().get(position).getDetails() != null) {
                    HistoryResponse.Details details = historyResponse.getLogsHistory().get(position).getDetails();
                    if (details.getIcon() != null) {
                        Glide.with(context).load(details.getIcon()).into(((RecyclerViewHolder) holder).iv);
                    } else {
                        Glide.with(context).load(R.mipmap.ic_logo_round).into(((RecyclerViewHolder) holder).iv);
                    }

                    if (details.getTitle() != null) {
                        ((RecyclerViewHolder) holder).title.setText(details.getTitle());
                    }

                   */
/* if (historyResponse.getLogsHistory().get(position).getType() != null) {
                        if (historyResponse.getLogsHistory().get(position).getType().equals(1)) {
                            if (details.getTitle() != null) {
                                ((RecyclerViewHolder) holder).title.setText(details.getTitle());
                            }
                        } else {
                            ((RecyclerViewHolder) holder).title.setText("Banner");
                        }
                    }*//*


                    if (details.getPayout() != null) {
                        ((RecyclerViewHolder) holder).payOut.setVisibility(View.VISIBLE);
                        ((RecyclerViewHolder) holder).payOut.setText(details.getPayout());
                        ((RecyclerViewHolder) holder).tv_bottom.setVisibility(View.GONE);
                    } else {
                        ((RecyclerViewHolder) holder).payOut.setVisibility(View.INVISIBLE);
                        ((RecyclerViewHolder) holder).tv_bottom.setVisibility(View.VISIBLE);
                        ((RecyclerViewHolder) holder).tv_bottom.setText(details.getPercentage() + "% " + details.getMessage());
                    }
                }
            }

        } else {
            if (!isEnd)
                ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
            else {
                isEnd = false;
                ((ProgressViewHolder) holder).progressBar.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public int getItemCount() {
        return historyResponse.getLogsHistory().size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv)
        ImageView iv;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.payOut)
        TextView payOut;

        @BindView(R.id.tv_bottom)
        TextView tv_bottom;

        @BindView(R.id.status)
        TextView status;

        @BindView(R.id.card)
        RelativeLayout card;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {


        ProgressBar progressBar;

        public ProgressViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);

        }
    }


}*/
