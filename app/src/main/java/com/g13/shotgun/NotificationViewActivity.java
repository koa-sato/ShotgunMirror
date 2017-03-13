package com.g13.shotgun;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.g13.shotgun.driveboard.DriveBoard;
import com.g13.shotgun.rideboard.RideBoard;
import com.g13.shotgun.sendbird.MainActivity;

public class NotificationViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_view);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView textView = (TextView) findViewById(R.id.studd);
        if(User.getInstance().getNotifications() != null) {
            String s = "";
            for(int i = 0; i < User.getInstance().getNotifications().size(); i++)
                s = s + User.getInstance().getNotifications().toString() + " ";
                s = s.replace('[', ' ');
                s = s.replace(']', ' ');
            textView.setText(s.replace(',', ' '));
        }
        else
            textView.setText("NULL");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        toolbar.setTitle("Messenger");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem menuItem) {
                // Handle navigation view item clicks here.
                int id = menuItem.getItemId();

                if (id == R.id.driveboard) {
                    Intent intent = new Intent(NotificationViewActivity.this, DriveBoard.class);
                    startActivity(intent);
                } else if (id == R.id.rideboard) {
                    Intent intent = new Intent(NotificationViewActivity.this, RideBoard.class);
                    startActivity(intent);
                } else if (id == R.id.messenger) {
                    Intent intent = new Intent(NotificationViewActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (id == R.id.profile) {
                    Intent intent = new Intent(NotificationViewActivity.this, UserProfile.class);
                    startActivity(intent);
                } else if (id == R.id.notification) {

                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }

        });
    }

}
