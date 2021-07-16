/*
package com.android.woonga.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.woonga.R;
import com.android.woonga.adapters.AdapterHistory;
import com.android.woonga.adapters.AdapterTopCashbackOffers;
import com.android.woonga.interfaces.OnLoadMoreListener;
import com.android.woonga.response.CategoriesResponse;
import com.android.woonga.response.HistoryResponse;
import com.android.woonga.utils.Constant;
import com.android.woonga.utils.MessageEvent;
import com.android.woonga.utils.ProgressDialog;
import com.android.woonga.utils.Utility;
import com.android.woonga.webapi.APICallBack;
import com.android.woonga.webapi.RestClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentHistory extends Fragment {

    @BindView(R.id.historyRecycler)
    RecyclerView historyRecycler;

    @BindView(R.id.nothing)
    TextView nothing;

    AdapterHistory adapterHistory;
    private RecyclerView.LayoutManager mLayoutManager;
    boolean isLoading;
    int pageNumber = 1;
    HistoryResponse historyResponse = new HistoryResponse();
    List<HistoryResponse.Lo> historyList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        historyRecycler.setLayoutManager(mLayoutManager);

        adapterHistory = new AdapterHistory(getActivity(), historyResponse, historyRecycler);
        historyRecycler.setAdapter(adapterHistory);
        historyRecycler.setNestedScrollingEnabled(false);


        adapterHistory.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add null , so the adapter_goals will check view_type and show progress bar at bottom
                isLoading = true;
                historyResponse.getLogsHistory().add(null);
                historyRecycler.post(new Runnable() {
                    public void run() {
                        adapterHistory.notifyItemInserted(historyResponse.getLogsHistory()
                                .size() - 1);
                    }
                });

                getHistory(pageNumber);
            }
        });

        getHistory(pageNumber);


        return view;

    }


    private void getHistory(int pageNumber) {
        if (Utility.getInstance().isNetworkAvailable(getActivity())) {
            HashMap hashMap = new HashMap();
            hashMap.put("security_token", Constant.getInstance().getSecurityToken());
            hashMap.put("page", pageNumber + "");
            hashMap.put("limit", "20");
            RestClient.getInstance().getBaseUrl().history(hashMap).enqueue(new APICallBack<HistoryResponse>() {
                @Override
                protected void success(HistoryResponse historyResponse) {
                    if (historyResponse != null) {
                        setData(historyResponse);
                    }
                }

                @Override
                protected void failure(String errorMsg) {

                }

                @Override
                protected void onFailure(String failureReason) {

                }
            });
        } else {
            ProgressDialog.dismissDialog(getActivity());
        }
    }

    private void setData(HistoryResponse historyResponse) {
        if (historyResponse.getLogsHistory().size() > 0) {
            nothing.setVisibility(View.GONE);
            if (isLoading) {
                isLoading = false;
                if (historyList.size() > 0)
                    historyList.remove(historyList.size() - 1);
                FragmentHistory.this.
                        historyResponse.setLogsHistory(historyList);
                adapterHistory.notifyItemRemoved(historyList.size());
            }

            if (historyResponse.getLogsHistory().size() > 0) {
                pageNumber++;

                for (int i = 0; i < historyResponse.getLogsHistory().size(); i++) {
                    historyList.add(historyResponse.getLogsHistory().get(i));
                }

                FragmentHistory.this.
                        historyResponse.setLogsHistory(historyList);
                adapterHistory.notifyItemInserted(historyList.size());
                adapterHistory.setLoaded();
            }
            adapterHistory.notifyDataSetChanged();

        } else {
            adapterHistory.notifyItemInserted(historyList.size() - 1);
            adapterHistory.notifyDataSetChanged();
            adapterHistory.isEnd();

            if (pageNumber == 1) {
                nothing.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MessageEvent event) {
        if (event.message.contains("NeedToRefreshHistory")) {
            EventBus.getDefault().removeStickyEvent(event);
            historyList.clear();
            getHistory(1);
        }

    }
}
*/
