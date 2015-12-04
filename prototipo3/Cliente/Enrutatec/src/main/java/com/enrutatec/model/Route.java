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

    public List<LatLng> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<LatLng> coordinates) {
        this.coordinates = coordinates;
    }

    public List<LatLng> getStops() {
        return stops;
    }

    public void setStops(List<LatLng> stops) {
        this.stops = stops;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    private List<LatLng> coordinates = new ArrayList<LatLng>();
    private List<LatLng> stops = new ArrayList<LatLng>();
    private String name;
    private int price;
    private double duracion;
    private double distancia;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;

        if (price != route.price) return false;
        if (Double.compare(route.duracion, duracion) != 0) return false;
        if (Double.compare(route.distancia, distancia) != 0) return false;
        if (coordinates != null ? !coordinates.equals(route.coordinates) : route.coordinates != null)
            return false;
        if (stops != null ? !stops.equals(route.stops) : route.stops != null) return false;
        return !(name != null ? !name.equals(route.name) : route.name != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = coordinates != null ? coordinates.hashCode() : 0;
        result = 31 * result + (stops != null ? stops.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + price;
        temp = Double.doubleToLongBits(duracion);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(distancia);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public JSONObject toJSON(){
        JSONObject jsonRoute = new JSONObject();
        JSONArray jsonRoutePoints = new JSONArray();
        JSONArray jsonParadas = new JSONArray();

        RoutesManagerService routeManager = new RoutesManagerServiceImpl();

        for(LatLng coordinate : coordinates){
            JSONArray point = new JSONArray();
            try {
                point.put(coordinate.getLatitude());
                point.put(coordinate.getLongitude());
                jsonRoutePoints.put(point);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for(LatLng stop : stops){
            JSONArray point = new JSONArray();
            try {
                point.put(stop.getLatitude());
                point.put(stop.getLongitude());

                jsonParadas.put(point);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        JSONObject finalJSON = new JSONObject();

        try {
            jsonRoute.put("coordinate",jsonRoutePoints);
            jsonRoute.put("stops",jsonParadas);
            jsonRoute.put("price",this.price);
            jsonRoute.put("name", this.name);
            jsonRoute.put("duration", this.duracion);
            //this.distancia = routeManager.calcDistance(this.coordinates);
            jsonRoute.put("distance",this.distancia);

            JSONArray finalArray = new JSONArray();
            finalArray.put(jsonRoute);

            finalJSON.put("busRoutes",finalArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }



        Log.d("CREATION", finalJSON.toString());

        return finalJSON;
    }


}
