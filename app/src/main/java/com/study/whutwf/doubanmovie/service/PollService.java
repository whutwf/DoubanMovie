package com.study.whutwf.doubanmovie.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import com.study.whutwf.doubanmovie.R;
import com.study.whutwf.doubanmovie.bean.MovieItem;
import com.study.whutwf.doubanmovie.db.MovieItemDbSchema.MovieItemDb;
import com.study.whutwf.doubanmovie.support.Check;
import com.study.whutwf.doubanmovie.support.Constants;
import com.study.whutwf.doubanmovie.support.NotificationDo;
import com.study.whutwf.doubanmovie.support.SerializableHashMap;
import com.study.whutwf.doubanmovie.ui.activity.MainActivity;
import com.study.whutwf.doubanmovie.ui.fragment.MovieSearchFragment;
import com.study.whutwf.doubanmovie.utils.FetchMovieItemUtils;
import com.study.whutwf.doubanmovie.utils.QueryPreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by whutwf on 17-4-6.
 */

public class PollService extends IntentService {

    private static final String TAG = "PollService";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public PollService() {
        super(TAG);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, PollService.class);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (!Check.isNetWorkAvailableAndConnected(this)) {
            return;
        }
        Log.i(TAG, "Received an intent:" + intent);
        int notiId = intent.getIntExtra(Constants.ExtraIntentString.NOTIFIACTION_ID, 0);

        String targetActivityName = intent.getStringExtra(Constants.ExtraIntentString.TARGET_ACTIVITY_NAME);
        String pageTag = intent.getStringExtra(Constants.ExtraIntentString.PAGE_TAG);
        String key = pageTag + Constants.ExtraIntentString.TARGET_LAST_RESULT_ID;
        String lastResultId = QueryPreferencesUtils.getStoredPreference(this, key);

        Bundle bundle = intent.getExtras();
        SerializableHashMap serializableHashMap = (SerializableHashMap) bundle.get(Constants.ExtraIntentString.S_HASH_MAP);

        List<MovieItem> items = new ArrayList<>();
        Log.i(TAG, "targetName:" + targetActivityName);

        if (!targetActivityName.equals(MovieSearchFragment.TAG)) {
            items = new FetchMovieItemUtils(this, targetActivityName).fetchMovieItems(serializableHashMap.getMap());
        }
        Log.i(TAG, items.size() + "   ");
        if (items.size() == 0) {
            return;
        }

        String resultId = items.get(0).getId();
        if (resultId.equals(lastResultId)) {
            Log.i(TAG, "Got an old result" + resultId);
        } else {
            Log.i(TAG, "Got a new result" + resultId);

            NotificationDo.createNotification(this, MainActivity.newIntent(this), initNotiParams(pageTag), notiId);
        }

        QueryPreferencesUtils.setStoredPreference(this, key, resultId);

    }

    private HashMap<String, String> initNotiParams(String pageTag) {
        Resources resources = getResources();
        HashMap<String, String> notiHash = new HashMap<>();
        switch (pageTag) {
            case MovieItemDb.DbBaseSettings.TABLE_TOP250:
                notiHash.put("title", resources.getString(R.string.top250));
                notiHash.put("text", resources.getString(R.string.top250) + resources.getString(R.string.update));
                break;
            case MovieItemDb.DbBaseSettings.TABLE_IN_THEATERS:
                notiHash.put("title", resources.getString(R.string.in_theaters));
                notiHash.put("text", resources.getString(R.string.in_theaters) + resources.getString(R.string.update));
                break;
            case MovieItemDb.DbBaseSettings.TABLE_COMING_SOON:
                notiHash.put("title", resources.getString(R.string.coming_soon));
                notiHash.put("text", resources.getString(R.string.coming_soon) + resources.getString(R.string.update));
                break;
            default:
                break;
        }

        return notiHash;
    }
}
