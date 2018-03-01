package com.hulahoop.mentalhealth.undepress.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.hulahoop.mentalhealth.undepress.NetworkUtils;

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
        super(context);Log.d("SocmedTaskLoader", accessToken);
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
        String formParameters = null;
        try {
            if (mTwitter != null) {
                formParameters = "&twitter=" + URLEncoder.encode(mTwitter, "UTF-8");
            }
            if (mFacebook != null) {
                formParameters += "&facebook=" + URLEncoder.encode(mFacebook, "UTF-8");
            }
            if (mInstagram != null) {
                formParameters += "&instagram=" + URLEncoder.encode(mInstagram, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("SocmedSet Param", formParameters);
        return NetworkUtils.getResponse("socmed/set", "POST", formParameters, mAccessToken);
    }
}
