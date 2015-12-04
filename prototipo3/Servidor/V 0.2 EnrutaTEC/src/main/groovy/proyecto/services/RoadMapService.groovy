package proyecto.services

import proyecto.model.BusRoute
import proyecto.model.Route


interface RoadMapService {

    /**
     * <code>batch.cities[0].name</code>
     * <code>batch.roads[0].from</code>
     * <code>batch.roads[0].to</code>
     * <code>batch.roads[0].name</code>
     * <code>batch.roads[0].distanceKms</code>
     *
     * @param batch
     */
    void saveBatch(Map batch)

    List<BusRoute> getBusRoutes()
    List<Route> getRoutes()
}
