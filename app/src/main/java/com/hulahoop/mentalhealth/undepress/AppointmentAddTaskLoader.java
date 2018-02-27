package com.hulahoop.mentalhealth.undepress;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.net.URLEncoder;

/**
 * Created by agoun on 2/27/2018.
 */

public class AppointmentAddTaskLoader extends AsyncTaskLoader<String> {
    private String mAccessToken;
    private Integer mExpertId;
    private String mTimestamp;
    private String mLocation;
    private String mIssueDescription;
    private String mStatus;

    public AppointmentAddTaskLoader(Context context, String accessToken, Integer expertId, String
            timestamp, String location, String issueDescription, String status) {
        super(context);
        mAccessToken = accessToken;
        mExpertId = expertId;
        mTimestamp = timestamp;
        mLocation = location;
        mIssueDescription = issueDescription;
        mStatus = status;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        String urlParameter = null;
        try {
            urlParameter = "status=" + URLEncoder.encode(mStatus, "UTF-8");
            urlParameter += "&expert_id" + URLEncoder.encode(mExpertId.toString(), "UTF-8");
            urlParameter += "&timestamp" + URLEncoder.encode(mTimestamp, "UTF-8");
            urlParameter += "&location" + URLEncoder.encode(mLocation, "UTF-8");
            urlParameter += "&issue_description" + URLEncoder.encode(mIssueDescription, "UTF-8");
            urlParameter += "&status" + URLEncoder.encode(mStatus, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("Account Param", urlParameter);
        return NetworkUtils.getResponse("appointment/add", "POST", urlParameter, mAccessToken);
    }
}
