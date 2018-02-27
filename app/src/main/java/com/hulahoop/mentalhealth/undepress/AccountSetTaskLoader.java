

package com.hulahoop.mentalhealth.undepress;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.net.URLEncoder;

/**
 * Created by agoun on 2/27/2018.
 */

public class AccountSetTaskLoader extends AsyncTaskLoader<String> {
    private String mAccessToken;
    private String mEmail;
    private String mPassword;
    private String mName;
    private String mAddress;
    private String mPhone;
    private boolean mIsExpert;
    private boolean mIsExpertVerified;
    private String mExpertDescription;
    private String mExpertExperience;
    private String mExpertLocation;

    public AccountSetTaskLoader(Context context, String accessToken, String email, String password,
                                String name, String
                                        address, String phone, boolean isExpert, boolean
                                        isExpertVerified, String
                                        expertDescription, String expertExperience, String
                                        expertLocation) {
        super(context);
        mAccessToken = accessToken;
        mEmail = email;
        mPassword = password;
        mName = name;
        mAddress = address;
        mPhone = phone;
        mIsExpert = isExpert;
        mIsExpertVerified = isExpertVerified;
        mExpertDescription = expertDescription;
        mExpertExperience = expertExperience;
        mExpertLocation = expertLocation;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        String urlParameter = null;
        try {
            if (mEmail != null) {
                urlParameter = "email=" + URLEncoder.encode(mEmail, "UTF-8");
            }
            if (mEmail != null) {
                urlParameter += "&password=" + URLEncoder.encode(mPassword, "UTF-8");
            }
            if (mEmail != null) {
                urlParameter += "&name=" + URLEncoder.encode(mName, "UTF-8");
            }
            if (mEmail != null) {
                urlParameter += "&address=" + URLEncoder.encode(mAddress, "UTF-8");
            }
            if (mEmail != null) {
                urlParameter += "&phone=" + URLEncoder.encode(mPhone, "UTF-8");
            }
            if (mIsExpert) {
                urlParameter += "&is_expert" + URLEncoder.encode("True", "UTF-8");
            }
            if (mIsExpertVerified) {
                urlParameter += "&is_expert_verified" + URLEncoder.encode("True", "UTF-8");
            }
            if (mEmail != null) {
                urlParameter += "&expert_description=" + URLEncoder.encode(mExpertDescription,
                        "UTF-8");
            }
            if (mEmail != null) {
                urlParameter += "&expert_experience=" + URLEncoder.encode(mExpertExperience,
                        "UTF-8");
            }
            if (mEmail != null) {
                urlParameter += "&expert_location=" + URLEncoder.encode(mExpertLocation, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("Account Param", urlParameter);
        return NetworkUtils.getResponse("account/", "GET", urlParameter, mAccessToken);
    }
}
