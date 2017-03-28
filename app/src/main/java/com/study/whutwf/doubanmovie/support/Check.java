package com.study.whutwf.doubanmovie.support;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * Created by whutwf on 17-3-28.
 */

public class Check {
    public Check() {}

    /**
    * @return true if there are apps which will respond to this action/data
    */
    public static boolean isIntentAvailable(Context context, Intent intent) {

        List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return !list.isEmpty();
    }

    /**
     * Retrieve overall information about an application package that is
     * installed on the system.
     **/
    public static boolean isApkInstalled(Context context, String packageName) {

        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return (info != null);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }
}
