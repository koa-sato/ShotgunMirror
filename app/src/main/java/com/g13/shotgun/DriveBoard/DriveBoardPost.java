package com.g13.shotgun.DriveBoard;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.g13.shotgun.Post;

import java.io.Serializable;

@DynamoDBTable(tableName = "Shotgun_Posts")
public class DriveBoardPost extends Post implements Serializable {

    private int numSeats;
    private long id;


    public DriveBoardPost() {};

    public DriveBoardPost(String __city, int __month, int __day, int __year, int __hour, int __minute,
                int __am, String __user, int __numSeats) {
        super(__city, __month, __day, __year, __hour, __minute, __am, __user);
        numSeats = __numSeats;
        id = (long)Integer.parseInt(key);
    }
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

    @DynamoDBAttribute(attributeName = "User")
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

    //public int getId(){return Integer.parseInt(key);}

    @Override
    public String toString() {
        return get_city() + "\n" + (month + 1) + '/' + day+ '/' + year;
    }

}