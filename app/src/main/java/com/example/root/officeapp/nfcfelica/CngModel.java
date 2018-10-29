package com.example.root.officeapp.nfcfelica;

import android.support.annotation.NonNull;

public class CngModel {
    public int ClockTimeFlg = 0;
    public int ContinueFlg1 = 0;
    public int ContinueFlg2 = 0;
    public int IndexValueFlg = 0;
    public int LogCountFlg = 0;
    public int LogDaysFlg = 0;
    public int LogIntervalFlg = 0;
    public int MaxFlowFlg = 0;
    public int OpenCockFlg = 0;
    public int WeekControlFlg = 0;
    public int WeekStartFlg = 0;

    public CngModel() {
    }

    public boolean Equals(@NonNull CngModel model) {
        return this.LogDaysFlg == model.LogDaysFlg && this.IndexValueFlg == model.IndexValueFlg && this.WeekControlFlg == model.WeekControlFlg && this.WeekStartFlg == model.WeekStartFlg && this.ClockTimeFlg == model.ClockTimeFlg && this.LogCountFlg == model.LogCountFlg && this.LogIntervalFlg == model.LogIntervalFlg && this.OpenCockFlg == model.OpenCockFlg && this.MaxFlowFlg == model.MaxFlowFlg && this.ContinueFlg2 == model.ContinueFlg2 && this.ContinueFlg1 == model.ContinueFlg1;
    }
}
