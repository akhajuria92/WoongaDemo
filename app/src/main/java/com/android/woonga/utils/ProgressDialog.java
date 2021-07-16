package com.android.woonga.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;

import com.android.woonga.R;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * Custom progress dialog which is displayed during API calls.
 */

public class ProgressDialog extends Dialog {

    private AVLoadingIndicatorView indicator = null;
    private static ProgressDialog progressDialog = null;


    public static void showDialog(Activity mContext) {
        if (progressDialog == null && !mContext.isFinishing()) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.show();
        }

    }

    public static void dismissDialog(Activity mContext) {
        if (progressDialog != null && progressDialog.isShowing() && !mContext.isFinishing() && !mContext.isDestroyed()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /**
     * @param context Context
     */
    public ProgressDialog(final Context context) {

        super(context, R.style.MyDialogTheme);

        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        this.setContentView(R.layout.dialog_layout);
        this.setCancelable(false);
        indicator = findViewById(R.id.indicator);
        indicator.setIndicatorColor(ContextCompat.getColor(context, R.color.colorPrimary));
        indicator.setIndicator("BallClipRotatePulseIndicator");

    }
}
