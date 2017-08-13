package com.haffa.happylocations;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.haffa.happylocations.LocationLogic.LocationFetcher;
import com.haffa.happylocations.Utilities.PermissionCheck;
import com.mohan.location.locationtrack.LocationProvider;
import com.mohan.location.locationtrack.LocationTrack;
import com.mohan.location.locationtrack.pojo.LocationObj;
import com.mohan.location.locationtrack.providers.FusedLocationProvider;

import static com.haffa.happylocations.Utilities.RetriveMyApplicationContext.getAppContext;


public class WelcomeScreenFragment extends Fragment {
    LocationFetcher locationFetcher;
    ImageButton phoneLocationButton, favoritesButton;
    Intent intent;
    public WelcomeScreenFragment() {
    }

    public static WelcomeScreenFragment newInstance() {
        WelcomeScreenFragment fragment = new WelcomeScreenFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_welcome_screen, container, false);

        locationFetcher = new LocationFetcher();
        phoneLocationButton = (ImageButton) rootView.findViewById(R.id.phoneLocationButton);
        favoritesButton = (ImageButton) rootView.findViewById(R.id.favorites_button);

        phoneLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationFetcher.displayCurrentLocation(getActivity());
            }
        });

        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getAppContext(), FavoritesActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
