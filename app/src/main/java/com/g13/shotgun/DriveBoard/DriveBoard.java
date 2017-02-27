package com.g13.shotgun.DriveBoard;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.g13.shotgun.Messenger;
import com.g13.shotgun.R;
import com.g13.shotgun.RideBoard.RideBoard;
import com.g13.shotgun.SignIn.SignInActivity;
import com.g13.shotgun.UserProfile;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DriveBoard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

  ArrayList<DriveBoardPost> posts;
    ArrayList<DriveBoardPost> d_posts;

    public void updateList(ArrayList<DriveBoardPost> posts){
        ArrayAdapter<DriveBoardPost> postAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, posts);
       // DriveBoardAdapter postAdapter = new DriveBoardAdapter(this,
          //      android.R.layout.simple_list_item_1, android.R.id.text1, posts);
        listView.setAdapter(postAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {

        if (requestCode == 1) {
            if(resultCode == DriveBoard.RESULT_OK){
                DriveBoardPost p = (DriveBoardPost)(i.getSerializableExtra("the_new_post"));
                if (p != null) {
                    posts.add(p);
                    CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                            getApplicationContext(),
                            "us-west-2:7252aed7-1cdf-439f-a16a-a97ef8ca7697", // Identity Pool ID
                            Regions.US_WEST_2 // Region
                    );
                    DriveBoardDataBaseInterface dbi = new DriveBoardDataBaseInterface(credentialsProvider);
                    dbi.push_post(p);
                }

                orderPosts();
                updateList(posts);
            }
            if (resultCode == CreateDriveBoardPostActivity.RESULT_CANCELED) {
                ;
            }
        }
    }

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "us-west-2:7252aed7-1cdf-439f-a16a-a97ef8ca7697", // Identity Pool ID
                Regions.US_WEST_2 // Region
        );
        DriveBoardDataBaseInterface dbi = new DriveBoardDataBaseInterface(credentialsProvider);
        SharedPreferences sharedPrefs = getSharedPreferences("DriveBoardPosts", MODE_PRIVATE);
        final Gson gson = new Gson();
        String json = sharedPrefs.getString("DriveBoardPostList", null);
        Type type = new TypeToken<ArrayList<DriveBoardPost>>() {
        }.getType();
        posts = gson.fromJson(json, type);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.getString("parent class").equals(SignInActivity.class.toString()) || posts == null) {
            d_posts = new ArrayList<>(dbi.get_posts());
            posts = new ArrayList<>();
            for (int i = 0; i < d_posts.size(); i++) {
                if (!posts.contains(d_posts.get(i)))
                    posts.add(d_posts.get(i));
            }
        }




        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.list);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CreateDriveBoardPostActivity.class);
                startActivityForResult(i, 1);
            }
        });

       /* FloatingActionButton rfab = (FloatingActionButton) findViewById(R.id.rfab);
        rfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < d_posts.size(); i++){
                    if(!posts.contains(d_posts.get(i)))
                        posts.add(d_posts.get(i));
                }

                updateList(posts);
            }
        });*/

        orderPosts();
        updateList(posts);


        final ArrayList<Integer> id_array= new ArrayList<Integer>();
        for(int i = 0; i < posts.size(); i++)
            id_array.add(Integer.parseInt(posts.get(i).get_key()));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //id = id_array.get(position);
                // ListView Clicked item value
                /*String  itemValue    = (String) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :"+position+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();
                        */
                Intent i = new Intent(getApplicationContext(), ViewDriveBoardPost.class);
                SharedPreferences prefs = getSharedPreferences("The_post", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(posts.get(position));
                editor.putString("THE_POST", json);
                editor.commit();
                startActivity(i);


            }

        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

        } else if (id == R.id.rideboard) {
            Intent intent = new Intent(DriveBoard.this, RideBoard.class);
            startActivity(intent);
        } else if (id == R.id.messenger) {
            Intent intent = new Intent(DriveBoard.this, Messenger.class);
            startActivity(intent);
        } else if (id == R.id.profile) {
            Intent intent = new Intent(DriveBoard.this, UserProfile.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void orderPosts() {
        Collections.sort(posts, new Comparator<DriveBoardPost>() {
            @Override
            public int compare(DriveBoardPost o1, DriveBoardPost o2) {
                if (o1.get_year() == o2.get_year()) {
                    if (o1.get_month() == o2.get_month()) {
                        return o2.get_day() - o1.get_day();
                    }
                    else {
                        return o2.get_month() - o1.get_month();
                    }
                }
                else {
                    return o2.get_year() - o1.get_year();
                }
            }
        });
        updateList(posts);
    }

    @Override
    public void onStop() {

        SharedPreferences prefs = getSharedPreferences("DriveBoardPosts", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(posts);
        editor.putString("DriveBoardPostList", json);
        editor.commit();

        super.onStop();
    }
}