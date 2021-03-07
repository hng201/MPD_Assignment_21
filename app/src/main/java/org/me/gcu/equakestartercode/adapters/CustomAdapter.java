package org.me.gcu.equakestartercode.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.me.gcu.equakestartercode.R;
import org.me.gcu.equakestartercode.models.Earthquake;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Earthquake> earthquakeList;

    public CustomAdapter(Context context, ArrayList<Earthquake> earthquakeList){
        this.context = context;
        this.earthquakeList = earthquakeList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private TextView tvLocation;
        private TextView tvMagnitude;
        private TextView tvDate;
        private TextView tvGeoLat;
        private TextView tvGeoLong;
        private TextView tvLink;
        private Button btnExpand;

        public ViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.cardView);
            tvLocation = view.findViewById(R.id.tvLocation);
            tvMagnitude = view.findViewById(R.id.tvMagnitude);
            tvDate =  view.findViewById(R.id.tvDate);
            tvGeoLat = view.findViewById(R.id.tvGeoLat);
            tvGeoLong = view.findViewById(R.id.tvGeoLong);
            tvLink = view.findViewById(R.id.tvLink);
            btnExpand = view.findViewById(R.id.btnExpand);
        }
    }

    /**
     * Creates new views. This is invoked by the layout manager.
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Replaces contents of view with location, Magnitude, Publication date, GeoLat, GeoLong and Link of the earthquake.
     * Layout manager calls this method.
     * Publication date, GeoLat, GeoLong and Link are hidden by default and will be visible when user clicks on Details button.
     * @param holder
     * @param position
     */

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        holder.tvLocation.setText(earthquakeList.get(position).getLocation());
        holder.tvMagnitude.setText("Magnitude \n" + String.valueOf(earthquakeList.get(position).getMagnitude()));
        if (earthquakeList.get(position).getMagnitude() <= 5){
            holder.tvMagnitude.setBackgroundColor(context.getColor(R.color.yellow));
        }
        else if (earthquakeList.get(position).getMagnitude() <=8){
            holder.tvMagnitude.setBackgroundColor(context.getColor(R.color.orange));
        }
        else{
            holder.tvMagnitude.setBackgroundColor(context.getColor(R.color.red));
        }

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
        String pubDate = sdf.format(earthquakeList.get(position).getPubDate());
        holder.tvDate.setText("Publication Date \n" + pubDate);
        holder.tvDate.setVisibility(View.GONE);

        holder.tvGeoLat.setText("Geo Latitude \n" + String.valueOf(earthquakeList.get(position).getGeoLat()));
        holder.tvGeoLat.setVisibility(View.GONE);

        holder.tvGeoLong.setText("Geo Longitude \n" + String.valueOf(earthquakeList.get(position).getGeoLong()));
        holder.tvGeoLong.setVisibility(View.GONE);

        holder.tvLink.setText("Link \n " + earthquakeList.get(position).getLink());
        holder.tvLink.setVisibility(View.GONE);

        holder.btnExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.btnExpand.getText().toString().equals("Details")){
                    holder.tvDate.setVisibility(View.VISIBLE);
                    holder.tvGeoLat.setVisibility(View.VISIBLE);
                    holder.tvGeoLong.setVisibility(View.VISIBLE);
                    holder.tvLink.setVisibility(View.VISIBLE);
                    holder.btnExpand.setText("Close");
                }
                else{
                    holder.tvDate.setVisibility(View.GONE);
                    holder.tvGeoLat.setVisibility(View.GONE);
                    holder.tvGeoLong.setVisibility(View.GONE);
                    holder.tvLink.setVisibility(View.GONE);
                    holder.btnExpand.setText("Details");
                }
            }
        });


    }

    /**
     * Returns the size of earthquakeList
     * @return size the size of the earthquakeList
     */
    @Override
    public int getItemCount() {
        if (earthquakeList != null) {
            return earthquakeList.size();
        } else{
            return 0;
        }
    }
}
