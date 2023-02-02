package com.example.weatherreport.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Weather {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String address;

    private int minTmp;

    private int maxTmp;

    private String weather;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Weather(String address, int minTmp, int maxTmp, String weather) {
        this.address = address;
        this.minTmp = minTmp;
        this.maxTmp = maxTmp;
        this.weather = weather;
    }

    @Override
    public String toString() {
        return  address +
                " 气温: " + maxTmp +
                "°/" + minTmp +
                "° 天气:" + weather
                ;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMinTmp() {
        return minTmp;
    }

    public void setMinTmp(int minTmp) {
        this.minTmp = minTmp;
    }

    public int getMaxTmp() {
        return maxTmp;
    }

    public void setMaxTmp(int maxTmp) {
        this.maxTmp = maxTmp;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }



}
