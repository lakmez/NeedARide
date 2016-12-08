package com.ucm.cis.android.myproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ucm.cis.android.db.DatabaseHelper;
import com.ucm.cis.android.db.User;


public class RegisterActivity extends ActionBarActivity {
    EditText fnam, lnam, email, phno, rollno, password;
    private DatabaseHelper db;
    private User mainUser;

    private static final String KEY_USERNAME="username";
    private static final String KEY_PASSWORD="password";
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fnam = (EditText) findViewById(R.id.fname);
        lnam = (EditText) findViewById(R.id.lname);
        email = (EditText) findViewById(R.id.emails);
        phno = (EditText) findViewById(R.id.phno);
        rollno = (EditText) findViewById(R.id.rollno);
        password = (EditText) findViewById(R.id.pass);
        Button btn = (Button) findViewById(R.id.register);

        SQLiteDatabase.CursorFactory factory = null;
        db = new DatabaseHelper(this, factory);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setFstName(fnam.getText().toString());
                user.setLstName(lnam.getText().toString());
                user.setEmail(email.getText().toString());
                user.setPhNo(phno.getText().toString());
                user.setRolNo(rollno.getText().toString());
                prefs = getSharedPreferences("Rider", Context.MODE_PRIVATE);
                mainUser = db.registerUser(user, password.getText().toString());
                if (null != mainUser) {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(KEY_USERNAME, user.getEmail());
                    editor.putString(KEY_PASSWORD, password.getText().toString());
                    editor.commit();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
