package com.hulahoop.mentalhealth.undepress;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hulahoop.mentalhealth.undepress.loaders.AccountLoginTaskLoader;

public class LoginActivity extends AppCompatActivity implements LoaderManager
        .LoaderCallbacks<String> {

    Button loginButton;
    EditText mInputEmail, mInputPassword;
    SharedPreferences mPreferences;
    ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mInputEmail = findViewById(R.id.login_input_email);
        mInputPassword = findViewById(R.id.login_input_password);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mPreferences = getSharedPreferences("authorization", MODE_PRIVATE);

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        }

        if (mPreferences.contains("access_token")) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }

        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });
    }

    public void login(View view) {
        String email = mInputEmail.getText().toString();
        String password = mInputPassword.getText().toString();
        boolean valid = true;

        Log.d("login_activity: ", email);
        Log.d("login_activity: ", password);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                mInputEmail.setError("Email is wrong");
                valid = false;
            }
            if (TextUtils.isEmpty(password)) {
                mInputPassword.setError("Password is empty");
                valid = false;
            }
            if (valid) {
                Bundle requestBundle = new Bundle();
                requestBundle.putString("email", email);
                requestBundle.putString("password", password);

                getSupportLoaderManager().restartLoader(0, requestBundle, this);
            }
        } else {
            Toast.makeText(this, "no_internet_access", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new AccountLoginTaskLoader(this, args.getString("email"), args.getString
                ("password"));
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String access_token) {
        try {
            SharedPreferences.Editor preferencesEditor = mPreferences.edit();
            preferencesEditor.putString("access_token", access_token);
            preferencesEditor.apply();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "credential_incorrect", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
