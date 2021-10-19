package com.example.noted;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainNotesPageActivity extends AppCompatActivity implements View.OnClickListener {

    private Button logout, all_notes_btn, todo_btn, reminder_btn;
    private FloatingActionButton add_note_btn;
    private ListView allNotesListView;
//    private ArrayList<IndNote> all_notes;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_notes_page);
        setTitle("All notes.");

        allNotesListView = findViewById(R.id.allNotesListView);

        logout = (Button) findViewById(R.id.logout_btn);
        all_notes_btn = (Button) findViewById(R.id.all_notes_btn);
        todo_btn = (Button) findViewById(R.id.to_do_btn);
        reminder_btn = (Button) findViewById(R.id.reminder_btn);

        add_note_btn = (FloatingActionButton) findViewById(R.id.add_new_note_btn);

        logout.setOnClickListener(this);
        add_note_btn.setOnClickListener(this);
        todo_btn.setOnClickListener(this);
        all_notes_btn.setOnClickListener(this);
        reminder_btn.setOnClickListener(this);

        // working on printing all the notes on screen from FirebaseDatabase.


        mAuth = FirebaseAuth.getInstance();
        view_all_notes();
    }
    
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.logout_btn:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainNotesPageActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.add_new_note_btn:
                startActivity(new Intent(MainNotesPageActivity.this, AddNewNote.class));
                break;
            case R.id.to_do_btn:
                view_todo_notes();
                break;
            case R.id.all_notes_btn:
                view_all_notes();
                break;
            case R.id.reminder_btn:
                view_reminder_notes();
                break;
        }
    }

    private void view_reminder_notes() {
        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(userId).child("Notes");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final ArrayList<String> every_note = new ArrayList<>();
                for(DataSnapshot row : snapshot.getChildren()) {
                    String note = row.child("note").getValue().toString();
                    if(note.startsWith("Reminder") || note.startsWith("reminder") || note.startsWith("Remind me to") || note.startsWith("remind me to")) {
                        every_note.add(note);
                    }
                }
                final ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item, every_note);
                allNotesListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void view_todo_notes() {
        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(userId).child("Notes");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final ArrayList<String> every_note = new ArrayList<>();
                for(DataSnapshot row : snapshot.getChildren()) {
                    String note = row.child("note").getValue().toString();
                    if(!(note.startsWith("Reminder") || note.startsWith("reminder") || note.startsWith("Remind me to") || note.startsWith("remind me to"))) {
                        every_note.add(note);
                    }
                }
                final ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item, every_note);
                allNotesListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void view_all_notes() {
        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(userId).child("Notes");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final ArrayList<String> every_note = new ArrayList<>();
                for(DataSnapshot row : snapshot.getChildren()) {
                    every_note.add(row.child("note").getValue().toString());
                }
                final ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item, every_note);
                allNotesListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}