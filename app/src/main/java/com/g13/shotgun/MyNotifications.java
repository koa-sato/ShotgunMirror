package com.g13.shotgun;

/**
 * Created by Dominic on 3/12/2017.
 */

public class MyNotifications {
    private String type;
    private String user;
    private boolean isNew;
    public String getType(){return type;}
    public String getUser(){return user;}
    public MyNotifications(String t, String u){
        type = t;
        user = u;
        isNew = true;
    }
    public String toString(){
        String s = "";
        if(type.equals("interested"))
            s = user + " is interested in your post" + '\n';

        if(type .equals("confirmed"))
            s = user + " confirmed that you have a ride"+ '\n';
        return s;
    }
}
