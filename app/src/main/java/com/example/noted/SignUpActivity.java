package com.example.noted;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        setTitle("Sign Up");
    }

    public void go_to_login(View view) {
        Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(i);
    }

    public void register_user(View view) {
        EditText usernameEditText = (EditText) findViewById(R.id.username);
        EditText passwordEditText = (EditText) findViewById(R.id.password);
        EditText cnfPasswordEditText = (EditText) findViewById(R.id.confirmPassword);

        if(usernameEditText.getText().toString().equals("")) {
            Toast.makeText(this, "username is required", Toast.LENGTH_SHORT).show();
            return;
        }
        if(passwordEditText.getText().toString().equals("")) {
            Toast.makeText(this, "Password must not be empty!", Toast.LENGTH_SHORT).show();
        }
        if(!passwordEditText.getText().toString().equals(cnfPasswordEditText.getText().toString())) {
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
            return;
        }

    }
}