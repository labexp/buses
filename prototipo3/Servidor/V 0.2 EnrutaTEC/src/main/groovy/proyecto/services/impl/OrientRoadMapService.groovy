package proyecto.services.impl

import proyecto.model.Route
import proyecto.model.BusRoute
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import proyecto.services.RoadMapService


@Service
class OrientRoadMapService implements RoadMapService {

    @Autowired
    OrientGraphFactory graphFactory

    @Override
    void saveBatch(Map batch) {

        // TODO: validation of the batch map structure

        def persistedBusRoutes = [:]

        graphFactory.withTransaction {
            // TODO: duplicate prevention validations
            batch.busRoutes?.each { busRoute ->
                persistedBusRoutes[busRoute] = new BusRoute(name: busRoute.name, coordinate: busRoute.coordinate, stops: busRoute.stops, price: busRoute.price, distance: busRoute.distance, duration: busRoute.duration)
            }

            // TODO: duplicate prevention validations
           // batch.routes?.each { route ->
            //    BusRoute from = persistedBusRoutes[route.from]
            //    BusRoute to = persistedBusRoutes[route.to]

            //    Route persistedRoute = from.addToNeighborBusRoutes(to)
            //    persistedRoute.name = route.name
             //   persistedRoute.distanceKms = route.distanceKms

              //  persistedRoute = to.addToNeighborBusRoutes(from)
             //   persistedRoute.name = route.name
              //  persistedRoute.distanceKms = route.distanceKms
          //  }
        }
    }

    @Override
    List<BusRoute> getBusRoutes() {
        return graphFactory.withTransaction {
            return BusRoute.graphQuery('select from BusRoute')
        }
    }

    @Override
    List<Route> getRoutes() {
        return graphFactory.withTransaction {
            return Route.graphQuery('select from Route')
        }
    }
}
