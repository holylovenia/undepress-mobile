package com.hulahoop.mentalhealth.undepress.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.hulahoop.mentalhealth.undepress.NetworkUtils;

/**
 * Created by holy on 27/02/18.
 */

public class ExpertTaskLoader extends AsyncTaskLoader<String> {
    private String mAccessToken;

    public ExpertTaskLoader(Context context, String accessToken) {
        super(context);
        mAccessToken = accessToken;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        return NetworkUtils.getResponse("expert/", "GET", null, mAccessToken);
    }
}
