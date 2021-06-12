package com.example.evsherpa.data.model;

public class ChargerInfo {

    private int id;
    private int type;
    private int status;
    private String statusUpdDt;

    public ChargerInfo(int id, int type) {
        this.id = id;
        this.type = type;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getType() { return type; }

    public void setType(int type) { this.type = type; }

    public int getStatus() { return status; }

    public void setStatus(int status) { this.status = status; }

    public String getStatusUpdDt() { return statusUpdDt; }

    public void setStatusUpdDt(String statusUpdDt) { this.statusUpdDt = statusUpdDt; }
}
