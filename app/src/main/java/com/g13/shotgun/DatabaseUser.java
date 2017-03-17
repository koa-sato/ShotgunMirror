package com.g13.shotgun;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.ArrayList;

/**
 * Created by Dominic on 3/2/2017.
 */
@DynamoDBTable(tableName = "Shotgun_Users")
public class DatabaseUser {

//singleton



    private static com.g13.shotgun.User the_user;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private double rating;
    private boolean isMale;
    private String phoneNumber;
    private ArrayList<String> connections;
    private ArrayList<String> t_notifications;
    private ArrayList<String> u_notifications;
    public DatabaseUser(){}

    public void clearNotifications(){
        t_notifications.clear();
        u_notifications.clear();
    }

    public DatabaseUser(String _username, String _firstName, String _lastName, String _email,
                        double _rating, boolean _isMale, String _phoneNumber, ArrayList<String > s) {



            username = _username;
            firstName = _firstName;
            lastName = _lastName;
            email = _email;
            //identityId = identityId;
            rating = _rating;
            isMale = _isMale;
            phoneNumber = _phoneNumber;
        t_notifications = new ArrayList<>();
        t_notifications = new ArrayList<>();
            connections = s;
        }
    public void addConnection(String s){
        if(connections == null)
            connections = new ArrayList<>();
        connections.add(s);

    }
    private void notifications_update(ArrayList<MyNotifications> n){

        if(t_notifications == null)
            t_notifications = new ArrayList<>();
        if(u_notifications == null)
            u_notifications = new ArrayList<>();
        for(int i =0; i < n.size(); i++){
            t_notifications.add(i,  n.get(i).getType());
            u_notifications.add(i, n.get(i).getUser());
        }
    }

    public void addNotification(MyNotifications n){
        if(t_notifications == null)
            t_notifications = new ArrayList<>();
        if(u_notifications == null)
            u_notifications = new ArrayList<>();
        t_notifications.add(n.getType());
        u_notifications.add(n.getUser());
    }
        public DatabaseUser(User u){
            username = u.getUsername();
            firstName = u.getFirstName();
            lastName = u.getLastName();
            email = u.getEmail();
            rating = u.getRating();
            isMale = u.whichGender();
            phoneNumber = u.getPhoneNumber();
            connections = u.getConnections();
            notifications_update(u.getNotifications());
        }
        @DynamoDBHashKey(attributeName = "username")
        public String getUsername() { return username; }
        public void setUsername(String newUsername) { username = newUsername; }


    @DynamoDBAttribute(attributeName = "firstName")
    public String getFirstName() { return firstName; }
    public void setFirstName(String newFirsName) { firstName = newFirsName; }


        @DynamoDBAttribute(attributeName = "Connections")
        public ArrayList<String> getConnections() { return connections; }
         public void setConnections(ArrayList<String> s) { connections = s; }

        @DynamoDBAttribute(attributeName = "lastName")
        public String getLastName() { return lastName; }
        public void setLastName(String newLastName) { lastName = newLastName; }


    @DynamoDBAttribute(attributeName = "email")
    public String getEmail() { return email; }
    public void setEmail(String newEmail) { email = newEmail; }


    @DynamoDBAttribute(attributeName = "rating")
    public double getRating() { return rating; }
    public void setRating(double newRating) { rating = newRating; }

    @DynamoDBAttribute(attributeName = "isMale")
    public boolean whichGender() { return isMale; }
    public void setGender(boolean _isMale) { isMale = _isMale; }

    @DynamoDBAttribute(attributeName = "phoneNumber")
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String _newPhoneNumber) { phoneNumber = _newPhoneNumber; }

    @DynamoDBAttribute(attributeName = "tNotifications")
    public ArrayList<String> gettNotifications() { return t_notifications; }
    public void settNotifications(ArrayList<String> s) { t_notifications = s; }

    @DynamoDBAttribute(attributeName = "uNotifications")
    public ArrayList<String> getuNotifications() { return u_notifications; }
    public void setuNotifications(ArrayList<String> s) { u_notifications = s; }

}


