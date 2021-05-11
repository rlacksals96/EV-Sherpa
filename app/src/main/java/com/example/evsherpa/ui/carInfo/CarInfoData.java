package com.example.evsherpa.ui.carInfo;

public class CarInfoData {
    private int car_profile;
    private String txt_car_name;
    private String txt_car_maker;
    private String txt_car_capacity;
    private String txt_max_speed;
    private String txt_max_distance;
    private String txt_battery;
    private String txt_subsidy;
    private String txt_maker_phone_num;

    public CarInfoData(int car_profile, String txt_car_name, String txt_car_maker, String txt_car_capacity, String txt_max_speed, String txt_max_distance, String txt_battery, String txt_subsidy, String txt_maker_phone_num) {
        this.car_profile = car_profile;
        this.txt_car_name = txt_car_name;
        this.txt_car_maker = txt_car_maker;
        this.txt_car_capacity = txt_car_capacity;
        this.txt_max_speed = txt_max_speed;
        this.txt_max_distance = txt_max_distance;
        this.txt_battery = txt_battery;
        this.txt_subsidy = txt_subsidy;
        this.txt_maker_phone_num = txt_maker_phone_num;
    }

    public CarInfoData(String txt_car_name, String txt_car_maker, String txt_car_capacity, String txt_max_speed, String txt_max_distance, String txt_battery, String txt_subsidy, String txt_maker_phone_num) {
        this.txt_car_name = txt_car_name;
        this.txt_car_maker = txt_car_maker;
        this.txt_car_capacity = txt_car_capacity;
        this.txt_max_speed = txt_max_speed;
        this.txt_max_distance = txt_max_distance;
        this.txt_battery = txt_battery;
        this.txt_subsidy = txt_subsidy;
        this.txt_maker_phone_num = txt_maker_phone_num;

        this.car_profile=1;
    }

    public int getCar_profile() {
        return car_profile;
    }

    public void setCar_profile(int car_profile) {
        this.car_profile = car_profile;
    }

    public String getTxt_car_name() {
        return txt_car_name;
    }

    public void setTxt_car_name(String txt_car_name) {
        this.txt_car_name = txt_car_name;
    }

    public String getTxt_car_maker() {
        return txt_car_maker;
    }

    public void setTxt_car_maker(String txt_car_maker) {
        this.txt_car_maker = txt_car_maker;
    }

    public String getTxt_car_capacity() {
        return txt_car_capacity;
    }

    public void setTxt_car_capacity(String txt_car_capacity) {
        this.txt_car_capacity = txt_car_capacity;
    }

    public String getTxt_max_speed() {
        return txt_max_speed;
    }

    public void setTxt_max_speed(String txt_max_speed) {
        this.txt_max_speed = txt_max_speed;
    }

    public String getTxt_max_distance() {
        return txt_max_distance;
    }

    public void setTxt_max_distance(String txt_max_distance) {
        this.txt_max_distance = txt_max_distance;
    }

    public String getTxt_battery() {
        return txt_battery;
    }

    public void setTxt_battery(String txt_battery) {
        this.txt_battery = txt_battery;
    }

    public String getTxt_subsidy() {
        return txt_subsidy;
    }

    public void setTxt_subsidy(String txt_subsidy) {
        this.txt_subsidy = txt_subsidy;
    }

    public String getTxt_maker_phone_num() {
        return txt_maker_phone_num;
    }

    public void setTxt_maker_phone_num(String txt_maker_phone_num) {
        this.txt_maker_phone_num = txt_maker_phone_num;
    }
}
