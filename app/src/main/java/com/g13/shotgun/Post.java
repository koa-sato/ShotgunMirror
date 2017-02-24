package com.g13.shotgun;

/**
 * Created by Dominic on 2/16/2017.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.io.Serializable;

/**
 * Created by Dominic on 2/14/2017.
 */


@DynamoDBTable(tableName = "Shotgun_Posts")
public class Post implements Serializable{
    private String city;
    private int month;
    private int day;
    private int year;
    private int hour;
    private int minute;
    private int am;
    private String user;
    private String key;

    public Post(){};

    public Post(Parcel in){};

    public int describeContents(){return 0;}

    public static final Parcelable.Creator<Post> CREATOR
            = new Parcelable.Creator<Post>() {
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        public Post[] newArray(int size) {
            return new Post[size];
        }
    };


    public void writeToParcel(Parcel dest, int flags){

    }

    /*public Post()
    {
        Random r = new Random(10);
        key = Integer.toString(r.nextInt());
    }*/

    //public post(String __city ,int __date, String __user )

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

    @Override
    public String toString() {
        //String retString = "";
        //Calendar calendar = Calendar.getInstance();
        //retString = get_city() + "\t\t" + calendar.MONTH + "/" + calendar.DAY_OF_MONTH + "/" + calendar.YEAR;
        //SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        //retString = get_city() + "\n" + sdf.format(new Date());
        //return "ji";
        return get_city() + "\n" + (month + 1) + '/' + day+ '/' + year;
    }
}