package com.example.mitul.application;

public class BusInfo {
    int bus_id;
    String bus_name;

    public BusInfo(int bus_id, String bus_name) {
        this.bus_id = bus_id;
        this.bus_name = bus_name;
    }

    public int getBus_id() {
        return bus_id;
    }

    public void setBus_id(int bus_id) {
        this.bus_id = bus_id;
    }

    public String getBus_name() {
        return bus_name;
    }

    public void setBus_name(String bus_name) {
        this.bus_name = bus_name;
    }
}
