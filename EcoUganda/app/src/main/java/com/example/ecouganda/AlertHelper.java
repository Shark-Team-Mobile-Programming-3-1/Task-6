package com.example.ecouganda;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavDeepLinkBuilder;

// Sends a NEMA alert notification. Tapping it opens ReportsFragment with a reportId.
public final class AlertHelper {

    private static final String CHANNEL_ID = "eco_alerts";

    private AlertHelper() {
        // Utility class - avoid instantiation
    }

    @SuppressLint("MissingPermission")
    public static void sendAlert(Context context, String reportId, String message) {

        // 1. Android 8+ needs a notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "Environmental alerts", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }

        // 2. Android 13+ needs the user's permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            int permission = ContextCompat.checkSelfPermission(
                    context, Manifest.permission.POST_NOTIFICATIONS);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, R.string.alert_allow_notifications, Toast.LENGTH_LONG).show();
                return;
            }
        }

        // 3. The arguments we send to ReportsFragment
        Bundle args = new Bundle();
        args.putString("reportId", reportId);

        // 4. The deep link: opens MainActivity, then goes straight to ReportsFragment.
        PendingIntent pendingIntent = new NavDeepLinkBuilder(context)
                .setComponentName(MainActivity.class)
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.reportsFragment)
                .setArguments(args)
                .createPendingIntent();

        // 5. Build and show the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle(context.getString(R.string.alert_title))
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat.from(context).notify(reportId.hashCode(), builder.build());
    }
}
