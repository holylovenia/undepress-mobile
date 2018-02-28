package com.hulahoop.mentalhealth.undepress.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.hulahoop.mentalhealth.undepress.NetworkUtils;

/**
 * Created by agoun on 2/27/2018.
 */

public class MonitoringTaskLoader extends AsyncTaskLoader<String> {
    private String mAccessToken;

    public MonitoringTaskLoader(Context context, String accessToken) {
        super(context);
        mAccessToken = accessToken;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        return NetworkUtils.getResponse("monitoring", "GET", null, mAccessToken);
    }
}