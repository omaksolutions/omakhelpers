package com.omak.outils.firebaseNotification;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.omak.outils.AccessHelpers;
import com.omak.outils.HelperFunctions;
import com.omak.outils.R;
import com.omak.outils.RealmHelpers.RealmHelpers;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;


public class NotificationHandler extends FirebaseMessagingService {
    public static Bitmap largeImage, bigImage, smallIconImage;
    public static String btnTitle = "";
    private static NotificationManager notificationManager;
    Context context;
    RealmHelpers realmHelpers;
    int nextNotificatoinId;
    Boolean showNotification;
    HashMap<String, String> notiData;
    private NotificationChannelHelpers mNotificationUtils;

    public NotificationHandler() {
        this.context = this;
    }

    public NotificationHandler(Context context, HashMap<String, String> notiData) {
        this.notiData = notiData;
        this.context = context;
        showGeneralNotification();
    }

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        HelperFunctions.theLogger("Check Notification", "" + new Gson().toJson(remoteMessage));

        btnTitle = getDataKey(remoteMessage, "btn_title");

        // store notification into realm
        notiData = new HashMap<>();
        notiData.put("data", new Gson().toJson(remoteMessage.getData()));
        notiData.put("type", getDataKey(remoteMessage, "type"));
        notiData.put("title", getDataKey(remoteMessage, "title"));
        notiData.put("message", getDataKey(remoteMessage, "message"));
        notiData.put("btn_title", getDataKey(remoteMessage, "btn_title"));
        notiData.put("longImageUrl", getDataKey(remoteMessage, "img_big"));
        notiData.put("smallImageUrl", getDataKey(remoteMessage, "img_icon"));
        notiData.put("smallIcon", getDataKey(remoteMessage, "small_icon"));
        notiData.put("show_notification", getDataKey(remoteMessage, "remoteShowNotification"));
        notiData.put("goto", getDataKey(remoteMessage, "goto"));
        notiData.put("channel_id", "channel_id_general");

        if (!getDataKey(remoteMessage, "channel_id").equals("")) {
            notiData.put("channel_id", getDataKey(remoteMessage, "channel_id"));
        }

        showGeneralNotification();
    }

    private String getDataKey(RemoteMessage remoteMessage, String key) {
        return (remoteMessage.getData().get(key) != null) ? remoteMessage.getData().get(key) : "";
    }

    public void clearNotification(int id) {
        //  NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);// o is the notification id which we want to remove after some actions
    }

    private void showGeneralNotification() {
        realmHelpers = new RealmHelpers(this);
        nextNotificatoinId = realmHelpers.getNotificationId(notiData);
        Intent intent = new Intent();
        String channelId = NotificationChannelHelpers.CHANNEL_ID_GENERAL;
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        showNotification = notiData.get("show_notification") != "false";

        try {
            URL largeImage = new URL(notiData.get("img_big"));
            URL bigImages = new URL(notiData.get("img_icon"));
            URL smallUrlicon = new URL(notiData.get("small_icon"));
            NotificationHandler.largeImage = BitmapFactory.decodeStream(largeImage.openConnection().getInputStream());
            bigImage = BitmapFactory.decodeStream(bigImages.openConnection().getInputStream());
            smallIconImage = BitmapFactory.decodeStream(smallUrlicon.openConnection().getInputStream());
        } catch (IOException e) {
            System.out.println(e);
        }

        NotificationCompat.Builder notificationCompat =
                new NotificationCompat.Builder(this, channelId)
                        .setLargeIcon(largeImage)
                        .setSmallIcon(R.drawable.logo)
                        .setContentText(notiData.get("message"))
                        .setColor(Color.GREEN)
                        .setContentTitle(notiData.get("title"))
                        .setSound(defaultSoundUri)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(notiData.get("message")))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationCompat.setColor(getResources().getColor(R.color.white));
        }

        switch (notiData.get("type")) {
            case "logout":
                // Perform logout action and clear realm and preferences
                AccessHelpers.actionLogout(context);
                break;
        }

//        intent = new Intent(getApplicationContext(), MainActivity.class);
//        intent.putExtra("goto", notiData.get("goto"));
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        notificationCompat.setContentIntent(pendingIntent).addAction(R.drawable.logo, "" + btnTitle, pendingIntent);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationUtils = new NotificationChannelHelpers(getApplicationContext());
            notificationCompat.setChannelId(notiData.get("channel_id"));
            mNotificationUtils.getManager().notify(nextNotificatoinId, notificationCompat.build());
        } else {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(nextNotificatoinId, notificationCompat.build());
        }
    }
}
