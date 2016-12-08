package com.ucm.cis.android.myproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.ucm.cis.android.db.nOffer;
import com.ucm.cis.android.db.nRequest;
import com.ucm.cis.android.util.RideServiceClient;
import com.ucm.cis.android.util.ServiceHelper;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView offrList, reqList;
    ServiceHelper helper = new ServiceHelper(ProfileActivity.this);
    TextView t;
    private CustomAdapter adapter1, adapter2;

    private static final String ACTION_FOR_INTENT_CALLBACK = "THIS_IS_A_UNIQUE_KEY_WE_USE_TO_COMMUNICATE";
    nOffer[] offerArray;
    nRequest[] rideArray;
    ArrayList<nRequest> requestList = new ArrayList();
    ArrayList <nOffer>offerList = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        t = (TextView) findViewById(R.id.userName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        offrList = (ListView) findViewById(R.id.myOffer);
        reqList = (ListView) findViewById(R.id.myRides);
        updateProfile();
    }


    private BroadcastReceiver receiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getApplicationContext(), "received", Toast.LENGTH_SHORT);
            String response = intent.getStringExtra(RideServiceClient.HTTP_RESPONSE);
        //    t.setText(response);
            Log.i("Profile", "RESPONSE = " + response);
            parse(response);
        }
    };

    private void parse(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            Type listType = new TypeToken<List<nOffer>>() {
            }.getType();
            Type listType2 = new TypeToken<List<nRequest>>() {
            }.getType();

            //
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    return new Date(json.getAsJsonPrimitive().getAsLong());
                }
            });
            Gson gson = gsonBuilder.create();
            offerList = gson.fromJson(jsonArray.get(0).toString(), listType);
            requestList = gson.fromJson(jsonArray.get(1).toString(), listType2);
             offerArray = offerList.toArray(new nOffer[offerList.size()]);
             rideArray = requestList.toArray(new nRequest[requestList.size()]);


            adapter1 = new CustomAdapter(this, R.layout.listview_item_row, offerArray);
            adapter2 = new CustomAdapter(this, R.layout.listview_item_row, rideArray);
            offrList.setAdapter(adapter1);
            reqList.setAdapter(adapter2);

        } catch (Exception e) {

        }
    }

    @Override
    protected void onResume() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("Login");
        registerReceiver(receiver1, filter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(receiver1);
        super.onPause();
    }

    private void updateProfile() {
        helper.getProfile();
        offrList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nOffer selOffer = offerList.get(position);
                Intent inter = new Intent(ProfileActivity.this, RideDetailsActivity.class);
                inter.putExtra("ride", selOffer);
                ProfileActivity.this.startActivity(inter);
            }
        });
        reqList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nRequest selOffer = requestList.get(position);
                Intent inter = new Intent(ProfileActivity.this, RideDetailsActivity.class);
                inter.putExtra("req", selOffer);
                ProfileActivity.this.startActivity(inter);
            }
        });
    }


}
