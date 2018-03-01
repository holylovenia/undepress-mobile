package com.hulahoop.mentalhealth.undepress;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hulahoop.mentalhealth.undepress.models.Symptoms;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    private Button getBetterButton;
    private Symptoms symptoms;
    private TextView welcomeText;
    private TextView depressionVerdictText;
    private TextView numberOfDepressionSymptomsText;
    private TextView symptomsDetails;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mPreferences = getSharedPreferences("authorization", MODE_PRIVATE);

        welcomeText = findViewById(R.id.welcome);
        welcomeText.setText("Welcome, " + mPreferences.getString("current_user_name", "Dear"));
        depressionVerdictText = findViewById(R.id.depression_verdict);
        numberOfDepressionSymptomsText = findViewById(R.id.number_of_symptoms);
        symptomsDetails = findViewById(R.id.symptoms_details);

        symptoms = (Symptoms) getIntent().getSerializableExtra("symptoms");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        int sum = 0;
        boolean requiredRulesFulfilled = true;
        requiredRulesFulfilled = !(symptoms.getSum().get(0) == 0 || symptoms.getSum().get(0) == 0);

        StringBuilder details = new StringBuilder();
        for(int i = 0; i < symptoms.getSum().size(); i++) {
            if(symptoms.getSum().get(i) == 1) {
                sum++;
                details.append(sum).append(". ").append(symptoms.getSymptomDescription().get(i)).append("\n");
            }
        }

        numberOfDepressionSymptomsText.setText(sum + " of 9");
        symptomsDetails.setText(details.toString().trim());

        if(requiredRulesFulfilled && sum >= 5) {
            depressionVerdictText.setText("have");
        } else {
            depressionVerdictText.setText("do not have");
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
