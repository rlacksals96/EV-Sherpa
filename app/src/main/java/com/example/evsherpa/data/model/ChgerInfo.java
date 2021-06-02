package com.example.evsherpa.data.model;

public class ChgerInfo {

    private int chgerId;
    private int chgerType;

    public ChgerInfo(int chgerId, int chgerType) {
        this.chgerId = chgerId;
        this.chgerType = chgerType;
    }

    public int getChgerId() { return chgerId; }

    public void setChgerId(int chgerId) { this.chgerId = chgerId; }

    public int getChgerType() { return chgerType; }

    public void setChgerType(int chgerType) { this.chgerType = chgerType; }
}
