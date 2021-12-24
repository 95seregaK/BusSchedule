package com.example.busschedule.json;

import com.example.busschedule.BusRoute;
import com.example.busschedule.BusStop;
import com.example.busschedule.Schedule;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.util.ArrayList;
import java.util.List;

public class JsonBusRouteAdapter {
    private final List<BusStop> busStops;

    JsonBusRouteAdapter(List<BusStop> busStops) {
        this.busStops = busStops;
    }

    @FromJson
    BusRoute busRouteFromJson(BusRouteJson busRouteJson) {
        List<BusStop> routeBusStops = new ArrayList<>();
        String name = busRouteJson.name;
        for (String busStop : busRouteJson.busStops) {
            routeBusStops.add(Schedule.findBusStopByName(busStops, busStop));
        }
        BusRoute busRoute = new BusRoute(name, routeBusStops);
        for (TripJson tripJson : busRouteJson.trips) {
            busRoute.addTrip(tripJson.daysOfWeek, tripJson.times, tripJson.active);
        }
        return busRoute;
    }

    @ToJson
    BusRouteJson busRouteToJson(BusRoute busRoute) {
        BusRouteJson busRouteJson = new BusRouteJson();

        return busRouteJson;
    }

   /* @FromJson
    BusRoute.Trip tripFromJson(TripJson tripJson) {
        List<BusStop> routeBusStops = new ArrayList<>();
        BusRoute.Trip trip = new BusRoute.Trip();
        return trip;
    }*/

    TripJson tripToJson(BusRoute.Trip trip) {
        TripJson tripJson = new TripJson();

        return tripJson;
    }
}
