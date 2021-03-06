package com.g13.shotgun.sendbird;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;


import com.g13.shotgun.Messenger;
import com.g13.shotgun.NotificationViewActivity;
import com.g13.shotgun.R;
import com.g13.shotgun.UserProfile;
import com.g13.shotgun.driveboard.DriveBoard;
import com.g13.shotgun.rideboard.RideBoard;
import com.google.firebase.iid.FirebaseInstanceId;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;

/**
 * SendBird Android Sample UI
 */
public class MainActivity extends FragmentActivity {
    public static String VERSION = "3.0.18.0";

    private enum State {DISCONNECTED, CONNECTING, CONNECTED}

    /**
     * To test push notifications with your own appId, you should replace google-services.json with yours.
     * Also you need to set Server API Token and Sender ID in SendBird dashboard.
     * Please carefully read "Push notifications" section in SendBird Android documentation
     */
    private static final String appId = "3A4B37C7-A3CA-4112-B12E-83D89F99B3DB"; /* Sample SendBird Application */

    public static String sUserId = com.g13.shotgun.User.getInstance().getUsername();
    private String mNickname = com.g13.shotgun.User.getInstance().getUsername();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
                    Intent intent = new Intent(MainActivity.this, DriveBoard.class);
                    startActivity(intent);
                } else if (id == R.id.rideboard) {
                    Intent intent = new Intent(MainActivity.this, RideBoard.class);
                    startActivity(intent);
                } else if (id == R.id.messenger) {

                } else if (id == R.id.profile) {
                    Intent intent = new Intent(MainActivity.this, UserProfile.class);
                    startActivity(intent);
                } else if (id == R.id.notification) {
                    Intent intent = new Intent(MainActivity.this, NotificationViewActivity.class);
                    startActivity(intent);
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }

        });

        // sUserId = getPreferences(Context.MODE_PRIVATE).getString("user_id", "");
        //mNickname = getPreferences(Context.MODE_PRIVATE).getString("nickname", "");

        SendBird.init(appId, this);

        /*((EditText) findViewById(R.id.etxt_user_id)).setText(sUserId);
        ((EditText) findViewById(R.id.etxt_user_id)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //sUserId = s.toString();
            }
        });

        ((TextInputEditText) findViewById(R.id.etxt_nickname)).setText(mNickname);
        ((TextInputEditText) findViewById(R.id.etxt_nickname)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //mNickname = s.toString();
            }
        });
*/
        findViewById(R.id.btn_connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btn = (Button) view;
                //btn.setText(sUserId + " " + mNickname );
                if (btn.getText().equals("Connect")) {
                    connect();
                } else {
                    disconnect();
                }

                Helper.hideKeyboard(MainActivity.this);
            }
        });

       /* findViewById(R.id.btn_open_channel_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SendBirdOpenChannelListActivity.class);
                startActivity(intent);
            }
        });*/

        findViewById(R.id.btn_group_channel_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SendBirdGroupChannelListActivity.class);
                startActivity(intent);
            }
        });

        TextView versionText = (TextView) findViewById(R.id.txt_sendbird_version);
        versionText.setText("1");

        setState(State.DISCONNECTED);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SendBird.disconnect(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /**
         * If the minimum SDK version you support is under Android 4.0,
         * you MUST uncomment the below code to receive push notifications.
         */
//        SendBird.notifyActivityResumedForOldAndroids();
    }

    @Override
    protected void onPause() {
        super.onPause();
        /**
         * If the minimum SDK version you support is under Android 4.0,
         * you MUST uncomment the below code to receive push notifications.
         */
//        SendBird.notifyActivityPausedForOldAndroids();
    }

    private void setState(State state) {
        switch (state) {
            case DISCONNECTED:
                ((Button) findViewById(R.id.btn_connect)).setText("Connect");
                findViewById(R.id.btn_connect).setEnabled(true);
                // findViewById(R.id.btn_open_channel_list).setEnabled(false);
                findViewById(R.id.btn_group_channel_list).setEnabled(false);
                break;

            case CONNECTING:
                ((Button) findViewById(R.id.btn_connect)).setText("Connecting...");
                findViewById(R.id.btn_connect).setEnabled(false);
                findViewById(R.id.btn_group_channel_list).setEnabled(false);
                break;

            case CONNECTED:
                ((Button) findViewById(R.id.btn_connect)).setText("Disconnect");
                findViewById(R.id.btn_connect).setEnabled(true);
                // findViewById(R.id.btn_open_channel_list).setEnabled(true);
                findViewById(R.id.btn_group_channel_list).setEnabled(true);
                break;
        }
    }

    private void connect() {
        SendBird.connect(sUserId, new SendBird.ConnectHandler() {
            @Override
            public void onConnected(User user, SendBirdException e) {
                if (e != null) {

                    setState(State.DISCONNECTED);
                    return;
                }

                String nickname = mNickname;

                SendBird.updateCurrentUserInfo(nickname, null, new SendBird.UserInfoUpdateHandler() {
                    @Override
                    public void onUpdated(SendBirdException e) {
                        if (e != null) {

                            setState(State.DISCONNECTED);
                            return;
                        }

                        SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
                        editor.putString("user_id", sUserId);
                        editor.putString("nickname", mNickname);
                        editor.commit();

                        setState(State.CONNECTED);
                    }
                });

                if (FirebaseInstanceId.getInstance().getToken() == null) return;

                SendBird.registerPushTokenForCurrentUser(FirebaseInstanceId.getInstance().getToken(), true, new SendBird.RegisterPushTokenWithStatusHandler() {
                    @Override
                    public void onRegistered(SendBird.PushTokenRegistrationStatus pushTokenRegistrationStatus, SendBirdException e) {
                        if (e != null) {

                            return;
                        }
                    }
                });
            }
        });

        setState(State.CONNECTING);
    }

    private void disconnect() {
        SendBird.disconnect(new SendBird.DisconnectHandler() {
            @Override
            public void onDisconnected() {
                setState(State.DISCONNECTED);
            }
        });
    }

}
