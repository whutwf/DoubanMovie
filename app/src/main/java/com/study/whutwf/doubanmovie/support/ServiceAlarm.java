package com.study.whutwf.doubanmovie.support;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/**
 * Created by whutwf on 17-4-7.
 */

public class ServiceAlarm {
    private static final String TAG = "ServiceAlarm";

    //延长运行服务时间
    private static final long POLL_INTERVAL = AlarmManager.INTERVAL_FIFTEEN_MINUTES;

    //启动定时器,通常是从前端的fragment或其他控制曾代码中启停
    //PendingIntent打包Inent, AlarmManager发送系统服务
    //PendingIntent.getService打包一个Context.startService(Intent)的调用
    public static void setServiceAlarm(Context context, Intent intent, boolean isOn) {

        /**
         *  *
         * @param context The Context in which this PendingIntent should start
         * the service.
         * @param requestCode Private request code for the sender
         * @param intent An Intent describing the service to be started.
         * @param flags May be {@link #FLAG_ONE_SHOT}, {@link #FLAG_NO_CREATE},
         * {@link #FLAG_CANCEL_CURRENT}, {@link #FLAG_UPDATE_CURRENT},
         * {@link #FLAG_IMMUTABLE} or any of the flags as supported by
         * {@link Intent#fillIn Intent.fillIn()} to control which unspecified parts
         * of the intent that can be supplied when the actual send happens.
         *
         * @return Returns an existing or new PendingIntent matching the given
         * parameters.  May return null only if {@link #FLAG_NO_CREATE} has been
         * supplied.
         */
        PendingIntent pi = PendingIntent.getService(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (isOn) {
            //是以走过的时间为基准的
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(), POLL_INTERVAL, pi);
        } else {

            //要同时取消两个
            alarmManager.cancel(pi);
            pi.cancel();
        }

    }

    public static boolean isServiceAlarm(Context context, Intent intent) {
        PendingIntent pi = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_NO_CREATE);

        return pi != null;
    }
}
