package com.example.noted;

import java.util.Calendar;
import java.util.Date;

public class IndNote {
    public String note;
    public String timeAdded;
    private  String userName;

    public IndNote(String note_string, String currentTime, String userName) {
        this.timeAdded= currentTime;
        this.note = note_string;
        this.userName = "";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
