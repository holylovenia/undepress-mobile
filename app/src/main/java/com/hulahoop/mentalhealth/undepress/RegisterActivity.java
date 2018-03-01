package com.hulahoop.mentalhealth.undepress;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hulahoop.mentalhealth.undepress.loaders.AccountRegisterTaskLoader;

public class RegisterActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private Button registerButton;
    private EditText mInputName, mInputAddress, mInputPhone, mInputEmail, mInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mInputName = findViewById(R.id.register_input_name);
        mInputAddress = findViewById(R.id.register_input_address);
        mInputPhone = findViewById(R.id.register_input_phone);
        mInputEmail = findViewById(R.id.register_input_email);
        mInputPassword = findViewById(R.id.register_input_password);

        registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isThereAnyBlankField()) {
                    Toast.makeText(getApplicationContext(), "Please fill the blank field(s)", Toast.LENGTH_SHORT).show();
                } else {
                    fetch();
                }
            }
        });
    }

    public void fetch() {
        getSupportLoaderManager().initLoader(3, null, this);
    }
    private boolean isThereAnyBlankField() {
        return mInputName.getText().toString().trim().isEmpty() || mInputAddress.getText().toString().trim().isEmpty() || mInputPhone.getText().toString().trim().isEmpty() || mInputEmail.getText().toString().trim().isEmpty() || mInputPassword.getText().toString().trim().isEmpty();
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new AccountRegisterTaskLoader(this, mInputEmail.getText().toString(), mInputPassword.getText().toString(), mInputName.getText().toString(), mInputAddress.getText().toString(), mInputPhone.getText().toString(), false);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        switch (data) {
            case "ACCOUNT_REGISTER_SUCCESS":
                Toast.makeText(this, "You have successfully registered", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case "ACCOUNT_REGISTER_EXIST":
                Toast.makeText(this, "Account already exists", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
