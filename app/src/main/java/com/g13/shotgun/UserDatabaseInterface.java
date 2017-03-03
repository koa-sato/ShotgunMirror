package com.g13.shotgun;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.g13.shotgun.RideBoard.RideBoardPost;

import java.util.List;

/**
 * Created by Dominic on 3/2/2017.
 */

public class UserDatabaseInterface {
    DatabaseUser u;
    private CognitoCachingCredentialsProvider credentialsProvider;
    private AmazonDynamoDBClient ddbClient;
    private DynamoDBMapper mapper;

    public UserDatabaseInterface(CognitoCachingCredentialsProvider cp)
    {
        credentialsProvider = cp;

        ddbClient = new AmazonDynamoDBClient(credentialsProvider);
        ddbClient.setRegion(Region.getRegion(Regions.US_WEST_2));
        mapper = new DynamoDBMapper(ddbClient);


    }

    public DatabaseUser get_user(final String username)
    {

        Runnable runnable = new Runnable() {
            public void run(){
                u = mapper.load(DatabaseUser.class, username);
            }
        };

        Thread mythread = new Thread(runnable);
        mythread.start();
        //TimeUnit.SECONDS.sleep(2);
        while (mythread.isAlive())
            android.os.SystemClock.sleep(5);
        return u;
    }

    public void push_user(final DatabaseUser u) {
        Runnable runnable = new Runnable() {
            public void run() {
                mapper.save(u);

            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();
        while (mythread.isAlive())
            android.os.SystemClock.sleep(5);
    }


}
