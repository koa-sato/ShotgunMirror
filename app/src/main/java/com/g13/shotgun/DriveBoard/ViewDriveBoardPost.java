package com.g13.shotgun.DriveBoard;

import android.content.SharedPreferences;
import android.support.v4.util.AtomicFile;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.g13.shotgun.Post;
import com.g13.shotgun.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class ViewDriveBoardPost extends AppCompatActivity {

    TextView date;
    TextView city;
    TextView num;
    TextView user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drive_board_post);
        Bundle b = getIntent().getExtras();
        SharedPreferences pref = getSharedPreferences("The_post", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = pref.getString("THE_POST", null);
        Type type = new TypeToken<DriveBoardPost>(){

        }.getType();
        DriveBoardPost p = gson.fromJson(json, type);

        user = (TextView) findViewById(R.id.name);
        date = (TextView) findViewById(R.id.date);
        num = (TextView) findViewById(R.id.num);
        city = (TextView) findViewById(R.id.city);
        user.setText("User: " + p.get_user());
        date.setText("Date: " + p.date_to_string());
        city.setText("City: " + p.get_city());
        num.setText("Number of Seats: " + Integer.toString(p.get_num_seats()));


    }
}
