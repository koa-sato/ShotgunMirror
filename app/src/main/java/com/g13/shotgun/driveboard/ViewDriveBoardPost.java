package com.g13.shotgun.driveboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.AWSConfiguration;
import com.g13.shotgun.DatabaseUser;
import com.g13.shotgun.R;
import com.g13.shotgun.User;
import com.g13.shotgun.UserDatabaseInterface;
import com.g13.shotgun.sendbird.MainActivity;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sendbird.android.GroupChannel;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;




public class ViewDriveBoardPost extends AppCompatActivity {

    TextView date;
    TextView city;
    TextView num;
    TextView buser;
    Button interested;
    ArrayList<String> USER_IDS;
    DriveBoardPost p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drive_board_post);
        SendBird.connect(User.getInstance().getUsername(), new SendBird.ConnectHandler() {
            @Override
            public void onConnected(com.sendbird.android.User user, SendBirdException e) {
                if (e != null) {
                    //Toast.makeText(MainActivity.this, "" + e.getCode() + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    //setState(MainActivity.State.DISCONNECTED);
                    return;
                }}});
        Bundle b = getIntent().getExtras();
        SharedPreferences pref = getSharedPreferences("The_post", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = pref.getString("THE_POST", null);
        Type type = new TypeToken<DriveBoardPost>(){

        }.getType();
         p = gson.fromJson(json, type);

        buser = (TextView) findViewById(R.id.name);
        date = (TextView) findViewById(R.id.date);
        num = (TextView) findViewById(R.id.num);
        city = (TextView) findViewById(R.id.city);
        interested = (Button) findViewById(R.id.interested);
        buser.setText(p.get_user());
       // user.setText("User: " + p.get_user());
        date.setText("Date: " + p.date_to_string());
        city.setText("City: " + p.get_city());
        num.setText("Number of Seats: " + Integer.toString(p.get_num_seats()));
        if(p.get_user().equals(User.getInstance().getUsername()) || p.get_interested_users().contains(User.getInstance().getUsername()))
            interested.setVisibility(View.INVISIBLE);

        interested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                        getApplicationContext(),
                        AWSConfiguration.AMAZON_COGNITO_IDENTITY_POOL_ID,
                        AWSConfiguration.AMAZON_COGNITO_REGION // Region
                );
                UserDatabaseInterface udbi = new UserDatabaseInterface(credentialsProvider);
                DatabaseUser the_user = udbi.get_user(p.get_user());
                the_user.addConnection(User.getInstance().getUsername());
                //User.getInstance().addConnection(p.get_user());
                udbi.push_user(the_user);
                udbi.push_user(new DatabaseUser(User.getInstance()));
                USER_IDS = new ArrayList<String>();
                USER_IDS.add(User.getInstance().getUsername());
                USER_IDS.add(p.get_user());
                p.add_interested_user(User.getInstance().getUsername());
                DriveBoardDataBaseInterface dbi = new DriveBoardDataBaseInterface(credentialsProvider);
                dbi.push_post(p);
                GroupChannel.createChannelWithUserIds(USER_IDS, true, p.get_user() + " " + p.get_city(), null, null, new GroupChannel.GroupChannelCreateHandler() {
                    @Override
                    public void onResult(GroupChannel groupChannel, SendBirdException e) {
                        if (e != null) {
                            Toast.makeText(getApplicationContext(), "" + e.getCode() + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }


                    }

                });


                interested.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Check your Messenger App to Chat with the driver", Toast.LENGTH_LONG ).show();
             //   setState(MainActivity.State.CONNECTING);
            }
        });
    }
}
