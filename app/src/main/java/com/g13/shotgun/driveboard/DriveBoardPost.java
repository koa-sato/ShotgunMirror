package com.g13.shotgun.driveboard;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.g13.shotgun.Post;

import java.io.Serializable;

import java.util.ArrayList;


@DynamoDBTable(tableName = "Shotgun_Posts")
public class DriveBoardPost extends Post implements Serializable {

    private int numSeats;
    private long id;

    public void add_going_user(String u){
        if(going_users == null)
            going_users = new ArrayList<>();
        going_users.add(u);
        numSeats--;
        if(numSeats == 0)
            show = false;
    }

    public void add_interested_user(String u){
        if(interested_users == null)
            interested_users = new ArrayList<>();
        interested_users.add(u);
    }

    public DriveBoardPost() {}

    public DriveBoardPost(String __city, int __month, int __day, int __year, int __hour, int __minute,
                int __am, String __user, int __numSeats, String t) {
        super(__city, __month, __day, __year, __hour, __minute, __am, __user, t);
        numSeats = __numSeats;
        id = (long)Integer.parseInt(key);}



    public long getId(){return id;}

    @DynamoDBHashKey(attributeName = "key")
    public String get_key(){ return key;}

    public void set_key(String d){ key = d;}



    @DynamoDBIndexHashKey(attributeName = "city")
    public String get_city(){
        return city;
    }
    public void set_city(String c){ city = c;}


    @DynamoDBIndexRangeKey(attributeName = "day")
    public void set_day(int t){ day = t;}
    public int get_day(){ return day;}

    @DynamoDBAttribute(attributeName = "Month")
    public void set_month(int t){ month = t;}
    public int get_month(){ return month;}

    @DynamoDBAttribute(attributeName = "Year")
    public void set_year(int t){ year = t;}
    public int get_year(){ return year;}

    @DynamoDBIndexHashKey(attributeName = "User")
    public String get_user(){ return user;}
    public void set_user(String u){ user = u;}

    @DynamoDBAttribute(attributeName = "Hour")
    public void set_hour(int t){ hour = t;}
    public int get_hour(){ return hour;}

    @DynamoDBAttribute(attributeName = "Minute")
    public void set_minute(int t){ minute = t;}
    public int get_minute(){ return minute;}

    @DynamoDBAttribute(attributeName = "AM")
    public void set_am(int t){ am = t;}
    public int get_am(){ return am;}

    @DynamoDBAttribute(attributeName = "NumSeats")
    public void set_num_seats(int n) { numSeats = n; }
    public int get_num_seats() { return numSeats; }

    public String date_to_string(){
        return "" + (month + 1) + '/' + day+ '/' + year;
    }



    @DynamoDBAttribute(attributeName = "Interested_Users")
    public void set_interested_users(ArrayList<String> t){ interested_users = t;}
    public ArrayList<String> get_interested_users(){ return interested_users;}

    @DynamoDBAttribute(attributeName = "Going_Users")
    public void set_going_users(ArrayList<String> t){ going_users = t;}
    public ArrayList<String> get_going_users(){ return going_users;}

    @DynamoDBAttribute(attributeName = "Time_of_day")
    public void set_time_of_day(String t){ time_of_day = t;}
    public String get_time_of_day(){ return time_of_day;}

    @DynamoDBAttribute(attributeName = "Show")
    public void set_show(Boolean n) { show = n; }
    public Boolean get_show() { return show; }


    public void setId(long _id) {
        id = _id;
    }

    @Override
    public String toString() {
        return get_city() + "\n" + (month + 1) + '/' + day+ '/' + year;
    }

}