package com.hulahoop.mentalhealth.undepress;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.net.URLEncoder;

/**
 * Created by agoun on 2/27/2018.
 */

public class AccountTaskLoader extends AsyncTaskLoader<String> {
    private String mAccessToken;

    public AccountTaskLoader(Context context, String accessToken) {
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
        Log.d("Account Param", urlParameter);
        return NetworkUtils.getResponse("account/", "GET", urlParameter, mAccessToken);
    }
}