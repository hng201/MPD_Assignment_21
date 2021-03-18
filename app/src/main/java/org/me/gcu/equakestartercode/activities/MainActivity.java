package org.me.gcu.equakestartercode.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.me.gcu.equakestartercode.R;
import org.me.gcu.equakestartercode.fragments.HomeFragment;
import org.me.gcu.equakestartercode.fragments.MapFragment;

/**
 * Shirley Ng S1626790
 */
public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bnView;
    private ActionBar toolbar;
    private HomeFragment homeFragment;
    private MapFragment mapFragment;

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

        bnView = findViewById(R.id.bnView);
        bnView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // Set the active menu item in the BottomNavigationView
        bnView.getMenu().getItem(0).setChecked(true);

        toolbar = getSupportActionBar();
        toolbar.setTitle("Home - Shirley Ng S1626790");

        homeFragment = new HomeFragment();
        mapFragment = new MapFragment();
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


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        /**
         * Used to identify which menu item has been selected.
         * For the selected menu item, load the corresponding fragment and
         * set an appropriate Title on the toolbar for screen
         * @param item
         * @return true indicates a particular menu item has been selected
         */
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:
                    toolbar.setTitle("Home - Shirley Ng S1626790");
                    loadFragment(homeFragment);
                    return true;
                case R.id.search:
                    toolbar.setTitle("Search - Shirley Ng S1626790");
                    return true;
                case R.id.map:
                    toolbar.setTitle("Map View - Shirley Ng S1626790");
                    loadFragment(mapFragment);
                    return true;
            }
            return false;
        }
    };
}
