package proyecto.model

/**
 * Created by santiago on 11/5/15.
 */

import com.github.eugene.kamenev.orient.graph.Edge
import com.github.eugene.kamenev.orient.graph.Vertex
import groovy.transform.Canonical
import groovy.transform.CompileStatic




/*@Vertex
@CompileStatic
@Canonical
class Stop {
    String name
    List<Stop> neighborStops

    static mapping = {
        neighborStops (edge: Route)
    }
}
*/
//@Edge(from = Stop, to = Stop)
@Canonical
@CompileStatic
class Route {
    String name
    int distanceKms
    int price
    double duration
    List<String> stops
    List<String> coordinates
}