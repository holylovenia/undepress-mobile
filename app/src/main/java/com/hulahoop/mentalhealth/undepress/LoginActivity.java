package com.hulahoop.mentalhealth.undepress;

import android.support.v4.content.Loader;
import android.support.v4.app.LoaderManager;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    Button loginButton;
    EditText mInputEmail, mInputPassword;
    SharedPreferences mPreferences;
    ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mInputEmail = (EditText) findViewById(R.id.login_input_email);
        mInputPassword = (EditText) findViewById(R.id.login_input_password);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        loginButton = (Button) findViewById(R.id.login_button);

        mPreferences = getSharedPreferences("authorization", MODE_PRIVATE);
        //check connectivity
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (mPreferences.contains("access_token")) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    public void login(View view) {
        String email, password;
        email = mInputEmail.getText().toString();
        password = mInputPassword.getText().toString();
        boolean ok = true;

        Log.d("login_activity: ", email);
        Log.d("login_activity: ", password);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                mInputEmail.setError("Email is wrong");
                ok = false;
            }
            if (TextUtils.isEmpty(password)) {
                mInputPassword.setError("Password is empty");
                ok = false;
            }
            if (ok) {
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
        return new AccountLoginTaskLoader(this, args.getString("email"), args.getString("password"));
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        try {
            String access_token = data;
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
