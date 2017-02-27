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

    private String getFirstName() { return firstName; }
    private void setFirstName(String newFirsName) { firstName = newFirsName; }

    private String getLastName() { return lastName; }
    private void setLastName(String newLastName) { lastName = newLastName; }

    private String getEmail() { return email; }
    private void setEmail(String newEmail) { email = newEmail; }

    private String getIdentityID() { return identityId; }
    private void setIdentityID(String newIdentityId) { identityId = newIdentityId; }

    private boolean whichGender() { return isMale; }
    private void setGender(boolean _isMale) { isMale = _isMale; }

    private String getPhoneNumber() { return phoneNumber; }
    private void setPhoneNumber(String _newPhoneNumber) { phoneNumber = _newPhoneNumber; }

}
