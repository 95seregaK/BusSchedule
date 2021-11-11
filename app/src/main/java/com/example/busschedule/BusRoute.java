package com.example.busschedule;

import android.text.format.Time;

import java.util.List;
import java.util.Map;

public class BusRoute {
    private String number;
    private String name;
    private List<BusStop> busStops;
    private Map<Map<Integer, Time>, boolean[]> schedule;
}
