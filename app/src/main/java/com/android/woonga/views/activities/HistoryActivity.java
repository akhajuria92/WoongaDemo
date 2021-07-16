package com.android.woonga.views.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.woonga.R;
import com.android.woonga.WoongaApplication;
import com.android.woonga.response.HistoryResponse;
import com.android.woonga.utils.Constant;
import com.android.woonga.utils.MessageEvent;
import com.android.woonga.utils.ProgressDialog;
import com.android.woonga.utils.Utility;
import com.android.woonga.views.fragments.FragmentApprovedHistory;
import com.android.woonga.views.fragments.FragmentPendingHistory;
import com.android.woonga.views.fragments.FragmentRejectedHistory;
import com.android.woonga.webapi.APICallBack;
import com.android.woonga.webapi.RestClient;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistoryActivity extends AppCompatActivity {

    @BindView(R.id.tab)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.tv_title)
    TextView tv_title;

    int pageNumber = 1;

    public List<HistoryResponse.Log> approvedList = new ArrayList<>();
    public List<HistoryResponse.Log> pendingList = new ArrayList<>();
    public List<HistoryResponse.Log> rejectedList = new ArrayList<>();
    HistoryResponse historyResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);

        tv_title.setText("History");
        historyResponse = new Gson().fromJson(WoongaApplication.getAppPreferences().getString("HistoryResponse"), HistoryResponse.class);
        if (historyResponse != null) {
            setData(historyResponse);
            getHistory(false);
        } else {
            getHistory(true);
        }
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new FragmentApprovedHistory(), "Approved");
        adapter.addFrag(new FragmentPendingHistory(), "Pending");
        adapter.addFrag(new FragmentRejectedHistory(), "Rejected");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @OnClick(R.id.iv_back)
    void back() {
        onBackPressed();
    }


    private void getHistory(boolean showProgress) {
        if (Utility.getInstance().isNetworkAvailable(this)) {
            if (showProgress) {
                ProgressDialog.showDialog(this);
            }
            HashMap hashMap = new HashMap();
            hashMap.put("security_token", Constant.getInstance().getSecurityToken());
            hashMap.put("page", pageNumber + "");
            hashMap.put("limit", "20");
            RestClient.getInstance().getBaseUrl().history(hashMap).enqueue(new APICallBack<HistoryResponse>() {
                @Override
                protected void success(HistoryResponse historyResponse) {
                    ProgressDialog.dismissDialog(HistoryActivity.this);
                    if (historyResponse != null) {
                        WoongaApplication.getAppPreferences().putString("HistoryResponse", new Gson().toJson(historyResponse));
                        if (showProgress) {
                            setData(historyResponse);
                        } else {
                            approvedList.clear();
                            pendingList.clear();
                            rejectedList.clear();
                            approvedList.addAll(historyResponse.getApprovedClickLogs());
                            pendingList.addAll(historyResponse.getPendingClickLogs());
                            rejectedList.addAll(historyResponse.getRejectedClickLogs());
                            EventBus.getDefault().postSticky(new MessageEvent("historyListsUpdated"));
                        }

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
            ProgressDialog.dismissDialog(this);
        }
    }

    private void setData(HistoryResponse historyResponse) {
        approvedList.addAll(historyResponse.getApprovedClickLogs());
        pendingList.addAll(historyResponse.getPendingClickLogs());
        rejectedList.addAll(historyResponse.getRejectedClickLogs());

        setupViewPager(viewpager);
        tabLayout.setupWithViewPager(viewpager);
        viewpager.setOffscreenPageLimit(3);
    }

   /* private void setData(HistoryResponse historyResponse) {
        if (historyResponse.getLogsHistory().size() > 0) {
            nothing.setVisibility(View.GONE);
            if (isLoading) {
                isLoading = false;
                if (historyList.size() > 0)
                    historyList.remove(historyList.size() - 1);
                HistoryActivity.this.
                        historyResponse.setLogsHistory(historyList);
                adapterHistory.notifyItemRemoved(historyList.size());
            }

            if (historyResponse.getLogsHistory().size() > 0) {
                pageNumber++;

                for (int i = 0; i < historyResponse.getLogsHistory().size(); i++) {
                    historyList.add(historyResponse.getLogsHistory().get(i));
                }

                HistoryActivity.this.
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
    }*/


}
