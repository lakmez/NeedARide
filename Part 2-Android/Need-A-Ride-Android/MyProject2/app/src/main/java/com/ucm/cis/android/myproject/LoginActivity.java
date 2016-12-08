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
import android.widget.Toast;

import com.ucm.cis.android.db.DatabaseHelper;
import com.ucm.cis.android.db.User;


public class LoginActivity extends ActionBarActivity {

    Button login;
    private SharedPreferences prefs;
    private EditText uName, pWrd;
    private DatabaseHelper db;
    private User user;
    private static final String KEY_USERNAME="username";
    private static final String KEY_PASSWORD="password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("Rider", Context.MODE_PRIVATE);

        setContentView(R.layout.activity_login);

        uName = (EditText) findViewById(R.id.email);
        pWrd = (EditText) findViewById(R.id.password);

        SQLiteDatabase.CursorFactory factory = null;
        db = new DatabaseHelper(this, factory);

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u = uName.getText().toString();
                String password = pWrd.getText().toString();
                if (null != u && !u.isEmpty() && null != password && !password.isEmpty()) {
                    user = validate(u, password);
                    if (null != user) {
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString(KEY_USERNAME, user.getEmail());
                        editor.putString(KEY_PASSWORD, password);
                        editor.commit();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Please enter your email address and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button buttonReg = (Button)findViewById(R.id.reg);
        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });
    }

    private User validate(String username, String password) {
        User usr = db.getRegisteredUser(username, password);
        return usr;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
