package com.example.noted;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainNotesPageActivity extends AppCompatActivity implements View.OnClickListener {

    private Button logout, all_notes_btn, todo_btn, reminder_btn;
    private FloatingActionButton add_note_btn;
    private ListView allNotesListView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_notes_page);
        setTitle("All notes.");

        allNotesListView = findViewById(R.id.allNotesListView);
        allNotesListView.setClickable(true);

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

                // making listView elements clickable.
                allNotesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView <? > arg0, View view, int position, long id) {
                        // when clicked, a new activity should start of the individual note.
                        String note = ((TextView) view).getText().toString();
                        Intent go_to_ind_note = new Intent(MainNotesPageActivity.this, IndividualNotePage.class);
                        go_to_ind_note.putExtra("noteContent", ((TextView) view).getText());


                        ref.child(note).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot data : snapshot.getChildren()) {
                                    if(data.getKey().equals("imageUri")){
                                        String imgUrl = data.getValue().toString();
                                        go_to_ind_note.putExtra("imageUri", imgUrl);
                                    }
                                    if(data.getKey().equals("latitude")){
                                        double lat = (double) data.getValue();
                                        String latStr = String.valueOf(lat);
                                        go_to_ind_note.putExtra("latitude", latStr);
                                    }
                                    if(data.getKey().equals("longitude")){
                                        double longitude = (double) data.getValue();
                                        String longStr = String.valueOf(longitude);
                                        go_to_ind_note.putExtra("longitude", longStr);
                                    }
                                    if(data.getKey().equals("timeAdded")){
                                        String datetime = data.getValue().toString();
                                        go_to_ind_note.putExtra("date_time", datetime);
                                    }
                                }
                                startActivity(go_to_ind_note);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });

                // reversing the notes, getting latest first.
                for (int i = 0; i < every_note.size() / 2; i++) {
                    String temp = every_note.get(i);
                    every_note.set(i, every_note.get(every_note.size() - i - 1));
                    every_note.set(every_note.size() - i - 1, temp);
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

                // making listView elements clickable.
                allNotesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView <? > arg0, View view, int position, long id) {
                        // when clicked, a new activity should start of the individual note.
                        String note = ((TextView) view).getText().toString();
                        Intent go_to_ind_note = new Intent(MainNotesPageActivity.this, IndividualNotePage.class);
                        go_to_ind_note.putExtra("noteContent", ((TextView) view).getText());


                        ref.child(note).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot data : snapshot.getChildren()) {
                                    if(data.getKey().equals("imageUri")){
                                        String imgUrl = data.getValue().toString();
                                        go_to_ind_note.putExtra("imageUri", imgUrl);
                                    }
                                    if(data.getKey().equals("latitude")){
                                        double lat = (double) data.getValue();
                                        String latStr = String.valueOf(lat);
                                        go_to_ind_note.putExtra("latitude", latStr);
                                    }
                                    if(data.getKey().equals("longitude")){
                                        double longitude = (double) data.getValue();
                                        String longStr = String.valueOf(longitude);
                                        go_to_ind_note.putExtra("longitude", longStr);
                                    }
                                    if(data.getKey().equals("timeAdded")){
                                        String datetime = data.getValue().toString();
                                        go_to_ind_note.putExtra("date_time", datetime);
                                    }
                                }
                                startActivity(go_to_ind_note);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });

                // reversing the notes, getting latest first.
                for (int i = 0; i < every_note.size() / 2; i++) {
                    String temp = every_note.get(i);
                    every_note.set(i, every_note.get(every_note.size() - i - 1));
                    every_note.set(every_note.size() - i - 1, temp);
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

                // making listView elements clickable.
                allNotesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView <? > arg0, View view, int position, long id) {
                        // when clicked, a new activity should start of the individual note.
                        String note = ((TextView) view).getText().toString();
                        Intent go_to_ind_note = new Intent(MainNotesPageActivity.this, IndividualNotePage.class);
                        go_to_ind_note.putExtra("noteContent", ((TextView) view).getText());


                        ref.child(note).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot data : snapshot.getChildren()) {
                                    if(data.getKey().equals("imageUri")){
                                        String imgUrl = data.getValue().toString();
                                        go_to_ind_note.putExtra("imageUri", imgUrl);
                                    }
                                    if(data.getKey().equals("latitude")){
                                        double lat = (double) data.getValue();
                                        String latStr = String.valueOf(lat);
                                        go_to_ind_note.putExtra("latitude", latStr);
                                    }
                                    if(data.getKey().equals("longitude")){
                                        double longitude = (double) data.getValue();
                                        String longStr = String.valueOf(longitude);
                                        go_to_ind_note.putExtra("longitude", longStr);
                                    }
                                    if(data.getKey().equals("timeAdded")){
                                        String datetime = data.getValue().toString();
                                        go_to_ind_note.putExtra("date_time", datetime);
                                    }
                                }
                                startActivity(go_to_ind_note);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });

                // reversing the notes, getting latest first.
                for (int i = 0; i < every_note.size() / 2; i++) {
                    String temp = every_note.get(i);
                    every_note.set(i, every_note.get(every_note.size() - i - 1));
                    every_note.set(every_note.size() - i - 1, temp);
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