package com.hulahoop.mentalhealth.undepress;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.hulahoop.mentalhealth.undepress.models.Expert;

public class ProfileActivity extends AppCompatActivity {
    Button messageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        Expert expert = (Expert) getIntent().getSerializableExtra("expert_details");

        messageButton = findViewById(R.id.message_button);
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChatActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
