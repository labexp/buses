package com.example.xavi.trazador_v10;


import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.Toast;

import com.mapbox.mapboxsdk.geometry.LatLng;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;

/**
 * Created by xavi on 29/09/16.
 */
public class GPS_Service extends Service {

    private LocationListener listener;
    private LocationManager locationManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {


        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Intent i = new Intent("location_update");
                //i.putExtra("coordinates",location.getLongitude() + " " + location.getLatitude());

                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                LatLng newPosition = new LatLng(latitude, longitude);

                i.putExtra("latitude", latitude);
                i.putExtra("longitude", longitude);
                i.putExtra("coordinates", location.getLongitude() + " " + location.getLatitude());
                i.putExtra("newPosition", newPosition);

                sendBroadcast(i);
                if (MainActivity.state == 1) {

                    MainActivity.trace.addPoint(location.getLatitude(), location.getLongitude());
                    GeoPoint position = new GeoPoint(location.getLatitude(), location.getLongitude());
                    String nombre = "punto";
                    addPoint(position, nombre, R.drawable.ic_menu_mylocation);


                } else if (MainActivity.state == 2) {
                    //Toast.makeText(getBaseContext(), "Agrega posicion como parada", Toast.LENGTH_SHORT).show();
                    MainActivity.trace.addStop(location.getLatitude(), location.getLongitude());
                    GeoPoint position = new GeoPoint(location.getLatitude(), location.getLongitude());
                    String nombre = "parada";
                    addPoint(position, nombre, R.drawable.ic_launcher);
                    MainActivity.state = 1;
                }


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        //noinspection MissingPermission


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, listener);




    }

    private void addPoint(GeoPoint position, String nombre, int drawable ) {
        Marker point;
        point = new Marker(MainActivity.map);
        point.setPosition(position);
        point.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

        Drawable myIcon = ResourcesCompat.getDrawable(getResources(), drawable, null);
        point.setIcon(myIcon);
        MainActivity.map.getOverlays().add(point);
        point.setTitle(nombre);
        MainActivity.points.add(point);
        MainActivity.map.invalidate();

    }

    @Override
    public void onTaskRemoved (Intent rootIntent){
        MainActivity.trace.discarded();
        super.onTaskRemoved (rootIntent);
    }
    @Override
    public void onDestroy() {


        super.onDestroy();
        if(locationManager != null)
            //noinspection MissingPermission
            locationManager.removeUpdates(listener);
    }

}
