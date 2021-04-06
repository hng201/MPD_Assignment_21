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

public class SearchResultFragment extends Fragment {
    private TextView tvLargeMag;
    private TextView tvNull;
    private ArrayList<Earthquake> filteredList = new ArrayList<>();
    private ArrayList<String> dates = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Search Criteria", dates.get(0));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container, false);

        tvLargeMag = view.findViewById(R.id.tvLargeMag);
        tvNull = view.findViewById(R.id.tvNull);

        new GetFilteredEarthquakesTask().execute(dates.get(0));

        return view;
    }

    private class GetFilteredEarthquakesTask extends AsyncTask<String, Void, ArrayList<Earthquake>> {
        @Override
        protected ArrayList<Earthquake> doInBackground(String... params) {
            Context context = getContext();
            AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "db-earthquake").build();
            Log.e("Param", params[0]);
            return (ArrayList<Earthquake>) db.earthquakeDao().getEarthquakesByDate(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Earthquake> result)
        {
            super.onPostExecute(result);
            Log.e("List", result.toString());
            filteredList = result;
            Log.e("Filtered List", filteredList.toString());
            if (filteredList.isEmpty()){
                tvNull.setText("No Earthquakes Found");
            }
            else {
                getEarthquakeSummary(filteredList);
                tvNull.setText("Number of Earthquakes Found: " + filteredList.size());
            }
        }
    }

    private void getEarthquakeSummary(ArrayList<Earthquake> earthquakeList) {
        Earthquake earthquake = new Earthquake();
        if (earthquakeList.size() < 2){
            earthquake = earthquakeList.get(0);
        }
        else {
            for (int i = 0; i < filteredList.size(); i++) {
                if (earthquake == null) {
                    earthquake = filteredList.get(i);
                    Log.e("Current LargeMag", earthquake.getMagnitude() + ", " + earthquake.getLocation() + ", " + earthquake.getPubDate());
                } else {
                    Log.e("Compare Value", earthquake.getMagnitude() + ", " + earthquake.getLocation() + ", " + earthquake.getPubDate());
                    if (earthquake.getMagnitude() < filteredList.get(i).getMagnitude()) {
                        earthquake = earthquakeList.get(i);
                        Log.e("Current LargeMag", earthquake.getMagnitude() + ", " + earthquake.getLocation() + ", " + earthquake.getPubDate());
                    }
                }
            }
        }

        String largestMagnitude = earthquake.getLocation() + " M" + earthquake.getMagnitude();
        tvLargeMag.setText(largestMagnitude);
    }

    public void setDates(ArrayList<String> temp){
        dates = temp;
    }
}
