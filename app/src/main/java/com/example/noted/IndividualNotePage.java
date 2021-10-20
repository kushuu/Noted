package com.example.noted;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;

public class IndividualNotePage extends AppCompatActivity {
    private TextView noteText;
    private ImageView noteImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_note_page);
        setTitle("Noted.");

        noteText = (TextView) findViewById(R.id.note_text);
        noteImage = (ImageView) findViewById(R.id.note_img);

        Intent receive_data = getIntent();
        String note = receive_data.getStringExtra("noteContent");
        noteText.setText(note);

        // working with image.
        String uri = receive_data.getStringExtra("imageUri");
        Picasso.with(IndividualNotePage.this).load(uri).into(noteImage);
    }
}