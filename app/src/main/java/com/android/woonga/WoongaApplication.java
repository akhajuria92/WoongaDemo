package com.android.woonga;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAppOptions;
import com.android.woonga.utils.AppPreferences;
import com.android.woonga.utils.Constant;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.FirebaseApp;
import com.vungle.warren.InitCallback;
import com.vungle.warren.Vungle;
import com.vungle.warren.error.VungleException;

public class WoongaApplication extends Application {


    private static WoongaApplication instance = null;
    public boolean isAppOpen = false;
    private static AppPreferences appPreferences;

    /**
     * @return Instance of ApplicationDetails Class.
     */
    public static WoongaApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isAppOpen = true;
        instance = this;
        appPreferences = AppPreferences.init(this);

        //  Created notification channel to support notification on Android Oreo and Pie
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channel_id = getString(R.string.app_name);
            String channel_name = getString(R.string.app_name);
            String channel_desc = getString(R.string.app_name);
            int noti_importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel mChannel = new NotificationChannel(channel_id, channel_name, noti_importance);
            mChannel.setDescription(channel_desc);
            mChannel.setShowBadge(true);
            mChannel.setSound(null, null);
            mChannel.enableVibration(false);
            mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(mChannel);
        }


        FirebaseApp.initializeApp(this);
        /*new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();*/

        AdColonyAppOptions appOptions = new AdColonyAppOptions()
                .setRequestedAdOrientation(AdColonyAppOptions.PORTRAIT)
                .setGDPRConsentString("1")
                .setGDPRRequired(true)
                .setKeepScreenOn(true);
        AdColony.configure(this, appOptions, Constant.AD_COLONY_APPID, Constant.AD_COLONY_ZONE_ID_1);


        Vungle.init(Constant.VUNGLE_APPID, this.getApplicationContext(), new InitCallback() {
            @Override
            public void onSuccess() {
                Log.e("Vungle", "Vungle initialized");
            }

            @Override
            public void onError(VungleException e) {

            }

            @Override
            public void onAutoCacheAdAvailable(String pid) {
            }
        });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

       /* AdGyde.allowPermissionIMEI(this, true);
        AdGyde.init(this, "K316113182166428", "Organic");
        AdGyde.setDebugEnabled(true);*/

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        isAppOpen = false;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    /**
     * @return The live application context.
     */
    public Context getContext() {
        return getApplicationContext();
    }

    public static AppPreferences getAppPreferences() {
        return appPreferences;
    }
}
