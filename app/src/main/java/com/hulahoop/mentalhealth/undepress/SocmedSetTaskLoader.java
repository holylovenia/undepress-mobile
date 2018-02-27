package com.hulahoop.mentalhealth.undepress;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.net.URLEncoder;

/**
 * Created by agoun on 2/27/2018.
 */

public class SocmedSetTaskLoader extends AsyncTaskLoader<String> {
    private String mAccessToken;
    private String mTwitter;
    private String mFacebook;
    private String mInstagram;

    public SocmedSetTaskLoader(Context context, String accessToken, String twitter, String
            facebook, String instagram) {
        super(context);
        mAccessToken = accessToken;
        mTwitter = twitter;
        mFacebook = facebook;
        mInstagram = instagram;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        String urlParameter = null;
        try {
            if (mTwitter != null) {
                urlParameter = "twitter=" + URLEncoder.encode(mTwitter, "UTF-8");
            }
            if (mFacebook != null) {
                urlParameter += "&facebook=" + URLEncoder.encode(mFacebook, "UTF-8");
            }
            if (mInstagram != null) {
                urlParameter += "&instagram=" + URLEncoder.encode(mInstagram, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        Log.d("SocmedSet Param", urlParameter);
        }
        return NetworkUtils.getResponse("socmed/set", "POST", urlParameter, mAccessToken);
    }
}
