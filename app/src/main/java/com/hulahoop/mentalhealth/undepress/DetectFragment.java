package com.hulahoop.mentalhealth.undepress;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wang.avi.AVLoadingIndicatorView;


/**
 * Created by holy on 10/02/18.
 */

public class DetectFragment extends Fragment {
    Button detectButton;
    AVLoadingIndicatorView loadingIndicatorView;
    Handler handler;

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

        detectButton = view.findViewById(R.id.detect_button);
        detectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingIndicatorView.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(view.getContext(), ResultActivity.class);
                        startActivity(intent);
                        loadingIndicatorView.hide();
                    }
                }, 3000);
            }
        });
    }
}
