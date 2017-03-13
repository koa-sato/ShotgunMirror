package com.g13.shotgun;


import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.g13.shotgun.driveboard.DriveBoard;
import com.g13.shotgun.driveboard.DriveBoardPost;

import java.util.ArrayList;

//import static com.amazonaws.http.impl.client.HttpRequestNoRetryHandler.Singleton;


//singleton


public class User {
    private static User the_user;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private ArrayList<MyNotifications> notifications;
    private ArrayList<String> connections;
    //private String identityId;

    private double rating;
    private boolean isMale;
    private String phoneNumber;
    private DriveBoardPost d_highlighted_post;

    public void add_notification(MyNotifications n){
        if(notifications == null)
            notifications = new ArrayList<>();
        notifications.add(n);
    }

    public ArrayList<MyNotifications> getNotifications(){
        return notifications;
    }

    public DriveBoardPost getD_highlighted_post(){
        return d_highlighted_post;
    }

    public void setD_highlighted_post(DriveBoardPost p){
        d_highlighted_post = p;
    }

    public static User getInstance(DatabaseUser u){
        the_user = new User(u.getUsername(), u.getFirstName(), u.getLastName(),
                u.getEmail(), u.getRating(), u.whichGender(), u.getPhoneNumber(), u.getConnections(), u.gettNotifications(), u.getuNotifications());//, u.getNotifications());
        return the_user;
    }

    public String displayConnections(){
        if(connections == null)
            connections = new ArrayList<>();
        String s = "";
        for(int i = 0; i < connections.size(); i++)
            s = s + connections.get(i) + '\n';
        return s;
            }

    public static User getInstance(){
        return the_user;
    }

    public void addConnection(String s){
        if(connections == null)
            connections = new ArrayList<>();
        connections.add(s);
    }

    private User(String _username, String _firstName, String _lastName, String _email,
                 double _rating, boolean _isMale, String _phoneNumber, ArrayList<String> s, ArrayList<String> tn, ArrayList<String> un) {
        username = _username;
        firstName = _firstName;
        lastName = _lastName;
        email = _email;
        rating = _rating;
        isMale = _isMale;
        phoneNumber = _phoneNumber;
        connections = s;
        notifications = new ArrayList<>();
        if(tn == null)
            return;
        else{
            for(int i =0; i < tn.size(); i++){
                notifications.add(i, new MyNotifications(tn.get(i), un.get(i)));
            }

        }

    }


    public ArrayList<String> getConnections() { return connections; }
    public void setConnections(ArrayList<String> s) { connections = s; }


    public String getUsername() { return username; }
    public void setUsername(String newUsername) { username = newUsername; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String newFirsName) { firstName = newFirsName; }

    public String getLastName() { return lastName; }
    public void setLastName(String newLastName) { lastName = newLastName; }

    public String getEmail() { return email; }
    public void setEmail(String newEmail) { email = newEmail; }

    public double getRating() { return rating; }
    public void setRating(double newRating) { rating = newRating; }

    public boolean whichGender() { return isMale; }
    public void setGender(boolean _isMale) { isMale = _isMale; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String _newPhoneNumber) { phoneNumber = _newPhoneNumber; }

}
