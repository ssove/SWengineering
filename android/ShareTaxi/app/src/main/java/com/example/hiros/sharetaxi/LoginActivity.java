package com.example.hiros.sharetaxi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText idInput, passwordInput;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Button login;
    Button signup;

    static final int ID_MIN_LENGTH = 8;
    static final int ID_MAX_LENGTH = 16;
    static final int PW_MIN_LENGTH = 8;
    static final int PW_MAX_LENGTH = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = getSharedPreferences("myFile", this.MODE_PRIVATE);

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
                        String userId = idInput.getText().toString();
                        String password = passwordInput.getText().toString();
                        Boolean isSignupValidation = signupValidation(userId, password);

                        if(isSignupValidation == true) {
                            editor = pref.edit();
                            editor.putString("id", userId);
                            editor.putString("pw", password);
                            editor.commit();
                            Toast.makeText(LoginActivity.this, "Success Sign Up", Toast.LENGTH_LONG).show();
                        }
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

    private boolean signupValidation(String id, String password) {

        if (pref.getString("id", "").equals(id)) {
            Toast.makeText(LoginActivity.this, "Already registered ID", Toast.LENGTH_LONG).show();
            return false;
        }

        if (checkInputOnlyNumberAndAlphabet(id) == false) {
            Toast.makeText(LoginActivity.this, "Please only number and alphabet ID", Toast.LENGTH_LONG).show();
            return false;
        } else if (checkInputOnlyNumberAndAlphabet(password) == false) {
            Toast.makeText(LoginActivity.this, "Please only number and alphabet Password ", Toast.LENGTH_LONG).show();
            return false;
        }

        if (id.length() < ID_MIN_LENGTH || id.length() > ID_MAX_LENGTH) {
            if (password.length() < PW_MIN_LENGTH || password.length() > PW_MAX_LENGTH) {
                Toast.makeText(LoginActivity.this, "Please ID & Password length 8~16, ", Toast.LENGTH_LONG).show();
                return false;
            } else {
                Toast.makeText(LoginActivity.this, "Please ID length 8~16, ", Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            if (password.length() < PW_MIN_LENGTH || password.length() > PW_MAX_LENGTH) {
                Toast.makeText(LoginActivity.this, "Please Password length 8~16, ", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        return true;
    }

    private boolean checkInputOnlyNumberAndAlphabet(String textInput) {
        char chrInput;

        for (int i = 0; i < textInput.length(); i++) {
            chrInput = textInput.charAt(i); // 입력받은 텍스트에서 문자 하나하나 가져와서 체크

            if (chrInput >= 0x61 && chrInput <= 0x7A) {
                // 영문(소문자) OK!
            }
            else if (chrInput >=0x41 && chrInput <= 0x5A) {
                // 영문(대문자) OK!
            }
            else if (chrInput >= 0x30 && chrInput <= 0x39) {
                // 숫자 OK!
            }
            else {
                return false;   // 영문자도 아니고 숫자도 아님!
            }
        }

        return true;
    }


}
