package com.ucm.cis.android.myproject;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ucm.cis.android.db.DatabaseHelper;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    Toolbar toolbar;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.app_bar);
        Button b1, b2, b3, b4,b5;
        b1 = (Button) findViewById(R.id.offerBtn);
        b2 = (Button) findViewById(R.id.hitchBtn);
        b3 = (Button) findViewById(R.id.requestBtn);
      //  b4 = (Button) findViewById(R.id.browseBtn);
        b5 = (Button) findViewById(R.id.profileBtn);
        //b5 = ()
        b1.setOnClickListener(this);
        //b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b3.setOnClickListener(this);
        b2.setOnClickListener(this);
        /***********/
        SQLiteDatabase.CursorFactory factory = null;
        db = new DatabaseHelper(this, factory);
        /*************/
    }


    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.offerBtn:
                intent = new Intent(MainActivity.this, OfferRideActivity.class);
                break;

            case R.id.hitchBtn:
                intent = new Intent(MainActivity.this, HitchRideActivity.class);
                break;

            case R.id.requestBtn:
                intent = new Intent(MainActivity.this, RequestRideActivity.class);
                break;
            case R.id.profileBtn:
                intent = new Intent(MainActivity.this, ProfileActivity.class);
                break;

            /*case R.id.browseBtn:
                intent = new Intent(MainActivity.this, BrowseRideActivity.class);
                //intent = new Intent(MainActivity.this, RideDetailsActivity.class);
                //intent
                break;*/
        }

//        intent.setPackage(getPackageManager().getInstallerPackageName("com.ucm.cis.android"));
        startActivity(intent);

    }


}
