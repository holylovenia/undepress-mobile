package com.hulahoop.mentalhealth.undepress;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

/**
 * Created by agoun on 2/27/2018.
 */

public class SocmedTaskLoader extends AsyncTaskLoader<String> {
    private String mAccessToken;

    public SocmedTaskLoader(Context context, String accessToken) {
        super(context);
        mAccessToken = accessToken;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        String urlParameter = null;
        Log.d("Socmed Param", urlParameter);
        return NetworkUtils.getResponse("socmed/", "GET", urlParameter, mAccessToken);
    }
}