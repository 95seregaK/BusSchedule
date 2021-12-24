package com.example.busschedule;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.ArrayList;
import java.util.List;


public class Schedule {
    private static Schedule instance;
    private final JsonAdapter<List> jsonAdapter1;
    private final JsonAdapter<BusRoute> jsonAdapter2;
    private List<BusStop> busStops;
    private List<BusRoute> routes;
    private OnInfoUpdateListener onInfoUpdateListener;

    private Schedule(OnInfoUpdateListener listener) {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        onInfoUpdateListener = listener;
        Moshi moshi = new Moshi.Builder().build();
        jsonAdapter1 = moshi.adapter(List.class);
        jsonAdapter2 = moshi.adapter(BusRoute.class);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                busStops = readBusStops(dataSnapshot.child("busStops"));
                routes = readRouts(dataSnapshot.child("routes"));
                onInfoUpdateListener.onInfoUpdate(busStops, routes);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("databaseError", "loadPost:onCancelled", databaseError.toException());
            }
        };
        database.addValueEventListener(postListener);
    }

    private void saveJSON() {
        /*for (BusStop busStop : busStops) {
            String json = jsonAdapter1.toJson(busStop);
            Log.d("jsonAdapter", json);
        }*/

        for (BusRoute route : routes) {
            //String json = jsonAdapter2.toJson(route);
            // Log.d("jsonAdapter", json);
        }
    }

    public static Schedule getInstance(@Nullable OnInfoUpdateListener listener) { // #3
        if (instance == null) {        //если объект еще не создан
            instance = new Schedule(listener);    //создать новый объект
        }
        return instance;        // вернуть ранее созданный объект
    }

    private List<BusRoute> readRouts(DataSnapshot routesSnapshot) {
        final List<BusRoute> routes = new ArrayList<>();
        for (DataSnapshot routeSnapshot : routesSnapshot.getChildren()) {
            //Log.d("dataSnapshot.getChild", routeSnapshot.getKey());
            String name = routeSnapshot.child("name").getValue(String.class);
            List<BusStop> stops = new ArrayList<>();
            for (DataSnapshot stopSnapshot : routeSnapshot.child("stops").getChildren()) {

                stops.add(findBusStopByName(busStops, stopSnapshot.getValue(String.class)));
            }
            BusRoute route = new BusRoute(name, stops);
            for (DataSnapshot tripSnapshot : routeSnapshot.child("trips").getChildren()) {
                boolean active = tripSnapshot.child("active").getValue(Boolean.class);
                boolean[] daysOfWeek = new boolean[7];
                int i = 0;
                for (DataSnapshot dayOfWeekSnapshot : tripSnapshot.child("daysOfWeek").getChildren()) {
                    daysOfWeek[i] = dayOfWeekSnapshot.getValue(Boolean.class);
                    i++;
                }
                List<Time> times = new ArrayList<>();
                for (DataSnapshot timeSnapshot : tripSnapshot.child("time").getChildren()) {
                    times.add(new Time(timeSnapshot.getValue(Long.class).intValue()));
                }
                route.addTrip(daysOfWeek, times, active);
            }
            routes.add(route);
        }
        return routes;
    }

    public static BusStop findBusStopByName(List<BusStop> busStops, String value) {
        for (BusStop busStop : busStops) {
            if (busStop.getName().equals(value)) return busStop;
        }
        return null;
    }

    private List<BusStop> readBusStops(DataSnapshot busStopsSnapshot) {
        final List<BusStop> busStops = new ArrayList();
        for (DataSnapshot busStopSnapshot : busStopsSnapshot.getChildren()) {
            String name = (String) busStopSnapshot.child("name").getValue();
            //long location = (Long) snapshot.child("location").getValue();
            busStops.add(new BusStop(name));
        }
        return busStops;
    }

    public List<BusStop> getBusStops() {
        return busStops;
    }

    public List<BusRoute> getRoutes() {
        return routes;
    }

    public BusRoute getRouteByName(String name) {
        for (BusRoute route : routes) {
            if (route.getName().equals(name)) return route;
        }
        return null;
    }

    public String getJSON() {
        /*for (BusStop busStop : busStops) {
            String json = jsonAdapter1.toJson(busStop);
            Log.d("jsonAdapter", json);
        }*/
        String json = jsonAdapter1.toJson(busStops);
        return json;
    }


    public interface OnInfoUpdateListener {
        public void onInfoUpdate(List<BusStop> stops, List<BusRoute> routes);
    }
}
