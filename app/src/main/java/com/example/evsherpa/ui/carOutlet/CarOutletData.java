package com.example.evsherpa.ui.carOutlet;

public class CarOutletData {
    private int outlet_profile;
    private String txt_connector_name;
    private String txt_charge_current;
    private String txt_charge_voltage;
    private String txt_charge_power;
    private String txt_charge_level;
    private String txt_available_car;

    public CarOutletData(int outlet_profile, String txt_connector_name, String txt_charge_current,String txt_charge_voltage, String txt_charge_power, String txt_charge_level, String txt_available_car) {
        this.outlet_profile = outlet_profile;
        this.txt_connector_name = txt_connector_name;
        this.txt_charge_current = txt_charge_current;
        this.txt_charge_voltage=txt_charge_voltage;
        this.txt_charge_power = txt_charge_power;
        this.txt_charge_level = txt_charge_level;
        this.txt_available_car = txt_available_car;
    }

    public CarOutletData(String txt_connector_name, String txt_charge_current, String txt_charge_voltage, String txt_charge_power, String txt_charge_level, String txt_available_car) {

        this.txt_connector_name = txt_connector_name;
        this.txt_charge_current = txt_charge_current;
        this.txt_charge_voltage = txt_charge_voltage;
        this.txt_charge_power = txt_charge_power;
        this.txt_charge_level = txt_charge_level;
        this.txt_available_car = txt_available_car;

        this.outlet_profile=1;
    }

    public String getTxt_charge_voltage() {
        return txt_charge_voltage;
    }

    public void setTxt_charge_voltage(String txt_charge_voltage) {
        this.txt_charge_voltage = txt_charge_voltage;
    }
    public int getOutlet_profile() {
        return outlet_profile;
    }

    public void setOutlet_profile(int outlet_profile) {
        this.outlet_profile = outlet_profile;
    }

    public String getTxt_connector_name() {
        return txt_connector_name;
    }

    public void setTxt_connector_name(String txt_connector_name) {
        this.txt_connector_name = txt_connector_name;
    }

    public String getTxt_charge_current() {
        return txt_charge_current;
    }

    public void setTxt_charge_current(String txt_charge_current) {
        this.txt_charge_current = txt_charge_current;
    }

    public String getTxt_charge_power() {
        return txt_charge_power;
    }

    public void setTxt_charge_power(String txt_charge_power) {
        this.txt_charge_power = txt_charge_power;
    }

    public String getTxt_charge_level() {
        return txt_charge_level;
    }

    public void setTxt_charge_level(String txt_charge_level) {
        this.txt_charge_level = txt_charge_level;
    }

    public String getTxt_available_car() {
        return txt_available_car;
    }

    public void setTxt_available_car(String txt_available_car) {
        this.txt_available_car = txt_available_car;
    }
}
