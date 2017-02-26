package com.g13.shotgun.DriveBoard;

import android.content.Intent;
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
import com.g13.shotgun.UserProfile;

import java.util.ArrayList;

public class DriveBoard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

  ArrayList<DriveBoardPost> posts;
    ArrayList<DriveBoardPost> d_posts;

    public void updateList(ArrayList<DriveBoardPost> posts){
        ArrayAdapter<DriveBoardPost> postAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, posts);
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

                updateList(posts);
            }
            if (resultCode == CreateDriveBoardPostActivity.RESULT_CANCELED) {
                ;
            }
        }
    }

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "us-west-2:7252aed7-1cdf-439f-a16a-a97ef8ca7697", // Identity Pool ID
                Regions.US_WEST_2 // Region
        );
        DriveBoardDataBaseInterface dbi = new DriveBoardDataBaseInterface(credentialsProvider);
        d_posts = new ArrayList<>(dbi.get_posts());
        //
        android.os.SystemClock.sleep(1000);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.list);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        posts = new ArrayList<>();
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

        for(int i = 0; i < d_posts.size(); i++){
            if(!posts.contains(d_posts.get(i)))
                posts.add(d_posts.get(i));
        }
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


}
