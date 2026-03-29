package top.niunaijun.blackbox.core.system;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import top.niunaijun.blackbox.R;
import top.niunaijun.blackbox.app.LauncherActivity;
import top.niunaijun.blackbox.utils.compat.BuildCompat;

public class DaemonService extends Service {

    public static final int NOTIFY_ID = 10001;
    private static final String CHANNEL_ID = "blackbox_core";
    private static final String CHANNEL_NAME = "BlackBox Core";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (BuildCompat.isOreo()) {
            startAsForegroundService();
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startAsForegroundService() {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager == null) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_LOW
            );
            channel.setShowBadge(false);
            notificationManager.createNotificationChannel(channel);
        }

        Intent launchIntent = new Intent(this, LauncherActivity.class);
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                launchIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.stat_notify_sync_noanim)
                .setContentTitle(getString(R.string.black_box_service_name))
                .setContentText("BlackBox core service is running")
                .setOngoing(true)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(NOTIFY_ID, notification);
    }
}
