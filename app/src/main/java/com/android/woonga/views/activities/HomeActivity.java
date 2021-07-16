package com.android.woonga.views.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.woonga.R;
import com.android.woonga.WoongaApplication;
import com.android.woonga.response.VerifyOtpResponse;
import com.android.woonga.utils.Constant;
import com.android.woonga.utils.MessageEvent;
import com.android.woonga.utils.Utility;
import com.android.woonga.views.fragments.FragmentCategories;
import com.android.woonga.views.fragments.FragmentHome;
import com.android.woonga.views.fragments.FragmentHotDeals;
import com.android.woonga.views.fragments.FragmentProfile;
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

public class HomeActivity extends AppCompatActivity {
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


    public String state;
    VerifyOtpResponse.Data userData;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setupViewPager(viewpager);
        tabLayout.setupWithViewPager(viewpager);
        viewpager.setOffscreenPageLimit(4);
        setupTabIcons();
        setTabListners();
      //  AdGyde.onSimpleEvent("registration");
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setupTabIcons() {

       /* LinearLayout tabOne = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        ImageView iv = tabOne.findViewById(R.id.iv);
        TextView text = tabOne.findViewById(R.id.text);
        text.setText("Home");
        iv.setImageResource(R.drawable.ic_home);
        text.setTextColor(getResources().getColor(R.color.colorPrimary));
        iv.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        tabLayout.getTabAt(0).setCustomView(tabOne);

        LinearLayout tabTwo = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        ImageView ivtabTwo = tabTwo.findViewById(R.id.iv);
        TextView texttabTwo = tabTwo.findViewById(R.id.text);
        texttabTwo.setText("More");
        ivtabTwo.setImageResource(R.drawable.ic_categories);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        LinearLayout tabThree = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        ImageView ivtabThree = tabThree.findViewById(R.id.iv);
        TextView texttabThree = tabThree.findViewById(R.id.text);
        texttabThree.setText("Hot Deals");
        ivtabThree.setImageResource(R.drawable.ic_hot_offer);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        LinearLayout tabFour = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        ImageView ivtabFour = tabFour.findViewById(R.id.iv);
        TextView texttabFour = tabFour.findViewById(R.id.text);
        texttabFour.setText("Profile");
        ivtabFour.setImageResource(R.drawable.ic_profile);
        tabLayout.getTabAt(3).setCustomView(tabFour);

        tabLayout.getTabAt(0).select();

        userData = new Gson().fromJson(Prefs.getString(Constant.USER_DATA, ""), VerifyOtpResponse.Data.class);
        frame.setVisibility(View.VISIBLE);
        // profile_logo.setVisibility(View.VISIBLE);
        if (userData.getApprovedPoints() != null) {
            priceOut.setText("₹ " + userData.getApprovedPoints());
        }
     *//*   if (userData.getProfilePic() != null && !userData.getProfilePic().equalsIgnoreCase("")) {
            Glide.with(this).load(userData.getProfilePic()).into(profile_logo);
        }*//*

*/
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
                    iv.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                    tab.setCustomView(view);
                } else if (tab.getPosition() == 1) {
                    View view = tab.getCustomView();
                    ImageView iv = view.findViewById(R.id.iv);
                    TextView tv = view.findViewById(R.id.text);
                    tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                    iv.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                    tab.setCustomView(view);

                } else if (tab.getPosition() == 2) {
                    View view = tab.getCustomView();
                    ImageView iv = view.findViewById(R.id.iv);
                    TextView tv = view.findViewById(R.id.text);
                    tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                    iv.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                    tab.setCustomView(view);

                } else if (tab.getPosition() == 3) {
                    //toolbar.setVisibility(View.GONE);
                    View view = tab.getCustomView();
                    ImageView iv = view.findViewById(R.id.iv);
                    TextView tv = view.findViewById(R.id.text);
                    tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                    iv.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
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
                    iv.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorBlack)));
                    tab.setCustomView(view);
                } else if (tab.getPosition() == 1) {
                    View view = tab.getCustomView();
                    ImageView iv = view.findViewById(R.id.iv);
                    TextView tv = view.findViewById(R.id.text);
                    tv.setTextColor(getResources().getColor(R.color.colorBlack));
                    iv.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorBlack)));
                    tab.setCustomView(view);

                } else if (tab.getPosition() == 2) {
                    View view = tab.getCustomView();
                    ImageView iv = view.findViewById(R.id.iv);
                    TextView tv = view.findViewById(R.id.text);
                    tv.setTextColor(getResources().getColor(R.color.colorBlack));
                    iv.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorBlack)));
                    tab.setCustomView(view);

                } else if (tab.getPosition() == 3) {
                    View view = tab.getCustomView();
                    ImageView iv = view.findViewById(R.id.iv);
                    TextView tv = view.findViewById(R.id.text);
                    tv.setTextColor(getResources().getColor(R.color.colorBlack));
                    iv.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorBlack)));
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
        adapter.addFrag(new FragmentCategories(), "More");
        adapter.addFrag(new FragmentHotDeals(), "Hot Deals");
        adapter.addFrag(new FragmentProfile(), "Profile");
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

            userData = new Gson().fromJson(WoongaApplication.getAppPreferences().getString(Constant.USER_DATA), VerifyOtpResponse.Data.class);
            frame.setVisibility(View.VISIBLE);
            // profile_logo.setVisibility(View.VISIBLE);
            if (userData.getApprovedPoints() != null) {
                priceOut.setText("₹ " + userData.getApprovedPoints());
            }
          /*  if (userData.getProfilePic() != null && !userData.getProfilePic().equalsIgnoreCase("")) {
                Glide.with(this).load(userData.getProfilePic()).into(profile_logo);
            }*/

        }
    }

}
