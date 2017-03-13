package com.g13.shotgun.driveboard;


import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.g13.shotgun.EditPostActivity;

import java.util.ArrayList;
import java.util.List;

public class DriveBoardDataBaseInterface {
    private static final String LOG_TAG = EditPostActivity.class.getSimpleName();
    private DriveBoardPost post;
    private List<DriveBoardPost> posts;
   
    private CognitoCachingCredentialsProvider credentialsProvider;
    private AmazonDynamoDBClient ddbClient;
    private DynamoDBMapper mapper;

    public DriveBoardDataBaseInterface(CognitoCachingCredentialsProvider cp)
    {
        credentialsProvider = cp;

        ddbClient = new AmazonDynamoDBClient(credentialsProvider);
        ddbClient.setRegion(Region.getRegion(Regions.US_WEST_2));
        mapper = new DynamoDBMapper(ddbClient);
    }

    public List<DriveBoardPost> get_posts()
    {

        Runnable runnable = new Runnable() {
            public void run(){
                posts = mapper.scan(DriveBoardPost.class, new DynamoDBScanExpression());
            }
        };

        Thread mythread = new Thread(runnable);
        mythread.start();

        while(mythread.isAlive())
            android.os.SystemClock.sleep(5);
        return posts;
    }

    public void delete_post(final String p){

        Runnable runnable = new Runnable() {
            public void run(){
                post = mapper.load(DriveBoardPost.class, p);
                if(post == null)
                    Log.d(LOG_TAG, "NOT DELETING" + p);
                else {
                    Log.d(LOG_TAG, "DELETING");
                    mapper.delete(post);
                }
            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();
        //TimeUnit.SECONDS.sleep(2);
        while(mythread.isAlive())
            android.os.SystemClock.sleep(5);
    }

    public List<DriveBoardPost> get_users_posts(String user){
        final DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                .withHashKeyValues(user)
                //.withRangeKeyCondition("Title", rangeKeyCondition)
                .withConsistentRead(false);
        Runnable runnable = new Runnable() {
            public void run(){
              posts = mapper.query(DriveBoardPost.class, queryExpression);
            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();
        //TimeUnit.SECONDS.sleep(2);
        while(mythread.isAlive())
            android.os.SystemClock.sleep(5);
        if(posts == null)
            return new ArrayList<>();

        while(mythread.isAlive())
            android.os.SystemClock.sleep(5);
        return posts;
    }

    public void push_post(final DriveBoardPost p) {
        Runnable runnable = new Runnable() {
            public void run() {
                mapper.save(p);

            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();

        while(mythread.isAlive())
            android.os.SystemClock.sleep(5);


    }

    public DriveBoardPost get_post(final String key){
        Runnable runnable = new Runnable() {
            public void run() {
                 post = mapper.load(DriveBoardPost.class, key);

            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();
        //TimeUnit.SECONDS.sleep(2);
        while(mythread.isAlive())
            android.os.SystemClock.sleep(5);
        return post;
    }

    public void push_posts(List<DriveBoardPost> posts)
    {
        for(int i = 0; i < posts.size(); i++)
            mapper.save(posts.get(i));
    }

}
