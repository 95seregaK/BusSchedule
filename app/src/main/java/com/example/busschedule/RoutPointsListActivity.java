package com.example.busschedule;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

public class RoutPointsListActivity extends AppCompatActivity {
    RoutePointRecyclerView recyclerView;
    RoutePointRecyclerAdapter adapter;
    TextView textRouteName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rout_points_list);
        recyclerView = findViewById(R.id.route_points_list_view);
        adapter = new RoutePointRecyclerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        Schedule schedule = Schedule.getInstance(null);
        String routeName = getIntent().getStringExtra(MainActivity.STRING_ROUTE_NAME);
        int tripIndex = getIntent().getIntExtra(MainActivity.STRING_TRIP_NUMBER, 0);
        BusRoute route = schedule.getRouteByName(routeName);
        adapter.setContent(route.getTrip(tripIndex));
        textRouteName = findViewById(R.id.text_route_name);
        textRouteName.setText(routeName);

    }
}