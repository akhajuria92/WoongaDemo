package com.android.woonga.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Vibrator;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.woonga.BuildConfig;
import com.android.woonga.R;
import com.android.woonga.WoongaApplication;
import com.android.woonga.views.activities.OtpActivity;
import com.google.android.material.snackbar.Snackbar;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
import static android.text.Html.fromHtml;

public class Utility {

    private static Utility utility = null;

    /**
     * @return Instance of Utility class
     */
    public static Utility getInstance() {
        return utility == null ? utility = new Utility() : utility;
    }


    /**
     * Check if device is connected to a active network.
     *
     * @param context Context
     * @return True if device is connected to an active network
     */
    public boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } catch (Exception e) {
            return false;
        }
    }

    public void logout(Activity activity) {
        activity.startActivity(new Intent(activity, OtpActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        activity.finish();
        WoongaApplication.getAppPreferences().clearEditor(activity);
    }

    /**
     * Hides visible keyboard from an activity.
     *
     * @param activity Activity Context
     */
    public void hideKeyBoard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        View focusedView = activity.getCurrentFocus();

        if (focusedView != null) {
            inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * Store string in preferences file.
     *
     * @param context Context
     * @param key     Preferences Key
     * @param value   String to be stored
     */
    public void setPreference(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(WoongaApplication.getInstance().getContext().getString(R.string.app_name), MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
        editor.commit();
    }

    /**
     * Remove string from preferences file.
     *
     * @param context Context
     * @param key     Preferences Key
     */
    public void removePreference(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(WoongaApplication.getInstance().getContext().getString(R.string.app_name), MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(key);
        editor.apply();
        editor.commit();
    }

    /**
     * Retrieve string from preferences file.
     *
     * @param context Context
     * @param key     Preferences Key
     * @return String from preferences file based on Key
     */
    public String getPreference(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(WoongaApplication.getInstance().getContext().getString(R.string.app_name), MODE_PRIVATE);
        return prefs.getString(key, "");
    }

    /**
     * Check if the string is blank or empty.
     *
     * @param text String
     * @return True if String is blank or empty
     */
    public boolean isBlankString(String text) {
        return TextUtils.isEmpty(text) || text.trim().length() == 0;
    }


    /**
     * Display a normal snackBar.
     *
     * @param rootView Root View
     * @param msg      Message to be displayed
     */
    public void showSnackBar(View rootView, String msg) {
        final Snackbar snackbar = Snackbar.make(rootView, fromHtml(msg), Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(WoongaApplication.getInstance().getContext().getResources().getColor(R.color.colorPrimary));

        snackbar.show();
        vibrate(WoongaApplication.getInstance().getContext());
    }

    /**
     * Vibrates device.
     *
     * @param context Context
     */
    public void vibrate(Context context) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(150);
    }

    public void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public boolean isValidEmail(String email) {

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void openSettings(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public int generateUniqueID() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("mmssSS", Locale.US);
        return Integer.parseInt(sdf.format(c.getTime()));
    }

}
