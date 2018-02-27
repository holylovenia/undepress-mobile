package com.hulahoop.mentalhealth.undepress.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.hulahoop.mentalhealth.undepress.NetworkUtils;

import java.net.URLEncoder;

/**
 * Created by agoun on 2/27/2018.
 */

public class AccountLoginTaskLoader extends AsyncTaskLoader<String> {

    private String mEmail;
    private String mPassword;

    public AccountLoginTaskLoader(Context context, String email, String password) {
        super(context);
        mEmail = email;
        mPassword = password;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        String formParameters = null;
        try {
            formParameters = "email=" + URLEncoder.encode(mEmail, "UTF-8");
            formParameters += "&password=" + URLEncoder.encode(mPassword, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("AccountLogin Param", formParameters);
        return NetworkUtils.getResponse("account/login", "POST", formParameters, "");
    }
}
