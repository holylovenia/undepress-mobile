package com.hulahoop.mentalhealth.undepress;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hulahoop.mentalhealth.undepress.loaders.DetectionTaskLoader;
import com.hulahoop.mentalhealth.undepress.models.Symptoms;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;



public class DetectFragment extends Fragment implements LoaderManager.LoaderCallbacks<String> {
    Button detectButton;
    AVLoadingIndicatorView loadingIndicatorView;
    Handler handler;
    private SharedPreferences mPreferences;
    private Symptoms symptoms;
    private ArrayList<Integer> verdict;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detect, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        handler = new Handler();
        loadingIndicatorView = view.findViewById(R.id.loading);

        verdict = new ArrayList<>();
        symptoms = new Symptoms(verdict);

        detectButton = view.findViewById(R.id.detect_button);
        detectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingIndicatorView.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        Intent intent = new Intent(view.getContext(), ResultActivity.class);
//                        startActivity(intent);
                        loadingIndicatorView.hide();
                    }
                }, 3000);
                Intent intent = new Intent(getActivity(), ResultActivity.class);
                intent.putExtra("symptoms", symptoms);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });

        mPreferences = getActivity().getSharedPreferences("authorization", MODE_PRIVATE);
        getActivity().getSupportLoaderManager().initLoader(1, null, this);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        Log.d("DetectFragment", mPreferences.getString("access_token", "default"));
        return new DetectionTaskLoader(getContext(), mPreferences.getString("access_token", "defaultaccesstoken"));
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        Log.d("Detection Fragment", data);
        try {
            JSONArray detectionJSONArray = new JSONArray(data);
            Log.d("Symptoms1", Integer.toString(detectionJSONArray.getInt(0)));
            Log.d("Symptoms2", Integer.toString(detectionJSONArray.getInt(1)));
            Log.d("Symptoms3", Integer.toString(detectionJSONArray.getInt(2)));
            Log.d("Symptoms4", Integer.toString(detectionJSONArray.getInt(3)));
            Log.d("Symptoms5", Integer.toString(detectionJSONArray.getInt(4)));
            Log.d("Symptoms6", Integer.toString(detectionJSONArray.getInt(5)));
            Log.d("Symptoms7", Integer.toString(detectionJSONArray.getInt(6)));
            Log.d("Symptoms8", Integer.toString(detectionJSONArray.getInt(7)));
            Log.d("Symptoms9", Integer.toString(detectionJSONArray.getInt(8)));

            for (int i = 0; i < 9; i++) {
                verdict.add(detectionJSONArray.getInt(i));;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
