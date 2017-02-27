package com.g13.shotgun.RideBoard;

import java.util.Date;

/**
 * Created by josephkompella on 2/7/17.
 */

public class Ride {
    String location;
    String destination;
    Date date;
    String user;

    public Ride(String l, String d, Date time, String u) {
        location = l;
        destination = d;
        date = time;
        user = u;
    }

    public Date getDate() {
        return date;
    }

    public String getDestination() {
        return destination;
    }

    public String getLocation() {
        return location;
    }

    public String getUser() {
        return user;
    }

    @Override
    public String toString() {
        return getUser() + ": " + getLocation() + " to " + getDestination() + " on " + getDate();
    }
}
