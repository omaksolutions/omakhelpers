package com.omak.outils.firebaseNotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class NotificationChannelHelpers extends ContextWrapper {
    public static final String CHANNEL_ID_GENERAL = "channel_id_general";
    public static final String CHANNEL_NAME_GENERAL = "General";
    public static String type = "";
    private NotificationManager mManager;
    private Context context;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationChannelHelpers(Context context) {
        super(context);
        createChannels();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannels() {

        // create android channel
        NotificationChannel generalChannel = new NotificationChannel(CHANNEL_ID_GENERAL,
                CHANNEL_NAME_GENERAL, NotificationManager.IMPORTANCE_DEFAULT);
        // Sets whether notifications posted to this channel should display notification lights
        generalChannel.enableLights(true);
        // Sets whether notification posted to this channel should vibrate.
        generalChannel.enableVibration(true);
        // Sets the notification light color for notifications posted to this channel
        generalChannel.setLightColor(Color.RED);
        // Sets whether notifications posted to this channel appear on the lockscreen or not
        generalChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(generalChannel);


    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }
}
