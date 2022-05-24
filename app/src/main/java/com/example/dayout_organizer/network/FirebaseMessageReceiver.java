package com.example.dayout_organizer.network;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;


import com.example.dayout_organizer.R;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;


@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class FirebaseMessageReceiver extends FirebaseMessagingService {

    private final static String TAG = "Firebase";

    @Override
    public void
    onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "onMessageReceived: received");
        if (remoteMessage.getNotification() != null) {
            showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
       } else if ( remoteMessage.getData() != null) {
            String title = remoteMessage.getData().get("title");
            String body = remoteMessage.getData().get("body");
            showNotification(title, body);
        }
    }

    @Override
    public void handleIntent(@NonNull Intent intent) {
        super.handleIntent(intent);
    }

    private RemoteViews getCustomDesign(String title, String message) {
        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.notification_layout);
        remoteViews.setTextViewText(R.id.title, title);
        remoteViews.setTextViewText(R.id.message, message);
        remoteViews.setImageViewResource(R.id.icon, R.drawable.ic_app_logo);
        return remoteViews;
    }

    public void showNotification(String title, String message) {

        Intent intent = new Intent(this, MainActivity.class);
        String channel_id = "notification_channel";

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat
                .Builder(getApplicationContext(),
                channel_id)
                .setSmallIcon(R.drawable.ic_app_logo)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);

        builder = builder.setContent(getCustomDesign(title, message));

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    channel_id, "web_app",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(
                    notificationChannel);
        }

        notificationManager.notify(0, builder.build());
    }

    public static boolean isBackgroundRunning(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (String activeProcess : processInfo.pkgList) {
                    if (activeProcess.equals(context.getPackageName())) {
                        //If your app is the process in foreground, then it's not in running in background
                        return false;
                    }
                }
            }
        }
        return true;
    }

}


