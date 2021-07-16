package com.android.woonga.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.GoalRow;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.woonga.R;
import com.android.woonga.adapters.AdapterTickets;
import com.android.woonga.response.TicketsResponse;
import com.android.woonga.utils.Constant;
import com.android.woonga.utils.MessageEvent;
import com.android.woonga.utils.ProgressDialog;
import com.android.woonga.utils.Utility;
import com.android.woonga.views.fragments.FragmentAddTicket;
import com.android.woonga.webapi.APICallBack;
import com.android.woonga.webapi.RestClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyTicketsActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    public TextView tv_title;

    @BindView(R.id.iv_attachment)
    public ImageView iv_attachment;

    @BindView(R.id.iv_send)
    public ImageView iv_send;

    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

    @BindView(R.id.ticketsList)
    RecyclerView ticketsList;

    @BindView(R.id.rl_no_tickets)
    RelativeLayout rl_no_tickets;

    FragmentTransaction fts;
    FragmentManager fragmentManager;
    AdapterTickets adapterTickets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tickets);
        ButterKnife.bind(this);
        tv_title.setText("My Tickets");
        getTickets(true);

    }

    private void setAdapter(List<TicketsResponse.Datum> tickets) {
        ticketsList.setVisibility(View.VISIBLE);
        ticketsList.setLayoutManager(new LinearLayoutManager(this));
        adapterTickets = new AdapterTickets(this, tickets);
        ticketsList.setAdapter(adapterTickets);
    }

    @OnClick(R.id.rl_add)
    void rl_add() {
        FragmentAddTicket fragmentAddTicket = FragmentAddTicket.newInstance();
        FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
        fts.replace(R.id.fragmentHolder, fragmentAddTicket);
        fts.addToBackStack(fragmentAddTicket.getClass().getSimpleName());
        fts.commit();
    }

    @OnClick(R.id.iv_back)
    void back(){
        onBackPressed();
    }


    @Override
    public void onBackPressed() {
        fts = this.getSupportFragmentManager().beginTransaction();
        fragmentManager = this.getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            if (fragmentManager.getBackStackEntryCount() == 1) {
                tv_title.setText("My Tickets");
                iv_attachment.setVisibility(View.GONE);
                iv_send.setVisibility(View.GONE);
            }
            fragmentManager.popBackStackImmediate();
            fts.commit();
        } else {
            finish();
        }
    }

    private void getTickets(boolean showProgress) {
        if (Utility.getInstance().isNetworkAvailable(this)) {
            if (showProgress) {
                ProgressDialog.showDialog(this);
            }
            HashMap hashMap = new HashMap();
            hashMap.put("security_token", Constant.getInstance().getSecurityToken());
            RestClient.getInstance().getBaseUrl().myTickets(hashMap).enqueue(new APICallBack<TicketsResponse>() {
                @Override
                protected void success(TicketsResponse ticketsResponse) {
                    ProgressDialog.dismissDialog(MyTicketsActivity.this);
                    if (ticketsResponse != null) {
                        if (ticketsResponse.getSuccess() == 1) {
                            if (ticketsResponse.getData().size() > 0) {
                                setAdapter(ticketsResponse.getData());
                                rl_no_tickets.setVisibility(View.GONE);
                            }else {
                                ticketsList.setVisibility(View.GONE);
                                rl_no_tickets.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Utility.getInstance().showSnackBar(mainLayout, ticketsResponse.getMessage());
                        }
                    }
                }

                @Override
                protected void failure(String errorMsg) {
                    ProgressDialog.dismissDialog(MyTicketsActivity.this);
                }

                @Override
                protected void onFailure(String failureReason) {
                    ProgressDialog.dismissDialog(MyTicketsActivity.this);
                }
            });
        } else {
            ProgressDialog.dismissDialog(MyTicketsActivity.this);
            Utility.getInstance().showSnackBar(mainLayout, "Please check your Internet Connection");
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
        if (event.message.contains("ticketAdded")||event.message.contains("ticketDeleted")) {
            EventBus.getDefault().removeStickyEvent(event);
            tv_title.setText("My Tickets");
            iv_attachment.setVisibility(View.GONE);
            iv_send.setVisibility(View.GONE);
            getTickets(false);
        }

    }
}
