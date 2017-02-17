package edu.ucsb.cs.cs185.dkirby.awshelp3;

/**
 * Created by Dominic on 2/16/2017.
 */

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.sql.Time;
import java.util.Date;



import java.sql.Time;
import java.util.Date;
import java.util.Random;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;

/**
 * Created by Dominic on 2/14/2017.
 */


@DynamoDBTable(tableName = "Shotgun_Posts")
public class Post {
    private String city;
    //private Date the_date;
    private int the_date;
   // private Time the_time;
    private String user;
    private String key;

    public Post()
    {
        Random r = new Random(10);
        key = Integer.toString(r.nextInt());

    }
    
    public Post(String __city, Date __the_date, Time __the_time, String __user) {
        city = __city;
        the_date = __the_date;
        the_time = __the_time;
        user = __user;
    }

    @DynamoDBHashKey(attributeName = "Key")
    public String get_key(){ return key;}
    public void set_key(String d){ key = d;}


    @DynamoDBIndexHashKey(attributeName = "Date")
    public int get_date(){ return the_date;}
    public void set_date(int d){ the_date = d;}


    @DynamoDBIndexRangeKey(attributeName = "City")
    public String get_city(){
        return city;
    }
    public void set_city(String c){ city = c;}


    /*@DynamoDBIndexRangeKey(attributeName = "Time")
    public void set_time(Time t){ the_time = t;}
    public Time get_time(){ return the_time;}*/

    @DynamoDBAttribute(attributeName = "User")
    public String get_user(){ return user;}
    public void set_user(String u){ user = u;}
    
    @Override
    public String toString() {
        String retString = "";
        Calendar calendar = Calendar.getInstance();
        retString = get_city() + "\t\t" + calendar.MONTH + "/" + calendar.DAY_OF_MONTH + "/" + calendar.YEAR;
        return retString;
    }
}
