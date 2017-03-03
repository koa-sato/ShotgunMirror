package com.g13.shotgun;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "Shotgun_Users")
public class User {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String identityId;
    private double rating;
    private boolean isMale;
    private String phoneNumber;

    public User() {
        username = "username";
        firstName = "First Name";
        lastName = "Last Name";
        email = "Email";
        identityId = "id";
        rating = 0.0;
        isMale = true;
        phoneNumber = "+12345678901";
    }

    public User(String _username, String _firstName, String _lastName, String _email, String _identityId, double _rating, boolean _isMale, String _phoneNumber) {
        username = _username;
        firstName = _firstName;
        lastName = _lastName;
        email = _email;
        identityId = _identityId;
        rating = _rating;
        isMale = _isMale;
        phoneNumber = _phoneNumber;

    }

    public String getUsername() { return username; }
    public void setUsername(String newUsername) { username = newUsername; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String newFirsName) { firstName = newFirsName; }

    public String getLastName() { return lastName; }
    public void setLastName(String newLastName) { lastName = newLastName; }

    public String getEmail() { return email; }
    public void setEmail(String newEmail) { email = newEmail; }

    public String getIdentityID() { return identityId; }
    public void setIdentityID(String newIdentityId) { identityId = newIdentityId; }

    public double getRating() { return rating; }
    public void setRating(double newRating) { rating = newRating; }

    public boolean whichGender() { return isMale; }
    public void setGender(boolean _isMale) { isMale = _isMale; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String _newPhoneNumber) { phoneNumber = _newPhoneNumber; }

}
