package com.android.woonga.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.woonga.R;
import com.android.woonga.adapters.AdapterHotOffers;
import com.android.woonga.response.CategoryOffersResponse;
import com.android.woonga.response.DashboardOffersResponse;
import com.android.woonga.utils.Constant;
import com.android.woonga.utils.ProgressDialog;
import com.android.woonga.utils.Utility;
import com.android.woonga.views.activities.HomeActivity;
import com.android.woonga.views.activities.OffersActivity;
import com.android.woonga.webapi.APICallBack;
import com.android.woonga.webapi.RestClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentHotDeals extends Fragment {

    @BindView(R.id.offersRecycler)
    RecyclerView offersRecycler;

    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

    @BindView(R.id.rl_no_data)
    RelativeLayout rl_no_data;

    private AdapterHotOffers adapterHotOffers;
    private List<DashboardOffersResponse.Offers> offersList;

    public static FragmentHotDeals newInstance() {
        return new FragmentHotDeals();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers, container, false);
        ButterKnife.bind(this, view);

        setAdapter();
        getOffers("13", false);

        return view;
    }


    private void setAdapter() {
        offersRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        offersList = new ArrayList<>();
        adapterHotOffers = new AdapterHotOffers(getActivity(), offersList, 1, 10);
        offersRecycler.setAdapter(adapterHotOffers);
    }

    @Override
    public void onResume() {
        super.onResume();
        getOffers("13", false);
    }

    private void getOffers(String categoryId, boolean showProgress) {
        if (Utility.getInstance().isNetworkAvailable(getActivity())) {
            if (showProgress) {
                ProgressDialog.showDialog(getActivity());
            }
            HashMap hashMap = new HashMap();
            hashMap.put("security_token", Constant.getInstance().getSecurityToken());
            hashMap.put("category_id", categoryId);
            hashMap.put("state", Constant.getInstance().getCurrentState());
            hashMap.put("city", Constant.getInstance().getCurrentCity());

            RestClient.getInstance().getBaseUrl().getOffers(hashMap).enqueue(new APICallBack<CategoryOffersResponse>() {
                @Override
                protected void success(CategoryOffersResponse response) {
                    if (getActivity() != null) {
                        ProgressDialog.dismissDialog(getActivity());
                    }
                    if (response != null) {
                        if (response.getSuccess().equals(Constant.FLAG_TRUE)) {
                            if (response.getOffers() != null) {
                                if (response.getOffers().size() > 0) {
                                    offersRecycler.setVisibility(View.VISIBLE);
                                    rl_no_data.setVisibility(View.GONE);
                                    offersList.clear();
                                    offersList.addAll(response.getOffers());
                                    adapterHotOffers.notifyDataSetChanged();
                                } else {
                                    offersRecycler.setVisibility(View.GONE);
                                    rl_no_data.setVisibility(View.VISIBLE);
                                }

                            }

                        } else if (response.getSuccess().equals(10)) {
                            if (getActivity() != null) {
                                Utility.getInstance().logout(getActivity());
                            }
                        } else {
                            Utility.getInstance().showSnackBar(mainLayout, response.getMessage());
                        }
                    }
                }

                @Override
                protected void failure(String errorMsg) {
                    if (getActivity() != null) {
                        ProgressDialog.dismissDialog(getActivity());
                    }
                }

                @Override
                protected void onFailure(String failureReason) {
                    if (getActivity() != null) {
                        ProgressDialog.dismissDialog(getActivity());
                    }
                }
            });
        }
    }

    @OnClick(R.id.btn_earn)
    void earn() {
        startActivity(new Intent(getActivity(), OffersActivity.class).putExtra("categoryId", 10)
                .putExtra("title", "Cashback Offers"));
      /*  FragmentOffers fragmentOffers = FragmentOffers.newInstance();
        FragmentTransaction fts = getActivity().getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("categoryId", 10);
        fragmentOffers.setArguments(bundle);
        fts.replace(R.id.fragmentHolder, fragmentOffers);
        fts.addToBackStack(fragmentOffers.getClass().getSimpleName());
        fts.commit();*/
    }


}
