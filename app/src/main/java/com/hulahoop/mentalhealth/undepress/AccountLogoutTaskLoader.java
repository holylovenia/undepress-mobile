package com.hulahoop.mentalhealth.undepress;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

/**
 * Created by agoun on 2/27/2018.
 */

public class AccountLogoutTaskLoader extends AsyncTaskLoader<String> {
    private String mAccessToken;

    public AccountLogoutTaskLoader(Context context, String accessToken) {
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
        Log.d("AccountLogout Param", urlParameter);
        return NetworkUtils.getResponse("account/logout", "GET", urlParameter, mAccessToken);
    }
}