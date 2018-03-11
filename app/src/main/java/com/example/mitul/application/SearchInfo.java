package com.example.mitul.application;

public class SearchInfo {
    int id;
    String source_station;
    String destination_station;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource_station() {
        return source_station;
    }

    public void setSource_station(String source_station) {
        this.source_station = source_station;
    }

    public String getDestination_station() {
        return destination_station;
    }

    public void setDestination_station(String destination_station) {
        this.destination_station = destination_station;
    }

    public SearchInfo(int id, String source_station, String destination_station) {

        this.id = id;
        this.source_station = source_station;
        this.destination_station = destination_station;
    }
}
