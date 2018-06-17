package com.example.hiros.sharetaxi;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.util.Strings;

public class SignupActivity extends AppCompatActivity {

    EditText idInput, passwordInput, confirmPasswordInput, nickNameInput;
    Button completeSignUp;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Toast refreshToast;

    static final int ID_MIN_LENGTH = 8;
    static final int ID_MAX_LENGTH = 16;
    static final int PW_MIN_LENGTH = 8;
    static final int PW_MAX_LENGTH = 16;
    static final int NICKNAME_MIN_BYTE = 4;
    static final int NICKNAME_MAX_BYTE = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        pref = getSharedPreferences("myFile", Activity.MODE_PRIVATE);

        idInput = (EditText) findViewById(R.id.signup_emailInput);
        passwordInput = (EditText) findViewById(R.id.signup_passwordInput);
        confirmPasswordInput = (EditText) findViewById(R.id.signup_confirmPasswordInput);
        nickNameInput = (EditText) findViewById(R.id.signup_nickNameInput);

        confirmPasswordInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            // 비밀번호와 비밀번호확인에 텍스트 입력 될때마다 동일한지 검사
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = passwordInput.getText().toString();
                String confirm = confirmPasswordInput.getText().toString();

                if( password.equals(confirm) ) {
                    if (refreshToast == null) {
                        refreshToast =  Toast.makeText(SignupActivity.this, "Password match", Toast.LENGTH_LONG);
                    } else {
                        refreshToast.setText("Password match");
                    }
                    refreshToast.show();
                } else {
                    if (refreshToast == null) {
                        refreshToast =  Toast.makeText(SignupActivity.this, "Password do not match", Toast.LENGTH_LONG);
                    } else {
                        refreshToast.setText("Password do not match");
                    }
                    refreshToast.show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        completeSignUp = (Button)findViewById(R.id.btn_completeSignUp);
        completeSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int id = v.getId();
                switch (id) {
                    case R.id.btn_completeSignUp:
                        String userId = idInput.getText().toString();
                        String password = passwordInput.getText().toString();
                        String nickname = nickNameInput.getText().toString();
                        Boolean isSignupValidation = signupValidation(userId, password, nickname);

                        if(isSignupValidation == true) {
                            editor = pref.edit();
                            editor.putString("id", userId);
                            editor.putString("pw", password);
                            editor.putString("nickname", nickname);
                            editor.commit();
                            finish();
                        }

                        break;
                }
            }
        });
    }

    private boolean signupValidation(String id, String password, String nickname) {

        if (pref.getString("id", "").equals(id)) {
            Toast.makeText(SignupActivity.this, "Already registered ID", Toast.LENGTH_LONG).show();
            return false;
        }

        if (checkInputOnlyNumberAndAlphabet(id) == false) {
            Toast.makeText(SignupActivity.this, "Please only number and alphabet ID", Toast.LENGTH_LONG).show();
            return false;
        } else if (checkInputOnlyNumberAndAlphabet(password) == false) {
            Toast.makeText(SignupActivity.this, "Please only number and alphabet Password", Toast.LENGTH_LONG).show();
            return false;
        }

        if (id.length() < ID_MIN_LENGTH || id.length() > ID_MAX_LENGTH) {
            if (password.length() < PW_MIN_LENGTH || password.length() > PW_MAX_LENGTH) {
                Toast.makeText(SignupActivity.this, "Please ID & Password length 8~16", Toast.LENGTH_LONG).show();
                return false;
            } else {
                Toast.makeText(SignupActivity.this, "Please ID length 8~16", Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            if (password.length() < PW_MIN_LENGTH || password.length() > PW_MAX_LENGTH) {
                Toast.makeText(SignupActivity.this, "Please Password length 8~16", Toast.LENGTH_LONG).show();
                return false;
            }
        }


        if(getByteLength(nickname) > NICKNAME_MAX_BYTE || getByteLength(nickname) < NICKNAME_MIN_BYTE)
        {
            Toast.makeText(SignupActivity.this, "Please Nickname byte 4~16", Toast.LENGTH_LONG).show();
            return false;
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


    /**
     * String code point를 이용한 한글/영어 구분으로 바이트 단위 길이 체크합니다.
     *
     * @param string the string
     * @return ex) 가나다 == 6, abcd = 4
     */
    private int getByteLength(String string) {
        if (Strings.isEmptyOrWhitespace(string)) {
            return 0;
        }

        int length = string.length();
        int charLength = 0;
        for (int i = 0; i < length; i++) {
            charLength += string.codePointAt(i) > 0x00ff ? 2 : 1;
        }

        return charLength;
    }
}
