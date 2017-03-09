package com.g13.shotgun.rideboard;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.util.List;

public class RideBoardDataBaseInterface {
    private List<RideBoardPost> posts;
    private CognitoCachingCredentialsProvider credentialsProvider;
    private AmazonDynamoDBClient ddbClient;
    private DynamoDBMapper mapper;

    public RideBoardDataBaseInterface(CognitoCachingCredentialsProvider cp)
    {
        credentialsProvider = cp;

        ddbClient = new AmazonDynamoDBClient(credentialsProvider);
        ddbClient.setRegion(Region.getRegion(Regions.US_WEST_2));
        mapper = new DynamoDBMapper(ddbClient);


    }

    public List<RideBoardPost> get_posts()
    {
        Runnable runnable = new Runnable() {
            public void run(){
                posts = mapper.scan(RideBoardPost.class, new DynamoDBScanExpression());
            }
        };

        Thread mythread = new Thread(runnable);
        mythread.start();
        while (mythread.isAlive())
            android.os.SystemClock.sleep(5);
        return posts;
    }

    public void push_post(final RideBoardPost p) {
        Runnable runnable = new Runnable() {
            public void run() {
                mapper.save(p);

            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();

    }

    public void push_posts(List<RideBoardPost> posts)
    {
        for(int i = 0; i < posts.size(); i++)
            mapper.save(posts.get(i));
    }

}
