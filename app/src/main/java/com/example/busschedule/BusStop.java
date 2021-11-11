package com.example.busschedule;

import android.graphics.Point;

public class BusStop {
    private String name;
    private Point location;

    public BusStop(String n) {
        name = n;
        location = new Point(0, 0);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
