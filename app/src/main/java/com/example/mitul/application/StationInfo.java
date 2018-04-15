package com.example.mitul.application;

public class StationInfo {
    private int id;
    private String stationName;

    public StationInfo(int id, String stationName){
        this.id = id;
        this.stationName = stationName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}
