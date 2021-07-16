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
import com.android.woonga.adapters.AdapterApprovedHistory;
import com.android.woonga.response.CategoriesResponse;
import com.android.woonga.utils.MessageEvent;
import com.android.woonga.views.activities.HistoryActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentApprovedHistory extends Fragment {
    @BindView(R.id.approvedListView)
    RecyclerView approvedListView;

    @BindView(R.id.nothing)
    TextView nothing;

    AdapterApprovedHistory adapterApprovedHistory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_approved_history, container, false);
        ButterKnife.bind(this, view);
        setUi();

        return view;

    }

    private void setUi() {
        if (getActivity() != null) {
            if (((HistoryActivity) getActivity()).approvedList.size() > 0) {
                approvedListView.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapterApprovedHistory = new AdapterApprovedHistory(getActivity(), ((HistoryActivity) getActivity()).approvedList);
                approvedListView.setAdapter(adapterApprovedHistory);
            } else {
                nothing.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MessageEvent event) {
        if (event.message.contains("historyListsUpdated")) {
            EventBus.getDefault().removeStickyEvent(event);
            setUi();
        }

    }
}
