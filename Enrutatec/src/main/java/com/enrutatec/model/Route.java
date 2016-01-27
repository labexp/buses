package com.enrutatec.model;


import android.util.Log;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.enrutatec.services.RoutesManagerService;
import com.enrutatec.services.impl.RoutesManagerServiceImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private String from;
    private String to;
    private double distance;
    private double duration;
    private String name;
    private double price;
    private List<LatLng> coordinates;

    public Route(String from, String to, double distance, double duration, String name, double price, List<LatLng> coordinates) {
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.duration = duration;
        this.name = name;
        this.price = price;
        this.coordinates = coordinates;
    }

    public Route(){
        coordinates = new ArrayList<LatLng>();
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public List<LatLng> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<LatLng> coordinates) {
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;

        if (Double.compare(route.distance, distance) != 0) return false;
        if (Double.compare(route.duration, duration) != 0) return false;
        if (from != null ? !from.equals(route.from) : route.from != null) return false;
        if (to != null ? !to.equals(route.to) : route.to != null) return false;
        if (name != null ? !name.equals(route.name) : route.name != null) return false;
        return !(coordinates != null ? !coordinates.equals(route.coordinates) : route.coordinates != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = from != null ? from.hashCode() : 0;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        temp = Double.doubleToLongBits(distance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(duration);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (coordinates != null ? coordinates.hashCode() : 0);
        return result;
    }
}
