package com.example.busschedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RoutePointRecyclerAdapter extends RecyclerView.Adapter<RoutePointRecyclerAdapter.ViewHolder> {

    private BusRoute.Trip trip;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.route_point_row, parent,
                false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String name = trip.getBusStopAt(position).getName();
        final String time = trip.getTimes().get(position).toString();
        holder.name.setText(name);
        holder.time.setText(time);
    }

    @Override
    public int getItemCount() {
        return trip == null ? 0 : trip.pointsNumber();
    }

    public void setContent(BusRoute.Trip trip) {
        this.trip = trip;
        notifyDataSetChanged();
    }

    static final class ViewHolder extends RecyclerView.ViewHolder {
        final TextView time, name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.row_stop_time);
            name = itemView.findViewById(R.id.row_bus_stop_name);
        }
    }
}