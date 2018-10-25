package com.example.root.officeapp.nfcfelica;

import java.util.Calendar;

class GMA_LOG_DATA {
    public Calendar GasTime = Calendar.getInstance();
    public double GasValue;

    public GMA_LOG_DATA() {
        this.GasTime.clear();
    }
}
