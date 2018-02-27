package com.hulahoop.mentalhealth.undepress.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.hulahoop.mentalhealth.undepress.NetworkUtils;

import java.net.URLEncoder;

/**
 * Created by agoun on 2/27/2018.
 */

public class AppointmentSetTaskLoader extends AsyncTaskLoader<String> {
    private String mAccessToken;
    private Integer mPatientId;
    private Integer mExpertId;
    private String mStatus;

    public AppointmentSetTaskLoader(Context context, String accessToken, Integer patientId,
                                    Integer expertId, String status) {
        super(context);
        mAccessToken = accessToken;
        mPatientId = patientId;
        mExpertId = expertId;
        mStatus = status;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        String formParameters = null;
        try {
            formParameters = "status=" + URLEncoder.encode(mStatus, "UTF-8");
            if (mPatientId != null) {
                formParameters += "&patient_id" + URLEncoder.encode(mPatientId.toString(), "UTF-8");
            } else {
                formParameters += "&expert_id" + URLEncoder.encode(mExpertId.toString(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("AppointmentSet Param", formParameters);
        return NetworkUtils.getResponse("appointment/set", "POST", formParameters, mAccessToken);
    }
}
