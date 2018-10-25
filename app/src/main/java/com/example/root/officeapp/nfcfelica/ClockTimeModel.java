package com.example.root.officeapp.nfcfelica;

import java.util.Calendar;

class ClockTimeModel {
    public Calendar ClockTime = Calendar.getInstance();
    public int ClockTimeFlg = 0;

    public ClockTimeModel() {
        this.ClockTime.clear();
    }
}
