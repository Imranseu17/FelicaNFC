package com.example.root.officeapp.nfcfelica;

final class ErrorListData {
    long lId = 0;
    String strGroup;
    String strTime;
    String strType;

    public ErrorListData() {
        String str = "";
        this.strTime = str;
        this.strType = str;
        this.strGroup = str;
    }

    public long getId() {
        return this.lId;
    }

    public void setId(long id) {
        this.lId = id;
    }

    public String getGroup() {
        return this.strGroup;
    }

    public void setGroup(String group) {
        this.strGroup = group;
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
