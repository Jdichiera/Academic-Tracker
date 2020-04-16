package com.example.academictracker.utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra(NotificationHelper.EXTRA_NOTIFICATION_TITLE);
        String message = intent.getStringExtra(NotificationHelper.EXTRA_NOTIFICATION_MESSAGE);
        int notificationId = intent.getIntExtra(NotificationHelper.EXTRA_NOTIFICATION_ID, -1);
        NotificationHelper notificationHelper = new NotificationHelper(context, title, message, notificationId);
        NotificationCompat.Builder notificationBuilder = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(notificationHelper.notificationId, notificationBuilder.build());
    }
}