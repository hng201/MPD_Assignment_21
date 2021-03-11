package org.me.gcu.equakestartercode.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.me.gcu.equakestartercode.R;
import org.me.gcu.equakestartercode.models.Earthquake;

import java.util.ArrayList;

public class MapFragment extends Fragment implements OnMapReadyCallback{
    private GoogleMap gmap;
    private ArrayList<Earthquake> earthquakeList = new ArrayList<>();;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync( this);
        return view;
    }

    /**
     * Sets a FragmentResultListener which will trigger when a result
     * with the result key 'elist' is set.
     * The earthquakeList is obtained here from the HomeFragment.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("elist", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(String requestKey, Bundle bundle) {
                // Get the value of the key 'earthquakes from the bundle
                earthquakeList = (ArrayList<Earthquake>) bundle.getSerializable("earthquakes");
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * The location of each earthquake is also marked in the map.
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;

        for (Earthquake earthquake: earthquakeList){
            // Create a new coordinates based on the earthquakes geo latitude and geo longitude
            LatLng latLng = new LatLng(earthquake.getGeoLat(), earthquake.getGeoLong());
            // Add the location of the earthquake as a marker on GoogleMaps based on its coordinates
            // Set the title to the earthquake's location name
            gmap.addMarker(new MarkerOptions().position(latLng).title(earthquake.getLocation()));
        }
        // Coordinates for London
        LatLng london = new LatLng(51.509865, -0.118092);
        // Moves the camera to the location of London
        // This is to just focus the camera on the UK
        gmap.moveCamera(CameraUpdateFactory.newLatLng(london));
        // Displays the Zoom in and out controls on the Map UI
        gmap.getUiSettings().setZoomControlsEnabled(true);
    }

}
