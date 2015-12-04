package proyecto.controllers

import proyecto.model.Route
import proyecto.model.Stop


abstract class BaseController {

    protected Map stopToJson(Stop stop) { [rid: stop.id as String, name: stop.name,routes: stop.routes, price:stop.price, latitude: stop.latitude, longitude: stop.longitude] }

    protected Map routeToJson(Route route) {
        [
            rid: route.id as String,
            coordinates: route.coordinates,
            distance: route.distance,
            from: stopToJson(route.out),
            to: stopToJson(route.in)
        ]
    }
}
