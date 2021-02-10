package org.me.gcu.equakestartercode.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.me.gcu.equakestartercode.R;
import org.me.gcu.equakestartercode.models.Earthquake;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class MainActivity extends AppCompatActivity implements OnClickListener
{
    private TextView rawDataDisplay;
    private Button startButton;
    private String result;
    private String url1="";
    private String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MyTag","in onCreate");
        // Set up the raw links to the graphical components
        rawDataDisplay = (TextView)findViewById(R.id.rawDataDisplay);
        startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
        Log.e("MyTag","after startButton");
        // More Code goes here
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
        new Thread(new Task(urlSource)).start();
    } //

    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
    private class Task implements Runnable
    {
        private String url;

        public Task(String aurl)
        {
            url = aurl;
        }
        @Override
        public void run()
        {

            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";


            Log.e("MyTag","in run");

            try
            {
                Log.e("MyTag","in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                Log.e("MyTag","after ready");
                //
                // Throw away the first 2 header lines before parsing
                //
                //
                //
                int i = 0;
                while ((inputLine = in.readLine()) != null)
                {
                    if(i > 1) {
                        result = result + inputLine;
                        Log.e("MyTag", inputLine);
                    }
                    else{
                        i++;
                    }

                }
                in.close();
            }
            catch (IOException ae)
            {
                Log.e("MyTag", "ioexception in run");
            }

            ArrayList<Earthquake> earthquakeList = null;
            Earthquake earthquake = null;
            result = result.substring(4);
            result = result.substring(0, result.length() -6);
            //
            // Now that you have the xml data you can parse it
            //
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                //factory.setNamespaceAware(true);

                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( new StringReader(result));
                System.out.println(result);
                int eventType = xpp.getEventType();

                while(eventType != XmlPullParser.END_DOCUMENT){
                    if(eventType == XmlPullParser.START_DOCUMENT) {
                        Log.e("MyTag","Start document");
                        earthquakeList = new ArrayList<>();
                        if (eventType == XmlPullParser.START_TAG) {
                            if(xpp.getName().equalsIgnoreCase("item")){
                                System.out.println("Item");
                                earthquake = new Earthquake();
                            }
                            else if (xpp.getName().equalsIgnoreCase("title")) {
                                String title = xpp.nextText();
                                earthquake.setTitle(title);
                            } else if (xpp.getName().equalsIgnoreCase("description")) {
                                String description = xpp.nextText();
                                earthquake.setDescription(description);
                            } else if (xpp.getName().equalsIgnoreCase("link")) {
                                String link = xpp.nextText();
                                earthquake.setLink(link);
                            } else if (xpp.getName().equalsIgnoreCase("pubDate")) {
                                String pubDate = xpp.nextText();
                                earthquake.setPubDate(pubDate);
                            } else if (xpp.getName().equalsIgnoreCase("category")) {
                                String category = xpp.nextText();
                                earthquake.setCategory(category);
                            } else if (xpp.getName().equalsIgnoreCase("geo:lat")) {
                                Log.e("My Tag", xpp.nextText());
                                double geoLat = Double.parseDouble(xpp.nextText());
                                earthquake.setGeoLat(geoLat);
                            } else if (xpp.getName().equalsIgnoreCase("geo:long")) {
                                double geoLong = Double.parseDouble(xpp.nextText());
                                earthquake.setGeoLong(geoLong);
                            }


                        }
                    }
                    else if(eventType == XmlPullParser.END_TAG){
                        if(xpp.getName().equalsIgnoreCase("item")) {
                            System.out.println("Earthquake added");
                            earthquakeList.add(earthquake);
                        }
                    }

                    eventType = xpp.next();
                }
                Log.e("MyTag","End document");
            }
            catch (XmlPullParserException ae1)
            {
                Log.e("MyTag","Parsing error" + ae1.toString());
            }
            catch (IOException ae1)
            {
                Log.e("MyTag","IO error during parsing");
            }

            // Now update the TextView to display raw XML data
            // Probably not the best way to update TextView
            // but we are just getting started !

            MainActivity.this.runOnUiThread(new Runnable()
            {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");
                    rawDataDisplay.setText(result);
                }
            });
        }

    }

}