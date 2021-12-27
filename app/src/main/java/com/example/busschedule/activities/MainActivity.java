package com.example.busschedule.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.busschedule.R;
import com.example.busschedule.adapters.TripRecyclerAdapter;
import com.example.busschedule.json.JsonHelper;
import com.example.busschedule.route.BusRoute;
import com.example.busschedule.route.BusStop;
import com.example.busschedule.route.Schedule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String STRING_ROUTE_NAME = "routeName";
    public static final String STRING_TRIP_NUMBER = "tripNumber";
    //private Map<String, BusStop> busStops;
    // private List<BusRoute> routes;
    private Spinner spinnerDepartureStops, spinnerDestinationStops;
    private RecyclerView tripRecyclerView;
    private TripRecyclerAdapter tripRecyclerAdapter;
    private View buttonFind, viewChooseDay;
    private Schedule schedule;
    private JsonHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerDepartureStops = (Spinner) findViewById(R.id.spinner_departure_stops);
        spinnerDestinationStops = (Spinner) findViewById(R.id.spinner_destination_stops);
        buttonFind = findViewById(R.id.button_find);
        viewChooseDay = findViewById(R.id.view_day_chooser);
        viewChooseDay.setOnClickListener(this::onClick);
        buttonFind.setOnClickListener(this/*::onClick*/);
        //viewChooseDay.setOnClickListener(this::onClick);
        tripRecyclerView = findViewById(R.id.trips_list_view);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        tripRecyclerView.setLayoutManager(llm);
        tripRecyclerAdapter = new TripRecyclerAdapter();
        tripRecyclerView.setAdapter(tripRecyclerAdapter);
        tripRecyclerAdapter.setOnItemClickListener(this::onItemClick);
        schedule = Schedule.getInstance(this::onInfoUpdate);
        helper = new JsonHelper();
        //tripRecyclerView.setAdapter(routePointRecyclerAdapter);
        try {
            String json = helper.readFromFile(this);
            Log.d("BufferedWriter", json);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void onInfoUpdate(List<BusStop> stops, List<BusRoute> busRoutes) {
        ArrayAdapter spinnerAdapter = new ArrayAdapter(MainActivity.this,
                android.R.layout.simple_spinner_item, schedule.getBusStops().toArray());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartureStops.setAdapter(spinnerAdapter);
        spinnerDestinationStops.setAdapter(spinnerAdapter);
        String jsonString = schedule.getJSON();
    }

    private List<BusRoute.Trip> selectTrips(@NonNull BusStop departure, @Nullable BusStop
            destination) {
        List<BusRoute.Trip> selectedTrips = new ArrayList<>();
        for (BusRoute route : schedule.getRoutes()) {
            if (route.busStopIndex(departure) < route.busStopIndex(destination)) {
                for (BusRoute.Trip trip : route.getAllTrips()) {
                    if (trip.isActive()) selectedTrips.add(trip);
                }
            }
        }
        return selectedTrips;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_find:
                updateTripsList();
                break;
            case R.id.view_day_chooser:
                //Dialog dialog = new DatePickerDialog(this);
                break;
        }
    }

    public void onItemClick(View v, BusRoute.Trip trip) {
        Log.d("onItemClick", trip.getTimeByBusStop((BusStop) spinnerDepartureStops.getSelectedItem()).toString());
        Intent intent = new Intent(this, RoutPointsListActivity.class);
        intent.putExtra(STRING_ROUTE_NAME, trip.getRouteName());
        intent.putExtra(STRING_TRIP_NUMBER, trip.getNumber());
        startActivity(intent);
    }

    private void updateTripsList() {
        BusStop departureBusStop = (BusStop) spinnerDepartureStops.getSelectedItem();
        BusStop destinationBusStop = (BusStop) spinnerDestinationStops.getSelectedItem();
        //Log.d("getSelectedItem", departureBusStop.getName());
        //Log.d("getSelectedItem", destinationBusStop.getName());
        List selectedTrips = selectTrips(departureBusStop, destinationBusStop);
        sortByTime(selectedTrips, departureBusStop);
        tripRecyclerAdapter.setContent(selectedTrips, departureBusStop);
    }

    private void sortByTime(List<BusRoute.Trip> trips, BusStop stop) {
        Collections.sort(trips, (o1, o2) ->
                o1.getTimeByBusStop(stop).compareTo(o2.getTimeByBusStop(stop)));
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            helper.writeToFile(this, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}