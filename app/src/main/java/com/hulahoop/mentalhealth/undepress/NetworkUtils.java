package com.hulahoop.mentalhealth.undepress;

import android.os.HandlerThread;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by agoun on 2/27/2018.
 */

public class NetworkUtils {
    private static final String BASE_URL = "http://undepress.southeastasia.cloudapp.azure.com/";

    public static String getStringResponse(String path, String requestMethod, String urlParams, String accessToken) {
        HttpURLConnection urlConnection = null;
        String result = null;
        BufferedReader br = null;

        try {
            URL requestURL = new URL(BASE_URL + path);
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod(requestMethod);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.setRequestProperty("Authorization", accessToken);
            urlConnection.connect();
            Log.d("NetworkUtils", "Done_Connecting");

            if (urlParams != null) {
                DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());
                out.writeBytes(urlParams);
                out.flush();
                out.close();
            }

            int HttpResult = urlConnection.getResponseCode();
            StringBuffer sb = new StringBuffer();

            if (HttpResult == HttpURLConnection.HTTP_OK) {
                br = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream(),"utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
            } else {
                br = new BufferedReader(new InputStreamReader(
                        urlConnection.getErrorStream(),"utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
            }
            Log.d("NetworkUtils_RESULT", sb.toString());
            result = sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            // cleaning data
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
