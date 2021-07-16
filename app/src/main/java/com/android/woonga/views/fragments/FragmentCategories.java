package com.android.woonga.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.woonga.R;
import com.android.woonga.adapters.AdapterCategories;
import com.android.woonga.response.CategoriesResponse;
import com.android.woonga.utils.Constant;
import com.android.woonga.utils.MessageEvent;
import com.android.woonga.utils.ProgressDialog;
import com.android.woonga.utils.Utility;
import com.android.woonga.webapi.APICallBack;
import com.android.woonga.webapi.RestClient;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentCategories extends Fragment {
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

    @BindView(R.id.categoriesList)
    RecyclerView categoriesList;

    List<CategoriesResponse.Category> list;
    AdapterCategories adapterCategories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        ButterKnife.bind(this, view);

        setAdapter();
        getCategories();
        return view;

    }


    public void setAdapter() {
        categoriesList.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        adapterCategories = new AdapterCategories(getActivity(), list);
        categoriesList.setAdapter(adapterCategories);

    }

    public void getCategories() {
        if (Utility.getInstance().isNetworkAvailable(getActivity())) {

            HashMap hashMap = new HashMap();
            hashMap.put("security_token", Constant.getInstance().getSecurityToken());
            RestClient.getInstance().getBaseUrl().getCategories(hashMap).enqueue(new APICallBack<CategoriesResponse>() {
                @Override
                protected void success(CategoriesResponse response) {
                    if (response != null) {
                        if (response.getSuccess().equals(Constant.FLAG_TRUE)) {
                            if (response.getCategories() != null) {
                                list.clear();
                                list.addAll(response.getCategories());
                                adapterCategories.notifyDataSetChanged();
                                EventBus.getDefault().postSticky(list);
                            }

                        } else {
                            Utility.getInstance().showSnackBar(mainLayout, response.getMessage());
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
            ProgressDialog.dismissDialog(getActivity());
        }
    }
}
