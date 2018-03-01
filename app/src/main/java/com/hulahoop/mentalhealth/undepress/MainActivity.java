package com.hulahoop.mentalhealth.undepress;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v4.app.LoaderManager;

import com.hulahoop.mentalhealth.undepress.loaders.AccountLoginTaskLoader;
import com.hulahoop.mentalhealth.undepress.loaders.SocmedSetTaskLoader;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private ViewPager viewPager;
    private PagerAdapter adapter;
    private AlertDialog socmedDialog;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0);
        }

        mPreferences = getSharedPreferences("authorization", MODE_PRIVATE);

        String[] fragmentTitles = getResources().getStringArray(R.array.fragments);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(fragmentTitles[0]));
        tabLayout.addTab(tabLayout.newTab().setText(fragmentTitles[1]));
        tabLayout.addTab(tabLayout.newTab().setText(fragmentTitles[2]));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = findViewById(R.id.view_pager);
        adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                System.out.println(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Integer fragmentNumber = getIntent().getIntExtra("getBetter", 0);
        viewPager.setCurrentItem(fragmentNumber);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_twitter:
                setUpAddSocmedDialog(0);
                break;
            case R.id.action_facebook:
                setUpAddSocmedDialog(1);
                break;
            case R.id.action_instagram:
                setUpAddSocmedDialog(2);
                break;
            case R.id.action_logout:
                logout();
//                Toast toast = Toast.makeText(getApplicationContext(), "hahahaha", Toast.LENGTH_SHORT);
//                toast.show();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpAddSocmedDialog(int socmedId) {
        Log.d("socmed: ", "" + socmedId);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(getLayoutInflater().inflate(R.layout.socmed_dialog, null));

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                EditText editText = socmedDialog.findViewById(R.id.socmed_dialog);
                addSocmed(socmedId, editText.getText().toString());
                editText.getText().clear();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        socmedDialog = builder.create();
        socmedDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        socmedDialog.show();
    }

    public void addSocmed(int socmedId, String username) {
        Bundle inputBundle = new Bundle();
        if(socmedId == 0) {
            inputBundle.putString("twitter", username);
            inputBundle.putString("facebook", null);
            inputBundle.putString("instagram", null);
        } else if(socmedId == 1) {
            inputBundle.putString("twitter", null);
            inputBundle.putString("facebook", username);
            inputBundle.putString("instagram", null);
        } else {
            inputBundle.putString("twitter", null);
            inputBundle.putString("facebook", null);
            inputBundle.putString("instagram", username);
        }
        getSupportLoaderManager().restartLoader(0, inputBundle, this);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new SocmedSetTaskLoader(this, mPreferences.getString("access_token", "defaultaccesstoken"), (String) args.get("twitter"), (String) args.get("facebook"), (String) args.get("instagram"));
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        Toast toast = Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }


    private void logout() {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.clear();
        editor.apply();
        if (!mPreferences.contains("access_token")) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
