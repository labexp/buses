package proyecto.controllers

import proyecto.model.BusRoute
import proyecto.model.Route
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import proyecto.services.RoadMapService


@RestController
@RequestMapping('/roadmap')
class RoadMapController extends BaseController {

    @Autowired
    private RoadMapService roadMapService

    @RequestMapping(value = '/batch', method = RequestMethod.POST)
    @ResponseBody
    Map batchPost(@RequestBody Map batch) {
        roadMapService.saveBatch(batch)
        return [status: 200]
    }

    @RequestMapping(value = '/busRoute', method = RequestMethod.GET)
    @ResponseBody
    def busRouteGet() {
        // la conversión directa de busRoute a JSON no es posible debido a la implementación interna de BusRoute
        roadMapService.busRoutes.collect { BusRoute busRoute -> busRouteToJson(busRoute) }
    }

    @RequestMapping(value = '/route', method = RequestMethod.GET)
    @ResponseBody
    def routeGet() {
        roadMapService.routes.collect { Route route -> routeToJson(route) }
    }
}
