package com.hulahoop.mentalhealth.undepress;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hulahoop.mentalhealth.undepress.models.Symptoms;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    Button getBetterButton;
    private Symptoms symptoms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        symptoms = (Symptoms) getIntent().getSerializableExtra("symptoms");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        int total = 0;
        for (int i = 0; i < symptoms.getSum().size(); i++) {
            if (symptoms.getSum().get(i) != 0) {
                total++;
            }
            Log.d("Result", Integer.toString(symptoms.getSum().get(i)));
        }




        getBetterButton = findViewById(R.id.get_better_button);
        getBetterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("getBetter", 2);
                startActivity(intent);
                finish();
            }
        });
    }
}
