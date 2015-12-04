package com.enrutatec.services.impl;


import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.Log;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.enrutatec.application.MainActivity;
import com.enrutatec.model.Route;
import com.enrutatec.services.RoutesManagerService;

import java.util.List;

public class RoutesManagerServiceImpl implements RoutesManagerService{

    private int price;
    private String name;
    private Route finalRoute;

    private MaterialDialog priceDialog;
    private MaterialDialog nameDialog;

    //Obtener todas las rutas de la base de datos
    //// TODO: 03/11/15
    @Override
    public List<Route> getRoutes() {
        return null;
    }

    @Override
    public void setRouteInfo(Route route, final MainActivity activity) {
        this.finalRoute=route;
        priceDialog = new MaterialDialog.Builder(activity)
                .title("Nueva ruta")
                .content("Precio")
                .inputType(InputType.TYPE_CLASS_NUMBER |
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .inputRange(2, 5)
                .neutralText("Parada") //// TODO: 16/11/15 Cambiar por valor de string
                .autoDismiss(false)
                .positiveText("Ok")
                .positiveColor(Color.WHITE)
                .neutralColor(Color.WHITE)
                .input("", "", false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        Log.d("CREATION", input.toString());
                        finalRoute.setPrice(Integer.parseInt(input.toString()));

                        priceDialog.dismiss();
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        addStop(finalRoute, new LatLng(activity.getLatitude(), activity.getLongitude()));
                    }
                })
                .show();


        nameDialog = new MaterialDialog.Builder(activity)
                .title("Nueva ruta")
                .content("Nombre")
                .inputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PERSON_NAME |
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .inputRange(2, 30)
                .neutralText("Parada")//// TODO: 16/11/15 Cambiar por un valor de string
                .autoDismiss(false)
                .positiveText("Siguiente")
                .positiveColor(Color.WHITE)
                .neutralColor(Color.WHITE)
                .input("", "", false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        Log.d("CREATION", input.toString());
                        finalRoute.setName(input.toString());

                        nameDialog.dismiss();

                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        addStop(finalRoute,new LatLng(activity.getLatitude(),activity.getLongitude()));
                    }
                })
                .show();
    }

    @Override
    public void showRouteInfo(MainActivity activity, String name, double duration, long price, double distance) {
        new MaterialDialog.Builder(activity)
                .title("Información de la ruta")
                .content("Nombre: "+name+"\nPrecio: "+price+" colones\nDuración: "+duration+" minutos\nDistancia: "+distance+" kilometros")
                .positiveText("Ok")
                .show();
    }

    @Override
    public double calcDistance(List<LatLng> coordinates) {
        double distance = 0;
        for(int i=0; i<coordinates.size()-1;i++){
            distance+=coordinates.get(i).distanceTo(coordinates.get(i + 1));
        }
        Log.d("CREATION", String.valueOf(distance));
        return distance/1000;
    }

    @Override
    public void addCoordinate(Route route, LatLng coordinate) {
        List<LatLng> coordinates = route.getCoordinates();
        coordinates.add(coordinate);
        route.setCoordinates(coordinates);
    }

    @Override
    public void addStop(Route route, LatLng stop) {
        List<LatLng> stops = route.getStops();
        stops.add(stop);
        route.setStops(stops);
    }
}
