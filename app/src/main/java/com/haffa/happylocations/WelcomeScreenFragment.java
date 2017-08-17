package com.haffa.happylocations;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.haffa.happylocations.Data.DatabaseHelper;
import com.haffa.happylocations.LocationLogic.LocationFetcher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.haffa.happylocations.Utilities.RetriveMyApplicationContext.getAppContext;


public class WelcomeScreenFragment extends Fragment {
    LocationFetcher locationFetcher;
    ImageButton phoneLocationButton, favoritesButton, mapButton;
    Intent intent;

    public WelcomeScreenFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_welcome_screen, container, false);


        locationFetcher = new LocationFetcher();
        phoneLocationButton = (ImageButton) rootView.findViewById(R.id.phoneLocationButton);
        favoritesButton = (ImageButton) rootView.findViewById(R.id.favorites_button);
        mapButton = (ImageButton) rootView.findViewById(R.id.map_button);

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
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getAppContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
