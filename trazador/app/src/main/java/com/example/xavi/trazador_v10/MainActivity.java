package com.example.xavi.trazador_v10;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.SettingInjectorService;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.SortedList;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.labexp.traces.Trace;
import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;


import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.views.overlay.Marker;
import com.mapbox.mapboxsdk.geometry.LatLng;
import org.osmdroid.views.overlay.PathOverlay;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.mylocation.IMyLocationProvider;




public class MainActivity extends FragmentActivity implements NoticeDialogFragment.NoticeDialogListener{

    public static Button btnInicio;
    private FloatingActionButton btnStop;
    private FloatingActionButton btnInfo;


    public static Trace trace;

    private Button btnParada;
    boolean isPressed = false;

    private BroadcastReceiver broadcastReceiver;

    Location location;

    private LocationManager locationManager;
    private LocationListener locationListener;




    private double latitud = 0.0;
    private double longitud = 0.0;



    static MapView map;
    static Marker actualPositionMarker;

    static ArrayList<Marker> points = new ArrayList<>();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //important! set your user agent to prevent getting banned from the osm servers
        OpenStreetMapTileProviderConstants.setUserAgentValue(BuildConfig.APPLICATION_ID);


        Intent i = new Intent(getApplicationContext(),GPS_Service.class);
        startService(i);

        btnInicio = (Button) findViewById(R.id.btn_iniciar);
        btnStop = (FloatingActionButton) findViewById(R.id.btn_stop);
        btnInfo = (FloatingActionButton) findViewById(R.id.btn_info);



        map = (MapView) findViewById(R.id.map);

        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        map.getController().setZoom(18);



        if(!runtime_permissions()) {
            enable_buttons();
        }
        AndroidThreeTen.init(this);




    }
    @Override
    protected void onResume() {
        super.onResume();
        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    /*Toast.makeText(getApplicationContext(), "Lat: " + intent.getExtras().get("latitude") + "Long: " +
                            intent.getExtras().get("longitude"),
                            Toast.LENGTH_LONG).show();*/

                    latitud = Double.parseDouble(intent.getExtras().get("latitude").toString());
                    longitud = Double.parseDouble(intent.getExtras().get("longitude").toString());

                    GeoPoint position = new GeoPoint(latitud,longitud);
                    map.getController().setCenter(position);



                    marketCurrentPosition(position);

                }
            };
        }

        registerReceiver(broadcastReceiver, new IntentFilter("location_update"));
    }

    private void marketCurrentPosition(GeoPoint position) {
        map.getOverlays().remove(actualPositionMarker);
        actualPositionMarker = new Marker(map);
        actualPositionMarker.setPosition(position);
        actualPositionMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        Drawable myIcon = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_follow_me, null);
        actualPositionMarker.setIcon(myIcon);
        map.getOverlays().add(actualPositionMarker);
        map.invalidate();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }

    static int state = 0;
    private void enable_buttons(){


        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v == btnInicio && state == 0) {
                    final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
                    final String tmDevice, tmSerial, androidId;
                    tmDevice = "" + tm.getDeviceId();
                    tmSerial = "" + tm.getSimSerialNumber();
                    androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
                    UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
                    String deviceId = deviceUuid.toString();
                    MainActivity.trace = new Trace(deviceId);

                    try {
                        MainActivity.trace.start("http://buses.tec.siua.ac.cr");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    state = 1;
                    //Toast.makeText(getBaseContext(), "Inicio de ruta", Toast.LENGTH_SHORT).show();
                    btnInicio.setBackgroundResource( R.drawable.btn_parada);
                    GeoPoint parada = new GeoPoint(latitud, longitud);
                    marketCurrentPosition(parada);
                }
                else if (v == btnInicio && state == 1){
                    state = 2;
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                if (state != 0) {
                    Intent myIntent = new Intent(getApplicationContext(), Finalize.class);
                    startActivityForResult(myIntent, 0);
                }

            }

        });

        btnInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){

                    Intent myIntent = new Intent(getApplicationContext(), metadata.class);
                    startActivityForResult(myIntent, 0);

            }

        });



    }


    private boolean runtime_permissions(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,Manifest.permission
                .ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED
                 && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE ) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.INTERNET}, 100);

            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 100){

            if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager
                    .PERMISSION_GRANTED){
                enable_buttons();
            }else {
                runtime_permissions();
            }


        }
    }

    public void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new NoticeDialogFragment();
        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");



    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

        Toast.makeText(getBaseContext(),"Aceptar",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

        Toast.makeText(getBaseContext(),"Cancelar",Toast.LENGTH_SHORT).show();

    }
}

