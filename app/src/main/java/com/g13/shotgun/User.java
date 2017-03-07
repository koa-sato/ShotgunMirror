package com.g13.shotgun;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.ArrayList;

//import static com.amazonaws.http.impl.client.HttpRequestNoRetryHandler.Singleton;

//singleton


public class User {
    private static User the_user;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private ArrayList<String> connections;
    //private String identityId;
    private double rating;
    private boolean isMale;
    private String phoneNumber;

   /* public User() {
        username = "username";
        firstName = "First Name";
        lastName = "Last Name";
        email = "Email";
        identityId = "id";
        rating = 0.0;
        isMale = true;
        phoneNumber = "+12345678901";
    }*/

    public static User getInstance(DatabaseUser u){
        the_user = new User(u.getUsername(), u.getFirstName(), u.getLastName(),
                u.getEmail(), u.getRating(), u.whichGender(), u.getPhoneNumber(), u.getConnections());
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
                 double _rating, boolean _isMale, String _phoneNumber, ArrayList<String> s) {
        username = _username;
        firstName = _firstName;
        lastName = _lastName;
        email = _email;
        //identityId = identityId;
        rating = _rating;
        isMale = _isMale;
        phoneNumber = _phoneNumber;
        connections = s;
    }

    public ArrayList<String> getConnections() { return connections; }
    public void setConnections(ArrayList<String> s) { connections = s; }

   // @DynamoDBHashKey(attributeName = "username")
    public String getUsername() { return username; }
    public void setUsername(String newUsername) { username = newUsername; }


    public String getFirstName() { return firstName; }
    public void setFirstName(String newFirsName) { firstName = newFirsName; }

   // @DynamoDBAttribute(attributeName = "lastName")
    public String getLastName() { return lastName; }
    public void setLastName(String newLastName) { lastName = newLastName; }

  //  @DynamoDBAttribute(attributeName = "email")
    public String getEmail() { return email; }
    public void setEmail(String newEmail) { email = newEmail; }


  //  @DynamoDBAttribute(attributeName = "rating")
    public double getRating() { return rating; }
    public void setRating(double newRating) { rating = newRating; }

 //   @DynamoDBAttribute(attributeName = "isMale")
    public boolean whichGender() { return isMale; }
    public void setGender(boolean _isMale) { isMale = _isMale; }

 //   @DynamoDBAttribute(attributeName = "phoneNumber")
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String _newPhoneNumber) { phoneNumber = _newPhoneNumber; }

}
