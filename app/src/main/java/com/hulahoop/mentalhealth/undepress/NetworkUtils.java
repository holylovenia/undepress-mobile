package com.hulahoop.mentalhealth.undepress;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by agoun on 2/27/2018.
 */

public class NetworkUtils {
    private static final String BASE_URL = "http://undepress.southeastasia.cloudapp.azure.com/";

    public static String getResponse(String path, String requestMethod, String formParameters,
                                     String accessToken) {
        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        String result;

        try {
            URL requestURL = new URL(BASE_URL + path);
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod(requestMethod);
            if(!urlConnection.getRequestMethod().equals("GET")) {
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
            }
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.setRequestProperty("Authorization", accessToken.trim());
            urlConnection.connect();
            Log.d("NetworkUtils", "Done_Connecting");

            if (formParameters != null) {
                DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());
                out.writeBytes(formParameters);
                out.flush();
                out.close();
            }

            int HttpResult = urlConnection.getResponseCode();
            StringBuffer stringBuffer = new StringBuffer();

            if (HttpResult == HttpURLConnection.HTTP_OK) {
                bufferedReader = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream(), "utf-8"));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line + "\n");
                }
                bufferedReader.close();
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(
                        urlConnection.getErrorStream(), "utf-8"));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line + "\n");
                }
                bufferedReader.close();
            }
            Log.d("NetworkUtils_RESULT", stringBuffer.toString());
            result = stringBuffer.toString().trim();
            return result;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            // cleaning data
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
