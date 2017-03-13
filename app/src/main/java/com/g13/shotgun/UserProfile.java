package com.g13.shotgun;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.AWSConfiguration;
import com.amazonaws.mobile.user.signin.CognitoUserPoolsSignInProvider;
import com.g13.shotgun.driveboard.DriveBoard;
import com.g13.shotgun.driveboard.DriveBoardDataBaseInterface;
import com.g13.shotgun.driveboard.DriveBoardPost;
import com.g13.shotgun.rideboard.RideBoard;
import com.g13.shotgun.sendbird.MainActivity;
import com.g13.shotgun.signIn.Shotgun;

import java.util.ArrayList;

public class UserProfile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //private User Fred;
    ArrayList<DriveBoardPost> the_users_posts;
    ArrayList<DriveBoardPost> post;
    ArrayList<DriveBoardPost> confirmed;
    ListView the_list;
    ListView the_confirmed_list;
    ArrayAdapter<DriveBoardPost> arrayAdapter;
    ArrayAdapter<DriveBoardPost> carrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        User.getInstance().Update(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        the_list = (ListView) findViewById(R.id.list);
        the_confirmed_list = (ListView) findViewById(R.id.confirmed);

        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                AWSConfiguration.AMAZON_COGNITO_IDENTITY_POOL_ID,
                AWSConfiguration.AMAZON_COGNITO_REGION // Region
        );
        final DriveBoardDataBaseInterface dbi = new DriveBoardDataBaseInterface(credentialsProvider);
         the_users_posts = new ArrayList<>(dbi.get_posts());


        final TextView noPosts = (TextView) findViewById(R.id.textView11);
        String noPostsMsg = "You have not made any posts!";
        noPosts.setText(noPostsMsg);

        final TextView postLabel = (TextView) findViewById(R.id.my_posts);
        String postsLabelMsg = "My Posts: ";
        postLabel.setText(postsLabelMsg);
        if(post == null) {
            post = new ArrayList<>();
            confirmed = new ArrayList<>();
            for (int i = 0; i < the_users_posts.size(); i++)
                if (!confirmed.contains(the_users_posts.get(i)) && the_users_posts.get(i).get_going_users() != null && the_users_posts.get(i).get_going_users().contains(User.getInstance().getUsername()))
                    confirmed.add(the_users_posts.get(i));
            for (int i = 0; i < the_users_posts.size(); i++)
                if (!post.contains(the_users_posts.get(i)) && the_users_posts.get(i).get_user().equals(User.getInstance().getUsername()))
                    post.add(the_users_posts.get(i));
        }
        if(post.size() != 0)
            noPosts.setVisibility(View.GONE);
        else
            postLabel.setVisibility(View.GONE);

        final Context c = getApplicationContext();

        arrayAdapter =
                        new ArrayAdapter<DriveBoardPost>(this, android.R.layout.simple_list_item_1, post);
        carrayAdapter =
                new ArrayAdapter<DriveBoardPost>(this, android.R.layout.simple_list_item_1, confirmed);
        the_list.setAdapter(arrayAdapter);
        the_confirmed_list.setAdapter(carrayAdapter);
        the_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User.getInstance().setD_highlighted_post(post.get(position));
                Intent i = new Intent(getApplicationContext(), EditPostActivity.class);
                i.putExtra("Position", position);
                startActivityForResult(i, 4);
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
                "\nName: " + User.getInstance().getFirstName() + " " + User.getInstance().getLastName()
        + "\n\n" + "Username: " + User.getInstance().getUsername() + "\n");

        final TextView email1 = (TextView) findViewById(R.id.textView9);
        //email1.setText(User.getInstance().displayConnections());
               // "Email: " +  User.getInstance().getEmail());
        String posts = "";
        //for(int i = 0; i < the_users_posts.size();i++)
          //  posts = posts + the_users_posts.get(i) + '\n';
        posts = posts + User.getInstance().displayConnections();
        email1.setText("Email: " + User.getInstance().getEmail() + "\n");
        final TextView phone = (TextView) findViewById(R.id.textView10);
        phone.setText("Phone Number: " + User.getInstance().getPhoneNumber() + "\n");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences prefs = getSharedPreferences("com.amazonaws.android.auth", Shotgun.MODE_PRIVATE);
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
        if(id == R.id.logout){
            CognitoUserPoolsSignInProvider signInProvider = new CognitoUserPoolsSignInProvider(getApplicationContext());
            signInProvider.signOut();
            Intent intent = new Intent(UserProfile.this, Shotgun.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        super.onResume();
        /*CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                AWSConfiguration.AMAZON_COGNITO_IDENTITY_POOL_ID,
                AWSConfiguration.AMAZON_COGNITO_REGION // Region
        );
        final DriveBoardDataBaseInterface dbi = new DriveBoardDataBaseInterface(credentialsProvider);
        the_users_posts = new ArrayList<>(dbi.get_posts());
        for(int j = 0; j < the_users_posts.size();j++)
            if(the_users_posts.get(j).get_going_users()!=null && the_users_posts.get(j).get_going_users().contains(User.getInstance().getUsername()))
                confirmed.add(the_users_posts.get(j));
        for(int k = 0; k < the_users_posts.size(); k++)
            if(the_users_posts.get(k).get_user().equals(User.getInstance().getUsername()))
                post.add(the_users_posts.get(k));

        arrayAdapter =
                new ArrayAdapter<DriveBoardPost>(getApplicationContext(), android.R.layout.simple_list_item_1, post);
        carrayAdapter =
                new ArrayAdapter<DriveBoardPost>(getApplicationContext(), android.R.layout.simple_list_item_1, confirmed);
*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean deleted = data.getBooleanExtra("Deleted", false);
        if(!deleted)
            return;

        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                AWSConfiguration.AMAZON_COGNITO_IDENTITY_POOL_ID,
                AWSConfiguration.AMAZON_COGNITO_REGION // Region
        );
        final DriveBoardDataBaseInterface dbi = new DriveBoardDataBaseInterface(credentialsProvider);
        ArrayList<DriveBoardPost> newList = new ArrayList<>(dbi.get_posts());
        //the_users_posts = new ArrayList<>(dbi.get_posts());
        //the_users_posts.set(0, new DriveBoardPost("QS", 1, 1, 1, 1, 1, 1, "string,", 1, "String"));
        String x = "";
        //the_users_posts = new ArrayList<>();
        x = newList.get(0).toString();
        Log.d("UserProfile", x);
        if(the_list == null) {
            for (int i = 0; i < newList.size(); i++)
                if (!confirmed.contains(newList.get(i)) && newList.get(i).get_going_users() != null && newList.get(i).get_going_users().contains(User.getInstance().getUsername()))
                    confirmed.add(newList.get(i));
            for (int i = 0; i < newList.size(); i++)
                if (!post.contains(newList.get(i)) && newList.get(i).get_user().equals(User.getInstance().getUsername()))
                    post.add(newList.get(i));
        }
        post.remove(resultCode);
        if (post.isEmpty())
            x = "null";
        else
            x = post.get(0).toString();

        Log.d("UserProfile", x);

        the_list = (ListView) findViewById(R.id.list);
        the_confirmed_list = (ListView) findViewById(R.id.confirmed);

        arrayAdapter =
                new ArrayAdapter<DriveBoardPost>(this, android.R.layout.simple_list_item_1, android.R.id.text1, post);
        carrayAdapter =
                new ArrayAdapter<DriveBoardPost>(this, android.R.layout.simple_list_item_1, android.R.id.text1, confirmed);
        the_list.setAdapter(arrayAdapter);
        the_confirmed_list.setAdapter(carrayAdapter);
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


        } else if (id == R.id.notification) {
            Intent intent = new Intent(UserProfile.this, NotificationViewActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
