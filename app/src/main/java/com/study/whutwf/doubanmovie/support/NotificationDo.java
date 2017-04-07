package com.study.whutwf.doubanmovie.support;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.HashMap;

/**
 * Created by whutwf on 17-4-7.
 */

public class NotificationDo {

    public static final void createNotification(Context context, Intent intent, HashMap<String, String> notiHash, int notiId) {

        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);

        Notification notification = new NotificationCompat.Builder(context)
                .setTicker(notiHash.get("title") == null ? "" : notiHash.get("title"))
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(notiHash.get("title") == null ? "" : notiHash.get("title"))
                .setContentText(notiHash.get("text") == null ? "" : notiHash.get("text"))
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notiId, notification);

    }
}
