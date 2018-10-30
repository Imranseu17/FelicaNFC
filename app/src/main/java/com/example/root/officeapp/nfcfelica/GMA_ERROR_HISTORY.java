package com.example.root.officeapp.nfcfelica;

import java.util.Calendar;

public class GMA_ERROR_HISTORY {
    public int ErrorGroup;
    public Calendar ErrorTime = Calendar.getInstance();
    public String ErrorType;

    public GMA_ERROR_HISTORY() {
        this.ErrorTime.clear();
    }
}
