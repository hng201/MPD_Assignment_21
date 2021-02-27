package org.me.gcu.equakestartercode.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.me.gcu.equakestartercode.R;
import org.me.gcu.equakestartercode.models.Earthquake;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private ArrayList<Earthquake> earthquakeList;

    public CustomAdapter(ArrayList<Earthquake> earthquakeList){
        this.earthquakeList = earthquakeList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView tvLocation;
        private TextView tvMagnitude;

        public ViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.cardView);
            tvLocation = view.findViewById(R.id.tvLocation);
            tvMagnitude = view.findViewById(R.id.tvMagnitude);
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
     * Replaces contents of view with Location anf Magnitude of earthquake.
     * Layout manager calls this method
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        holder.tvLocation.setText(earthquakeList.get(position).getLocation());
        holder.tvMagnitude.setText(String.valueOf(earthquakeList.get(position).getMagnitude()));
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
