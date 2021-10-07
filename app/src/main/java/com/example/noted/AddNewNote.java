package com.example.noted;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddNewNote extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    FirebaseDatabase db;
    DatabaseReference ref;
    Button add_note;
    EditText note_string_ET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);
        setTitle("Note it down.");

        mAuth = FirebaseAuth.getInstance();
        note_string_ET = (EditText) findViewById(R.id.note_string);
        add_note = (Button) findViewById(R.id.add_new_btn);
        add_note.setOnClickListener(this);
        db = FirebaseDatabase.getInstance();

        // Testing firebase and android studio communication.
        // ref = db.getReference("First node");
        // ref.setValue("test value.");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_new_btn:
                add_note_method();
                break;
        }
    }

    private void add_note_method() {

        String note_text = note_string_ET.getText().toString().trim();
        ref = db.getReference("Notes");

        String uid = mAuth.getCurrentUser().getUid();
        String currentTime = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.getDefault()).format(new Date());

        IndNote noteObj = new IndNote(note_text, currentTime, uid);
        noteObj.setUserName(uid);

        // saving note into the db.
        ref.child(currentTime).setValue(noteObj);
        Toast.makeText(AddNewNote.this, "Note added :)", Toast.LENGTH_SHORT).show();

        // resetting editText field and going back to all_notes page.
        note_string_ET.setText("");
        startActivity(new Intent(AddNewNote.this, MainNotesPageActivity.class));
    }
}