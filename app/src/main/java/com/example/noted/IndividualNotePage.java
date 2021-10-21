package com.example.noted;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class IndividualNotePage extends AppCompatActivity {
    private TextView noteText, latText, lonText, dtText;
    private ImageView noteImage;
    private Button deleteBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_note_page);
        setTitle("Noted.");

        noteText = (TextView) findViewById(R.id.note_text);
        noteImage = (ImageView) findViewById(R.id.note_img);
        latText = (TextView) findViewById(R.id.note_latitude);
        lonText = (TextView) findViewById(R.id.note_longitude);
        dtText = (TextView) findViewById(R.id.note_datetime);
        deleteBtn = (Button) findViewById(R.id.delete_note);

        Intent receive_data = getIntent();
        String note = receive_data.getStringExtra("noteContent");
        String latitude = receive_data.getStringExtra("latitude");
        String longitude = receive_data.getStringExtra("longitude");
        String date_time = receive_data.getStringExtra("date_time");

        noteText.setText("Note: " + note);
        latText.setText("Latitude: " + latitude);
        lonText.setText("Longitude: " + longitude);
        dtText.setText("Date added: " + date_time);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String userId = mAuth.getCurrentUser().getUid();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(userId).child("Notes").child(note);
                ref.setValue(null);
                Toast.makeText(IndividualNotePage.this, "This note has been deleted!", Toast.LENGTH_SHORT).show();

                // going back to all notes page.
                startActivity(new Intent(IndividualNotePage.this, MainNotesPageActivity.class));
                finish();
            }
        });

        // working with image.
        String uri = receive_data.getStringExtra("imageUri");
        Picasso.with(IndividualNotePage.this).load(uri).into(noteImage);
    }
}