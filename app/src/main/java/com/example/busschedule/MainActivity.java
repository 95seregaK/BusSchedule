package com.example.busschedule;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Map<String, BusStop> busStops;
    private List<BusRoute> routes;
    private Spinner spinnerDepartureStops, spinnerDestinationStops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerDepartureStops = (Spinner) findViewById(R.id.spinner_departure_stops);
        spinnerDestinationStops = (Spinner) findViewById(R.id.spinner_destination_stops);

        //final RoutePointRecyclerView routePointRecyclerView = findViewById(R.id.route_points_list_view);
        final TripRecyclerView tripRecyclerView = findViewById(R.id.trips_list_view);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        tripRecyclerView.setLayoutManager(llm);
        //RoutePointRecyclerAdapter routePointRecyclerAdapter = new RoutePointRecyclerAdapter();
        TripRecyclerAdapter tripRecyclerAdapter = new TripRecyclerAdapter();
        tripRecyclerView.setAdapter(tripRecyclerAdapter);
        //tripRecyclerView.setAdapter(routePointRecyclerAdapter);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                busStops = readBusStops(dataSnapshot.child("busStops"));
                routes = readRouts(dataSnapshot.child("routes"));
                //routePointRecyclerAdapter.setContent(routes.get(0).getTrip(0));
                Object[] busStopArray = busStops.values().toArray();
                ArrayAdapter spinnerAdapter = new ArrayAdapter<>(MainActivity.this,
                        android.R.layout.simple_spinner_item, busStopArray);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDepartureStops.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        BusStop selectedBusStop = (BusStop) busStopArray[position];
                        tripRecyclerAdapter.setContent(selectTrips(selectedBusStop, null), selectedBusStop);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                spinnerDepartureStops.setAdapter(spinnerAdapter);
                spinnerDestinationStops.setAdapter(spinnerAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("databaseError", "loadPost:onCancelled", databaseError.toException());
            }
        };
        database.addValueEventListener(postListener);
    }

    private List<BusRoute.Trip> selectTrips(BusStop departure, @Nullable BusStop destination) {
        List<BusRoute.Trip> selectedTrips = new ArrayList<>();
        for (BusRoute route : routes) {
            if (route.hasBusStop(departure)) {
                selectedTrips.addAll(route.getAllTrips());
            }
        }
        return selectedTrips;
    }

    private List<BusRoute> readRouts(DataSnapshot routesSnapshot) {
        final List<BusRoute> routes = new ArrayList<>();
        for (DataSnapshot routeSnapshot : routesSnapshot.getChildren()) {
            //Log.d("dataSnapshot.getChild", routeSnapshot.getKey());
            String name = routeSnapshot.child("name").getValue(String.class);
            List<BusStop> stops = new ArrayList<>();
            for (DataSnapshot stopSnapshot : routeSnapshot.child("stops").getChildren()) {
                stops.add(busStops.get(stopSnapshot.getValue(String.class)));
            }
            BusRoute route = new BusRoute(name, stops);
            for (DataSnapshot tripSnapshot : routeSnapshot.child("trips").getChildren()) {
                int i = 0;
                boolean[] daysOfWeek = new boolean[7];
                for (DataSnapshot dayOfWeekSnapshot : tripSnapshot.child("daysOfWeek").getChildren()) {
                    daysOfWeek[i] = dayOfWeekSnapshot.getValue(Boolean.class);
                    i++;
                }
                List<Time> times = new ArrayList<>();
                for (DataSnapshot timeSnapshot : tripSnapshot.child("time").getChildren()) {
                    times.add(new Time(timeSnapshot.getValue(Long.class).intValue()));
                }
                route.addTrip(daysOfWeek, times);
            }
            routes.add(route);
        }
        return routes;
    }

    private Map<String, BusStop> readBusStops(DataSnapshot busStopsSnapshot) {
        final Map<String, BusStop> busStops = new HashMap<>();
        for (DataSnapshot busStopSnapshot : busStopsSnapshot.getChildren()) {
            String name = (String) busStopSnapshot.child("name").getValue();
            //long location = (Long) snapshot.child("location").getValue();
            busStops.put(name, new BusStop(name));
        }
        return busStops;
    }
}