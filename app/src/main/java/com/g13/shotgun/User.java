package com.g13.shotgun;

public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String identityId;
    private double rating;
    private boolean isMale;
    private String phoneNumber;

    public User() {
        firstName = "First Name";
        lastName = "Last Name";
        email = "Email";
        identityId = "";
    }

    public User(String _firstName, String _lastName, String _email, String _identityId, boolean _isMale, String _phoneNumber) {
        firstName = _firstName;
        lastName = _lastName;
        email = _email;
        identityId = _identityId;
        isMale = _isMale;
        phoneNumber = _phoneNumber;

    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String newFirsName) { firstName = newFirsName; }

    public String getLastName() { return lastName; }
    public void setLastName(String newLastName) { lastName = newLastName; }

    public String getEmail() { return email; }
    public void setEmail(String newEmail) { email = newEmail; }

    public String getIdentityID() { return identityId; }
    public void setIdentityID(String newIdentityId) { identityId = newIdentityId; }

    public boolean whichGender() { return isMale; }
    public void setGender(boolean _isMale) { isMale = _isMale; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String _newPhoneNumber) { phoneNumber = _newPhoneNumber; }

}
