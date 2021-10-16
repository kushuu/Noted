package com.example.noted;

import android.net.Uri;
import android.widget.ImageView;

import java.net.URI;
import java.util.Calendar;
import java.util.Date;

public class IndNote {
    public String note;
    public String timeAdded;
    private  String userName;
    private ImageView note_img;
    private double latitude, longitude;
    private String imageUri;

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) { this.imageUri = imageUri; }

    public double getLatitude() { return latitude; }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(String timeAdded) {
        this.timeAdded = timeAdded;
    }

    public ImageView getNote_img() {
        return note_img;
    }

    public void setNote_img(ImageView note_img) {
        this.note_img = note_img;
    }

    public IndNote(String note_string, String currentTime, String userName, double latitude, double longitude) {
        this.timeAdded= currentTime;
        this.note = note_string;
        this.userName = "";
        this.latitude = 0.0d;
        this.longitude = 0.0d;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
