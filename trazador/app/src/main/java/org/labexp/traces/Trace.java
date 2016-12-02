/**
 * 
 */
package org.labexp.traces;

import java.util.ArrayList;

/**
 * @author Leo
 *
 */
public class Trace {
    /*
     * Trace identifier  (provide by the server)
     */
    private String traceId;
    /*
     * Device identifier (provide by the user)
     */
    private String deviceId;
    /*
     * The url of the Api server
     */
    private String apiBaseUrl;
    /**
     * @return the trace id
     */
    public final String getTraceId() {
        return traceId;
    }
    /**
     * @return the device id
     */
    public final String getDeviceId() {
        return deviceId;
    }
    
    
    /**
     * @return the apiBaseUrl
     */
    public String getApiBaseUrl() {
        return apiBaseUrl;
    }

    /**
     * Creates a trace using a unique device identificator.
     * @param deviceId
     */
    public Trace(String deviceId) {
        super();
        this.deviceId = deviceId;
    }
    
    /**
     * Starts the trace on the server.
     * @param apiBaseUrl the base url of the API Server: 
     *  example: "http://10.173.1.153"
     * @return a unique trace id and this is saved on traceId
     * 
     */
    public void start(String apiBaseUrl) throws java.io.IOException{
        this.apiBaseUrl = apiBaseUrl;  
        ApiConnector.createClient(apiBaseUrl);
        this.traceId = ApiConnector.startTrace (deviceId);
    }
    
    /**
     * @param routeCode code
     * @param routeName name
     * @param routePrice prices
     * @return transaction success
     */
    public boolean setMetadata(
            final String routeCode,
            final String routeName,
            final String routePrice) {
        Metadata metadata = new Metadata(routeCode, routeName, routePrice);
        setMetadata(metadata);
        return true;
    }

    /**
     * @param metadata trace metadata
     * @return transaction success
     */
    public boolean setMetadata(final Metadata metadata) {
        return ApiConnector.setMetadata(deviceId, traceId, metadata);
        
    }

    /**
     * @param points List of points to add
     * @return transaction success
     */
    public boolean addPoints(final ArrayList<MapPoint> points) {
        return ApiConnector.addPoints(deviceId, traceId, points);
    }
    
    /**
     * @param x latitude.
     * @param y longitude.
     * @return transaction success
     */
    public boolean addPoint(final double x, final double y) {
        MapPoint point = new MapPoint(x, y);
        ArrayList<MapPoint> points = new ArrayList<MapPoint>();
        points.add(point);
        addPoints (points);
        return true;
    }
    
    /**
     * @param point position
     * @return transaction success
     */
    public boolean addPoint(final MapPoint point) {
        ArrayList<MapPoint> points = new ArrayList<MapPoint>();
        points.add(point);
        addPoints (points);
        return true;
    }
    
    /**
     * @param stop stop position
     * @return transaction success
     */
    public boolean addStop(final MapPoint stop) {
        return ApiConnector.addStop(deviceId, traceId, stop);
    }
    
    /**
     * @param x latitude.
     * @param y longitude.
     * @return transaction success
     */
    public boolean addStop(final double x, final double y) {
        MapPoint point = new MapPoint(x, y);
        return addStop(point); 
    }
    
    
    
    /**
     * Mark trace as finished on the server
     * @return transaction success
     */
    public boolean finished() {
        ApiConnector.finished(traceId);
        return true;
    }
    
    /**
     * Mark trace as finished on the server
     * @return transaction success
     */
    public boolean discarded() {
        ApiConnector.discarded(traceId);
        return true;
    }

}
