package proyecto.controllers

import proyecto.model.Route
import proyecto.model.Stop


abstract class BaseController {

    protected Map stopToJson(Stop stop) { [rid: stop.id as String, name: stop.name] }

    protected Map routeToJson(Route route) {
        [
            rid: route.id as String,
            name: route.name,
            distanceKms: route.distanceKms,
            from: stopToJson(route.out),
            to: stopToJson(route.in)
        ]
    }
}
