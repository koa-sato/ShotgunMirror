package com.g13.shotgun;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

/**
 * Created by Dominic on 3/2/2017.
 */
@DynamoDBTable(tableName = "Shotgun_Users")
public class DatabaseUser {


//import static com.amazonaws.http.impl.client.HttpRequestNoRetryHandler.Singleton;

//singleton



    private static com.g13.shotgun.User the_user;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private double rating;
    private boolean isMale;
    private String phoneNumber;

    public DatabaseUser(){}

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

    /*public static User getInstance(User u){
        the_user = u;
        return the_user;
    }*/


   /* public static User getInstance(){
        return the_user;
    }*/

    /*public static User getInstance(String _username, String _firstName, String _lastName, String _email, int _rating, boolean _isMale, String _phoneNumber){
        if(the_user == null) {
            the_user = new User(_username, _firstName, _lastName, _email, _rating, _isMale, _phoneNumber);
        }
        return the_user;
    }*/

    public DatabaseUser(String _username, String _firstName, String _lastName, String _email,  double _rating, boolean _isMale, String _phoneNumber) {
            username = _username;
            firstName = _firstName;
            lastName = _lastName;
            email = _email;
            //identityId = identityId;
            rating = _rating;
            isMale = _isMale;
            phoneNumber = _phoneNumber;

        }
        @DynamoDBHashKey(attributeName = "username")
        public String getUsername() { return username; }
        public void setUsername(String newUsername) { username = newUsername; }

        @DynamoDBAttribute(attributeName = "firstName")
        public String getFirstName() { return firstName; }
        public void setFirstName(String newFirsName) { firstName = newFirsName; }

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

}

