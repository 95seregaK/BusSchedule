package com.example.busschedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RoutePointRecyclerAdapter extends RecyclerView.Adapter<RoutePointRecyclerAdapter.ViewHolder> {

    private List<RoutePoint> routePoints;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.route_point_row, parent,
                false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final RoutePoint routePoint = routePoints.get(position);
        holder.time.setText(routePoint.getTime());
        holder.name.setText(routePoint.getName());
    }

    @Override
    public int getItemCount() {
        return routePoints.size();
    }

    public void setContent(List<RoutePoint> routePointList) {
        routePoints = routePointList;
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