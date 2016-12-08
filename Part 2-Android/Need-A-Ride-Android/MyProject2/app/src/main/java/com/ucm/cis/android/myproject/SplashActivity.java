package com.ucm.cis.android.myproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;


public class SplashActivity extends Activity {

    private Intent intent;
    private static final String KEY_USERNAME="username";
    private static final String KEY_PASSWORD="password";
    private String storedUsername, storedPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences prefs = getSharedPreferences("Rider", Context.MODE_PRIVATE);
         storedUsername = prefs.getString(KEY_USERNAME, "");
         storedPassword = prefs.getString(KEY_PASSWORD, ""); //return nothing if no pass saved
      //  Toast.makeText(SplashActivity.this, "Please enter your email address and password-"+storedUsername+" "+storedPassword, Toast.LENGTH_LONG).show();
        if (!storedUsername .isEmpty() || !storedPassword .isEmpty()) {
            intent  = new Intent(this, MainActivity.class);
            //startActivity(intent);
          //  finish();

        }else {
            intent  = new Intent(this, LoginActivity.class);
            //startActivity(intent);
        }

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {

                startActivity(intent);
                finish();
            }
        }, 2000);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
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
}
