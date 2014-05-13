package com.example.openstreetmap;

import org.osmdroid.api.IMapController;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MinimapOverlay;

import com.example.openstreetmap.R;
 
import android.app.Activity;
import android.os.Bundle;
 
public class MainActivity extends Activity {
 
 private MapView myOpenMapView;
 
 @Override
 protected void onCreate(Bundle savedInstanceState) {
 	super.onCreate(savedInstanceState);
 	setContentView(R.layout.main);
 	myOpenMapView = (MapView)findViewById(R.id.openmapview);
	myOpenMapView.setBuiltInZoomControls(true);
	myOpenMapView.setMultiTouchControls(true);
	IMapController myMapController = myOpenMapView.getController();
	myMapController.setZoom(4);
   
  GeoPoint startPoint = new GeoPoint(98.13, -1.63);
  myMapController.setCenter(startPoint);
  myOpenMapView.setUseDataConnection(true);
  
  MinimapOverlay miniMapOverlay = new MinimapOverlay(this, myOpenMapView.getTileRequestCompleteHandler());
  miniMapOverlay.setZoomDifference(5);
  miniMapOverlay.setHeight(200);
  miniMapOverlay.setWidth(200);
  myOpenMapView.getOverlays().add(miniMapOverlay);
 }
}