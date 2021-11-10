package com.example.busschedule;

import android.os.Bundle;
import android.text.format.Time;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<RoutePoint> routePointList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RoutePointRecyclerView recyclerView = findViewById(R.id.route_points_list_view);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        RoutePointRecyclerAdapter routePointRecyclerAdapter = new RoutePointRecyclerAdapter();
        routePointList = readRoutPoints();
        routePointRecyclerAdapter.setContent(routePointList);
        recyclerView.setAdapter(routePointRecyclerAdapter);
    }

    private List<RoutePoint> readRoutPoints() {
        final List<RoutePoint> routePoints = new ArrayList<>();
        routePoints.add(new RoutePoint(new BusStop("Yaroshovka"), new Time()));
        routePoints.add(new RoutePoint(new BusStop("Minsk"), new Time()));
        return routePoints;
    }
}