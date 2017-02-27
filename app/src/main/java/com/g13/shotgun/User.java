package com.g13.shotgun;

public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String identityId;
    private double rating;

    public User() {
        firstName = "First Name";
        lastName = "Last Name";
        email = "Email";
        identityId = "";
    }

    public User(String _firstName, String _lastName, String _email, String _identityId) {
        firstName = _firstName;
        lastName = _lastName;
        email = _email;
        identityId = _identityId;
    }

    private String getFirstName() { return firstName; }
    private void setFirstName(String newFirsName) { firstName = newFirsName; }

    private String getLastName() { return lastName; }
    private void setLastName(String newLastName) { lastName = newLastName; }

    private String getEmail() { return email; }
    private void setEmail(String newEmail) { email = newEmail; }

    private String getIdentityID() { return identityId; }
    private void setIdentityID(String newIdentityId) { identityId = newIdentityId; }

}
