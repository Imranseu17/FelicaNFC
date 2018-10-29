package com.example.root.officeapp.nfcfelica;

import android.support.annotation.NonNull;

public class Cng2Model {
    public int EmergencyConFlg = 0;
    public int EmergencyValueFlg = 0;
    public int FlowDetectionFlg = 0;
    public int OpenCoverConFlg = 0;
    public int QuakeConFlg = 0;
    public int ReductionConFlg = 0;

   public Cng2Model() {
    }

    public boolean Equals(@NonNull Cng2Model model) {
        return this.FlowDetectionFlg == model.FlowDetectionFlg && this.QuakeConFlg == model.QuakeConFlg && this.ReductionConFlg == model.ReductionConFlg && this.OpenCoverConFlg == model.OpenCoverConFlg && this.EmergencyValueFlg == model.EmergencyValueFlg && this.EmergencyConFlg == model.EmergencyConFlg;
    }
}
