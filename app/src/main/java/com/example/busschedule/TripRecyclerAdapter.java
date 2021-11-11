package com.example.busschedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TripRecyclerAdapter extends RecyclerView.Adapter<TripRecyclerAdapter.ViewHolder> {

    private List<BusRoute.Trip> trips;
    private BusStop busStop;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_row, parent,
                false);
        return new TripRecyclerAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String routeName = trips.get(position).getRouteName();
        final String time = trips.get(position).getTimeByBusStop(busStop).toString();
        holder.routeName.setText(routeName);
        holder.time.setText(time);
    }

    public void setContent(List<BusRoute.Trip> trips, BusStop stop) {
        this.trips = trips;
        this.busStop = stop;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return trips == null ? 0 : trips.size();
    }

    static final class ViewHolder extends RecyclerView.ViewHolder {
        final TextView time, routeName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.row_stop_time);
            routeName = itemView.findViewById(R.id.row_route_name);
        }

    }
}
