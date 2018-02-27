package com.hulahoop.mentalhealth.undepress;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.net.URLEncoder;

/**
 * Created by agoun on 2/27/2018.
 */

public class ChatSendTaskLoader extends AsyncTaskLoader<String> {
    private String mAccessToken;
    private int mReceiverId;
    private String mMessage;
    private String mTimestamp;

    public ChatSendTaskLoader(Context context, String accessToken, int receiverId, String
            message, String timestamp) {
        super(context);
        mAccessToken = accessToken;
        mReceiverId = receiverId;
        mMessage = message;
        mTimestamp = timestamp;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        String urlParameter = null;
        try {
            urlParameter = "receiver_id=" + URLEncoder.encode(String.valueOf(mReceiverId), "UTF-8");
            urlParameter += "&message=" + URLEncoder.encode(mMessage, "UTF-8");
            urlParameter += "&timestamp=" + URLEncoder.encode(mTimestamp, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("ChatSend Param", urlParameter);
        return NetworkUtils.getResponse("chat/send", "POST", urlParameter, mAccessToken);
    }
}