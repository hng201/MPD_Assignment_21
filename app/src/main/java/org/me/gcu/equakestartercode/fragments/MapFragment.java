package org.me.gcu.equakestartercode.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.me.gcu.equakestartercode.R;
import org.me.gcu.equakestartercode.database.AppDatabase;
import org.me.gcu.equakestartercode.models.Earthquake;

import java.util.ArrayList;

/**
 * Shirley Ng S1626790
 */
public class MapFragment extends Fragment implements OnMapReadyCallback{
    private GoogleMap gmap;
    private ArrayList<Earthquake> earthquakeList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new GetEarthquakesTask().execute();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync( this);
        return view;
    }

    private class GetEarthquakesTask extends AsyncTask<Void, Void, ArrayList<Earthquake>> {
        @Override
        protected ArrayList<Earthquake> doInBackground(Void... params) {
            AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "db-earthquake").build();
            return (ArrayList<Earthquake>) db.earthquakeDao().getEarthquakes();
        }

        @Override
        protected void onPostExecute(ArrayList<Earthquake> result)
        {
            super.onPostExecute(result);
            earthquakeList = result;
            Log.e("MapList", earthquakeList.toString());
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * The location of each earthquake is also marked in the map
     * and colour coded based on magnitude strength.
     * NOTE: Colour coding here does not match the earthquake
     * colour coding outside of the MapView due to the limitted
     * options of colours for the markers
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;

        for (Earthquake earthquake: earthquakeList){
            // Create a new coordinates based on the earthquakes geo latitude and geo longitude
            LatLng latLng = new LatLng(earthquake.getGeoLat(), earthquake.getGeoLong());
            // Add the location of the earthquake as a marker on GoogleMaps based on its coordinates
            if (earthquake.getMagnitude() <= 1){
                // Set the title to the earthquake's location name
                gmap.addMarker(new MarkerOptions().position(latLng).title(earthquake.getLocation())).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            }
            else if (earthquake.getMagnitude() <= 2) {
                // Set the title to the earthquake's location name
                gmap.addMarker(new MarkerOptions().position(latLng).title(earthquake.getLocation())).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
            }
            else if (earthquake.getMagnitude() <= 3) {
                // Set the title to the earthquake's location name
                gmap.addMarker(new MarkerOptions().position(latLng).title(earthquake.getLocation())).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            }
            else if (earthquake.getMagnitude() <= 4) {
                // Set the title to the earthquake's location name
                gmap.addMarker(new MarkerOptions().position(latLng).title(earthquake.getLocation())).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            }
            else if (earthquake.getMagnitude() <= 5) {
                // Set the title to the earthquake's location name
                gmap.addMarker(new MarkerOptions().position(latLng).title(earthquake.getLocation())).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
            }
            else if (earthquake.getMagnitude() <= 6) {
                // Set the title to the earthquake's location name
                gmap.addMarker(new MarkerOptions().position(latLng).title(earthquake.getLocation())).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            }
            else if (earthquake.getMagnitude() <= 7) {
                // Set the title to the earthquake's location name
                gmap.addMarker(new MarkerOptions().position(latLng).title(earthquake.getLocation())).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
            }
            else if (earthquake.getMagnitude() <= 8) {
                // Set the title to the earthquake's location name
                gmap.addMarker(new MarkerOptions().position(latLng).title(earthquake.getLocation())).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
            }
            else if (earthquake.getMagnitude() <= 9) {
                // Set the title to the earthquake's location name
                gmap.addMarker(new MarkerOptions().position(latLng).title(earthquake.getLocation())).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            }
            else {
                // Set the title to the earthquake's location name
                gmap.addMarker(new MarkerOptions().position(latLng).title(earthquake.getLocation())).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            }

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
