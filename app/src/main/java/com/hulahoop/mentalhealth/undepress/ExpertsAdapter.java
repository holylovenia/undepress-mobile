package com.hulahoop.mentalhealth.undepress;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hulahoop.mentalhealth.undepress.models.Expert;

import java.util.ArrayList;

/**
 * Created by holy on 25/02/18.
 */

public class ExpertsAdapter extends RecyclerView.Adapter<ExpertsAdapter.ViewHolder> {

    //Member variables
    private ArrayList<Expert> mExpertsData;
    private Context mContext;

    ExpertsAdapter(Context context, ArrayList<Expert> expertsData) {
        this.mExpertsData = expertsData;
        this.mContext = context;
    }

    @Override
    public ExpertsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.expert_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ExpertsAdapter.ViewHolder holder, int position) {
        Expert currentExpert = mExpertsData.get(position);
        holder.bindTo(currentExpert);
    }

    @Override
    public int getItemCount() {
        return mExpertsData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView expertName;
        private TextView expertDescription;
        private Button expertDetailsButton;

        ViewHolder(View itemView) {
            super(itemView);

            expertName = itemView.findViewById(R.id.expert_name);
            expertDescription = itemView.findViewById(R.id.expert_desc);
            expertDetailsButton = itemView.findViewById(R.id.expert_details_button);
            itemView.setOnClickListener(this);
        }

        void bindTo(final Expert currentExpert) {
            expertName.setText(currentExpert.getName());
            expertDescription.setText(currentExpert.getDescription());
            expertDetailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent profileIntent = new Intent(v.getContext(), ProfileActivity.class);
                    profileIntent.putExtra("expert_details", currentExpert);
                    mContext.startActivity(profileIntent);
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }
}
