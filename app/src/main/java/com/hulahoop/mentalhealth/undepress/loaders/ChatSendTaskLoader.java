package com.hulahoop.mentalhealth.undepress.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.hulahoop.mentalhealth.undepress.NetworkUtils;

import java.net.URLEncoder;

/**
 * Created by agoun on 2/27/2018.
 */

public class ChatSendTaskLoader extends AsyncTaskLoader<String> {
    private String mAccessToken;
    private int mTheirId;
    private String mMessage;
    private String mTimestamp;

    public ChatSendTaskLoader(Context context, String accessToken, int theirId, String
            message, String timestamp) {
        super(context);
        mAccessToken = accessToken;
        mTheirId = theirId;
        mMessage = message;
        mTimestamp = timestamp;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        String formParameters = null;
        try {
            formParameters = "receiver_id=" + URLEncoder.encode(String.valueOf(mTheirId),
                    "UTF-8");
            formParameters += "&message=" + URLEncoder.encode(mMessage, "UTF-8");
            formParameters += "&timestamp=" + URLEncoder.encode(mTimestamp, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("ChatSend Param", formParameters);
        return NetworkUtils.getResponse("chat/send", "POST", formParameters, mAccessToken);
    }
}