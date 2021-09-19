package com.example.noted;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
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
        EditText passwordEditText = (EditText) findViewById(R.id.password);
        EditText usernameEditText = (EditText) findViewById(R.id.username);
//        ProgressBar pgsBar = (ProgressBar)findViewById(R.id.loading);
//        pgsBar.setVisibility(view.GONE);

        String sUsername = usernameEditText.getText().toString();
        String sPassword = passwordEditText.getText().toString();

        if(!sUsername.equals("") && !sPassword.equals("")) {
            ProgressDialog.show(this, "Loading", "Wait while loading...");
            usernameEditText.setText("");
            passwordEditText.setText("");

            Intent i = new Intent(LoginActivity.this, MainNotesPageActivity.class);
            startActivity(i);
        }
        else {
            Toast.makeText(this, "Please fill all the details!", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}