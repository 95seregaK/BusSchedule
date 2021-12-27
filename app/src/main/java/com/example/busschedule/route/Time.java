package com.example.busschedule.route;

public class Time {
    int hour;
    int minute;

    public Time(int t) {
        hour = t / 100;
        minute = t % 100;
    }

    @Override
    public String toString() {
        return hour + ":" + (minute < 9 ? "0" : "") + minute;
    }

    public int compareTo(Time t) {
        return (hour - t.hour) * 60 + (minute - t.minute);
    }
}
