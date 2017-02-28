package com.g13.shotgun;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.amazonaws.mobile.AWSConfiguration;
import com.amazonaws.mobile.user.signin.CognitoUserPoolsSignInProvider;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.tokens.CognitoAccessToken;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.g13.shotgun.DriveBoard.DriveBoard;
import com.g13.shotgun.DriveBoard.DriveBoardPost;
import com.g13.shotgun.RideBoard.RideBoard;
import com.g13.shotgun.SignIn.SignInActivity;
import com.g13.shotgun.userpools.SignUpConfirmActivity;
import com.g13.shotgun.util.ViewHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.g13.shotgun.R.string.sign_up_success;
import static com.g13.shotgun.R.string.title_activity_sign_up;

public class UserProfile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences prefs = getSharedPreferences("com.amazonaws.android.auth", SignInActivity.MODE_PRIVATE);
        String identityId = prefs.getString("us-west-2:62219bfc-e563-454f-b3a5-4c36c1853c14.identityId", null);
        String email;
        CognitoUserPool userPool = new CognitoUserPool(getApplicationContext(),
                AWSConfiguration.AMAZON_COGNITO_USER_POOL_ID,
                AWSConfiguration.AMAZON_COGNITO_USER_POOL_CLIENT_ID,
                AWSConfiguration.AMAZON_COGNITO_USER_POOL_CLIENT_SECRET,
                AWSConfiguration.AMAZON_COGNITO_REGION);

        final GetDetailsHandler handler = new GetDetailsHandler() {
            @Override
            public void onSuccess(CognitoUserDetails cognitoUserDetails) {
                /*Log.d(LOG_TAG, "Signed up. User ID = " + user.getUserId());
                ViewHelper.showDialog(activity, activity.getString(title_activity_sign_up),
                        activity.getString(sign_up_success) + " " + user.getUserId());
                        */
            }

            @Override
            public void onFailure(Exception exception) {

            }
        };

        /*
        final CognitoUser currentUser = userPool.getCurrentUser();
        Runnable runnable = new Runnable() {
            public void run(){
                currentUser.getDetails(handler);
            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();
        while(mythread.isAlive())
            android.os.SystemClock.sleep(5);

        final CognitoUserPoolsSignInProvider cognitoUserPoolsSignInProvider =
                new CognitoUserPoolsSignInProvider(getApplicationContext());

        final Gson gson = new Gson();
        String jsonToken = cognitoUserPoolsSignInProvider.getToken();
        Type type = new TypeToken<String>() {
        }.getType();
        email = gson.fromJson(jsonToken, type);

        CognitoAccessToken accessToken =



        User currentUser = new User("firstname", "lastname", email, identityId, true, "14082324223");
        Log.d("EMAIL", "email = " + currentUser.getEmail());
        */
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.driveboard) {
            Intent intent = new Intent(UserProfile.this, DriveBoard.class);
            startActivity(intent);
        } else if (id == R.id.rideboard) {
            Intent intent = new Intent(UserProfile.this, RideBoard.class);
            startActivity(intent);
        } else if (id == R.id.messenger) {
            Intent intent = new Intent(UserProfile.this, Messenger.class);
            startActivity(intent);
        } else if (id == R.id.profile) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
