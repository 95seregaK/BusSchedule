package com.example.busschedule;

public class BusStop {
    private String name;
    private GeoPoint location;

    public BusStop(String n) {
        name = n;
        location = new GeoPoint(0, 0);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
