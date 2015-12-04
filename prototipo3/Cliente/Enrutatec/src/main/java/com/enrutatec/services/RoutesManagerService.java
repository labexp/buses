package com.enrutatec.services;


import com.mapbox.mapboxsdk.geometry.LatLng;
import com.enrutatec.application.MainActivity;
import com.enrutatec.model.Route;

import java.util.List;

public interface RoutesManagerService {
    List<Route> getRoutes();
    void setRouteInfo(Route route, MainActivity activity);
    void showRouteInfo(MainActivity activity, String name, double duration, long price, double distance);
    double calcDistance(List<LatLng> coordinates);
    void addCoordinate(Route route, LatLng coordinate);
    void addStop(Route route, LatLng stop);
}
