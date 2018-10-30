package com.example.root.officeapp.nfcfelica;

import java.util.Calendar;

public class GMA_LOG_DATA {
    public Calendar GasTime = Calendar.getInstance();
    public double GasValue;

    public GMA_LOG_DATA() {
        this.GasTime.clear();
    }
}
