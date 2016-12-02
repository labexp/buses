
package org.labexp.traces;

/**
 * @author Leo
 *
 */
public class MapPoint {
    /*
     * Represents position over latitude.
     */
    private double  x;
    /*
     * Represents position over longitude.
     */
    private double y;

    /**
     * New MapPoint based on latitude and longitude values.
     * @param x Latitude.
     * @param y Longitude.
     */
    public MapPoint(final double x, final double y) {
        this.x = x;
        this.y = y;
    }
    /**
     * Get Latitude.
     * @return latitude
     */
    public double getX() {
        return x;
    }

    /**
     * Set Latitude.
     * @param x latitude
     */
    public void setX(final double x) {
        this.x = x;
    }

    /**
     * Get Longitude.
     * @return longitude
     */
    public double getY() {
        return y;
    }

    /**
     * Set longitude.
     * @param y longitude
     */
    public void setY(final double y) {
        this.y = y;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "MapPoint [latitude=" + x + ", longitude=" + y + "]";
    }
    
    

}
