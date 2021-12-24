package com.example.busschedule.json;

import java.util.List;

public class BusRouteJson {
    String number;
    String name;
    List<String> busStops;
    List<TripJson> trips;

    protected BusRouteJson() {

    }
}
