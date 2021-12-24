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

    public void addTrip(boolean[] daysOfWeek, List<Time> times, boolean active) {
        trips.add(new Trip(this, daysOfWeek, times, active));
    }

    public boolean hasBusStop(BusStop busStop) {
        return busStops.contains(busStop);
    }

    public List<Trip> getAllTrips() {
        return trips;
    }

    public int busStopIndex(BusStop stop) {
        busStops.indexOf(null);
        return busStops.indexOf(stop);
    }

    public String getName() {
        return name;
    }


    public static class Trip {
        //        @Json(ignore = true)
        private final BusRoute fatherRoute;
        private boolean[] daysOfWeek;
        private List<Time> times;
        private boolean active;

        private Trip(BusRoute fatherRoute, boolean[] daysOfWeek, List<Time> times, boolean active) {
            this.fatherRoute = fatherRoute;
            this.daysOfWeek = daysOfWeek;
            this.times = times;
            this.active = active;
        }

        public List<Time> getTimes() {
            return times;
        }

        public int pointsNumber() {
            return fatherRoute.busStops.size();
        }

        public BusStop getBusStopAt(int position) {
            return fatherRoute.busStops.get(position);
        }

        public String getRouteName() {
            return fatherRoute.name;
        }

        public Time getTimeByBusStop(BusStop stop) {
            return times.get(fatherRoute.busStops.indexOf(stop));
        }

        public int getNumber() {
            return fatherRoute.trips.indexOf(this);
        }

        public boolean isActive() {
            return active;
        }
    }
}
