package com.g13.shotgun;


import java.io.Serializable;
import java.util.ArrayList;

public abstract class Post implements Serializable {
    protected String city;
    protected int month;
    protected int day;
    protected int year;
    protected int hour;
    protected int minute;
    protected int am;
    protected String time_of_day;
    protected String user;
    protected String key;
    protected ArrayList<String> interested_users;


    public Post() {

    }




    public Post(String __city, int __month, int __day, int __year, int __hour, int __minute,
                int __am, String __user, String t) {
        interested_users = new ArrayList<>();
        time_of_day = t;
        city = __city;
        user = __user;
        month = __month;
        day = __day;
        year = __year;
        hour = __hour;
        minute = __minute;
        am = __am;
        key = user + city + Integer.toString(month) + Integer.toString(day) + Integer.toString(year);
        int newKey = key.hashCode()%997;
        key = "" + newKey;
        //interested_users = new ArrayList<>();
    }

}
