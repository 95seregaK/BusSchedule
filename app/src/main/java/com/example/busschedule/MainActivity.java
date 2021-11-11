package com.example.busschedule;

import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        Log.d("getChild", database.child("routePoints").getKey());
        //Log.d("getChild", database.child("routePoints").getKey());
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                //RoutePoint routePoint = dataSnapshot.getValue(RoutePoint.class);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // TODO: handle the
                    Log.d("dataSnapshot.getChild",
                            snapshot.getKey());
                }
                // ..
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("databaseError", "loadPost:onCancelled", databaseError.toException());
            }
        };
        database.addValueEventListener(postListener);
    }

    private List<RoutePoint> readRoutPoints() {
        final List<RoutePoint> routePoints = new ArrayList<>();
        routePoints.add(new RoutePoint(new BusStop("Yaroshovka"), new Time()));
        routePoints.add(new RoutePoint(new BusStop("Minsk"), new Time()));
        return routePoints;
    }
}