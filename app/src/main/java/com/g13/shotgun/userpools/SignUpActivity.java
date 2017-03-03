//
// Copyright 2017 Amazon.com, Inc. or its affiliates (Amazon). All Rights Reserved.
//
// Code generated by AWS Mobile Hub. Amazon gives unlimited permission to 
// copy, distribute and modify it.
//
// Source code generated from template: aws-my-sample-app-android v0.15
//
package com.g13.shotgun.userpools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.AWSConfiguration;
import com.amazonaws.mobile.user.signin.CognitoUserPoolsSignInProvider;
import com.g13.shotgun.DatabaseUser;
import com.g13.shotgun.User;
import com.g13.shotgun.UserDatabaseInterface;
import com.g13.shotgun.util.ViewHelper;
import com.g13.shotgun.R;

/**
 * Activity to prompt for account sign up information.
 */
public class SignUpActivity extends Activity {
    /** Log tag. */
    private static final String LOG_TAG = SignUpActivity.class.getSimpleName();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    /**
     * Retrieve input and return to caller.
     * @param view the Android View
     */
    public void signUp(final View view) {
        final String username = ViewHelper.getStringValue(this, R.id.signup_username);
        final String password = ViewHelper.getStringValue(this, R.id.signup_password);
        final String firstName = ViewHelper.getStringValue(this, R.id.signup_first_name);
        final String lastName = ViewHelper.getStringValue(this, R.id.signup_last_name);
        final String gender = ViewHelper.getStringValue(this, R.id.signup_gender);
        final String email = ViewHelper.getStringValue(this, R.id.signup_email);
        final String phone = ViewHelper.getStringValue(this, R.id.signup_phone);
        boolean g = (gender.toLowerCase().equals("male"));

        if((username.equals("")) || (password.equals(""))|| (firstName.equals( "")) || (lastName.equals( ""))
                || (gender.equals( "")) || (email.equals( "")) || (phone.equals(""))){
            Toast.makeText(getApplicationContext(), "Please Enter All of The Fields",
                    Toast.LENGTH_LONG).show();

        }
        else {
            DatabaseUser u = new DatabaseUser(username, firstName, lastName, email, 3, g, phone);
            //User.getInstance(username, firstName, lastName, email, 3, g, phone);
            CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                    getApplicationContext(),
                    //"us-west-2:7252aed7-1cdf-439f-a16a-a97ef8ca7697", // Identity Pool ID
                    AWSConfiguration.AMAZON_COGNITO_IDENTITY_POOL_ID,
                    AWSConfiguration.AMAZON_COGNITO_REGION // Region
            );
            UserDatabaseInterface udbi = new UserDatabaseInterface(credentialsProvider);
            udbi.push_user(u);
            User.getInstance(u);
            //udbi.push_user(User.getInstance());
        }
        Log.d(LOG_TAG, "username = " + username);
        Log.d(LOG_TAG, "firstname = " + firstName);
        Log.d(LOG_TAG, "lastname = " + lastName);
        Log.d(LOG_TAG, "gender = " + gender);
        Log.d(LOG_TAG, "email = " + email);
        Log.d(LOG_TAG, "phone = " + phone);

        final Intent intent = new Intent();
        intent.putExtra(CognitoUserPoolsSignInProvider.AttributeKeys.USERNAME, username);
        intent.putExtra(CognitoUserPoolsSignInProvider.AttributeKeys.PASSWORD, password);
        intent.putExtra(CognitoUserPoolsSignInProvider.AttributeKeys.GIVEN_NAME, firstName);
        intent.putExtra(CognitoUserPoolsSignInProvider.AttributeKeys.FAMILY_NAME, lastName);
        intent.putExtra(CognitoUserPoolsSignInProvider.AttributeKeys.GENDER, gender);
        intent.putExtra(CognitoUserPoolsSignInProvider.AttributeKeys.EMAIL_ADDRESS, email);
        intent.putExtra(CognitoUserPoolsSignInProvider.AttributeKeys.PHONE_NUMBER, phone);


        setResult(RESULT_OK, intent);

        finish();
    }
}
