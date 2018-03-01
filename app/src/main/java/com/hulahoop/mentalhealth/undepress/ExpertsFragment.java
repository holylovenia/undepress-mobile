package com.hulahoop.mentalhealth.undepress;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hulahoop.mentalhealth.undepress.loaders.ExpertTaskLoader;
import com.hulahoop.mentalhealth.undepress.models.Expert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by holy on 10/02/18.
 */

public class ExpertsFragment extends Fragment implements LoaderManager.LoaderCallbacks<String> {
    private SharedPreferences mPreferences;
    private RecyclerView expertsRecyclerView;
    private ArrayList<Expert> experts;
    private ExpertsAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_experts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPreferences = getActivity().getSharedPreferences("authorization", MODE_PRIVATE);

        expertsRecyclerView = getView().findViewById(R.id.expert_recycler_view);

        //Set the Layout Manager
        expertsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        experts = new ArrayList<>();
        Log.d("ExpertsSize", "" + experts.size());

        if (experts.size() == 0) {
            getActivity().getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        Log.d("ExpertsFragment", mPreferences.getString("access_token", "default"));
        return new ExpertTaskLoader(getContext(), mPreferences.getString("access_token", "defaultaccesstoken"));
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        try {
            experts.clear();
            Log.d("ExpertTaskLoader", data);
            JSONArray expertsJsonArray = new JSONArray(data);
            JSONObject currentExpertJsonObject;
            for (int i = 0; i < expertsJsonArray.length(); i++) {
                currentExpertJsonObject = expertsJsonArray.getJSONObject(i);
                int id = currentExpertJsonObject.getInt("user_id");
                String name = currentExpertJsonObject.getString("name");
                String description, experience;
                if(currentExpertJsonObject.has("expert_description")) {
                    description = currentExpertJsonObject.getString("expert_description");
                } else {
                    description = "Ready to help.";
                }
                String address = currentExpertJsonObject.getString("address");
                String phoneNumber = currentExpertJsonObject.getString("phone");
                String email = currentExpertJsonObject.getString("email");
                if(currentExpertJsonObject.has("expert_experience")) {
                    experience = currentExpertJsonObject.getString("expert_experience");
                } else {
                    experience = "-";
                }
                Expert temp = new Expert(id, name, description, address, phoneNumber, email, experience);
                experts.add(temp);
            }
            adapter = new ExpertsAdapter(getContext(), experts);
            expertsRecyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
