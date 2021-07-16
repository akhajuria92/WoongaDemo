package com.android.woonga.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.woonga.R;
import com.android.woonga.adapters.AdapterAttachments;
import com.android.woonga.adapters.AdapterComments;
import com.android.woonga.adapters.AdapterImageUpload;
import com.android.woonga.response.SuccessBean;
import com.android.woonga.response.TicketDetailsResponse;
import com.android.woonga.utils.Constant;
import com.android.woonga.utils.MessageEvent;
import com.android.woonga.utils.ProgressDialog;
import com.android.woonga.utils.Utility;
import com.android.woonga.webapi.APICallBack;
import com.android.woonga.webapi.RestClient;
import com.google.android.material.tabs.TabLayout;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TicketDetailActivity extends AppCompatActivity {

    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

    @BindView(R.id.rl_main)
    RelativeLayout rl_main;

    @BindView(R.id.rl_attachments)
    RelativeLayout rl_attachments;

    @BindView(R.id.ll_comments)
    LinearLayout ll_comments;

    @BindView(R.id.rl_comments)
    RelativeLayout rl_comments;

    @BindView(R.id.tv_subject)
    TextView tv_subject;

    @BindView(R.id.tv_description)
    TextView tv_description;

    @BindView(R.id.attachment_viewpager)
    ViewPager attachment_viewpager;

    @BindView(R.id.layoutDots)
    TabLayout layoutDots;

    @BindView(R.id.commentsList)
    RecyclerView commentsList;

    @BindView(R.id.edt_comment)
    EditText edt_comment;

    @BindView(R.id.iv_send)
    ImageView iv_send;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.status)
    TextView status;

    @BindView(R.id.tv_time)
    TextView tv_time;

    @BindView(R.id.tv_id)
    TextView tv_id;

    @BindView(R.id.tv_sent_by)
    TextView tv_sent_by;

    @BindView(R.id.iv_delete)
    ImageView iv_delete;

    @BindView(R.id.topView)
    View topView;

    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    int ticketId = 0;
    AdapterComments adapterComments;
    List<TicketDetailsResponse.Comment> comments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        setUi();
        if (bundle != null) {
            ticketId = bundle.getInt("ticketId");
        }
        if (ticketId != 0) {
            tv_title.setText("Ticket detail");
            getTicketDetails(ticketId + "");
        }
    }

    private void setUi() {
        commentsList.setLayoutManager(new LinearLayoutManager(this));

    }

    private void getTicketDetails(String ticketId) {
        if (Utility.getInstance().isNetworkAvailable(this)) {
            ProgressDialog.showDialog(this);

            HashMap hashMap = new HashMap();
            hashMap.put("security_token", Constant.getInstance().getSecurityToken());
            hashMap.put("ticket_id", ticketId);

            RestClient.getInstance().getBaseUrl().getTicketDetails(hashMap).enqueue(new APICallBack<TicketDetailsResponse>() {
                @Override
                protected void success(TicketDetailsResponse ticketDetailsResponse) {
                    ProgressDialog.dismissDialog(TicketDetailActivity.this);
                    if (ticketDetailsResponse != null) {
                        if (ticketDetailsResponse.getSuccess() == 1) {
                            rl_main.setVisibility(View.VISIBLE);
                            setData(ticketDetailsResponse.getData());
                        } else {
                            Utility.getInstance().showSnackBar(mainLayout, ticketDetailsResponse.getMessage());
                        }
                    }
                }

                @Override
                protected void failure(String errorMsg) {
                    ProgressDialog.dismissDialog(TicketDetailActivity.this);
                }

                @Override
                protected void onFailure(String failureReason) {
                    ProgressDialog.dismissDialog(TicketDetailActivity.this);
                }
            });
        } else {
            Utility.getInstance().showSnackBar(mainLayout, "Please check your Internet Connection");
        }
    }


    private void setData(TicketDetailsResponse.Data ticketDetails) {
        if (ticketDetails.getSubject() != null) {
            tv_subject.setText(ticketDetails.getSubject());
        }
        if (ticketDetails.getDescription() != null) {
            tv_description.setText(ticketDetails.getDescription());
        }
        if (ticketDetails.getImages() != null && !ticketDetails.getImages().equalsIgnoreCase("")) {
            rl_attachments.setVisibility(View.VISIBLE);
            ArrayList<String> images = new ArrayList<String>(Arrays.asList(ticketDetails.getImages().split(",")));
            AdapterAttachments adapterAttachments = new AdapterAttachments(this, images);
            layoutDots.setupWithViewPager(attachment_viewpager);
            attachment_viewpager.setAdapter(adapterAttachments);
        }
        if (ticketDetails.getComments() != null) {
            if (ticketDetails.getComments().size() > 0) {
                commentsList.setVisibility(View.VISIBLE);
                comments.addAll(ticketDetails.getComments());
                adapterComments = new AdapterComments(this, comments);
                commentsList.setAdapter(adapterComments);
            }
        }
        if (ticketDetails.getIsActive() == 1) {
            rl_comments.setVisibility(View.VISIBLE);
            topView.setBackgroundColor(getResources().getColor(R.color.green));
            status.setText("Status: Open");
            status.setTextColor(getResources().getColor(R.color.green));
        } else {
            rl_comments.setVisibility(View.GONE);
            topView.setBackgroundColor(Color.RED);
            status.setText("Status: Closed");
            status.setTextColor(Color.RED);
        }

        if (ticketDetails.getIsAdmin() == 1) {
            tv_sent_by.setVisibility(View.VISIBLE);
            iv_delete.setVisibility(View.INVISIBLE);
            tv_sent_by.setText("Sent by: Admin");
        }else {
            tv_sent_by.setVisibility(View.GONE);
            iv_delete.setVisibility(View.VISIBLE);
        }

        if (ticketDetails.getCreatedAt() != null) {
            tv_time.setText("Sent on: " + time_format(ticketDetails.getCreatedAt()));
        }

        if (ticketDetails.getId() != null) {
            tv_id.setText("Reference: TKT" + ticketDetails.getId());
        }
    }

    public String time_format(String time) {
        try {
            final SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            curFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
            final Date dateObj = curFormater.parse(time);

            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy, h:mm a"); //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault());
            time = dateFormatter.format(dateObj);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return time;
    }


    @OnClick(R.id.iv_delete)
    void deleteTicket() {
        deleteTicket(ticketId + "");
    }

    private void deleteTicket(String ticketId) {
        if (Utility.getInstance().isNetworkAvailable(this)) {
            ProgressDialog.showDialog(this);

            HashMap hashMap = new HashMap();
            hashMap.put("security_token", Constant.getInstance().getSecurityToken());
            hashMap.put("ticket_id", ticketId);

            RestClient.getInstance().getBaseUrl().deleteTicket(hashMap).enqueue(new APICallBack<SuccessBean>() {
                @Override
                protected void success(SuccessBean successBean) {
                    ProgressDialog.dismissDialog(TicketDetailActivity.this);
                    if (successBean != null) {
                        if (successBean.getSuccess() == 1) {
                            EventBus.getDefault().postSticky(new MessageEvent("ticketDeleted"));
                            Utility.getInstance().showToast(TicketDetailActivity.this, "Ticket deleted successfully!");
                            finish();
                        } else {
                            Utility.getInstance().showSnackBar(mainLayout, successBean.getMessage());
                        }
                    }
                }

                @Override
                protected void failure(String errorMsg) {

                    ProgressDialog.dismissDialog(TicketDetailActivity.this);

                }

                @Override
                protected void onFailure(String failureReason) {
                    ProgressDialog.dismissDialog(TicketDetailActivity.this);
                }
            });
        } else {
            Utility.getInstance().showSnackBar(mainLayout, "Please check your Internet Connection");
        }
    }

    @OnClick(R.id.iv_back)
    void back() {
        onBackPressed();
    }

    @OnClick(R.id.sendComment)
    void send() {
        String comment = edt_comment.getText().toString().trim();
        if (comment.equalsIgnoreCase("")) {
            Utility.getInstance().showSnackBar(mainLayout, "Please enter comment");
        } else {
            Utility.getInstance().hideKeyBoard(this);
            addCommentToList(comment);
            sendComment(comment, ticketId + "");
        }
    }

    private void addCommentToList(String comment) {
        edt_comment.setText("");
        TicketDetailsResponse.Comment comment1 = new TicketDetailsResponse.Comment();
        comment1.setComment(comment);
        comment1.setCreatedAt(getCurrentTimeStamp());
        comment1.setCommentBy(Integer.valueOf(Constant.getInstance().getUserId()));
        comments.add(comment1);
        if (adapterComments == null) {
            commentsList.setVisibility(View.VISIBLE);
            adapterComments = new AdapterComments(TicketDetailActivity.this, comments);
            commentsList.setAdapter(adapterComments);
        } else {
            adapterComments.notifyItemInserted(comments.size() - 1);
        }
        scrollToBottom();
    }

    private void sendComment(String comment, String ticketId) {
        if (Utility.getInstance().isNetworkAvailable(this)) {
            HashMap hashMap = new HashMap();
            hashMap.put("security_token", Constant.getInstance().getSecurityToken());
            hashMap.put("comment", comment);
            hashMap.put("ticket_id", ticketId);

            RestClient.getInstance().getBaseUrl().addComment(hashMap).enqueue(new APICallBack<SuccessBean>() {
                @Override
                protected void success(SuccessBean successBean) {
                    ProgressDialog.dismissDialog(TicketDetailActivity.this);
                    if (successBean != null) {
                        if (successBean.getSuccess() == 1) {

                        }
                    }
                }

                @Override
                protected void failure(String errorMsg) {

                    ProgressDialog.dismissDialog(TicketDetailActivity.this);

                }

                @Override
                protected void onFailure(String failureReason) {
                    ProgressDialog.dismissDialog(TicketDetailActivity.this);
                }
            });
        } else {
            Utility.getInstance().showSnackBar(mainLayout, "Please check your Internet Connection");
        }
    }

    public String getCurrentTimeStamp() {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        return sdf.format(new Date());

    }

    private void scrollToBottom() {
        nestedScrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final int scrollViewHeight = nestedScrollView.getHeight();
                if (scrollViewHeight > 0) {
                    nestedScrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    final View lastView = nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);
                    final int lastViewBottom = lastView.getBottom() + nestedScrollView.getPaddingBottom();
                    final int deltaScrollY = lastViewBottom - scrollViewHeight - nestedScrollView.getScrollY();
                    /* If you want to see the scroll animation, call this. */
                    nestedScrollView.smoothScrollBy(0, deltaScrollY);
                }
            }
        });
    }
}
