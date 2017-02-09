package com.ipos.restaurant.model;

import android.annotation.SuppressLint;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by SupeMrOne on 2/7/2017.
 */
public class DmAtm implements Serializable {

    private  String name;

    private  String addres;

    private int id;


    @SerializedName("Latitude")
    private double latitude;


    @SerializedName("Longitude")
    private double longtitude;

    private double distance=-1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @SuppressLint("DefaultLocale")
    public String getShowDistance() {
        if (distance == -1) {
            return "";
        }
        if (distance < 1000) {//1km
            return String.format("%3.0fm", distance);
        }

        return String.format("%.2fkm", distance / 1000);

    }
}
