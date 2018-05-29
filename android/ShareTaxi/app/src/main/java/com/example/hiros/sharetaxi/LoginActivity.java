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

//    EditText idInput, passwordInput;
//    CheckBox autoLogin;
//    Boolean loginChecked;
//    SharedPreferences pref;
//    SharedPreferences.Editor editor;

    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button)findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int id = v.getId();
                switch(id) {
                    case R.id.loginButton:
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });

//        idInput = (EditText) findViewById(R.id.emailInput);
//        passwordInput = (EditText) findViewById(R.id.passwordInput);
//        autoLogin = (CheckBox) findViewById(R.id.checkBox);
//
//        // if autoLogin checked, get input
//        if (pref.getBoolean("autoLogin", false)) {
//            idInput.setText(pref.getString("id", ""));
//            passwordInput.setText(pref.getString("pw", ""));
//            autoLogin.setChecked(true);
//            // goto mainActivity
//
//        } else {
//            // if autoLogin unChecked
//            String id = idInput.getText().toString();
//            String password = passwordInput.getText().toString();
//            Boolean validation = loginValidation(id, password);
//
//            if(validation) {
//                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();
//                // save id, password to Database
//
//                if(loginChecked) {
//                    // if autoLogin Checked, save values
//                    editor.putString("id", id);
//                    editor.putString("pw", password);
//                    editor.putBoolean("autoLogin", true);
//                    editor.commit();
//                }
//                // goto mainActivity
//
//            } else {
//                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
//                // goto LoginActivity
//            }
      }
//        // set checkBoxListener
//        autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked) {
//                   loginChecked = true;
//                } else {
//                    // if unChecked, removeAll
//                    loginChecked = false;
//                    editor.clear();
//                    editor.commit();
//                }
//            }
//        });
//    }
//
//    private boolean loginValidation(String id, String password) {
//        if(pref.getString("id","").equals(id) && pref.getString("pw","").equals(password)) {
//            // login success
//            return true;
//        } else if (pref.getString("id","").equals(null)){
//            // sign in first
//            Toast.makeText(LoginActivity.this, "Please Sign in first", Toast.LENGTH_LONG).show();
//            return false;
//        } else {
//            // login failed
//            return false;
//        }
//    }
}
