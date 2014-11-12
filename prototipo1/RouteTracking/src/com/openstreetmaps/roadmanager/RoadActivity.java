package com.openstreetmaps.roadmanager;

import java.util.ArrayList;
import java.util.List;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.bonuspack.overlays.Polyline;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import com.openstreetmaps.roadmanager.R;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class RoadActivity extends Activity implements LocationListener{

    private MapView mapView;
    private MapController mapController;
    private LocationManager locationManager;
    private ItemizedOverlay<OverlayItem> myLocationOverlay;
    private ResourceProxy resourceProxy;
    
    private int longitude = 10019446; 
    private int latitude = -841970462;
    
    private List<OverlayItem> items;
    private List<GeoPoint> waypoints=new ArrayList<GeoPoint>();
    
    Road roadToDraw;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        resourceProxy = new DefaultResourceProxyImpl(getApplicationContext());
        
        mapView = (MapView) this.findViewById(R.id.openmapview);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        
        mapController = (MapController) mapView.getController();
        mapController.setZoom(15);   
        
        GeoPoint currentPoint = new GeoPoint(longitude, latitude);
        GeoPoint itemPoint = new GeoPoint(longitude + 2000, latitude + 2000); 
                                                                           
        mapController.setCenter(currentPoint);


        items = new ArrayList<OverlayItem>();
        items.add(new OverlayItem("Current position", itemPoint.toString(), itemPoint));

        this.myLocationOverlay = new ItemizedIconOverlay<OverlayItem>(items,
                (OnItemGestureListener<OverlayItem>) new ItemActionHandler() , resourceProxy);
        this.mapView.getOverlays().add(this.myLocationOverlay);
        mapView.invalidate();
        
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);  
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 25, 
                this);
    }
    
    public void onLocationChanged(Location location) {
    	
        latitude = (int) (location.getLatitude() * 1E6);
        longitude = (int) (location.getLongitude() * 1E6);
        Toast.makeText(RoadActivity.this,
                "Location changed. Lat:" + latitude + " long:" + longitude,
                Toast.LENGTH_LONG).show();
        GeoPoint newPosition = new GeoPoint(latitude, longitude);
        mapController.setCenter(newPosition);
        items.clear(); 
        items.add(new OverlayItem("Current position", newPosition.toString(), newPosition));
        this.myLocationOverlay = new ItemizedIconOverlay<OverlayItem>(items,
                new ItemActionHandler() , resourceProxy);
        this.mapView.getOverlays().clear();
        this.mapView.getOverlays().add(this.myLocationOverlay);
        
        waypoints.add(newPosition);
        
        if(startDrawing){	        	    
	        for(GeoPoint g:waypoints){
				Log.v("GeoPoint", g.toString());
			}
	        
	        roadToDraw=new Road((ArrayList<GeoPoint>) waypoints);
			Polyline roadOverlay = RoadManager.buildRoadOverlay(roadToDraw, this);
			mapView.getOverlays().add(roadOverlay);
        }
        
        mapView.invalidate();
    }
    
    boolean startDrawing=false;
    public void setStartDrawing(View view){
    	this.startDrawing=!startDrawing;
    	TextView tx=(TextView) findViewById(R.id.button1);
    	if(startDrawing){   		
    		tx.setText("Stop");
    	}
    	else{
    		tx.setText("Start");
    		waypoints.clear();
    	}
    }

    @Override
    public void onProviderDisabled(String arg0) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
	
	
    class ItemActionHandler implements OnItemGestureListener<OverlayItem> {
        @Override
        public boolean onItemLongPress(int index, OverlayItem item) {
            Toast.makeText(RoadActivity.this, item.getSnippet(),
                    Toast.LENGTH_LONG).show();
            return false;
        }
        
        @Override
        public boolean onItemSingleTapUp(int index, OverlayItem item) {
            Toast.makeText(RoadActivity.this, item.getTitle(),
                    Toast.LENGTH_LONG).show();
            return true;
        } 
    }
}