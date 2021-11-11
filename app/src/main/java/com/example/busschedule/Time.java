package com.example.busschedule;

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
}
