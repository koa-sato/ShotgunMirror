package com.g13.shotgun;


import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;

import java.io.Serializable;

public abstract class Post implements Serializable{
    protected String city;
    protected int month;
    protected int day;
    protected int year;
    protected int hour;
    protected int minute;
    protected int am;
    protected String user;
    protected String key;

    public Post(){};
    public Post(String __city, int __month, int __day, int __year, int __hour, int __minute,
                          int __am, String __user) {
        city = __city;
        user = __user;
        month = __month;
        day = __day;
        year = __year;
        hour = __hour;
        minute = __minute;
        am = __am;
        key = user + city + Integer.toString(month) + Integer.toString(day) + Integer.toString(year);
    }

    protected String get_key(){ return key;}
    protected void set_key(String d){ key = d;}

    protected String get_city(){
        return city;
    }
    protected void set_city(String c){ city = c;}

    protected void set_day(int t){ day = t;}
    protected int get_day(){ return day;}

    protected void set_month(int t){ month = t;}
    protected int get_month(){ return month;}

    protected void set_year(int t){ year = t;}
    protected int get_year(){ return year;}

    protected String get_user(){ return user;}
    protected void set_user(String u){ user = u;}

    protected void set_hour(int t){ hour = t;}
    protected int get_hour(){ return hour;}

    @DynamoDBAttribute(attributeName = "Minute")
    protected void set_minute(int t){ minute = t;}
    protected int get_minute(){ return minute;}

    @DynamoDBAttribute(attributeName = "AM")
    protected void set_am(int t){ am = t;}
    protected int get_am(){ return am;}

    @Override
    public String toString() {
        return get_city() + "\n" + (month + 1) + '/' + day+ '/' + year;
    }
}
