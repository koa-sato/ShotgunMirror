package com.g13.shotgun.RideBoard;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.g13.shotgun.Post;

@DynamoDBTable(tableName = "Shotgun_rides")
public class RideBoardPost extends Post {

    public RideBoardPost(String __city, int __month, int __day, int __year, int __hour, int __minute,
                          int __am, String __user) {
        super(__city, __month, __day, __year, __hour, __minute, __am, __user);
    }
}