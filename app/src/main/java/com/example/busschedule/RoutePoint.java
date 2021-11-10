package com.example.busschedule;

import android.text.format.Time;

public class RoutePoint {
    public BusStop busStop;
    private Time time;

    public RoutePoint(BusStop b, Time t) {
        busStop = b;
        time = t;

    }

    public String getName() {
        return busStop.getName();
    }

    public String getTime() {
        return time.toString();
    }
}
