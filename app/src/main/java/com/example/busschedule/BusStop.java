package com.example.busschedule;

import android.graphics.Point;

public class BusStop {
    private String name;
    private Point location;

    public BusStop(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }
}
