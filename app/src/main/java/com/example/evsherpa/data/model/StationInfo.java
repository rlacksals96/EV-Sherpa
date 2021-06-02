package com.example.evsherpa.data.model;

import java.util.ArrayList;

public class StationInfo {
    private String statId;
    private String statNm;
    private ArrayList<ChgerInfo> chgers = new ArrayList<ChgerInfo>();
    private String addr;
    private float lat;
    private float lng;
    private String useTime;
    private String busiId;
    private String busiNm;
    private String busiCall;
    private int zcode;
    private String parkingFree;

    //private int stat;
    //private int statUpdDt;
    //private String powerType;
    //private String note;
    //private boolean limitYn;
    //private String limitDetail;
    //private boolean delYn;
    //private String delDetail;

    public String getStatId() {
        return statId;
    }

    public void setStatId(String statId) {
        this.statId = statId;
    }

    public String getStatNm() {
        return statNm;
    }

    public void setStatNm(String statNm) {
        this.statNm = statNm;
    }

    public void addChger(ChgerInfo chger) { this.chgers.add(chger); }

    public ArrayList<ChgerInfo> getChgers() { return chgers; }

    public ChgerInfo getChger(int index) { return chgers.get(index); }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public String getBusiId() {
        return busiId;
    }

    public void setBusiId(String busiId) {
        this.busiId = busiId;
    }

    public String getBusiNm() {
        return busiNm;
    }

    public void setBusiNm(String busiNm) {
        this.busiNm = busiNm;
    }

    public String getBusiCall() {
        return busiCall;
    }

    public void setBusiCall(String busiCall) {
        this.busiCall = busiCall;
    }

    public int getZcode() {
        return zcode;
    }

    public void setZcode(int zcode) {
        this.zcode = zcode;
    }

    public String getParkingFree() {
        return parkingFree;
    }

    public void setParkingFree(String parkingFree) {
        this.parkingFree = parkingFree;
    }
}
