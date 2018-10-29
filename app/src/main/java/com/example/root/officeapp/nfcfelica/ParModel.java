package com.example.root.officeapp.nfcfelica;

import android.support.annotation.NonNull;

public class ParModel {
    public int EmergencyCon = 0;
    public int FlowDetection = 0;
    public int OpenCoverCon = 0;
    public int QuakeCon = 0;
    public int ReductionCon = 0;

   public ParModel() {
    }

    public boolean Equals(@NonNull ParModel model) {
        return this.QuakeCon == model.QuakeCon && this.OpenCoverCon == model.OpenCoverCon && this.FlowDetection == model.FlowDetection && this.EmergencyCon == model.EmergencyCon && this.ReductionCon == model.ReductionCon;
    }
}
