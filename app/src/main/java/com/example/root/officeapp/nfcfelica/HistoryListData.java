package com.example.root.officeapp.nfcfelica;

public final class HistoryListData {
    long lId = 0;
    String strNo;
    String strTime;
    String strType;

    public HistoryListData() {
        String str = "";
        this.strTime = str;
        this.strType = str;
        this.strNo = str;
    }

    public long getId() {
        return this.lId;
    }

    public void setId(long id) {
        this.lId = id;
    }

    public String getNo() {
        return this.strNo;
    }

    public void setNo(String no) {
        this.strNo = no;
    }

    public void setType(String type) {
        this.strType = type;
    }

    public String getType() {
        return this.strType;
    }

    public String getTime() {
        return this.strTime;
    }

    public void setTime(String time) {
        this.strTime = time;
    }
}
