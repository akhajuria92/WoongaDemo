package com.android.woonga.views.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.woonga.BuildConfig;
import com.android.woonga.R;
import com.android.woonga.adapters.AdapterCoupon;
import com.android.woonga.response.AddVideoPointsResponse;
import com.android.woonga.response.AddVisitedResponse;
import com.android.woonga.response.ClickLogResponse;
import com.android.woonga.response.DashboardBannersResponse;
import com.android.woonga.response.DashboardOffersResponse;
import com.android.woonga.response.OfferDetailResponse;
import com.android.woonga.utils.Constant;
import com.android.woonga.utils.MessageEvent;
import com.android.woonga.utils.ProgressDialog;
import com.android.woonga.utils.Utility;
import com.android.woonga.webapi.APICallBack;
import com.android.woonga.webapi.RestClient;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.views.YouTubePlayerSeekBar;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.internal.Util;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class OfferDetailActivity extends AppCompatActivity {

/*    @BindView(R.id.shortDescription)
    TextView shortDescription;

    @BindView(R.id.icon)
    ImageView icon;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.iv)
    ImageView iv;

    @BindView(R.id.description)
    TextView description;

    @BindView(R.id.steps)
    TextView steps;

    @BindView(R.id.note)
    TextView note;

    @BindView(R.id.payOut)
    TextView payOut;

    @BindView(R.id.tv_coupon)
    TextView tv_coupon;

    @BindView(R.id.couponList)
    RecyclerView couponList;

    @BindView(R.id.tv_reward)
    TextView tv_reward;

    @BindView(R.id.tv_new_users)
    TextView tv_new_users;

    @BindView(R.id.priceOut)
    TextView priceOut;

    @BindView(R.id.button)
    Button button;

    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

    @BindView(R.id.rl_coupon)
    RelativeLayout rl_coupon;

    @BindView(R.id.ll_reward)
    LinearLayout ll_reward;

    @BindView(R.id.frame)
    FrameLayout frame;

    @BindView(R.id.youtube_player_view)
    YouTubePlayerView youtube_player_view;*/

    // New layout

    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

    @BindView(R.id.iv1)
    ImageView iv1;

    @BindView(R.id.iv2)
    ImageView iv2;

    @BindView(R.id.title1)
    TextView title1;

    @BindView(R.id.priceOut1)
    TextView priceOut1;

    @BindView(R.id.shortDescription1)
    TextView shortDescription1;

    @BindView(R.id.title2)
    TextView title2;

    @BindView(R.id.priceOut2)
    TextView priceOut2;

    @BindView(R.id.shortDescription2)
    TextView shortDescription2;

    @BindView(R.id.description)
    WebView description;

    @BindView(R.id.button)
    Button button;

    @BindView(R.id.tvDate)
    TextView tvDate;

    @BindView(R.id.title3)
    TextView title3;

    @BindView(R.id.note)
    TextView note;

    @BindView(R.id.priceOut3)
    TextView priceOut3;

    @BindView(R.id.rl_coupon)
    RelativeLayout rl_coupon;

    @BindView(R.id.tv_coupon)
    TextView tv_coupon;

    @BindView(R.id.couponList)
    RecyclerView couponList;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.bottomView)
    CardView bottomView;


    DashboardOffersResponse.Offers offers;
    DashboardBannersResponse.TopBanner topBanner;
    DashboardBannersResponse.BottomBanner bottomBanner;
    boolean fromCategories, fromCashback, fromRewards, fromNotifications;
    int categoryId;
    AdapterCoupon adapterCoupon;
    List<DashboardOffersResponse.OfferCoupon> coupons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_detail);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();

        description.getSettings().setDefaultFontSize(12);

        if (bundle != null) {
            offers = (DashboardOffersResponse.Offers) bundle.getSerializable("offerData");
            topBanner = (DashboardBannersResponse.TopBanner) bundle.getSerializable("topBannerData");
            bottomBanner = (DashboardBannersResponse.BottomBanner) bundle.getSerializable("bottomBannerData");
            fromCategories = bundle.getBoolean("fromCategories", false);
            fromCashback = bundle.getBoolean("fromCashback", false);
            fromRewards = bundle.getBoolean("fromRewards", false);
            categoryId = bundle.getInt("categoryId");
            if (bundle.containsKey("fromNotifications")) {
                String categoryId = bundle.getString("categoryId");
                String offerId = bundle.getString("offerId");
                fromNotifications = bundle.getBoolean("fromNotifications");
                getOfferDetails(categoryId, offerId);
            }
            setCouponAdapter();
        }

        if (offers != null) {
            // setData();
            setDataInNewView();
        } else if (topBanner != null) {
            // setTopBannerData();
            setTopBannerDataInNewView();
        } else if (bottomBanner != null) {
            // setBottomBannerData();
            setBottomBannerDataInNewView();
        }
    }

    private void setCouponAdapter() {
        couponList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getOfferDetails(String categoryId, String offerId) {
        if (Utility.getInstance().isNetworkAvailable(this)) {
            ProgressDialog.showDialog(this);
            HashMap hashMap = new HashMap();
            hashMap.put("security_token", Constant.getInstance().getSecurityToken());
            hashMap.put("category_id", categoryId);
            hashMap.put("offer_id", offerId);

            RestClient.getInstance().getBaseUrl().offerDetails(hashMap).enqueue(new APICallBack<OfferDetailResponse>() {
                @Override
                protected void success(OfferDetailResponse offerDetailResponse) {
                    ProgressDialog.dismissDialog(OfferDetailActivity.this);
                    if (offerDetailResponse != null) {
                        if (offerDetailResponse.getSuccess() == 1) {
                            offers = offerDetailResponse.getOffersDetails();
                            // setData();
                            setDataInNewView();
                        }
                    }
                }

                @Override
                protected void failure(String errorMsg) {
                    ProgressDialog.dismissDialog(OfferDetailActivity.this);
                }

                @Override
                protected void onFailure(String failureReason) {
                    ProgressDialog.dismissDialog(OfferDetailActivity.this);
                }
            });
        } else {
            Utility.getInstance().showSnackBar(mainLayout, "Please check your Internet Connection");
        }
    }

    void setFontOnWebView(String textValue) {
//        String head1 = "<head><style>body {font-family: 'poppins_regular.otf';}</style></head>";
//        String text="<html>"
//                + "<body style=font-family: poppins_regular.otf>" + textValue
//                + "</body></html>";

        String head1 = "<head><style>@font-face {font-family: 'poppins_regular';src: url('file:///android_asset/fonts/poppins_regular.otf');}body {font-family: 'poppins_regular';}</style></head>";
        String text = "<html>" + head1
                + "<body style=\"font-family: poppins_regular\">" + textValue
                + "</body></html>";
        description.loadData(text, "text/html", "UTF-8");
    }
    /* public void setData() {
         ProgressDialog.showDialog(this);
         if (offers.getShortDescription() != null) {
             shortDescription.setText(offers.getShortDescription());
         }
         if (offers.getIcon() != null) {
             Glide.with(this).load(offers.getIcon()).into(icon);
         } else {
             Glide.with(this).load(R.mipmap.ic_logo_round).into(icon);
         }
         if (offers.getOfferCoupon() != null && offers.getOfferCoupon().size() > 0) {
             List<DashboardOffersResponse.OfferCoupon> futureCoupons = new ArrayList<>(getCouponsInFuture(offers.getOfferCoupon()));
             if (futureCoupons.size() > 0) {
                 if (offers.getOfferFor().equalsIgnoreCase("1")) {
                     tv_new_users.setVisibility(View.VISIBLE);
                 } else {
                     tv_new_users.setVisibility(View.GONE);
                 }
                 tv_coupon.setVisibility(View.VISIBLE);
                 rl_coupon.setVisibility(View.VISIBLE);

                 adapterCoupon = new AdapterCoupon(this, futureCoupons);
                 couponList.setAdapter(adapterCoupon);
             }
         }
         if (offers.getTitle() != null) {
             title.setText(offers.getTitle());
         }
         if (offers.getDescription() != null) {
             description.setText(Html.fromHtml(offers.getDescription()));
         }
         if (offers.getStepToEarn() != null) {
             steps.setVisibility(View.VISIBLE);
             steps.setText(Html.fromHtml(offers.getStepToEarn()));
         }
         if (offers.getNote() != null) {
             note.setVisibility(View.VISIBLE);
             note.setText(Html.fromHtml(offers.getNote()));
         }

         if (offers.getButtonName() != null) {
             button.setText(offers.getButtonName());
         }
         if (offers.getPayout() != null) {
             if (!offers.getPayout().equalsIgnoreCase("0.00")) {
                 //ll_reward.setVisibility(View.VISIBLE);
                 frame.setVisibility(View.VISIBLE);
                 priceOut.setText(offers.getPayout());
                 payOut.setText("₹ " + offers.getPayout());
             }
         } else {
             // ll_reward.setVisibility(View.VISIBLE);
             frame.setVisibility(View.VISIBLE);
             priceOut.setText(offers.getPercentage() + "% ");
             payOut.setText(offers.getPercentage() + "% " + offers.getMessage());
         }
         if (offers.getDisplayImage() != null && !offers.getDisplayImage().equalsIgnoreCase("")) {
             Glide.with(this).load(offers.getDisplayImage()).addListener(new RequestListener<Drawable>() {
                 @Override
                 public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                     ProgressDialog.dismissDialog(OfferDetailActivity.this);
                     return false;
                 }

                 @Override
                 public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                     ProgressDialog.dismissDialog(OfferDetailActivity.this);
                     mainLayout.setVisibility(View.VISIBLE);
                     return false;
                 }
             }).into(iv);
         } else {
             ProgressDialog.dismissDialog(this);

         }

         if (offers.getTrackingLink() != null) {
             if (isYoutubeUrl(offers.getTrackingLink())) {
                 button.setVisibility(View.GONE);
                 youtube_player_view.setVisibility(View.VISIBLE);
                 getLifecycle().addObserver(youtube_player_view);

                 youtube_player_view.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                     @Override
                     public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                         youTubePlayer.loadVideo(extractVideoIdFromUrl(offers.getTrackingLink()), 0);
                         youTubePlayer.pause();
                     }

                     @Override
                     public void onStateChange(YouTubePlayer youTubePlayer, PlayerConstants.PlayerState state) {
                         if (state.equals(PlayerConstants.PlayerState.ENDED)) {
                             addVideoPoints(offers.getId() + "", offers.getPayout());
                         }
                     }
                 });
             } else {
                 button.setVisibility(View.VISIBLE);
                 setClickOnButton(offers.getTrackingLink(), true, "1", offers.getIsUnique());
             }

         }
     }
 */

    @OnClick(R.id.iv_back)
    void button() {
        onBackPressed();
    }

    public void setDataInNewView() {
        ProgressDialog.dismissDialog(OfferDetailActivity.this);
        tv_title.setText("Offer Detail");
        if (offers.getTitle() != null) {
            title1.setText(offers.getTitle());
        }
        if (offers.getTitle2() != null && !offers.getTitle2().equalsIgnoreCase("")) {
            title2.setText(offers.getTitle2());
        } else {
            title2.setText(getString(R.string.todays_task));
        }
        if (offers.getShortDescription() != null) {
            shortDescription1.setText(offers.getShortDescription());
        }
        if (offers.getShortDescription3() == null && offers.getRewardsAfterHowManyDays() == null) {
            bottomView.setVisibility(View.GONE);
        } else {
            bottomView.setVisibility(View.VISIBLE);
            if (offers.getRewardsAfterHowManyDays() != null) {
                tvDate.setText(getDateAfterDays(Integer.parseInt(offers.getRewardsAfterHowManyDays())));
                title3.setText("Use app for " + offers.getRewardsAfterHowManyDays() + " days");
            } else {
                tvDate.setVisibility(View.INVISIBLE);
            }
            if (offers.getShortDescription3() != null) {
                title3.setText(offers.getShortDescription3());
            }
        }
        if (offers.getIcon() != null) {
            Glide.with(this).load(offers.getIcon()).into(iv1);
        } else {
            Glide.with(this).load(R.mipmap.ic_logo_round).into(iv1);
        }
        /*if (offers.getIcon2() != null) {
            Glide.with(this).load(offers.getIcon2()).into(iv2);
        } else {
            Glide.with(this).load(R.mipmap.ic_logo_round).into(iv2);
        }*/
        if (offers.getOfferCoupon() != null && offers.getOfferCoupon().size() > 0) {
            List<DashboardOffersResponse.OfferCoupon> futureCoupons = new ArrayList<>(getCouponsInFuture(offers.getOfferCoupon()));
            if (futureCoupons.size() > 0) {
                /*if (offers.getOfferFor().equalsIgnoreCase("1")) {
                    tv_new_users.setVisibility(View.VISIBLE);
                } else {
                    tv_new_users.setVisibility(View.GONE);
                }*/
                tv_coupon.setVisibility(View.VISIBLE);
                rl_coupon.setVisibility(View.VISIBLE);

                adapterCoupon = new AdapterCoupon(this, futureCoupons);
                couponList.setAdapter(adapterCoupon);
            }
        }

        if (offers.getDescription() != null) {
            setFontOnWebView(offers.getDescription());
//            description.loadData(offers.getDescription(),"text/html", "UTF-8");
//            description.setText(Html.fromHtml(offers.getDescription()));
        }

        if (offers.getNote() != null) {
            note.setVisibility(View.VISIBLE);
            note.setText(Html.fromHtml(offers.getNote()));
        }

        if (offers.getButtonName() != null) {
            button.setText(offers.getButtonName());
            shortDescription2.setText(offers.getButtonName());
        }
        if (offers.getPayout() != null && offers.getPercentage() == null) {
            if (!offers.getPayout().equalsIgnoreCase("0.00")) {
                priceOut1.setVisibility(View.VISIBLE);
                priceOut1.setText("Reward ₹ " + offers.getPayout());
            } else {
                priceOut1.setVisibility(View.GONE);
            }
            if (offers.getPayout2() != null) {
                if (!offers.getPayout2().equalsIgnoreCase("0.00")) {
                    priceOut2.setVisibility(View.VISIBLE);
                    priceOut2.setText("Reward ₹ " + offers.getPayout2());
                } else {
                    priceOut2.setVisibility(View.GONE);
                }
            } else {
                priceOut2.setVisibility(View.GONE);
            }

            if (offers.getPayout3() != null) {
                if (!offers.getPayout3().equalsIgnoreCase("0.00")) {
                    priceOut3.setVisibility(View.VISIBLE);
                    priceOut3.setText("Reward ₹ " + offers.getPayout3());
                } else {
                    priceOut3.setVisibility(View.GONE);
                }
            } else {
                priceOut3.setVisibility(View.GONE);
            }
        } else {
            // ll_reward.setVisibility(View.VISIBLE);
            priceOut1.setText(offers.getPercentage() + "% " + offers.getMessage());
            priceOut2.setVisibility(View.GONE);
            priceOut3.setVisibility(View.GONE);
        }

        if (offers.getTrackingLink() != null) {
            button.setVisibility(View.VISIBLE);
            setClickOnButton(offers.getTrackingLink(), true, "1", offers.getIsUnique());
        }
    }

    public void setTopBannerDataInNewView() {
        ProgressDialog.dismissDialog(OfferDetailActivity.this);
        tv_title.setText("Offer Detail");
        if (topBanner.getTitle() != null) {
            title1.setText(topBanner.getTitle());
        }
        if (topBanner.getTitle2() != null && !topBanner.getTitle2().equalsIgnoreCase("")) {
            title2.setText(topBanner.getTitle2());
        } else {
            title2.setText(getString(R.string.todays_task));
        }
        if (topBanner.getShortDescription() != null) {
            shortDescription1.setText(topBanner.getShortDescription());
        }
        if (topBanner.getShortDescription3() == null && topBanner.getRewardsAfterHowManyDays() == null) {
            bottomView.setVisibility(View.GONE);
        } else {
            bottomView.setVisibility(View.VISIBLE);
            if (topBanner.getRewardsAfterHowManyDays() != null) {
                tvDate.setText(getDateAfterDays(Integer.parseInt(topBanner.getRewardsAfterHowManyDays())));
                title3.setText("Use app for " + topBanner.getRewardsAfterHowManyDays() + " days");
            } else {
                tvDate.setVisibility(View.INVISIBLE);
            }
            if (topBanner.getShortDescription3() != null) {
                title3.setText(topBanner.getShortDescription3());
            }
        }
        if (topBanner.getIcon() != null) {
            Glide.with(this).load(topBanner.getIcon()).into(iv1);
        } else {
            Glide.with(this).load(R.mipmap.ic_logo_round).into(iv1);
        }
       /* if (topBanner.getIcon2() != null) {
            Glide.with(this).load(topBanner.getIcon2()).into(iv2);
        } else {
            Glide.with(this).load(R.mipmap.ic_logo_round).into(iv2);
        }*/
        if (topBanner.getOfferCoupon() != null && topBanner.getOfferCoupon().size() > 0) {
            List<DashboardOffersResponse.OfferCoupon> futureCoupons = new ArrayList<>(getCouponsInFuture(topBanner.getOfferCoupon()));
            if (futureCoupons.size() > 0) {
                /*if (offers.getOfferFor().equalsIgnoreCase("1")) {
                    tv_new_users.setVisibility(View.VISIBLE);
                } else {
                    tv_new_users.setVisibility(View.GONE);
                }*/
                tv_coupon.setVisibility(View.VISIBLE);
                rl_coupon.setVisibility(View.VISIBLE);

                adapterCoupon = new AdapterCoupon(this, futureCoupons);
                couponList.setAdapter(adapterCoupon);
            }
        }

        if (topBanner.getDescription() != null) {
            setFontOnWebView(topBanner.getDescription());
//            description.loadData(topBanner.getDescription(),"text/html", "UTF-8");
//            description.setText(Html.fromHtml(topBanner.getDescription()));
        }

        if (topBanner.getNote() != null) {
            note.setVisibility(View.VISIBLE);
            note.setText(Html.fromHtml(topBanner.getNote()));
        }

        if (topBanner.getButtonName() != null) {
            button.setText(topBanner.getButtonName());
            shortDescription2.setText(topBanner.getButtonName());
        }
        if (topBanner.getPayout() != null && topBanner.getPercentage() == null) {
            if (!topBanner.getPayout().equalsIgnoreCase("0.00")) {
                priceOut1.setVisibility(View.VISIBLE);
                priceOut1.setText("Reward ₹ " + topBanner.getPayout());
            } else {
                priceOut1.setVisibility(View.GONE);
            }
            if (topBanner.getPayout2() != null) {
                if (!topBanner.getPayout2().equalsIgnoreCase("0.00")) {
                    priceOut2.setVisibility(View.VISIBLE);
                    priceOut2.setText("Reward ₹ " + topBanner.getPayout2());
                } else {
                    priceOut2.setVisibility(View.GONE);
                }
            } else {
                priceOut2.setVisibility(View.GONE);
            }

            if (topBanner.getPayout3() != null) {
                if (!topBanner.getPayout3().equalsIgnoreCase("0.00")) {
                    priceOut3.setVisibility(View.VISIBLE);
                    priceOut3.setText("Reward ₹ " + topBanner.getPayout3());
                } else {
                    priceOut3.setVisibility(View.GONE);
                }
            } else {
                priceOut3.setVisibility(View.GONE);
            }
        } else {
            // ll_reward.setVisibility(View.VISIBLE);
            priceOut1.setText(topBanner.getPercentage() + "% " + topBanner.getMessage());
            priceOut2.setVisibility(View.GONE);
            priceOut3.setVisibility(View.GONE);
        }

        if (topBanner.getTrackingLink() != null) {
            button.setVisibility(View.VISIBLE);
            setClickOnButton(topBanner.getTrackingLink(), false, "2", 0);
        }
    }

    public void setBottomBannerDataInNewView() {
        ProgressDialog.dismissDialog(OfferDetailActivity.this);
        tv_title.setText("Offer Detail");
        if (bottomBanner.getTitle() != null) {
            title1.setText(bottomBanner.getTitle());
        }
        if (bottomBanner.getTitle2() != null && !bottomBanner.getTitle2().equalsIgnoreCase("")) {
            title2.setText(bottomBanner.getTitle2());
        } else {
            title2.setText(getString(R.string.todays_task));
        }
        if (bottomBanner.getShortDescription() != null) {
            shortDescription1.setText(bottomBanner.getShortDescription());
        }
        if (bottomBanner.getShortDescription3() == null && bottomBanner.getRewardsAfterHowManyDays() == null) {
            bottomView.setVisibility(View.GONE);
        } else {
            bottomView.setVisibility(View.VISIBLE);
            if (bottomBanner.getRewardsAfterHowManyDays() != null) {
                tvDate.setText(getDateAfterDays(Integer.parseInt(bottomBanner.getRewardsAfterHowManyDays())));
                title3.setText("Use app for " + bottomBanner.getRewardsAfterHowManyDays() + " days");
            } else {
                tvDate.setVisibility(View.INVISIBLE);
            }
            if (bottomBanner.getShortDescription3() != null) {
                title3.setText(bottomBanner.getShortDescription3());
            }
        }
        if (bottomBanner.getIcon() != null) {
            Glide.with(this).load(bottomBanner.getIcon()).into(iv1);
        } else {
            Glide.with(this).load(R.mipmap.ic_logo_round).into(iv1);
        }
        /*if (bottomBanner.getIcon2() != null) {
            Glide.with(this).load(bottomBanner.getIcon2()).into(iv2);
        } else {
            Glide.with(this).load(R.mipmap.ic_logo_round).into(iv2);
        }*/
        if (bottomBanner.getOfferCoupon() != null && bottomBanner.getOfferCoupon().size() > 0) {
            List<DashboardOffersResponse.OfferCoupon> futureCoupons = new ArrayList<>(getCouponsInFuture(offers.getOfferCoupon()));
            if (futureCoupons.size() > 0) {
                /*if (offers.getOfferFor().equalsIgnoreCase("1")) {
                    tv_new_users.setVisibility(View.VISIBLE);
                } else {
                    tv_new_users.setVisibility(View.GONE);
                }*/
                tv_coupon.setVisibility(View.VISIBLE);
                rl_coupon.setVisibility(View.VISIBLE);

                adapterCoupon = new AdapterCoupon(this, futureCoupons);
                couponList.setAdapter(adapterCoupon);
            }
        }

        if (bottomBanner.getDescription() != null) {
            setFontOnWebView(bottomBanner.getDescription());
//            description.loadData(bottomBanner.getDescription(),"text/html", "UTF-8");
//            description.setText(Html.fromHtml(bottomBanner.getDescription()));
        }

        if (bottomBanner.getNote() != null) {
            note.setVisibility(View.VISIBLE);
            note.setText(Html.fromHtml(bottomBanner.getNote()));
        }

        if (bottomBanner.getButtonName() != null) {
            button.setText(bottomBanner.getButtonName());
            shortDescription2.setText(bottomBanner.getButtonName());
        }
        if (bottomBanner.getPayout() != null && bottomBanner.getPercentage() == null) {
            if (!bottomBanner.getPayout().equalsIgnoreCase("0.00")) {
                priceOut1.setVisibility(View.VISIBLE);
                priceOut1.setText("Reward ₹ " + bottomBanner.getPayout());
            } else {
                priceOut1.setVisibility(View.GONE);
            }
            if (bottomBanner.getPayout2() != null) {
                if (!bottomBanner.getPayout2().equalsIgnoreCase("0.00")) {
                    priceOut2.setVisibility(View.VISIBLE);
                    priceOut2.setText("Reward ₹ " + bottomBanner.getPayout2());
                } else {
                    priceOut2.setVisibility(View.GONE);
                }
            } else {
                priceOut2.setVisibility(View.GONE);
            }

            if (bottomBanner.getPayout3() != null) {
                if (!bottomBanner.getPayout3().equalsIgnoreCase("0.00")) {
                    priceOut3.setVisibility(View.VISIBLE);
                    priceOut3.setText("Reward ₹ " + bottomBanner.getPayout3());
                } else {
                    priceOut3.setVisibility(View.GONE);
                }
            } else {
                priceOut3.setVisibility(View.GONE);
            }
        } else {
            // ll_reward.setVisibility(View.VISIBLE);
            priceOut1.setText(bottomBanner.getPercentage() + "% " + bottomBanner.getMessage());
            priceOut2.setVisibility(View.GONE);
            priceOut3.setVisibility(View.GONE);
        }

        if (bottomBanner.getTrackingLink() != null) {
            button.setVisibility(View.VISIBLE);
            setClickOnButton(bottomBanner.getTrackingLink(), false, "3", 0);
        }
    }

    private void addVideoPoints(String offerId, String payout) {
        if (Utility.getInstance().isNetworkAvailable(this)) {
            ProgressDialog.showDialog(this);
            HashMap hashMap = new HashMap();
            hashMap.put("security_token", Constant.getInstance().getSecurityToken());
            hashMap.put("payout", payout);
            hashMap.put("offer_id", offerId);

            RestClient.getInstance().getBaseUrl().addVideoPoints(hashMap).enqueue(new APICallBack<AddVideoPointsResponse>() {
                @Override
                protected void success(AddVideoPointsResponse addVideoPointsResponse) {
                    ProgressDialog.dismissDialog(OfferDetailActivity.this);
                    if (addVideoPointsResponse != null) {
                        if (addVideoPointsResponse.getSuccess() == 1) {
                            Utility.getInstance().showToast(OfferDetailActivity.this, "Points Added Successfully!");
                            finish();
                        }
                    }
                }

                @Override
                protected void failure(String errorMsg) {
                    ProgressDialog.dismissDialog(OfferDetailActivity.this);
                }

                @Override
                protected void onFailure(String failureReason) {
                    ProgressDialog.dismissDialog(OfferDetailActivity.this);
                }
            });
        } else {
            Utility.getInstance().showSnackBar(mainLayout, "Please check your Internet Connection");
        }
    }


    final String[] videoIdRegex = {"\\?vi?=([^&]*)", "watch\\?.*v=([^&]*)", "(?:embed|vi?)/([^/?]*)", "^([A-Za-z0-9\\-]*)"};

    final String youTubeUrlRegEx = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/";

    public String extractVideoIdFromUrl(String url) {

        String youTubeLinkWithoutProtocolAndDomain = youTubeLinkWithoutProtocolAndDomain(url);

        for (String regex : videoIdRegex) {

            Pattern compiledPattern = Pattern.compile(regex);

            Matcher matcher = compiledPattern.matcher(youTubeLinkWithoutProtocolAndDomain);

            if (matcher.find()) {

                return matcher.group(1);

            }
        }

        return null;

    }

    private String youTubeLinkWithoutProtocolAndDomain(String url) {

        Pattern compiledPattern = Pattern.compile(youTubeUrlRegEx);

        Matcher matcher = compiledPattern.matcher(url);

        if (matcher.find()) {
            return url.replace(matcher.group(), "");
        }

        return url;

    }


    /* public void setTopBannerData() {
         ProgressDialog.showDialog(this);
         if (topBanner.getShortDescription() != null) {
             shortDescription.setText(topBanner.getShortDescription());
         }
         icon.setVisibility(View.GONE);
         title.setVisibility(View.GONE);
         if (topBanner.getOfferCoupon() != null && topBanner.getOfferCoupon().size() > 0) {
             List<DashboardOffersResponse.OfferCoupon> futureCoupons = new ArrayList<>(getCouponsInFuture(topBanner.getOfferCoupon()));
             if (futureCoupons.size() > 0) {
                 if (topBanner.getOffer_for().equalsIgnoreCase("1")) {
                     tv_new_users.setVisibility(View.VISIBLE);
                 } else {
                     tv_new_users.setVisibility(View.GONE);
                 }
                 tv_coupon.setVisibility(View.VISIBLE);
                 rl_coupon.setVisibility(View.VISIBLE);

                 adapterCoupon = new AdapterCoupon(this, futureCoupons);
                 couponList.setAdapter(adapterCoupon);
             }
         }

         if (topBanner.getDescription() != null) {
             description.setText(Html.fromHtml(topBanner.getDescription()));
         }
         if (topBanner.getStepToEarn() != null) {
             steps.setVisibility(View.VISIBLE);
             steps.setText(Html.fromHtml(topBanner.getStepToEarn()));
         }
         if (topBanner.getNote() != null) {
             note.setVisibility(View.VISIBLE);
             note.setText(Html.fromHtml(topBanner.getNote()));
         }
         if (topBanner.getButtonName() != null) {
             button.setText(topBanner.getButtonName());
         }
         if (topBanner.getPayout() != null) {
             if (!topBanner.getPayout().equalsIgnoreCase("0.00")) {
                 //ll_reward.setVisibility(View.VISIBLE);
                 frame.setVisibility(View.VISIBLE);
                 priceOut.setText(topBanner.getPayout());
                 payOut.setText("₹ " + topBanner.getPayout());
             }
         } else {
             // ll_reward.setVisibility(View.VISIBLE);
             frame.setVisibility(View.VISIBLE);
             priceOut.setText(topBanner.getPercentage() + "% ");
             payOut.setText(topBanner.getPercentage() + "% " + topBanner.getMessage());
         }
         if (topBanner.getDisplayImage() != null && !topBanner.getDisplayImage().equalsIgnoreCase("")) {
             Glide.with(this).load(topBanner.getDisplayImage()).addListener(new RequestListener<Drawable>() {
                 @Override
                 public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                     ProgressDialog.dismissDialog(OfferDetailActivity.this);
                     return false;
                 }

                 @Override
                 public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                     ProgressDialog.dismissDialog(OfferDetailActivity.this);
                     mainLayout.setVisibility(View.VISIBLE);
                     return false;
                 }
             }).into(iv);
         } else {
             ProgressDialog.dismissDialog(this);
         }
         if (topBanner.getTrackingLink() != null) {
             setClickOnButton(topBanner.getTrackingLink(), false, "2", 0);
         }
     }


     public void setBottomBannerData() {
         ProgressDialog.showDialog(this);
         if (bottomBanner.getShortDescription() != null) {
             shortDescription.setText(bottomBanner.getShortDescription());
         }
         icon.setVisibility(View.GONE);
         title.setVisibility(View.GONE);

         if (bottomBanner.getOfferCoupon() != null && bottomBanner.getOfferCoupon().size() > 0) {
             List<DashboardOffersResponse.OfferCoupon> futureCoupons = new ArrayList<>(getCouponsInFuture(bottomBanner.getOfferCoupon()));
             if (futureCoupons.size() > 0) {
                 if (bottomBanner.getOffer_for().equalsIgnoreCase("1")) {
                     tv_new_users.setVisibility(View.VISIBLE);
                 } else {
                     tv_new_users.setVisibility(View.GONE);
                 }
                 tv_coupon.setVisibility(View.VISIBLE);
                 rl_coupon.setVisibility(View.VISIBLE);

                 adapterCoupon = new AdapterCoupon(this, futureCoupons);
                 couponList.setAdapter(adapterCoupon);
             }
         }

         if (bottomBanner.getDescription() != null) {
             description.setText(Html.fromHtml(bottomBanner.getDescription()));
         }
         if (bottomBanner.getStepToEarn() != null) {
             steps.setVisibility(View.VISIBLE);
             steps.setText(Html.fromHtml(bottomBanner.getStepToEarn()));
         }
         if (bottomBanner.getNote() != null) {
             note.setVisibility(View.VISIBLE);
             note.setText(Html.fromHtml(bottomBanner.getNote()));
         }
         if (bottomBanner.getButtonName() != null) {
             button.setText(bottomBanner.getButtonName());
         }
         if (bottomBanner.getPayout() != null) {
             if (!bottomBanner.getPayout().equalsIgnoreCase("0.00")) {
                 // ll_reward.setVisibility(View.VISIBLE);
                 frame.setVisibility(View.VISIBLE);
                 priceOut.setText(bottomBanner.getPayout());
                 payOut.setText("₹ " + bottomBanner.getPayout());
             }
         } else {
             // ll_reward.setVisibility(View.VISIBLE);
             frame.setVisibility(View.VISIBLE);
             priceOut.setText(bottomBanner.getPercentage() + "% ");
             payOut.setText(bottomBanner.getPercentage() + "% " + bottomBanner.getMessage());
         }
         if (bottomBanner.getDisplayImage() != null && !bottomBanner.getDisplayImage().equalsIgnoreCase("")) {
             Glide.with(this).load(bottomBanner.getDisplayImage()).addListener(new RequestListener<Drawable>() {
                 @Override
                 public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                     ProgressDialog.dismissDialog(OfferDetailActivity.this);
                     return false;
                 }

                 @Override
                 public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                     ProgressDialog.dismissDialog(OfferDetailActivity.this);
                     mainLayout.setVisibility(View.VISIBLE);
                     return false;
                 }
             }).into(iv);
         } else {
             ProgressDialog.dismissDialog(this);
         }
         if (bottomBanner.getTrackingLink() != null) {
             setClickOnButton(bottomBanner.getTrackingLink(), false, "3", 0);
         }
     }
 */
    private void setClickOnButton(String url, boolean isOffer, String type, int isUnique) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOffer) {
                    if (fromCategories) {
                        if (isUnique == 1) {
                            // addVisitedOffer(categoryId + "", offers.getId() + "");
                        }
                    } else {
                        if (fromCashback) {
                            //  addVisitedOffer(offers.getCategories(), offers.getId() + "");
                        } else if (fromRewards) {
                            if (isUnique == 1) {
                                //  addVisitedOffer("2", offers.getId() + "");
                            }
                        }
                    }
                }
                if (type.equalsIgnoreCase("1")) {
                    clickLog(type, offers.getId() + "", url);
                } else if (type.equalsIgnoreCase("2")) {
                    clickLog(type, topBanner.getId() + "", url);
                } else if (type.equalsIgnoreCase("3")) {
                    clickLog(type, bottomBanner.getId() + "", url);
                }


            }
        });
    }

    private void clickLog(String type, String offerId, final String url) {
        if (Utility.getInstance().isNetworkAvailable(this)) {
            ProgressDialog.showDialog(this);
            String browserRequestUrl = BuildConfig.BASE_URL + "clicklogs/id?security_token=" + Constant.getInstance().getSecurityToken() + "&type=" + type +
                    "&offer_id=" + offerId;

            WebView w = new WebView(this);
            w.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    try {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(browserIntent);
                        ProgressDialog.dismissDialog(OfferDetailActivity.this);
                        finish();
                    } catch (ActivityNotFoundException e) {
                        /*Toast.makeText(this, " You don't have any browser to open web page", Toast.LENGTH_SHORT).show();*/
                    }
                    return true;
                }
            });
            w.loadUrl(browserRequestUrl);


          /*
            try {
                String browserRequestUrl = getResources().getString(R.string.base_url) + "clicklogs/id?security_token=" + Constant.getInstance().getSecurityToken() + "&type=" + type +
                        "&offer_id=" + offerId;

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(browserRequestUrl));
                startActivity(browserIntent);
                finish();
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, " You don't have any browser to open web page", Toast.LENGTH_SHORT).show();
            }*/

            /*ProgressDialog.showDialog(this);
            HashMap hashMap = new HashMap();
            hashMap.put("security_token", Constant.getInstance().getSecurityToken());
            hashMap.put("type", type);
            hashMap.put("offer_id", offerId);

            RestClient.getInstance().getBaseUrl().clickLog(hashMap).enqueue(new APICallBack<ClickLogResponse>() {
                @Override
                protected void success(ClickLogResponse response) {
                    ProgressDialog.dismissDialog(OfferDetailActivity.this);
                    if (response != null) {
                        if (response.getSuccess() == 1) {
                            if (response.getLogId() != null) {
                                String urlLink=url;
                                if (!urlLink.startsWith("http://") && !urlLink.startsWith("https://")) {
                                    urlLink = "http://" + urlLink;
                                }
                                String finalUrl = "";
                                if (urlLink.contains("{log_id}")) {
                                    finalUrl=urlLink.replace("{log_id}", response.getLogId());
                                }

                                if (finalUrl.contains("{google_id}")) {
                                    finalUrl=finalUrl.replace("{google_id}", Constant.getInstance().getGoogleId());
                                }
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalUrl.equalsIgnoreCase("")?urlLink:finalUrl));
                                startActivity(browserIntent);
                                finish();
                            }
                        }
                    }
                }

                @Override
                protected void failure(String errorMsg) {

                }

                @Override
                protected void onFailure(String failureReason) {

                }
            });*/
        } else {
            Utility.getInstance().showSnackBar(mainLayout, "Please check your Internet Connection");
        }
    }

    private void addVisitedOffer(String categoryId, String offerId) {
        if (Utility.getInstance().isNetworkAvailable(this)) {


            HashMap hashMap = new HashMap();
            hashMap.put("security_token", Constant.getInstance().getSecurityToken());
            hashMap.put("offer_category_id", categoryId + "");
            hashMap.put("offer_id", offerId + "");

            RestClient.getInstance().getBaseUrl().addVisited(hashMap).enqueue(new APICallBack<AddVisitedResponse>() {
                @Override
                protected void success(AddVisitedResponse response) {

                    if (response != null) {

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
            Utility.getInstance().showSnackBar(mainLayout, "Please check your Internet Connection");
        }
    }


    @Override
    public void onBackPressed() {
        if (fromNotifications) {
            startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        } else {
            finish();
        }
    }

    public static boolean isYoutubeUrl(String youTubeURl) {
        boolean success;
        String pattern = "^(http(s)?:\\/\\/)?((w){3}.)?youtu(be|.be)?(\\.com)?\\/.+";
        if (!youTubeURl.isEmpty() && youTubeURl.matches(pattern)) {
            success = true;
        } else {
            // Not Valid youtube URL
            success = false;
        }
        return success;
    }

    private boolean isDateinFuture(String date) {
        Date enteredDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            enteredDate = sdf.parse(date);
        } catch (Exception ex) {
            // enteredDate will be null if date="287686";
        }
        Date currentDate = new Date();
        if (enteredDate != null) {
            if (enteredDate.after(currentDate)) {
                return true;
            } else
                return false;
        } else {
            return false;
        }

    }

    private List<DashboardOffersResponse.OfferCoupon> getCouponsInFuture(List<DashboardOffersResponse.OfferCoupon> couponList) {
        List<DashboardOffersResponse.OfferCoupon> futureCoupons = new ArrayList<>();
        for (int i = 0; i < couponList.size(); i++) {
            if (couponList.get(i).getExpiryDate() != null) {
                if (isDateinFuture(couponList.get(i).getExpiryDate())) {
                    futureCoupons.add(couponList.get(i));
                }
            }
        }

        return futureCoupons;
    }

    private String getDateAfterDays(int days) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, days);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd\nMMM");
        return sdf1.format(c.getTime());
    }

}
