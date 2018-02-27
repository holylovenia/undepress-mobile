package com.hulahoop.mentalhealth.undepress.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.hulahoop.mentalhealth.undepress.NetworkUtils;

/**
 * Created by agoun on 2/27/2018.
 */

public class ChatTaskLoader extends AsyncTaskLoader<String> {
    private String mAccessToken;
    private int mSenderId;

    public ChatTaskLoader(Context context, String accessToken, int senderId) {
        super(context);
        mAccessToken = accessToken;
        mSenderId = senderId;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        String urlParameters = "?sender_id" + mSenderId;
        Log.d("Chat Param", urlParameters);
        return NetworkUtils.getResponse("chat" + urlParameters + "/", "GET", null, mAccessToken);
    }
}