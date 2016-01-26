
package proyecto.services.impl

import proyecto.model.Route
import proyecto.model.Stop
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import proyecto.services.RoadMapService
import sun.net.www.http.HttpClient


@Service
class OrientRoadMapService implements RoadMapService {

    @Autowired
    OrientGraphFactory graphFactory

    @Override
    void saveBatch(Map batch) {

        // TODO: validation of the batch map structure

        def persistedStops = [:]

        graphFactory.withTransaction {
            // TODO: duplicate prevention validations
            batch.stops?.each { stop ->
                persistedStops[stop.name] = new Stop(name: stop.name,latitude: stop.latitude, longitude: stop.longitude, price: stop.price, routes: stop.routes)
               // persistedStops[stop.name] = new Stop(name: stop.name)

            }

            // TODO: duplicate prevention validations
            batch.routes?.each { route ->
                //Hay que verificar que exista la ruta mediante el persistedStops y que agarre los datos de ahi
                //Stop from = new Stop (name : route.from)
                //Stop to = new Stop (name :route.to)
                Stop from = persistedStops[route.from]
                Stop to = persistedStops[route.to]


                Route persistedRoute = from.addToNeighborStops(to)
                persistedRoute.coordinates = route.coordinate
                persistedRoute.distance = route.distance
//La ruta puede ser para ambos lados, pero si se comenta el siguiente bloque de codigo, se vuelve unidireccional
                persistedRoute = to.addToNeighborStops(from)
                persistedRoute.coordinates = route.coordinate
                persistedRoute.distance = route.distance
            }
        }
    }

    @Override
    List<Stop> getStops() {
        return graphFactory.withTransaction {
            return Stop.graphQuery('select from Stop')
        }
    }

    @Override
    List<Route> getRoutes() {

        return graphFactory.withTransaction {
            return Route.graphQuery('select from Route')
        }
    }
}
/*
package proyecto.services.impl

import proyecto.model.Route
import proyecto.model.Stop
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

        def persistedStops = [:]

        graphFactory.withTransaction {
            // TODO: duplicate prevention validations
            batch.stops?.each { stop ->
                persistedStops[stop.name] = new Stop(name: stop.name)
            }

            // TODO: duplicate prevention validations
            batch.routes?.each { route ->
                Stop from = persistedStops[route.from]
                Stop to = persistedStops[route.to]

                Route persistedRoute = from.addToNeighborStops(to)
                persistedRoute.name = route.name
                persistedRoute.distanceKms = route.distanceKms

                persistedRoute = to.addToNeighborStops(from)
                persistedRoute.name = route.name
                persistedRoute.distanceKms = route.distanceKms
            }
        }
    }

    @Override
    List<Stop> getStops() {
        return graphFactory.withTransaction {
            return Stop.graphQuery('select from Stop')
        }
    }

    @Override
    List<Route> getRoutes() {
        return graphFactory.withTransaction {
            return Route.graphQuery('select from Route')
        }
    }
}
*/