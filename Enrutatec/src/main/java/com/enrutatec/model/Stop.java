package com.enrutatec.model;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.List;

public class Stop {
    private String name;
    private LatLng coordinate;
    private double price;
    private List<String> routes;

    public Stop(String name, LatLng coordinate, double price, List<String> routes) {
        this.name = name;
        this.coordinate = coordinate;
        this.price = price;
        this.routes = routes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(LatLng coordinate) {
        this.coordinate = coordinate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getRoutes() {
        return routes;
    }

    public void setRoutes(List<String> routes) {
        this.routes = routes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stop stop = (Stop) o;

        if (Double.compare(stop.price, price) != 0) return false;
        if (name != null ? !name.equals(stop.name) : stop.name != null) return false;
        if (coordinate != null ? !coordinate.equals(stop.coordinate) : stop.coordinate != null)
            return false;
        return !(routes != null ? !routes.equals(stop.routes) : stop.routes != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + (coordinate != null ? coordinate.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (routes != null ? routes.hashCode() : 0);
        return result;
    }
}
