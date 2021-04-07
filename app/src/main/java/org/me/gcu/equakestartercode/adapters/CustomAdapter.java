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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Shirley Ng S1626790
 */
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
        private TextView tvDepth;
        private TextView tvGeoLat;
        private TextView tvGeoLong;
        private TextView tvLink;
        private Button btnExpand;

        public ViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.cardView);
            tvLocation = view.findViewById(R.id.tvLocation);
            tvMagnitude = view.findViewById(R.id.tvMagnitude);
            tvDepth = view.findViewById(R.id.tvDepth);
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
        holder.tvMagnitude.setText("M" + String.valueOf(earthquakeList.get(position).getMagnitude()));
        if (earthquakeList.get(position).getMagnitude() <= 1){
            holder.tvMagnitude.setBackgroundColor(context.getColor(R.color.level1));
        }
        else if (earthquakeList.get(position).getMagnitude() <= 2){
            holder.tvMagnitude.setBackgroundColor(context.getColor(R.color.level2));
        }
        else if (earthquakeList.get(position).getMagnitude() <= 3){
            holder.tvMagnitude.setBackgroundColor(context.getColor(R.color.level3));
        }
        else if (earthquakeList.get(position).getMagnitude() <= 4){
            holder.tvMagnitude.setBackgroundColor(context.getColor(R.color.level4));
        }
        else if (earthquakeList.get(position).getMagnitude() <= 5){
            holder.tvMagnitude.setBackgroundColor(context.getColor(R.color.level5));
        }
        else if (earthquakeList.get(position).getMagnitude() <= 6){
            holder.tvMagnitude.setBackgroundColor(context.getColor(R.color.level6));
        }
        else if (earthquakeList.get(position).getMagnitude() <= 7){
            holder.tvMagnitude.setBackgroundColor(context.getColor(R.color.level7));
        }
        else if (earthquakeList.get(position).getMagnitude() <= 8){
            holder.tvMagnitude.setBackgroundColor(context.getColor(R.color.level8));
        }
        else if (earthquakeList.get(position).getMagnitude() <= 9){
            holder.tvMagnitude.setBackgroundColor(context.getColor(R.color.level9));
        }
        else{
            holder.tvMagnitude.setBackgroundColor(context.getColor(R.color.level10));
        }
        
        String formatPubDate = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            Date pubDate = sdf.parse(earthquakeList.get(position).getPubDate());
            formatPubDate = sdf2.format(pubDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tvDepth.setText("Depth \n" + String.valueOf(earthquakeList.get(position).getDepth()));
        holder.tvDepth.setVisibility(View.GONE);

        holder.tvDate.setText("Publication Date \n" + formatPubDate);
        holder.tvDate.setVisibility(View.GONE);

        holder.tvGeoLat.setText("Geo Latitude \n" + String.valueOf(earthquakeList.get(position).getGeoLat()));
        holder.tvGeoLat.setVisibility(View.GONE);

        holder.tvGeoLong.setText("Geo Longitude \n" + String.valueOf(earthquakeList.get(position).getGeoLong()));
        holder.tvGeoLong.setVisibility(View.GONE);

        holder.tvLink.setText("Link \n " + earthquakeList.get(position).getLink());
        holder.tvLink.setVisibility(View.GONE);

        holder.btnExpand.setText(R.string.detail);
        holder.btnExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.btnExpand.getText().toString().equals("Details")){
                    holder.tvDepth.setVisibility(View.VISIBLE);
                    holder.tvDate.setVisibility(View.VISIBLE);
                    holder.tvGeoLat.setVisibility(View.VISIBLE);
                    holder.tvGeoLong.setVisibility(View.VISIBLE);
                    holder.tvLink.setVisibility(View.VISIBLE);
                    holder.btnExpand.setText(R.string.close);
                }
                else{
                    holder.tvDepth.setVisibility(View.GONE);
                    holder.tvDate.setVisibility(View.GONE);
                    holder.tvGeoLat.setVisibility(View.GONE);
                    holder.tvGeoLong.setVisibility(View.GONE);
                    holder.tvLink.setVisibility(View.GONE);
                    holder.btnExpand.setText(R.string.detail);
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
