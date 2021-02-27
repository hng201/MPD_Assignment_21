package org.me.gcu.equakestartercode.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.me.gcu.equakestartercode.R;
import org.me.gcu.equakestartercode.adapters.CustomAdapter;
import org.me.gcu.equakestartercode.models.Earthquake;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeFragment extends Fragment implements OnClickListener
{
    private TextView rawDataDisplay;
    private Button startButton;
    private String result;
    private String url1="";
    private String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    private ProgressBar pbData;
    private TextView tvProgress;
    private RecyclerView rvData;
    private  RecyclerView.Adapter rvAdapter;
    private  ArrayList<Earthquake> recentList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Log.e("MyTag","in onCreate");
        // Set up the raw links to the graphical components
        rawDataDisplay = (TextView)view.findViewById(R.id.rawDataDisplay);
        startButton = (Button)view.findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
        Log.e("MyTag","after startButton");
        // More Code goes here
        pbData = view.findViewById(R.id.pbData);
        tvProgress = view.findViewById(R.id.tvProgress);

        rvData = view.findViewById(R.id.rvData);
        rvData.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvData.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvData.getContext(),
                DividerItemDecoration.VERTICAL);
        rvData.addItemDecoration(dividerItemDecoration);

        rvAdapter = new CustomAdapter(recentList);
        rvData.setAdapter(rvAdapter);
        return view;
    }

    public void onClick(View aview)
    {
        Log.e("MyTag","in onClick");
        startProgress();
        Log.e("MyTag","after startProgress");
    }

    public void startProgress()
    {
        // Run network access on a separate thread;
        new DownLoadDataTask().execute(urlSource);
    } //

    /**
     * Updates dataset used in recycler view
     * @param earthquakeList array list of earthquakes
     */
    public void updateData(ArrayList<Earthquake> earthquakeList){
        recentList.addAll(earthquakeList);
        rvAdapter.notifyDataSetChanged();
    }

    private class DownLoadDataTask extends AsyncTask<String, Integer, ArrayList<Earthquake>>
    {

        int progress_status;
        ArrayList<Earthquake> earthquakeList;

        @Override
        protected void onPreExecute()
        {
            // update the UI immediately after the task is executed
            super.onPreExecute();


            progress_status = 0;
            tvProgress.setText("Downloading 0%");

        }
        @Override
        protected ArrayList<Earthquake> doInBackground(String... params) {
            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";

            Log.e("MyTag", "in run");

            try {
                Log.e("MyTag", "in try");
                aurl = new URL(params[0]);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                Log.e("MyTag", "after ready");
                //
                // Throw away the first 2 header lines before parsing
                //
                //
                //
                int i = 0;
                while ((inputLine = in.readLine()) != null) {
                    if (i > 1) {
                        result = result + inputLine;
                        Log.e("MyTag", inputLine);
                    } else {
                        i++;
                    }

                }
                earthquakeList = null;
                Earthquake earthquake = null;
                result = result.substring(4);
                result = result.substring(0, result.length() - 6);
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                //factory.setNamespaceAware(true);

                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(new StringReader(result));
                System.out.println(result);
                Double totalItems = 0.0;
                Pattern p = Pattern.compile("<item>");
                Matcher m = p.matcher(result);
                while (m.find()) {
                    totalItems++;
                }
                Log.e("Total Items: ", String.valueOf(totalItems));
                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_DOCUMENT) {
                        Log.e("MyTag", "Start document");
                        earthquakeList = new ArrayList<>();
                    } else if (eventType == XmlPullParser.START_TAG) {
                        Log.e("Current Tag", xpp.getName());
                        if (xpp.getName().equalsIgnoreCase("item")) {
                            Log.e("Item", "start");
                            earthquake = new Earthquake();
                        } else if (earthquake != null) {
                            if (xpp.getName().equalsIgnoreCase("title")) {
                                String title = xpp.nextText();
                                Log.e("Title is", title);
                            } else if (xpp.getName().equalsIgnoreCase("description")) {
                                String description = xpp.nextText();
                                String[] temp = description.split(";");
                                earthquake.setLocation(temp[1].split(" ")[2]);
                                earthquake.setDepth(Integer.parseInt(temp[3].replaceAll("[^0-9\\.]+", "")));
                                earthquake.setMagnitude(Double.parseDouble(temp[4].replaceAll("[^0-9\\.]+", "")));
                                Log.e("Description is", description);
                                Log.e("Location is", earthquake.getLocation());
                                Log.e("Depth is", String.valueOf(earthquake.getDepth()));
                                Log.e("Magnitude is", String.valueOf(earthquake.getMagnitude()));
                            } else if (xpp.getName().equalsIgnoreCase("link")) {
                                String link = xpp.nextText();
                                earthquake.setLink(link);
                                Log.e("Link is", earthquake.getLink());
                            } else if (xpp.getName().equalsIgnoreCase("pubDate")) {
                                String temp = xpp.nextText();
                                SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
                                Date pubDate = sdf.parse(temp);
                                earthquake.setPubDate(pubDate);
                                Log.e("pubDate is", earthquake.getPubDate().toString());
                            } else if (xpp.getName().equalsIgnoreCase("category")) {
                                String category = xpp.nextText();
                                earthquake.setCategory(category);
                                Log.e("Category is", earthquake.getCategory());
                            } else if (xpp.getName().equalsIgnoreCase("geo:lat")) {
                                double geoLat = Double.parseDouble(xpp.nextText());
                                earthquake.setGeoLat(geoLat);
                                Log.e("Geo:lat is", String.valueOf(earthquake.getGeoLat()));
                            } else if (xpp.getName().equalsIgnoreCase("geo:long")) {
                                double geoLong = Double.parseDouble(xpp.nextText());
                                earthquake.setGeoLong(geoLong);
                                Log.e("Geo:long is", String.valueOf(earthquake.getGeoLong()));
                                earthquakeList.add(earthquake);
                                Log.e("Added", earthquake.toString());
                                progress_status += Math.ceil((100/totalItems));
                                publishProgress(progress_status);
                            }
                        }

                    }

                    eventType = xpp.next();
                }
                Log.e("MyTag", "End document");
                publishProgress(progress_status);

            } catch (XmlPullParserException ae1) {
                Log.e("MyTag", "Parsing error" + ae1.toString());
            } catch (IOException ae1) {
                Log.e("MyTag", "IO error during parsing");
            } catch (ParseException e) {
                e.printStackTrace();
            }


            return earthquakeList;
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);

            pbData.setProgress(values[0]);

            if (values[0] >= 100){
                tvProgress.setText("Downloading 100%");
            }
            else {
                tvProgress.setText("Downloading " + values[0] + "%");
            }

        }

        @Override
        protected void onPostExecute(ArrayList<Earthquake> result)
        {
            super.onPostExecute(result);


            tvProgress.setText("Download complete");
            startButton.setEnabled(true);
            startButton.setText("Update Data");
            updateData(earthquakeList);
           // pbData.setVisibility(View.INVISIBLE);
            //rawDataDisplay.setText(result.toString());
        }
    }

}