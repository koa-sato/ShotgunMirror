package com.g13.shotgun;

/**
 * Created by Dominic on 2/16/2017.
 */

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by Dominic on 2/14/2017.
 */


@DynamoDBTable(tableName = "Shotgun_Posts")
public class Post implements Serializable{
    private String city;
    private Date the_date2;
    private int the_date;
    private Date the_time;
    private String user;
    private String key;

    public Post()
    {
        Random r = new Random(10);
        key = Integer.toString(r.nextInt());

    }

    //public post(String __city ,int __date, String __user )
    
    public Post(String __city, Date __the_date, Time __the_time, String __user) {
        city = __city;
        the_date2 = __the_date;
        the_time = __the_time;
        user = __user;
        key = "HELP " + __city;
    }

    @DynamoDBHashKey(attributeName = "Key")
    public String get_key(){ return key;}
    public void set_key(String d){ key = d;}


   // @DynamoDBIndexHashKey(attributeName = "Date")
    public int get_date(){ return the_date;}
    public void set_date(int d){ the_date = d;}


    @DynamoDBIndexRangeKey(attributeName = "City")
    public String get_city(){
        return city;
    }
    public void set_city(String c){ city = c;}


    //@DynamoDBIndexRangeKey(attributeName = "Time")
    public void set_time(Date t){ the_time = t;}
    public Date get_time(){ return the_time;}

    @DynamoDBAttribute(attributeName = "User")
    public String get_user(){ return user;}
    public void set_user(String u){ user = u;}
    
    @Override
    public String toString() {
        String retString = "";
        //Calendar calendar = Calendar.getInstance();
        //retString = get_city() + "\t\t" + calendar.MONTH + "/" + calendar.DAY_OF_MONTH + "/" + calendar.YEAR;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        retString = get_city() + "\n" + sdf.format(new Date());
        return retString;
    }
}
