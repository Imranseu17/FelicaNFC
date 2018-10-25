package com.example.root.officeapp.nfcfelica;

import java.util.Calendar;

class GMA_CARD_HISTORY {
    public Calendar HistoryTime = Calendar.getInstance();
    public int HistoryType;

    public GMA_CARD_HISTORY() {
        this.HistoryTime.clear();
    }
}
