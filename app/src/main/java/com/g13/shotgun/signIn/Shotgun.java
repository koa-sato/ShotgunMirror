package com.g13.shotgun.signIn;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.AWSConfiguration;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobile.user.IdentityManager;
import com.amazonaws.mobile.user.IdentityProvider;
import com.amazonaws.mobile.user.signin.CognitoUserPoolsSignInProvider;
import com.amazonaws.mobile.user.signin.SignInManager;
import com.g13.shotgun.DatabaseUser;
import com.g13.shotgun.Opening_Screen;
import com.g13.shotgun.R;
import com.g13.shotgun.UserDatabaseInterface;
import com.sendbird.android.SendBird;


public class Shotgun extends Activity {
    private static final String LOG_TAG = Shotgun.class.getSimpleName();
    private SignInManager signInManager;

    private static final String appId = "3A4B37C7-A3CA-4112-B12E-83D89F99B3DB";
    /** Permission Request Code (Must be < 256). */
    private static final int GET_ACCOUNTS_PERMISSION_REQUEST_CODE = 93;

    /** The Google OnClick listener, since we must override it to get permissions on Marshmallow and above. */
    private View.OnClickListener googleOnClickListener;

    /**
     * SignInResultsHandler handles the final result from sign in. Making it static is a best
     * practice since it may outlive the SplashActivity's life span.
     */
    private class SignInResultsHandler implements IdentityManager.SignInResultsHandler {
        /**
         * Receives the successful sign-in result and starts the main activity.
         * @param provider the identity provider used for sign-in.
         */
        @Override
        public void onSuccess(final IdentityProvider provider) {
            SendBird.init(appId, getApplicationContext());
            Log.d(LOG_TAG, String.format("User sign-in with %s succeeded",
                    provider.getDisplayName()));

            CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                    getApplicationContext(),
                    //"us-west-2:7252aed7-1cdf-439f-a16a-a97ef8ca7697", // Identity Pool ID
                    AWSConfiguration.AMAZON_COGNITO_IDENTITY_POOL_ID,
                    AWSConfiguration.AMAZON_COGNITO_REGION // Region
            );
            UserDatabaseInterface udbi = new UserDatabaseInterface(credentialsProvider);
            DatabaseUser u = udbi.get_user(provider.getUserName());
            com.g13.shotgun.User.getInstance(u);
           // User.getInstance(udbi.get_user(provider.getUserName()));
            // The sign-in manager is no longer needed once signed in.
            SignInManager.dispose();


            Toast.makeText(Shotgun.this, "Sign-in succeeded.", Toast.LENGTH_LONG).show();

            // Load user name and image.
            AWSMobileClient.defaultMobileClient()
                    .getIdentityManager().loadUserInfoAndImage(provider, new Runnable() {
                @Override
                public void run() {
                    Log.d(LOG_TAG, "Launching Main Activity...");
                    Intent i = new Intent(Shotgun.this, Opening_Screen.class);
                    i.putExtra("parent class", Shotgun.class.toString());
                    startActivity(i
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    // finish should always be called on the main thread.
                    finish();
                }
            });
        }

        /**
         * Receives the sign-in result indicating the user canceled and shows a toast.
         * @param provider the identity provider with which the user attempted sign-in.
         */
        @Override
        public void onCancel(final IdentityProvider provider) {
            Log.d(LOG_TAG, String.format("User sign-in with %s canceled.",
                    provider.getDisplayName()));

            Toast.makeText(Shotgun.this, String.format("Sign-in with %s canceled.",
                    provider.getDisplayName()), Toast.LENGTH_LONG).show();
        }

        /**
         * Receives the sign-in result that an error occurred signing in and shows a toast.
         * @param provider the identity provider with which the user attempted sign-in.
         * @param ex the exception that occurred.
         */
        @Override
        public void onError(final IdentityProvider provider, final Exception ex) {
            Log.e(LOG_TAG, String.format("User Sign-in failed for %s : %s",
                    provider.getDisplayName(), ex.getMessage()), ex);

            final AlertDialog.Builder errorDialogBuilder = new AlertDialog.Builder(Shotgun.this);
            errorDialogBuilder.setTitle("Sign-In Error");
            errorDialogBuilder.setMessage(
                    String.format("Sign-in with %s failed.\n%s", provider.getDisplayName(), ex.getMessage()));
            errorDialogBuilder.setNeutralButton("Ok", null);
            errorDialogBuilder.show();
        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signInManager = SignInManager.getInstance(this);

        signInManager.setResultsHandler(this, new SignInResultsHandler());

        // Initialize sign-in buttons.
        signInManager.initializeSignInButton(CognitoUserPoolsSignInProvider.class,
                this.findViewById(R.id.signIn_imageButton_login));
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        signInManager.handleActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
    }
}
