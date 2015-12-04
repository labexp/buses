package proyecto.model

import com.github.eugene.kamenev.orient.graph.Edge
import com.github.eugene.kamenev.orient.graph.Vertex
import groovy.transform.Canonical
import groovy.transform.CompileStatic



@Vertex
@CompileStatic
@Canonical
class BusRoute {
    String name
    //String coordinate
    List<String> coordinate
    List<String> stops
    int price
    double distance
    double duration

    List<BusRoute> neighborBusRoutes


    static mapping = {
        neighborBusRoutes(edge: Route)
    }
}

@Edge(from = BusRoute, to = BusRoute)
@Canonical
@CompileStatic
class Route {
    String name
    int distanceKms
}