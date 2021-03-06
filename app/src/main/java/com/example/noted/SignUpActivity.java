package com.example.noted;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText usernameEditText, passwordEditText, cnfPasswordEditText;
    private Button signup_btn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        setTitle("Sign Up");

        mAuth = FirebaseAuth.getInstance();
        signup_btn = (Button) findViewById(R.id.signup);
        signup_btn.setOnClickListener(this);

        usernameEditText = (EditText) findViewById(R.id.email);
        passwordEditText = (EditText) findViewById(R.id.password);
        cnfPasswordEditText = (EditText) findViewById(R.id.confirmPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String email = usernameEditText.getText().toString().trim();
        String password1 = passwordEditText.getText().toString().trim();
        String password2 = cnfPasswordEditText.getText().toString().trim();

        if(email.isEmpty()) {
            usernameEditText.setError("Email must not be empty!");
            usernameEditText.requestFocus();
            return;
        }
        if(password1.isEmpty()) {
            passwordEditText.setError("Password must not be empty!");
            passwordEditText.requestFocus();
            return;
        }
        if(!password1.equals(password2)) {
            cnfPasswordEditText.setError("Passwords do not match!");
            cnfPasswordEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            usernameEditText.setError("Enter a valid email address!");
            usernameEditText.requestFocus();
            return;
        }
        if(password1.length() < 8) {
            passwordEditText.setError("Password length should be at least 8 characters.");
            passwordEditText.requestFocus();
            return;
        }

        // now i've handled all the test cases of validation.

        // following section for registering user.
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password1)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            User user = new User(email, password1);

                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(SignUpActivity.this, "User has been registered successfully", Toast.LENGTH_SHORT).show();
                                        FirebaseAuth.getInstance().signOut();
                                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(SignUpActivity.this, "Failed to register. Please try again!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(SignUpActivity.this, "Failed to register. Please try again!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void go_to_login(View view) {
        Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(i);
    }
}