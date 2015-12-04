package proyecto.controllers

import proyecto.model.Route
import proyecto.model.BusRoute


abstract class BaseController {

    protected Map busRouteToJson(BusRoute busRoute) { [rid: busRoute.id as String, name: busRoute.name,coordinate: busRoute.coordinate, stops: busRoute.stops, price: busRoute.price, distance: busRoute.distance, duration: busRoute.duration] }

    protected Map routeToJson(Route route) {
        [
            rid: route.id as String,
            name: route.name,
            distanceKms: route.distanceKms,
            from: busRouteToJson(route.out),
            to: busRouteToJson(route.in)
        ]
    }
}
