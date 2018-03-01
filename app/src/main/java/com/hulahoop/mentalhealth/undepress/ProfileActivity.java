package com.hulahoop.mentalhealth.undepress;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hulahoop.mentalhealth.undepress.models.Expert;

import org.w3c.dom.Text;

import java.util.List;

public class ProfileActivity extends AppCompatActivity implements OnMapReadyCallback {
    private Button messageButton;
    private TextView expertNameText;
    private TextView expertAddressText;
    private TextView expertPhoneText;
    private TextView expertEmailText;
    private TextView expertExperienceText;
    private SupportMapFragment mapFragment;
    private Expert expert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);

        expertNameText = findViewById(R.id.profile_expert_name);
        expertAddressText = findViewById(R.id.profile_expert_address);
        expertPhoneText = findViewById(R.id.profile_expert_phone);
        expertEmailText = findViewById(R.id.profile_expert_email);
        expertExperienceText = findViewById(R.id.profile_expert_experience);

        expert = (Expert) getIntent().getSerializableExtra("expert_details");

        expertNameText.setText(expert.getName());
        expertAddressText.setText(expert.getAddress());
        expertPhoneText.setText(expert.getPhoneNumber());
        expertEmailText.setText(expert.getEmail());
        expertExperienceText.setText(expert.getExperience());

        messageButton = findViewById(R.id.message_button);
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChatActivity.class);
                intent.putExtra("current_expert", expert);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = getLocationFromAddress(this, expert.getAddress());
        googleMap.addMarker(new MarkerOptions().position(latLng)
                .title("Marker in " + expert.getAddress()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng point = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            point = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return point;
    }
}
