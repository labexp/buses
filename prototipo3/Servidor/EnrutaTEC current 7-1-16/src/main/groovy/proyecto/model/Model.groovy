/*package proyecto.model

import com.github.eugene.kamenev.orient.graph.Edge
import com.github.eugene.kamenev.orient.graph.Vertex
import groovy.transform.Canonical
import groovy.transform.CompileStatic



@Vertex
@CompileStatic
@Canonical
class Stop {
    String name
    List<Stop> neighborStops

    static mapping = {
        neighborStops(edge: Route)
    }
}

@Edge(from = Stop, to = Stop)
@Canonical
@CompileStatic
class Route {
    String name
    int distanceKms
}*/

package proyecto.model

import com.github.eugene.kamenev.orient.graph.Edge
import com.github.eugene.kamenev.orient.graph.Vertex
import groovy.transform.Canonical
import groovy.transform.CompileStatic



@Vertex
@CompileStatic
@Canonical
class Stop {
    String name
    float latitude
    float longitude
    int price
    List<Stop> neighborStops
    List<String> routes
    static mapping = {
        neighborStops(edge: Route)
    }
}

@Edge(from = Stop, to = Stop)
@Canonical
@CompileStatic
class Route {
    int distance
    List<Float> coordinates
}