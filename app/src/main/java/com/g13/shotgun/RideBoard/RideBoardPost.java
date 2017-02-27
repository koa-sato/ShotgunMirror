package com.g13.shotgun.RideBoard;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.g13.shotgun.Post;

@DynamoDBTable(tableName = "Shotgun_rides")
public class RideBoardPost extends Post {

    int end_year;
    int end_month;
    int end_day;

    public RideBoardPost() {};

    public RideBoardPost(String __city, int __month, int __day, int __year, int __hour, int __minute,
                          int __am, String __user, int e_year, int e_month, int e_day) {
        super(__city, __month, __day, __year, __hour, __minute, __am, __user);
        end_year = e_year;
        end_month = e_month;
        end_day = e_day;
    }

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


    @DynamoDBAttribute(attributeName = "End Month")
    public void set_end_month(int t){ end_month = t;}
    public int get_end_month(){ return end_month;}


    @DynamoDBAttribute(attributeName = "End Year")
    public void set_end_year(int t){ end_year = t;}
    public int get_end_year(){ return end_year;}


    @DynamoDBAttribute(attributeName = "End Day")
    public void set_end_day(int t){ end_day = t;}
    public int get_end_day(){ return end_day;}

    @Override
    public String toString() {
        return get_city() + "\n" + (month + 1) + '/' + day+ '/' + year
                + " - " + (end_month+1) + '/' + end_day + '/' + end_year;
    }
}