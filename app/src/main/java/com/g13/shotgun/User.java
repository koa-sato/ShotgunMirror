package com.g13.shotgun;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

//import static com.amazonaws.http.impl.client.HttpRequestNoRetryHandler.Singleton;

//singleton


public class User {
    private static User the_user;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
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
        the_user = new User(u.getUsername(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getRating(), u.whichGender(), u.getPhoneNumber());
        return the_user;
    }


    public static User getInstance(){
        return the_user;
    }



    private User(String _username, String _firstName, String _lastName, String _email,  double _rating, boolean _isMale, String _phoneNumber) {
        username = _username;
        firstName = _firstName;
        lastName = _lastName;
        email = _email;
        //identityId = identityId;
        rating = _rating;
        isMale = _isMale;
        phoneNumber = _phoneNumber;

    }
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
