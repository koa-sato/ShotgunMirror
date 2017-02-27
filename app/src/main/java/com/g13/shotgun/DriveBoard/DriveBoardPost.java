package com.g13.shotgun.DriveBoard;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.g13.shotgun.Post;

import java.io.Serializable;

@DynamoDBTable(tableName = "Shotgun_Posts")
public class DriveBoardPost extends Post implements Serializable {

    private int numSeats;

    public DriveBoardPost(String __city, int __month, int __day, int __year, int __hour, int __minute,
                int __am, String __user, int __numSeats) {
        super(__city, __month, __day, __year, __hour, __minute, __am, __user);
        numSeats = __numSeats;
    }

}