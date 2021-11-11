package com.example.busschedule;


import java.util.ArrayList;
import java.util.List;

public class BusRoute {
    private String number;
    private String name;
    private List<BusStop> busStops;
    private List<Trip> trips;

    public BusRoute(String name, List<BusStop> stops) {
        this.name = name;
        this.busStops = stops;
        trips = new ArrayList<>();
    }

    public Trip getTrip(int i) {
        return trips.get(i);
    }

    public void addTrip(boolean[] daysOfWeek, List<Time> times) {
        trips.add(new Trip(daysOfWeek, times));
    }

    public boolean hasBusStop(BusStop busStop) {
        return busStops.contains(busStop);
    }

    public List<Trip> getAllTrips() {
        return trips;
    }

    public class Trip {
        private boolean[] daysOfWeek;
        private List<Time> times;

        public Trip(boolean[] daysOfWeek, List<Time> times) {
            this.daysOfWeek = daysOfWeek;
            this.times = times;
        }

        public List<Time> getTimes() {
            return times;
        }

        public int pointsNumber() {
            return busStops.size();
        }

        public BusStop getBusStopAt(int position) {
            return busStops.get(position);
        }

        public String getRouteName() {
            return name;
        }

        public Time getTimeByBusStop(BusStop stop) {
            return times.get(busStops.indexOf(stop));
        }
    }
}
