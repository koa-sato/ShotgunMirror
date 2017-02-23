package com.g13.shotgun;

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

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import static com.g13.shotgun.R.id.rideboard;

public class RideBoard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Post> posts;
    ArrayAdapter<Post> postAdapter;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {

        if (requestCode == 1) {
            if(resultCode == RideBoard.RESULT_OK){
                Post p = (Post)i.getSerializableExtra("the_new_post");
                if (p != null)
                    posts.add(p);
                postAdapter.notifyDataSetChanged();
            }
            if (resultCode == CreatePostActivity.RESULT_CANCELED) {
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
        postAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, posts);
        listView.setAdapter(postAdapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CreatePostActivity.class);
                startActivityForResult(i, 1);
            }
        });



        posts.add(new Post("Santa Barbara", new Date (2017, 2, 16),
                new Time(12, 12, 12), "FirstName LastName"));
        posts.add(new Post("Goleta", new Date (2000, 1, 1),
                new Time (1, 1, 1), "Bob Smith"));
        posts.add(new Post("San Francisco", new Date(1990, 3,   4),
                new Time (5, 5, 5), "Bob Ross"));
        posts.add(new Post("San Francisco", new Date(1990, 3, 4),
                new Time (5, 5, 5), "Bob Ross"));
        posts.add(new Post("San Francisco", new Date(1990, 3, 4),
                new Time (5, 5, 5), "Bob Ross"));
        posts.add(new Post("San Francisco", new Date(1990, 3, 4),
                new Time (5, 5, 5), "Bob Ross"));
        posts.add(new Post("San Francisco", new Date(1990, 3, 4),
                new Time (5, 5, 5), "Bob Ross"));
        posts.add(new Post("San Francisco", new Date(1990, 3, 4),
                new Time (5, 5, 5), "Bob Ross"));
        posts.add(new Post("San Francisco", new Date(1990, 3, 4),
                new Time (5, 5, 5), "Bob Ross"));
        posts.add(new Post("San Francisco", new Date(1990, 3, 4),
                new Time (5, 5, 5), "Bob Ross"));
        posts.add(new Post("San Francisco", new Date(1990, 3, 4),
                new Time (5, 5, 5), "Bob Ross"));
        posts.add(new Post("San Francisco", new Date(1990, 3, 4),
                new Time (5, 5, 5), "Bob Ross"));
        posts.add(new Post("San Francisco", new Date(1990, 3, 4),
                new Time (5, 5, 5), "Bob Ross"));
        posts.add(new Post("San Francisco", new Date(1990, 3, 4),
                new Time (5, 5, 5), "Bob Ross"));
        posts.add(new Post("San Francisco", new Date(1990, 3, 4),
                new Time (5, 5, 5), "Bob Ross"));
        posts.add(new Post("San Francisco", new Date(1990, 3, 4),
                new Time (5, 5, 5), "Bob Ross"));
        posts.add(new Post("San Francisco", new Date(1990, 3, 4),
                new Time (5, 5, 5), "Bob Ross"));
        posts.add(new Post("San Francisco", new Date(1990, 3, 4),
                new Time (5, 5, 5), "Bob Ross"));
        posts.add(new Post("San Francisco", new Date(1990, 3, 4),
                new Time (5, 5, 5), "Bob Ross"));
        posts.add(new Post("San Francisco", new Date(1990, 3, 4),
                new Time (5, 5, 5), "Bob Ross"));
        posts.add(new Post("San Francisco", new Date(1990, 3, 4),
                new Time (5, 5, 5), "Bob Ross"));
        posts.add(new Post("Last", new Date(1990, 3, 4),
                new Time (5, 5, 5), "Bob Ross"));

        postAdapter.notifyDataSetChanged();

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
            Intent intent = new Intent(RideBoard.this, DriveBoard.class);
            startActivity(intent);
        } else if (id == rideboard) {
            ;
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


}
