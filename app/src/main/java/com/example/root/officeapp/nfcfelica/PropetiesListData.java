package com.example.root.officeapp.nfcfelica;

public final class PropetiesListData {
    long lId = 0;
    String strName;
    String strValue;

    public PropetiesListData() {
        String str = "";
        this.strValue = str;
        this.strName = str;
    }

    public long getId() {
        return this.lId;
    }

    public void setId(long id) {
        this.lId = id;
    }

    public void setName(String name) {
        this.strName = name;
    }

    public String getName() {
        return this.strName;
    }

    public String getValue() {
        return this.strValue;
    }

    public void setValue(String phone) {
        this.strValue = phone;
    }
}
