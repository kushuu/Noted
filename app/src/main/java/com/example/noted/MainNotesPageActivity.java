package com.example.noted;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainNotesPageActivity extends AppCompatActivity implements View.OnClickListener {

    private Button logout, all_notes_btn, todo_btn, reminder_btn;
    private FloatingActionButton add_note_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_notes_page);
        setTitle("All notes.");

        logout = (Button) findViewById(R.id.logout_btn);
        all_notes_btn = (Button) findViewById(R.id.all_notes_btn);
        todo_btn = (Button) findViewById(R.id.to_do_btn);
        reminder_btn = (Button) findViewById(R.id.reminder_btn);
        add_note_btn = (FloatingActionButton) findViewById(R.id.add_new_note_btn);

        logout.setOnClickListener(this);
        add_note_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.logout_btn:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainNotesPageActivity.this, LoginActivity.class));
                break;
            case R.id.add_new_note_btn:
                Toast.makeText(MainNotesPageActivity.this, "Going to next page...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainNotesPageActivity.this, AddNewNote.class));
        }
    }
}