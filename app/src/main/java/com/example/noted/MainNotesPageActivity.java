package com.example.noted;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainNotesPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_notes_page);
    }

    public void go_to_login(View view) {
        Intent i = new Intent(MainNotesPageActivity.this, LoginActivity.class);
        startActivity(i);
    }
}