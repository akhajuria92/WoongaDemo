package com.android.woonga.views.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.viewpager.widget.ViewPager;

import com.android.woonga.BuildConfig;
import com.android.woonga.R;
import com.android.woonga.WoongaApplication;
import com.android.woonga.response.VerifyOtpResponse;
import com.android.woonga.utils.Constant;
import com.android.woonga.utils.MessageEvent;
import com.android.woonga.utils.Utility;
import com.android.woonga.views.fragments.FragmentCategories;
import com.android.woonga.views.fragments.FragmentHome;
import com.android.woonga.views.fragments.FragmentHotDeals;
import com.android.woonga.views.fragments.FragmentOnGoing;
import com.android.woonga.views.fragments.FragmentRefer;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tab)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.frame)
    FrameLayout frame;

    @BindView(R.id.priceOut)
    TextView priceOut;

    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

  /*  @BindView(R.id.profile_logo)
    ImageView profile_logo;*/

    @BindView(R.id.toolbar)
    public View toolbar;

    @BindView(R.id.drawerIcon)
    ImageView drawerIcon;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    TextView userName, userEmail, userPhone, version;
    CircleImageView iv_user;
    LinearLayout llProfile, llFrequently, llPrivacy, llTerms, llTickets, llHistory, llWithdraw, llBankdetails, llRefer;
    VerifyOtpResponse.Data userData;
    private AppBarConfiguration mAppBarConfiguration;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();


        ButterKnife.bind(this);
        setupViewPager(viewpager);
        tabLayout.setupWithViewPager(viewpager);
        viewpager.setOffscreenPageLimit(4);
        setupTabIcons();
        setTabListners();
        getDrawerIds();
        setUi();
    }

    private void getDrawerIds() {
        iv_user = findViewById(R.id.iv_user);
        userName = findViewById(R.id.userName);
        userPhone = findViewById(R.id.userPhone);
        userEmail = findViewById(R.id.userEmail);
        version = findViewById(R.id.version);
        llProfile = findViewById(R.id.ll_profile);
        llFrequently = findViewById(R.id.ll_frequently);
        llPrivacy = findViewById(R.id.ll_privacy);
        llTerms = findViewById(R.id.ll_terms);
        llTickets = findViewById(R.id.ll_tickets);
        llHistory = findViewById(R.id.ll_history);
        llWithdraw = findViewById(R.id.ll_withdraw);
        llBankdetails = findViewById(R.id.ll_bank);
        llRefer = findViewById(R.id.ll_refer);

        llProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (tabLayout.getSelectedTabPosition() == 4) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    viewpager.setCurrentItem(4);
                    drawer.closeDrawer(GravityCompat.START);
                }*/

              /*  Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmentHolder);
                if (f instanceof FragmentProfile) {
                } else {
                    FragmentProfile fragmentProfile = FragmentProfile.newInstance();
                    FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
                    fts.replace(R.id.fragmentHolder, fragmentProfile);
                    fts.addToBackStack(null);
                    fts.commit();
                }*/


                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                drawer.closeDrawer(GravityCompat.START);

            }
        });

        llFrequently.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.intent.setPackage("com.android.chrome");
                customTabsIntent.launchUrl(MainActivity.this, Uri.parse("http://docs.google.com/gview?embedded=true&url=" + Constant.getInstance().FREQUENTYLY_ASKED_QUESTIONS_URL));
                //startActivity(new Intent(MainActivity.this, ViewDocumentActivity.class).putExtra("url", Constant.getInstance().FREQUENTYLY_ASKED_QUESTIONS_URL));
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        llPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.intent.setPackage("com.android.chrome");
                customTabsIntent.launchUrl(MainActivity.this, Uri.parse("http://docs.google.com/gview?embedded=true&url=" + Constant.getInstance().PRIVACY_URL));
                // startActivity(new Intent(MainActivity.this, ViewDocumentActivity.class).putExtra("url", Constant.getInstance().PRIVACY_URL));
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        llTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.intent.setPackage("com.android.chrome");
                customTabsIntent.launchUrl(MainActivity.this, Uri.parse("http://docs.google.com/gview?embedded=true&url=" + Constant.getInstance().TERMS_URL));
                //startActivity(new Intent(MainActivity.this, ViewDocumentActivity.class).putExtra("url", Constant.getInstance().TERMS_URL));
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        llTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MyTicketsActivity.class));
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        llHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HistoryActivity.class));
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        llWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, WithdrawlActivity.class));
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        llBankdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BankDetailActivity.class));
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        llRefer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ReferralActivity.class));
                drawer.closeDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setupTabIcons() {

        LinearLayout tabOne = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        ImageView iv = tabOne.findViewById(R.id.iv);
        TextView text = tabOne.findViewById(R.id.text);
        text.setText("Home");
        iv.setImageResource(R.drawable.ic_home_selected);
        text.setTextColor(getResources().getColor(R.color.colorPrimary));
        tabLayout.getTabAt(0).setCustomView(tabOne);

        LinearLayout tabTwo = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        ImageView ivtabTwo = tabTwo.findViewById(R.id.iv);
        TextView texttabTwo = tabTwo.findViewById(R.id.text);
        texttabTwo.setText("Offerwall");
        ivtabTwo.setImageResource(R.drawable.ic_ongoing);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        LinearLayout tabFive = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        ImageView ivtabFive = tabFive.findViewById(R.id.iv);
        TextView texttabFive = tabFive.findViewById(R.id.text);
        texttabFive.setText("Refer & Earn");
        ivtabFive.setImageResource(R.drawable.ic_refer);
        tabLayout.getTabAt(2).setCustomView(tabFive);

        LinearLayout tabThree = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        ImageView ivtabThree = tabThree.findViewById(R.id.iv);
        TextView texttabThree = tabThree.findViewById(R.id.text);
        texttabThree.setText("Hot Deals");
        ivtabThree.setImageResource(R.drawable.ic_hotdeals);
        tabLayout.getTabAt(3).setCustomView(tabThree);

        LinearLayout tabFour = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        ImageView ivtabFour = tabFour.findViewById(R.id.iv);
        TextView texttabFour = tabFour.findViewById(R.id.text);
        texttabFour.setText("More");
        ivtabFour.setImageResource(R.drawable.ic_more);
        tabLayout.getTabAt(4).setCustomView(tabFour);

        tabLayout.getTabAt(0).select();

        LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));

        tabStrip.getChildAt(2).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

    }

    @OnClick(R.id.floating_referral)
    void floating_referral() {
        startActivity(new Intent(this, ReferralActivity.class));
    }


    private void setUi() {
        userData = new Gson().fromJson(WoongaApplication.getAppPreferences().getString(Constant.USER_DATA), VerifyOtpResponse.Data.class);
        frame.setVisibility(View.VISIBLE);
        drawerIcon.setVisibility(View.VISIBLE);
        if (userData.getApprovedPoints() != null) {
            priceOut.setText("₹ " + userData.getApprovedPoints());
        }
        if (userData.getProfilePic() != null) {
            Glide.with(getApplicationContext()).load(userData.getProfilePic()).into(iv_user);
        }
        if (userData.getName() != null) {
            userName.setText(userData.getName());
        }
        if (userData.getPhoneNumber() != null) {
            userPhone.setText(userData.getPhoneNumber());
        }
        if (userData.getEmail() != null) {
            userEmail.setText(userData.getEmail());
        }

        version.setText("Version- " + BuildConfig.VERSION_NAME);
    }

    @OnClick(R.id.drawerIcon)
    void drawerIcon() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }

 /*   @OnClick(R.id.profile_logo)
    void profile_logo() {
        if (tabLayout.getSelectedTabPosition() != 3) {
            tabLayout.getTabAt(3).select();
        }
    }*/

    @OnClick(R.id.frame)
    void frame() {
        startActivity(new Intent(this, HistoryActivity.class));
    }

    private void setTabListners() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    View view = tab.getCustomView();
                    ImageView iv = view.findViewById(R.id.iv);
                    TextView tv = view.findViewById(R.id.text);
                    tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                    iv.setImageResource(R.drawable.ic_home_selected);
                    tab.setCustomView(view);
                } else if (tab.getPosition() == 1) {
                    View view = tab.getCustomView();
                    ImageView iv = view.findViewById(R.id.iv);
                    TextView tv = view.findViewById(R.id.text);
                    tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                    iv.setImageResource(R.drawable.ic_ongoing_selected);
                    tab.setCustomView(view);

                } else if (tab.getPosition() == 2) {
                    View view = tab.getCustomView();
                    ImageView iv = view.findViewById(R.id.iv);
                    TextView tv = view.findViewById(R.id.text);
                    tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                    iv.setImageResource(R.drawable.ic_refer_selected);
                    tab.setCustomView(view);

                } else if (tab.getPosition() == 3) {
                    //toolbar.setVisibility(View.GONE);
                    View view = tab.getCustomView();
                    ImageView iv = view.findViewById(R.id.iv);
                    TextView tv = view.findViewById(R.id.text);
                    tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                    iv.setImageResource(R.drawable.ic_hotdeals_selected);
                    tab.setCustomView(view);

                } else if (tab.getPosition() == 4) {
                    //toolbar.setVisibility(View.GONE);
                    View view = tab.getCustomView();
                    ImageView iv = view.findViewById(R.id.iv);
                    TextView tv = view.findViewById(R.id.text);
                    tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                    iv.setImageResource(R.drawable.ic_more_selected);
                    tab.setCustomView(view);

                }
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    View view = tab.getCustomView();
                    ImageView iv = view.findViewById(R.id.iv);
                    TextView tv = view.findViewById(R.id.text);
                    tv.setTextColor(getResources().getColor(R.color.colorBlack));
                    iv.setImageResource(R.drawable.ic_home);
                    tab.setCustomView(view);
                } else if (tab.getPosition() == 1) {
                    View view = tab.getCustomView();
                    ImageView iv = view.findViewById(R.id.iv);
                    TextView tv = view.findViewById(R.id.text);
                    tv.setTextColor(getResources().getColor(R.color.colorBlack));
                    iv.setImageResource(R.drawable.ic_ongoing);
                    tab.setCustomView(view);

                } else if (tab.getPosition() == 2) {
                    View view = tab.getCustomView();
                    ImageView iv = view.findViewById(R.id.iv);
                    TextView tv = view.findViewById(R.id.text);
                    tv.setTextColor(getResources().getColor(R.color.colorBlack));
                    iv.setImageResource(R.drawable.ic_refer);
                    tab.setCustomView(view);

                } else if (tab.getPosition() == 3) {
                    View view = tab.getCustomView();
                    ImageView iv = view.findViewById(R.id.iv);
                    TextView tv = view.findViewById(R.id.text);
                    tv.setTextColor(getResources().getColor(R.color.colorBlack));
                    iv.setImageResource(R.drawable.ic_hotdeals);
                    tab.setCustomView(view);
                    toolbar.setVisibility(View.VISIBLE);
                } else if (tab.getPosition() == 4) {
                    View view = tab.getCustomView();
                    ImageView iv = view.findViewById(R.id.iv);
                    TextView tv = view.findViewById(R.id.text);
                    tv.setTextColor(getResources().getColor(R.color.colorBlack));
                    iv.setImageResource(R.drawable.ic_more);
                    tab.setCustomView(view);
                    toolbar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new FragmentHome(), "Home");
        adapter.addFrag(new FragmentOnGoing(), "Offerwall");
        adapter.addFrag(new FragmentRefer(), "Refer & Earn");
        adapter.addFrag(new FragmentHotDeals(), "Hot Deals");
        adapter.addFrag(new FragmentCategories(), "More");
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

    long mBackPressed;
    FragmentTransaction fts;
    FragmentManager fragmentManager;

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            fts = this.getSupportFragmentManager().beginTransaction();
            fragmentManager = this.getSupportFragmentManager();
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStackImmediate();
                fts.commit();
            } else {
                if (mBackPressed + 2000 > System.currentTimeMillis()) {
                    finish();
                    return;
                } else {
                    Utility.getInstance().showSnackBar(mainLayout, "Press once again to exit!");
                }

                mBackPressed = System.currentTimeMillis();
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

        if (event.message.contains("UserDataUpdated")) {
            EventBus.getDefault().removeStickyEvent(event);
            setUi();

        }
    }


}
