package com.hulahoop.mentalhealth.undepress.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.hulahoop.mentalhealth.undepress.NetworkUtils;

import java.net.URLEncoder;

/**
 * Created by agoun on 2/27/2018.
 */

public class AccountRegisterTaskLoader extends AsyncTaskLoader<String> {

    private String mEmail;
    private String mPassword;
    private String mName;
    private String mAddress;
    private String mPhone;
    private boolean mIsExpert;

    public AccountRegisterTaskLoader(Context context, String email, String password, String name,
                                     String address, String phone, Boolean isExpert) {
        super(context);
        mEmail = email;
        mPassword = password;
        mName = name;
        mAddress = address;
        mPhone = phone;
        mIsExpert = isExpert;
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
            formParameters += "&name=" + URLEncoder.encode(mName, "UTF-8");
            formParameters += "&address=" + URLEncoder.encode(mAddress, "UTF-8");
            formParameters += "&phone=" + URLEncoder.encode(mPhone, "UTF-8");
            if (mIsExpert) {
                formParameters += "&is_expert" + URLEncoder.encode("True", "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("AccountRegister Param", formParameters);
        return NetworkUtils.getResponse("account/register", "POST", formParameters, "");
    }
}
