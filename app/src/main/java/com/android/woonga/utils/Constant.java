package com.android.woonga.utils;


import com.android.woonga.WoongaApplication;
import com.android.woonga.response.VerifyOtpResponse;
import com.google.gson.Gson;

public class Constant {


    public static final String SHARED_PREFERENCE = "woonga";
    private static Constant constants = null;

    public static Constant getInstance() {
        return constants == null ? constants = new Constant() : constants;
    }


    public static final String OS_TYPE = "2";
    public static final String USER_DATA = "user_data";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String SECURITY_TOKEN = "security_token";
    public static final String REFERRAL_CODE = "referral_code";
    public static final String FIRST_TIME = "first_time";
    public static final String AMOUNT_WHO_REFER = "amount_who_refer";
    public static final String LOGIN = "login";
    public static final String GOOGLE_ID = "google_id";
    public static final String CURRENT_STATE = "current_state";
    public static final String CURRENT_CITY = "current_city";
    public static final String FREQUENTYLY_ASKED_QUESTIONS_URL = "https://woonga.s3.ap-south-1.amazonaws.com/termsandconditions/Frequently+Asked+Questions.docx";
    public static final String PRIVACY_URL = "https://woonga.s3.ap-south-1.amazonaws.com/termsandconditions/PRIVACY+POLICY.docx";
    public static final String TERMS_URL = "https://woonga.s3.ap-south-1.amazonaws.com/termsandconditions/TERMS+AND+CONDITIONS.docx";
    public static final String GAMEZOP_URL = "https://www.gamezop.com/?id=2hYzbA8o";
    public static final String AD_COLONY_APPID = "app2472345027fe4d4ba0";

    //Live AdMobs Key
    public static final String AD_UNIT_ID_ADMOBS = "ca-app-pub-6024018297379450/9901870860";

    //Test AdMobs Key
    //public static final String AD_UNIT_ID_ADMOBS = "ca-app-pub-3940256099942544/6300978111";

    public static final String VUNGLE_APPID = "5e941dcb8ced3d0001e52284";
    public static final String AD_COLONY_ZONE_ID_1 = "vz5eb8257d209341e0a8";
    public static final String AD_COLONY_ZONE_ID_2 = "vz73043775e7244cd895";
    public static final String UNITY_GAME_ID = "3500726";
    public static final int FLAG_TRUE = 1;


    public String getPhoneNumber() {
        return new Gson().fromJson(WoongaApplication.getAppPreferences().getString(USER_DATA), VerifyOtpResponse.Data.class).getPhoneNumber() + "";
    }

    public String getSecurityToken() {
        return new Gson().fromJson(WoongaApplication.getAppPreferences().getString(USER_DATA), VerifyOtpResponse.Data.class).getSecurityToken() + "";
    }

    public String getCurrentState() {
        return WoongaApplication.getAppPreferences().getString(Constant.CURRENT_STATE);
    }

    public String getCurrentCity() {
        return WoongaApplication.getAppPreferences().getString(Constant.CURRENT_CITY);
    }

    public String getUserId() {
        return new Gson().fromJson(WoongaApplication.getAppPreferences().getString(USER_DATA), VerifyOtpResponse.Data.class).getId() + "";
    }

    public String getEmail() {
        return new Gson().fromJson(WoongaApplication.getAppPreferences().getString(USER_DATA), VerifyOtpResponse.Data.class).getEmail() + "";
    }

    public String getProfilePic() {
        return new Gson().fromJson(WoongaApplication.getAppPreferences().getString(USER_DATA), VerifyOtpResponse.Data.class).getProfilePic() + "";
    }

    public String getName() {
        return new Gson().fromJson(WoongaApplication.getAppPreferences().getString(USER_DATA), VerifyOtpResponse.Data.class).getName() + "";
    }

    public String getGoogleId() {
        return WoongaApplication.getAppPreferences().getString(GOOGLE_ID);
    }

    public String getReferralCode() {
        return WoongaApplication.getAppPreferences().getString(REFERRAL_CODE);
    }

    public int getFirstTimeUser() {
        return WoongaApplication.getAppPreferences().getInt(FIRST_TIME);
    }
}
