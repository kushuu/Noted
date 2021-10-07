package com.example.noted;

import java.util.Calendar;
import java.util.Date;

public class IndNote {
    public String note;
    public String timeAdded;

    public IndNote(String note_string, String currentTime) {
        this.timeAdded= currentTime;
        this.note = note_string;
    }
}
