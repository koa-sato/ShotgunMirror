package com.g13.shotgun.rideboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.g13.shotgun.R;
import com.g13.shotgun.User;
import com.g13.shotgun.UserProfile;
import com.g13.shotgun.driveboard.DriveBoard;
import com.g13.shotgun.sendbird.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sendbird.android.GroupChannel;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ViewRideBoardPost extends AppCompatActivity {

    TextView beginning_date;
    TextView end_date;
    TextView city;
    TextView user;
    Button interested;
    ArrayList<String> USER_IDS;
    RideBoardPost p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ride_board_post);
        SharedPreferences pref = getSharedPreferences("The_post", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = pref.getString("THE_POST", null);
        Type type = new TypeToken<com.g13.shotgun.rideboard.RideBoardPost>(){

        }.getType();
        p = gson.fromJson(json, type);

        user = (TextView) findViewById(R.id.name);
        beginning_date = (TextView) findViewById(R.id.beginning_date);
        end_date = (TextView) findViewById(R.id.end_date);
        city = (TextView) findViewById(R.id.city);
        interested = (Button) findViewById(R.id.interested);
        user.setText(p.get_user());
        beginning_date.setText("From: " + p.beginning_date_to_string());
        end_date.setText("Until: " + p.end_date_to_string());
        city.setText("City: " + p.get_city());
        if(p.get_user().equals(User.getInstance().getUsername()))
            interested.setVisibility(View.INVISIBLE);

        interested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                USER_IDS = new ArrayList<String>();
                USER_IDS.add(User.getInstance().getUsername());
                USER_IDS.add(p.get_user());

                SendBird.connect(User.getInstance().getUsername(), new SendBird.ConnectHandler() {
                    @Override
                    public void onConnected(com.sendbird.android.User user, SendBirdException e) {
                        if (e != null) {
                            // Error.
                            Toast.makeText(getApplicationContext(), "" + e.getCode() + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;

                        }
                    }
                });

                GroupChannel.createChannelWithUserIds(USER_IDS, true, p.get_user(), null, null, new GroupChannel.GroupChannelCreateHandler() {
                    @Override
                    public void onResult(GroupChannel groupChannel, SendBirdException e) {
                        if (e != null) {
                            Toast.makeText(getApplicationContext(), "" + e.getCode() + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        //mAdapter.replace(groupChannel);
                    }

                });


                SendBird.disconnect(new SendBird.DisconnectHandler() {
                    @Override
                    public void onDisconnected() {
                        // You are disconnected from SendBird.
                    }
                });

               /* GroupChannel.createChannelWithUserIds(USER_IDS, true, new GroupChannel.GroupChannelCreateHandler() {
                    @Override
                    public void onResult(GroupChannel groupChannel, SendBirdException e) {
                        if (e != null) {
                            // Error.
                            return;
                        }
                    }
                });*/
                interested.setText(USER_IDS.get(0) + " " + USER_IDS.get(1));
            }
        });
    }
}