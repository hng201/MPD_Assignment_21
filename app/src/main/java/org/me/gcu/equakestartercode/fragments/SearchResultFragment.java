 package org.me.gcu.equakestartercode.fragments;

 import android.content.Context;
 import android.os.AsyncTask;
 import android.os.Bundle;
 import android.util.Log;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.Button;
 import android.widget.TextView;

 import androidx.fragment.app.Fragment;
 import androidx.fragment.app.FragmentTransaction;
 import androidx.room.Room;

 import org.me.gcu.equakestartercode.R;
 import org.me.gcu.equakestartercode.database.AppDatabase;
 import org.me.gcu.equakestartercode.models.Earthquake;

 import java.util.Date;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;

 /**
  * Shirley Ng S1626790
  */
public class SearchResultFragment extends Fragment implements View.OnClickListener {
    private TextView tvSearchCriteria;
    private TextView tvNorth;
    private TextView tvSouth;
    private TextView tvEast;
    private TextView tvWest;
    private TextView tvLargeMag;
    private TextView tvEarthquakeNo;
    private TextView tvShallowest;
    private TextView tvDeepest;
    private ArrayList<String> dates = new ArrayList<>();
    private Earthquake northEq;
    private Earthquake southEq;
    private Earthquake eastEq;
    private Earthquake westEq;
    private Earthquake largestMagEq;
    private Earthquake shallowestEq;
    private Earthquake deepestEq;
    private Button btnChangeSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container, false);

        tvSearchCriteria = view.findViewById(R.id.tvSearchCriteria);
        tvNorth = view.findViewById(R.id.tvNorth);
        tvSouth = view.findViewById(R.id.tvSouth);
        tvEast = view.findViewById(R.id.tvEast);
        tvWest = view.findViewById(R.id.tvWest);
        tvLargeMag = view.findViewById(R.id.tvLargeMag);
        tvEarthquakeNo = view.findViewById(R.id.tvEarthquakeNo);
        tvShallowest = view.findViewById(R.id.tvShallowest);
        tvDeepest = view.findViewById(R.id.tvDeepest);
        btnChangeSearch = view.findViewById(R.id.btnChangeSearch);
        btnChangeSearch.setOnClickListener(this);

        new GetFilteredEarthquakesTask().execute(dates);

        return view;
    }

     @Override
     public void onClick(View v) {
         if (v == btnChangeSearch){
             SearchFragment searchFragment = new SearchFragment();
             FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
             // Replace current fragment with new fragment
             transaction.replace(R.id.frameLayout, searchFragment);
             transaction.commit();
         }
     }

     private class GetFilteredEarthquakesTask extends AsyncTask<ArrayList<String>, Void, ArrayList<Earthquake>> {
        @Override
        protected ArrayList<Earthquake> doInBackground(ArrayList<String>... params) {
            Context context = getContext();
            AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "db-earthquake").build();
            // Get the search criteria from params[0]
            ArrayList<String> searchCriteria = params[0];
            // Check to see if there are more than one date
            if (searchCriteria.size() > 1){
                // Log the search criteria
                Log.e("Search Criteria", searchCriteria.get(0) + " to " + searchCriteria.get(1));

                String start = "";
                String end = "";

                try {
                    // Foramtters to convert date into suitable format for
                    // Room DB querying of dates
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat sdfSearchFormat = new SimpleDateFormat("yyyy-MM-dd");
                    // Convert strings into date
                    Date date1 = sdf.parse(searchCriteria.get(0));
                    Date date2 = sdf.parse(searchCriteria.get(1));
                    // Format dates into string query format
                    start = sdfSearchFormat.format(date1);
                    end = sdfSearchFormat.format(date2);
                }
                catch (Exception e){
                    Log.e("Exception", e.toString());
                }

                // Get earthquake for each category
                northEq = db.earthquakeDao().getNorthEarthquakeByDateRange(start, end);
                southEq = db.earthquakeDao().getSouthEarthquakeByDateRange(start, end);
                eastEq = db.earthquakeDao().getEastEarthquakeByDateRange(start, end);
                westEq = db.earthquakeDao().getWestEarthquakeByDateRange(start, end);
                largestMagEq = db.earthquakeDao().getLargestMagnitudeEarthquakeByDateRange(start, end);
                shallowestEq = db.earthquakeDao().getShallowestEarthquakeByDateRange(start, end);
                deepestEq = db.earthquakeDao().getDeepestEarthquakeByDateRange(start, end);

                // Return filtered earthquake list
                return (ArrayList<Earthquake>) db.earthquakeDao().getEarthquakesByDateRange(start, end);
            }
            else {
                // Log search criteria
                Log.e("Search Criteria", searchCriteria.get(0));

                String date = "";

                try {
                    // Foramtters to convert date into suitable format for
                    // Room DB querying of date
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat sdfSearchFormat = new SimpleDateFormat("yyyy-MM-dd");
                    // Convert strings into date
                    Date date1 = sdf.parse(searchCriteria.get(0));
                    // Format dates into string query format
                    date = sdfSearchFormat.format(date1);
                }
                catch (Exception e){
                    Log.e("Exception", e.toString());
                }

                // Get earthquake for each category
                northEq = db.earthquakeDao().getNorthEarthquake(date);
                southEq = db.earthquakeDao().getSouthEarthquake(date);
                eastEq = db.earthquakeDao().getEastEarthquake(date);
                westEq = db.earthquakeDao().getWestEarthquake(date);
                largestMagEq = db.earthquakeDao().getLargestMagnitudeEarthquake(date);
                shallowestEq = db.earthquakeDao().getShallowestEarthquake(date);
                deepestEq = db.earthquakeDao().getDeepestEarthquake(date);

                // Return filtered earthquake list
                return (ArrayList<Earthquake>) db.earthquakeDao().getEarthquakesByDate(date);
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Earthquake> result)
        {
            super.onPostExecute(result);
            // Log the content of the filtered earthquake list
            Log.e("Filtered List", result.toString());
            // If there are more than one date
            if (dates.size() > 1){
                // Update the text view to show the search criteria
                tvSearchCriteria.setText(dates.get(0) + " to " + dates.get(1));
            }
            else{
                // Update the text view to show the search criteria
                tvSearchCriteria.setText(dates.get(0));
            }

            if (result.isEmpty()){
                // Update text view to display no earthquakes found
                tvEarthquakeNo.setText("No Earthquakes Found");
            }
            else {
                getEarthquakeSummary();
                // Update text view to display the number of earthquakes found
                tvEarthquakeNo.setText("Number of Earthquakes Found: " + result.size());
            }
        }
    }

     /**
      * Updates the textview content for each category
      */
    private void getEarthquakeSummary() {
        String north = northEq.getLocation() + "\n" + northEq.getGeoLat() + "," + northEq.getGeoLong();
        tvNorth.setText(north);
        String south = southEq.getLocation() + "\n" + southEq.getGeoLat() + "," + southEq.getGeoLong();
        tvSouth.setText(south);
        String east = eastEq.getLocation() + "\n" + eastEq.getGeoLat() + "," + eastEq.getGeoLong();
        tvEast.setText(east);
        String west = westEq.getLocation() + "\n" + westEq.getGeoLat() + "," + westEq.getGeoLong();
        tvWest.setText(west);
        String largestMagnitude = largestMagEq.getLocation() + " M" + largestMagEq.getMagnitude();
        tvLargeMag.setText(largestMagnitude);
        String shallowest = shallowestEq.getLocation() + "\nDepth: " + shallowestEq.getDepth();
        tvShallowest.setText(shallowest);
        String deepest = deepestEq.getLocation() + "\nDepth: " + deepestEq.getDepth();
        tvDeepest.setText(deepest);
    }

     /**
      * Used by SearchFragment to pass the search criteria to this fragment
      * @param temp
      */
    public void setDates(ArrayList<String> temp){
        dates = temp;
    }
}
