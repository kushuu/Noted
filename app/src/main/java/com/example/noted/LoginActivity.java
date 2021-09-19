package com.example.noted;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
    }

    public void go_to_signup(View view) {
        Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(i);
    }

    public void sign_in_user(View view) {
//        EditText passwordEditText = (EditText) findViewById(R.id.password);
//        EditText usernameEditText = (EditText) findViewById(R.id.username);
//        String sUsername = usernameEditText.getText().toString();
//        Log.d("this is the username: ",sUsername);
//
//        String sPassword = passwordEditText.getText().toString();
//        Log.d("this is the password: ",sPassword);
//        EditText etUserName = (EditText) findViewById(R.id.username);
//        String strUserName = etUserName.getText().toString();
//
//        if(TextUtils.isEmpty(strUserName)) {
//            Toast.makeText(this, "button clicked.", Toast.LENGTH_SHORT).show();
//        }
        Toast.makeText(this, "button clicked.", Toast.LENGTH_SHORT).show();
    }
}