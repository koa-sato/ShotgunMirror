package com.g13.shotgun.RideBoard;

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
import android.widget.SearchView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.AWSConfiguration;
import com.amazonaws.regions.Regions;
import com.g13.shotgun.DriveBoard.DriveBoard;
import com.g13.shotgun.Messenger;
import com.g13.shotgun.R;
import com.g13.shotgun.UserProfile;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RideBoard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<RideBoardPost> posts;
    ArrayList<RideBoardPost> d_posts;
    ArrayAdapter<RideBoardPost> postAdapter;

    public void updateList(ArrayList<RideBoardPost> posts){
        postAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, posts);
        listView.setAdapter(postAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {

        if (requestCode == 1) {
            if(resultCode == RideBoard.RESULT_OK){
                RideBoardPost p = (RideBoardPost) i.getSerializableExtra("the_new_post");
                if (p != null) {
                    posts.add(p);
                    CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                            getApplicationContext(),
                            AWSConfiguration.AMAZON_COGNITO_IDENTITY_POOL_ID,
                            AWSConfiguration.AMAZON_COGNITO_REGION // Region
                    );
                    RideBoardDataBaseInterface dbi = new RideBoardDataBaseInterface(credentialsProvider);
                    dbi.push_post(p);
                }

                orderPosts(posts);
                updateList(posts);
            }
            if (resultCode == CreateRideBoardPostActivity.RESULT_CANCELED) {
                ;
            }
        }
    }

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                //xxxx
                // "us-west-2:7252aed7-1cdf-439f-a16a-a97ef8ca7697", // Identity Pool ID
                AWSConfiguration.AMAZON_COGNITO_IDENTITY_POOL_ID,
                AWSConfiguration.AMAZON_COGNITO_REGION// Region
        );
        RideBoardDataBaseInterface dbi = new RideBoardDataBaseInterface(credentialsProvider);
        SharedPreferences sharedPrefs = getSharedPreferences("RideBoardPosts", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("RideBoardPostList", null);
        Type type = new TypeToken<ArrayList<RideBoardPost>>() {
        }.getType();
        posts = gson.fromJson(json, type);

//xxx
        if (posts == null || !equalLists(posts, d_posts)) {
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
                Intent i = new Intent(getApplicationContext(), CreateRideBoardPostActivity.class);
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


        orderPosts(posts);
        updateList(posts);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item value
                /*String  itemValue    = (String) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :"+position+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();
                        */
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
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit (String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                postAdapter.getFilter().filter(newText);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
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
            Intent intent = new Intent(RideBoard.this, DriveBoard.class);
            startActivity(intent);
        } else if (id == R.id.rideboard) {

        } else if (id == R.id.messenger) {
            Intent intent = new Intent(RideBoard.this, Messenger.class);
            startActivity(intent);
        } else if (id == R.id.profile) {
            Intent intent = new Intent(RideBoard.this, UserProfile.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void orderPosts(ArrayList<RideBoardPost> posts) {
        Collections.sort(posts, new Comparator<RideBoardPost>() {
            @Override
            public int compare(RideBoardPost o1, RideBoardPost o2) {
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
    }

    public  boolean equalLists(ArrayList<RideBoardPost> list1, ArrayList<RideBoardPost> list2){
        if (list1 == null && list2 == null){
            return true;
        }

        if((list1 == null && list2 != null)
                || list1 != null && list2 == null
                || list1.size() != list2.size()){
            return false;
        }

        orderPosts(list1);
        orderPosts(list2);
        return list1.equals(list2);
    }

    @Override
    public void onStop() {

        SharedPreferences prefs = getSharedPreferences("RideBoardPosts", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(posts);
        editor.putString("RideBoardPostList", json);
        editor.commit();

        super.onStop();
    }
}