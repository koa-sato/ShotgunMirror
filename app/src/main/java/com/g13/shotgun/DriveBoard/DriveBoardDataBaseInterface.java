package com.g13.shotgun.DriveBoard;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.util.List;

public class DriveBoardDataBaseInterface {
    private List<DriveBoardPost> posts;
    /*CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
            getApplicationContext(),
            "us-west-2:a1e05d5b-80d8-4be4-afdd-8fe55238156d", // Identity Pool ID
            Regions.US_WEST_2 // Region
    );*/
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
        //TimeUnit.SECONDS.sleep(2);
        android.os.SystemClock.sleep(1000);
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

    }

    public void push_posts(List<DriveBoardPost> posts)
    {
        for(int i = 0; i < posts.size(); i++)
            mapper.save(posts.get(i));
    }

}
