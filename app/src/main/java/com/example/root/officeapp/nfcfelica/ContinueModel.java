package com.example.root.officeapp.nfcfelica;

import android.support.annotation.NonNull;

class ContinueModel {
    public int ContinueCon = 0;
    public int ContinueFlg = 0;
    public int ContinueTime = 0;
    public int ContinueValue = 0;

    ContinueModel() {
    }

    public boolean Equals(@NonNull ContinueModel model) {
        return this.ContinueValue == model.ContinueValue && this.ContinueTime == model.ContinueTime && this.ContinueFlg == model.ContinueFlg && this.ContinueCon == model.ContinueCon;
    }
}
