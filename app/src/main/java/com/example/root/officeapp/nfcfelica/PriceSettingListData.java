package com.example.root.officeapp.nfcfelica;

final class PriceSettingListData {
    long lId = 0;
    String strBasicPrice;
    String strPrice;
    String strPriceID;
    String strStartDay;

    public PriceSettingListData() {
        String str = "";
        this.strBasicPrice = str;
        this.strPrice = str;
        this.strStartDay = str;
        this.strPriceID = str;
    }

    public long getId() {
        return this.lId;
    }

    public void setId(long id) {
        this.lId = id;
    }

    public String getPriceID() {
        return this.strPriceID;
    }

    public void setPriceID(String priceID) {
        this.strPriceID = priceID;
    }

    public void setStartDay(String startDay) {
        this.strStartDay = startDay;
    }

    public String getStartDay() {
        return this.strStartDay;
    }

    public void setPrice(String price) {
        this.strPrice = price;
    }

    public String getPrice() {
        return this.strPrice;
    }

    public void setBasicPrice(String basicPrice) {
        this.strBasicPrice = basicPrice;
    }

    public String getBasicPrice() {
        return this.strBasicPrice;
    }
}
