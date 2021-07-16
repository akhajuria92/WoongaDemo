package com.android.woonga.firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioAttributes;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.android.woonga.R;
import com.android.woonga.utils.Utility;
import com.android.woonga.views.activities.OfferDetailActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        try {
          /*  if (!TextUtils.isEmpty(token)) {
                AdGyde.onTokenRefresh(token);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        try {
            Map<String, String> data = remoteMessage.getData();
            final String titleStr = data.get("title");
            final String message = data.get("message");
            final String icon = data.get("image");
            final String categoryId = data.get("category_id");
            final String offerId = data.get("offer_id");


            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    new Intent(this, OfferDetailActivity.class).putExtra("categoryId", categoryId)
                            .putExtra("offerId", offerId).putExtra("fromNotifications", true), PendingIntent.FLAG_UPDATE_CURRENT);

            sendNotification(titleStr, message, icon, pendingIntent);

        } catch (Exception e) {
            Log.i("MyActivity", e.getMessage());
        }
    }

    private void sendNotification(String title, String messageBody, String icon, PendingIntent pendingIntent) {

        String channel_id = getString(R.string.app_name);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //  Created notification channel to support notification on Android Oreo and Pie
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            String channel_name = getString(R.string.app_name);
            String channel_desc = getString(R.string.app_name);
            int noti_importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(channel_id, channel_name, noti_importance);
            mChannel.setDescription(channel_desc);
            mChannel.enableLights(true);
            mChannel.setSound(Settings.System.DEFAULT_NOTIFICATION_URI, attributes);
            mChannel.setLightColor(getColor(R.color.colorPrimary));
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(mChannel);
        }

        //  Assign appropriate notification icon based on device OS.
        int smallIcon;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            smallIcon = R.mipmap.ic_notification_icon;
        } else {
            smallIcon = R.mipmap.ic_launcher;
        }

        //  Created notification builder and notified it.
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), getString(R.string.app_name))
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSmallIcon(smallIcon)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setContentTitle(title)
                .setContentText(messageBody)
                .setChannelId(channel_id)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[0])
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(messageBody)
                        .setBigContentTitle(title))
                .setAutoCancel(true);

      /*  NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();
        // Sets a title for the Inbox in expanded layout
        inboxStyle.setBigContentTitle(channel_id);
        // inboxStyle.setSummaryText("You have "+notifications.size()+" Notifications.");
        // Moves events into the expanded layout
        notifications.add(messageBody);
        for (int i = 0; i < notifications.size(); i++) {
            inboxStyle.addLine(notifications.get(i));
        }
        // Moves the expanded layout object into the notification object.
        mBuilder.setStyle(inboxStyle);*/

        if (!Utility.getInstance().isBlankString(icon)) {
            Glide.with(getApplicationContext())
                    .asBitmap()
                    .load(icon)
                    .apply(new RequestOptions().override(200, 200))
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            Notification notification = mBuilder.build();
                            notificationManager.notify(1, notification);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            mBuilder.setLargeIcon(resource);
                            Notification notification = mBuilder.build();
                            notificationManager.notify(1, notification);
                            return true;
                        }
                    }).submit();
        } else {
            Glide.with(getApplicationContext())
                    .asBitmap()
                    .load(R.mipmap.ic_launcher)
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            Notification notification = mBuilder.build();
                            notificationManager.notify(1, notification);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            mBuilder.setLargeIcon(resource);
                            Notification notification = mBuilder.build();
                            notificationManager.notify(1, notification);
                            return true;
                        }
                    }).submit();
        }

    }
}
