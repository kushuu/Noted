package com.example.noted;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddNewNote extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    FirebaseDatabase db;
    DatabaseReference ref;
    Button add_note, add_img;
    EditText note_string_ET;
    ImageView imgSection;

    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);
        setTitle("Note it down.");


        add_note = (Button) findViewById(R.id.add_new_btn);
        add_note.setOnClickListener(this);

        add_img = findViewById(R.id.add_note_img_btn);
        add_img.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        note_string_ET = (EditText) findViewById(R.id.note_string);
        db = FirebaseDatabase.getInstance();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        imgSection = (ImageView) findViewById(R.id.add_note_temp_img);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_new_btn:
                add_note_method();
                break;
            case R.id.add_note_img_btn:
                captureImg();
                break;
        }
    }

    private void captureImg() {
        if(ContextCompat.checkSelfPermission(AddNewNote.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddNewNote.this, new String[]{Manifest.permission.CAMERA}, 100);
        }
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent img_work = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(img_work, 100);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Bitmap capture = (Bitmap) data.getExtras().get("data");
            // setting captured image to ImageView.
            imgSection.setImageBitmap(capture);
        }
    }

    private void add_note_method() {

        if (ActivityCompat.checkSelfPermission(AddNewNote.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // this is when location permission is granted.
            getLocation();  // user defined function.

        } else {
            // case when the permission is not granted.
            ActivityCompat.requestPermissions(AddNewNote.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(AddNewNote.this, Locale.getDefault());
                        List<Address> address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        String note_text = note_string_ET.getText().toString().trim();
                        String userId = mAuth.getCurrentUser().getUid();
                        ref = db.getReference("users").child(userId).child("Notes");

                        String uid = mAuth.getCurrentUser().getUid();
                        String currentTime = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.getDefault()).format(new Date());
                        double latitude = address.get(0).getLatitude(), longitude = address.get(0).getLongitude();
                        IndNote noteObj = new IndNote(note_text, currentTime, uid, latitude, longitude);
                        noteObj.setUserName(uid);
                        noteObj.setLatitude(latitude);
                        noteObj.setLongitude(longitude);

                        // saving note into the db.
                        ref.child(currentTime).setValue(noteObj);
                        Toast.makeText(AddNewNote.this, "Note added :)", Toast.LENGTH_SHORT).show();

                        // resetting editText field and going back to all_notes page.
                        note_string_ET.setText("");
                        startActivity(new Intent(AddNewNote.this, MainNotesPageActivity.class));
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}