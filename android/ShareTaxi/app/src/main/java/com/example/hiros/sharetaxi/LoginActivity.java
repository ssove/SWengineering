package com.example.hiros.sharetaxi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText idInput, passwordInput;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Button login;
    Button signup;

    static final int SIGNUP_REQUEST = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = getSharedPreferences("myFile", Activity.MODE_PRIVATE);

        idInput = (EditText) findViewById(R.id.emailInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);

        login = (Button)findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int id = v.getId();
                switch(id) {
                    case R.id.loginButton:
                        String userId = idInput.getText().toString();
                        String password = passwordInput.getText().toString();
                        Boolean validation = loginValidation(userId, password);

                        if(validation) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        break;
                }
            }
        });

        signup = (Button)findViewById(R.id.signupButton);
        signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int id = v.getId();
                switch (id) {
                    case R.id.signupButton:
                        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                        startActivityForResult(intent, SIGNUP_REQUEST);
                        break;
                }
            }
        });
    }

    private boolean loginValidation(String id, String password) {
        if (pref.getString("id","").equals(null)) {
            Toast.makeText(LoginActivity.this, "Please Sign Up first", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(pref.getString("id","").equals(id) == false || pref.getString("pw","").equals(password) == false) {
            Toast.makeText(LoginActivity.this, "Please check your ID/Password", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(pref.getString("id","").equals(id) && pref.getString("pw","").equals(password)) {
            return true;
        }
        else {
            Toast.makeText(LoginActivity.this, "Unknown Login State", Toast.LENGTH_LONG).show();
            return false;
        }
    }

}
