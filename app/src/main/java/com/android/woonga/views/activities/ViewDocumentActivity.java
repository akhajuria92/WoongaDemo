package com.android.woonga.views.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.woonga.R;
import com.android.woonga.utils.Constant;
import com.android.woonga.utils.ProgressDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewDocumentActivity extends AppCompatActivity {

    @BindView(R.id.webView)
    WebView webView;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_document);
        ButterKnife.bind(this);
        ProgressDialog.showDialog(ViewDocumentActivity.this);
        settingWebView();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            url = bundle.getString("url");
           // webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + url);

            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.intent.setPackage("com.android.chrome");
            customTabsIntent.launchUrl(this, Uri.parse("http://docs.google.com/gview?embedded=true&url=" + url));
           /* PdfWebViewClient pdfWebViewClient = new PdfWebViewClient(this, webView);
            pdfWebViewClient.loadPdfUrl(url);*/
        }
    }

    private void settingWebView() {
        String url1 = "javascript:(function() {" + "document.querySelector('[role=\"toolbar\"]').remove();})()";
       /* webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setSaveFormData(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setInitialScale(3);*/

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.getSettings().setBuiltInZoomControls(true);


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                ProgressDialog.dismissDialog(ViewDocumentActivity.this);
            }

            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                ProgressDialog.showDialog(ViewDocumentActivity.this);
            }

            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                final Uri uri = request.getUrl();
                view.loadUrl(uri.toString());
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);

            }
        });

    }

    private class PdfWebViewClient extends WebViewClient {
        private static final String PDF_EXTENSION = ".docx";
        private static final String PDF_VIEWER_URL = "http://docs.google.com/gview?embedded=true&url=";

        private Context mContext;
        private WebView mWebView;
        private boolean isLoadingPdfUrl;

        public PdfWebViewClient(Context context, WebView webView) {
            mContext = context;
            mWebView = webView;
            mWebView.setWebViewClient(this);
        }

        public void loadPdfUrl(String url) {
            mWebView.stopLoading();

            if (!TextUtils.isEmpty(url)) {
                isLoadingPdfUrl = isPdfUrl(url);
                if (isLoadingPdfUrl) {
                    mWebView.clearHistory();
                }

                ProgressDialog.showDialog(ViewDocumentActivity.this);
            }

            mWebView.loadUrl(url);
        }

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            return shouldOverrideUrlLoading(url);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
            handleError(errorCode, description.toString(), failingUrl);
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request) {
            final Uri uri = request.getUrl();
            return shouldOverrideUrlLoading(webView, uri.toString());
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public void onReceivedError(final WebView webView, final WebResourceRequest request, final WebResourceError error) {
            final Uri uri = request.getUrl();
            handleError(error.getErrorCode(), error.getDescription().toString(), uri.toString());
        }

        @Override
        public void onPageFinished(final WebView view, final String url) {
            ProgressDialog.dismissDialog(ViewDocumentActivity.this);
        }

        private boolean shouldOverrideUrlLoading(final String url) {


            if (!isLoadingPdfUrl && isPdfUrl(url)) {
                mWebView.stopLoading();

                final String pdfUrl = PDF_VIEWER_URL + url;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadPdfUrl(pdfUrl);
                    }
                }, 300);

                return true;
            }

            return false; // Load url in the webView itself
        }

        private void handleError(final int errorCode, final String description, final String failingUrl) {
        }

        private boolean isPdfUrl(String url) {
            if (!TextUtils.isEmpty(url)) {
                url = url.trim();
                int lastIndex = url.toLowerCase().lastIndexOf(PDF_EXTENSION);
                if (lastIndex != -1) {
                    return url.substring(lastIndex).equalsIgnoreCase(PDF_EXTENSION);
                }
            }
            return false;
        }
    }

}
