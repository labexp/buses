package org.labexp.traces;

/**
 * Used to save the metadata related to a trace
 * @author Leo
 *
 */
public class Metadata {
    /**
     * The code of the route.
     */
    private String routeCode;
    /**
     * The name of the route.
     */
    private String routeName;
    /**
     * The price of the route.
     */
    private String routePrice;
    /**
     * @return the route code
     */
    public String getRouteCode() {
        return routeCode;
    }
    /**
     * @param routeCode the route code to set
     */
    public void setRouteCode(final String routeCode) {
        this.routeCode = routeCode;
    }
    /**
     * @return the route name
     */
    public String getRouteName() {
        return routeName;
    }
    /**
     * @param routeName the route name to set
     */
    public void setRouteName(final String routeName) {
        this.routeName = routeName;
    }
    /**
     * @return the route price
     */
    public String getRoutePrice() {
        return routePrice;
    }
    /**
     * @param routePrice the route price to set
     */
    public void setRoutePrice(final String routePrice) {
        this.routePrice = routePrice;
    }
    /**
     * @param routeCode code
     * @param routeName name
     * @param routePrice price
     */
    public Metadata(final String routeCode, final String routeName, final String routePrice) {
        super();
        this.routeCode = routeCode;
        this.routeName = routeName;
        this.routePrice = routePrice;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Metadata [Code=" + routeCode + ", Name=" + routeName + ", Price=" + routePrice + "]";
    }

}
