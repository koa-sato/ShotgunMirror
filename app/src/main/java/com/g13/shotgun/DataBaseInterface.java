package com.g13.shotgun;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominic on 2/16/2017.
 */

public class DataBaseInterface {
    /*CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
            getApplicationContext(),
            "us-west-2:a1e05d5b-80d8-4be4-afdd-8fe55238156d", // Identity Pool ID
            Regions.US_WEST_2 // Region
    );*/
    private CognitoCachingCredentialsProvider credentialsProvider;
    private AmazonDynamoDBClient ddbClient;
    private DynamoDBMapper mapper;

    public DataBaseInterface(CognitoCachingCredentialsProvider cp)
    {

        credentialsProvider = cp;

        ddbClient = new AmazonDynamoDBClient(credentialsProvider);
        ddbClient.setRegion(Region.getRegion(Regions.US_WEST_2));
        mapper = new DynamoDBMapper(ddbClient);


    }


    public List<Post> get_posts(int n)
    {

        List<Post> posts = mapper.scan(Post.class, new DynamoDBScanExpression());
        return posts;
    }

    public void push_post(Post p)
    {
        mapper.save(p);
    }

    public void push_posts(List<Post> posts)
    {
        for(int i = 0; i < posts.size(); i++)
            mapper.save(posts.get(i));
    }




}
