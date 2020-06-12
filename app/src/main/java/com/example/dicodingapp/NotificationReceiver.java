package com.example.dicodingapp;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {
    public static final int NOTIFICATION_ID = 200;
    public static String CHANNEL_ID = "channelNotification";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent repeating = new Intent(context, MainActivity.class);
        repeating.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_add_alert_black_24dp)
                .setContentTitle("Daily Reminder")
                .setContentText("Please open your app")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }
}
