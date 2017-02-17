package com.g13.shotgun;

import java.sql.Time;
import java.util.Date;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;

/**
 * Created by Dominic on 2/14/2017.
 */


@DynamoDBTable(tableName = "My_books")
public class Post {
    private String city;
    private Date the_date;
    private Time the_time;
    private String user;

    @DynamoDBIndexRangeKey(attributeName = "Date")
    public void set_date(Date d){ the_date = d;}
    public Date get_date(){ return the_date;}

    @DynamoDBIndexRangeKey(attributeName = "City")
    public void set_city(String c){ city = c;}
    public String get_city(){
        return city;
    }

    @DynamoDBIndexRangeKey(attributeName = "Time")
    public void set_time(Time t){ the_time = t;}
    public Time get_time(){ return the_time;}

    @DynamoDBIndexRangeKey(attributeName = "User")
    public void set_user(String u){ user = u;}
    public String get_user(){ return user;}
}
