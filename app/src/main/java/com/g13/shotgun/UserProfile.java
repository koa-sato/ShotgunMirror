package com.g13.shotgun;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.AWSConfiguration;
import com.g13.shotgun.driveboard.DriveBoard;
import com.g13.shotgun.driveboard.DriveBoardDataBaseInterface;
import com.g13.shotgun.driveboard.DriveBoardPost;
import com.g13.shotgun.rideboard.RideBoard;
import com.g13.shotgun.sendbird.MainActivity;
import com.g13.shotgun.signIn.SignInActivity;

import java.util.ArrayList;

public class UserProfile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //private User Fred;
    ArrayList<DriveBoardPost> the_users_posts;
    ArrayList<DriveBoardPost> post;
    ListView the_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        the_list = (ListView) findViewById(R.id.list);
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                AWSConfiguration.AMAZON_COGNITO_IDENTITY_POOL_ID,
                AWSConfiguration.AMAZON_COGNITO_REGION // Region
        );
        DriveBoardDataBaseInterface dbi = new DriveBoardDataBaseInterface(credentialsProvider);
         the_users_posts = new ArrayList<>(dbi.get_posts());
        post = new ArrayList<>();
        for(int i = 0; i < the_users_posts.size(); i++)
            if(the_users_posts.get(i).get_user().equals(User.getInstance().getUsername()))
                post.add(the_users_posts.get(i));
        ArrayAdapter<DriveBoardPost> arrayAdapter =
                new ArrayAdapter<DriveBoardPost>(this, android.R.layout.simple_list_item_1, post);
        the_list.setAdapter(arrayAdapter);
        the_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User.getInstance().setD_highlighted_post(post.get(position));
                Intent i = new Intent(getApplicationContext(), EditPostActivity.class);
                i.putExtra("Position", position);
                startActivity(i);
            }
        });
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final TextView name = (TextView) findViewById(R.id.textView8);
        name.setText(
                "Name: " + User.getInstance().getFirstName() + " " + User.getInstance().getLastName()
        + '\n' + User.getInstance().getUsername());

        final TextView email1 = (TextView) findViewById(R.id.textView9);
        //email1.setText(User.getInstance().displayConnections());
               // "Email: " +  User.getInstance().getEmail());
        String posts = "";
        //for(int i = 0; i < the_users_posts.size();i++)
          //  posts = posts + the_users_posts.get(i) + '\n';
        posts = posts + User.getInstance().displayConnections();
        email1.setText(User.getInstance().getEmail());
        final TextView phone = (TextView) findViewById(R.id.textView10);
        phone.setText(
                User.getInstance().getPhoneNumber());

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences prefs = getSharedPreferences("com.amazonaws.android.auth", SignInActivity.MODE_PRIVATE);
        String identityId = prefs.getString(AWSConfiguration.AMAZON_COGNITO_IDENTITY_POOL_ID + ".identityId", null);


        /*
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                AWSConfiguration.AMAZON_ACCT_ID,
                AWSConfiguration.AMAZON_COGNITO_IDENTITY_POOL_ID,
                AWSConfiguration.AMAZON_COGNITO_AUTH_ID,
                AWSConfiguration.AMAZON_COGNITO_UNAUTH_ID,
                AWSConfiguration.AMAZON_COGNITO_REGION);

        CognitoSyncManager client = new CognitoSyncManager(
                getApplicationContext(),
                AWSConfiguration.AMAZON_COGNITO_REGION,
                credentialsProvider);

        Dataset dataset = client.openOrCreateDataset("DataSet");

        String username = CognitoUserPoolsSignInProvider.username;
        //dataset.get(CognitoUserPoolsSignInProvider.AttributeKeys.USERNAME);
        String firstName = dataset.get(CognitoUserPoolsSignInProvider.AttributeKeys.GIVEN_NAME);
        String lastName = dataset.get(CognitoUserPoolsSignInProvider.AttributeKeys.FAMILY_NAME);
        String gender = dataset.get(CognitoUserPoolsSignInProvider.AttributeKeys.GENDER);
        String email = dataset.get(CognitoUserPoolsSignInProvider.AttributeKeys.EMAIL_ADDRESS);
        String phoneNumber = dataset.get(CognitoUserPoolsSignInProvider.AttributeKeys.PHONE_NUMBER);

        CognitoUserPool cognitoUserPool = new CognitoUserPool(getApplicationContext(),
                AWSConfiguration.AMAZON_COGNITO_USER_POOL_ID,
                AWSConfiguration.AMAZON_COGNITO_USER_POOL_CLIENT_ID,
                AWSConfiguration.AMAZON_COGNITO_USER_POOL_CLIENT_SECRET,
                AWSConfiguration.AMAZON_COGNITO_REGION);
        final CognitoUser cognitoUser = cognitoUserPool.getUser(username);
        final CognitoUserDetails[] userDetails = new CognitoUserDetails[1];
        cognitoUser.getDetails(new GetDetailsHandler() {
            @Override
            public void onSuccess(CognitoUserDetails cognitoUserDetails) {
                userDetails[0] = cognitoUserDetails;
                Log.d("UserProfile", "Got the goddamn details");
            }

            @Override
            public void onFailure(Exception exception) {
                Log.d("UserProfile", exception.getMessage());
            }
        });

        final CognitoUserAttributes attributes = userDetails[0].getAttributes();
        final Map<String, String> mapOfAttributes = attributes.getAttributes();
        firstName = mapOfAttributes.get(CognitoUserPoolsSignInProvider.AttributeKeys.GIVEN_NAME);
        lastName = mapOfAttributes.get(CognitoUserPoolsSignInProvider.AttributeKeys.FAMILY_NAME);
        gender = mapOfAttributes.get(CognitoUserPoolsSignInProvider.AttributeKeys.GENDER);
        email = mapOfAttributes.get(CognitoUserPoolsSignInProvider.AttributeKeys.EMAIL_ADDRESS);
        phoneNumber = mapOfAttributes.get(CognitoUserPoolsSignInProvider.AttributeKeys.PHONE_NUMBER);
        */
        /*dataset.synchronize(new DefaultSyncCallback() {
            @Override
            public void onSuccess(Dataset dataset, List newRecords) {
                //Your handler code here
            }
        });*/

        //Log.d("identityid", identityId);
        //Log.d("otherid", credentialsProvider.getIdentityId());


        //Log.d("Username = ", username);
        /*Log.d("First Name = ", firstName);
        Log.d("Last Name = ", lastName);
        Log.d("Gender = ", gender);
        Log.d("Email = ", email);
        Log.d("PhoneNumber = ", phoneNumber);*/



        /*(CognitoUserPool userPool = new CognitoUserPool(getApplicationContext(),
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

            }

            @Override
            public void onFailure(Exception exception) {

            }
        };*/


        /*
        SharedPreferences sharedPrefs = getSharedPreferences("user_attributes", MODE_PRIVATE);
        final Gson gson = new Gson();
        String json = sharedPrefs.getString("attributes", null);
        Type type = new TypeToken<CognitoUserAttributes>() {
        }.getType();
        CognitoUserAttributes attributes = gson.fromJson(json, type);
        Map mapOfAttributes = attributes.getAttributes();

        String username = (String) mapOfAttributes.get(CognitoUserPoolsSignInProvider.AttributeKeys.USERNAME);
        String firstName = (String) mapOfAttributes.get(CognitoUserPoolsSignInProvider.AttributeKeys.GIVEN_NAME);
        String lastName = (String) mapOfAttributes.get(CognitoUserPoolsSignInProvider.AttributeKeys.FAMILY_NAME);
        String gender = (String) mapOfAttributes.get(CognitoUserPoolsSignInProvider.AttributeKeys.GENDER);
        String email = (String) mapOfAttributes.get(CognitoUserPoolsSignInProvider.AttributeKeys.EMAIL_ADDRESS);
        String phoneNumber = (String) mapOfAttributes.get(CognitoUserPoolsSignInProvider.AttributeKeys.PHONE_NUMBER);


        Log.d("Username = ", username);
        Log.d("First Name = ", firstName);
        Log.d("Last Name = ", lastName);
        Log.d("Gender = ", gender);
        Log.d("Email = ", email);
        Log.d("PhoneNumber = ", phoneNumber);

/*
        String accessIDToken = prefs.getString(AWSConfiguration.AMAZON_COGNITO_IDENTITY_POOL_ID + ".accessKey", null);
        Log.d("THE TOKEN", "TOKEN = " + accessIDToken);

        userPool.getUser();
        CognitoJWTParser parser = new CognitoJWTParser();
        JSONObject payload = parser.getPayload(accessIDToken);
        Log.d("payload", "PAYLOAD = " + payload.toString());
*/



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

    @Override
    public void onResume(){
        super.onResume();
        the_users_posts = null;
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                AWSConfiguration.AMAZON_COGNITO_IDENTITY_POOL_ID,
                AWSConfiguration.AMAZON_COGNITO_REGION // Region
        );
        DriveBoardDataBaseInterface dbi = new DriveBoardDataBaseInterface(credentialsProvider);
        the_users_posts = new ArrayList<>(dbi.get_posts());
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
            Intent intent = new Intent(UserProfile.this, MainActivity.class);
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
