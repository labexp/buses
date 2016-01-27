package com.enrutatec.application;

import com.mapbox.mapboxsdk.api.ILatLng;
import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.views.MapView;
import com.mapbox.mapboxsdk.views.MapViewListener;
import com.enrutatec.services.RoutesManagerService;
import com.enrutatec.services.impl.RoutesManagerServiceImpl;


public class RouteMarker implements MapViewListener {

    private String name;
    private int price;
    private double duration;
    private double distance;
    private MainActivity activity;

    private RoutesManagerService routeManager = new RoutesManagerServiceImpl();

    public RouteMarker(MainActivity activity){
        this.activity = activity;
    }

    /*public void setData(String name, int price, double duration, double distance){
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.distance = distance;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setRouteManager(RoutesManagerService routeManager) {
        this.routeManager = routeManager;
    }

    @Override
    public void onShowMarker(MapView mapView, Marker marker) {

    }

    @Override
    public void onHideMarker(MapView mapView, Marker marker) {

    }

    @Override
    public void onTapMarker(MapView mapView, Marker marker) {
        String[] data = marker.getTitle().split("/");

        routeManager.showRouteInfo(activity,data[0], Double.parseDouble(data[2]),Long.parseLong(data[1]),Double.parseDouble(data[3]));

        //marker.setTitle("");
    }

    @Override
    public void onLongPressMarker(MapView mapView, Marker marker) {
    }

    @Override
    public void onTapMap(MapView mapView, ILatLng iLatLng) {

    }

    @Override
    public void onLongPressMap(MapView mapView, ILatLng iLatLng) {

    }
}
