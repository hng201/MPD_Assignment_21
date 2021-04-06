 package org.me.gcu.equakestartercode.fragments;

 import android.content.Context;
 import android.os.AsyncTask;
 import android.os.Bundle;
 import android.util.Log;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.TextView;

 import androidx.fragment.app.Fragment;
 import androidx.room.Room;

 import org.me.gcu.equakestartercode.R;
 import org.me.gcu.equakestartercode.database.AppDatabase;
 import org.me.gcu.equakestartercode.models.Earthquake;

 import java.util.ArrayList;

 /**
  * Shirley Ng S1626790
  */
public class SearchResultFragment extends Fragment {
    private TextView tvSearchCriteria;
    private TextView tvNorth;
    private TextView tvSouth;
    private TextView tvEast;
    private TextView tvWest;
    private TextView tvLargeMag;
    private TextView tvEarthquakeNo;
    private TextView tvShallowest;
    private TextView tvDeepest;
    private ArrayList<Earthquake> filteredList = new ArrayList<>();
    private ArrayList<String> dates = new ArrayList<>();
    private Earthquake northEq;
    private Earthquake southEq;
    private Earthquake eastEq;
    private Earthquake westEq;
    private Earthquake largestMagEq;
    private Earthquake shallowestEq;
    private Earthquake deepestEq;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Search Criteria", dates.get(0));
    }

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

        new GetFilteredEarthquakesTask().execute(dates.get(0));

        return view;
    }

    private class GetFilteredEarthquakesTask extends AsyncTask<String, Void, ArrayList<Earthquake>> {
        @Override
        protected ArrayList<Earthquake> doInBackground(String... params) {
            Context context = getContext();
            AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "db-earthquake").build();
            Log.e("Param", params[0]);
            northEq = db.earthquakeDao().getNorthEarthquake(params[0]);
            southEq = db.earthquakeDao().getSouthEarthquake(params[0]);
            eastEq = db.earthquakeDao().getEastEarthquake(params[0]);
            westEq = db.earthquakeDao().getWestEarthquake(params[0]);
            largestMagEq = db.earthquakeDao().getLargestMagnitudeEarthquake(params[0]);
            shallowestEq = db.earthquakeDao().getShallowestEarthquake(params[0]);
            deepestEq = db.earthquakeDao().getDeepestEarthquake(params[0]);
            return (ArrayList<Earthquake>) db.earthquakeDao().getEarthquakesByDate(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Earthquake> result)
        {
            super.onPostExecute(result);
            Log.e("List", result.toString());
            filteredList = result;
            Log.e("Filtered List", filteredList.toString());
            tvSearchCriteria.setText(dates.get(0));
            if (filteredList.isEmpty()){
                tvEarthquakeNo.setText("No Earthquakes Found");
            }
            else {
                getEarthquakeSummary(filteredList);
                tvEarthquakeNo.setText("Number of Earthquakes Found: " + filteredList.size());
            }
        }
    }

    private void getEarthquakeSummary(ArrayList<Earthquake> earthquakeList) {
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

    public void setDates(ArrayList<String> temp){
        dates = temp;
    }
}
