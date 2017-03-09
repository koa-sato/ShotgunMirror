package com.g13.shotgun;

//singleton


public class User {
    private static User the_user;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private double rating;
    private boolean isMale;
    private String phoneNumber;



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

    public double getRating() { return rating; }
    public void setRating(double newRating) { rating = newRating; }

    public boolean whichGender() { return isMale; }
    public void setGender(boolean _isMale) { isMale = _isMale; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String _newPhoneNumber) { phoneNumber = _newPhoneNumber; }

}
