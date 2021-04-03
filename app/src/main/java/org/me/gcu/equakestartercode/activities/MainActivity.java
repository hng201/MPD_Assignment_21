package org.me.gcu.equakestartercode.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.me.gcu.equakestartercode.R;
import org.me.gcu.equakestartercode.fragments.HomeFragment;
import org.me.gcu.equakestartercode.fragments.MapFragment;
import org.me.gcu.equakestartercode.fragments.SearchFragment;

/**
 * Shirley Ng S1626790
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private SwitchCompat switchMapView;
    private HomeFragment homeFragment;
    private MapFragment mapFragment;
    private SearchFragment searchFragment;

    /**
     * Sets the content to be displayed to activity_main
     * and assigns references to components.
     * Initial fragment HomeFragment is loaded.
     * @param savedInstance
     */
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_main);

        switchMapView = findViewById(R.id.switchMapView);

        switchMapView.setChecked(false);
        switchMapView.setOnClickListener(this);
        homeFragment = new HomeFragment();
        mapFragment = new MapFragment();
        searchFragment = new SearchFragment();
        Fragment fragment = homeFragment;
        loadFragment(fragment);
    }

    /**
     * Used to load new fragments
     * @param fragment the fragment to be loaded
     */
    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace current fragment with new fragment
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }


    @Override
    public void onClick(View v) {
        if (switchMapView.isChecked()){
            loadFragment(mapFragment);
        }
        else {
            loadFragment(homeFragment);

        }
    }
}
