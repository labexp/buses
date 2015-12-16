package ac.tec.buses.engine.model

import com.github.eugene.kamenev.orient.document.OrientDocument
import com.github.eugene.kamenev.orient.graph.Edge
import com.github.eugene.kamenev.orient.graph.Vertex
import com.orientechnologies.orient.core.metadata.schema.OType
import groovy.transform.Canonical
import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode

/**
 *   buses engine
 *   Copyright (C) 2015  Laboratorio Experimental IC-Alajuela TEC
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License along
 *   with this program; if not, write to the Free Software Foundation, Inc.,
 *   51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

@OrientDocument
@CompileStatic
@EqualsAndHashCode
class Point {
    double latitude
    double longitude

    Map toJson() { [latitude: latitude, longitude: longitude] }
}

@Vertex
@CompileStatic
@Canonical
class BusStop {
    String name
    Point location
    List<String> routes = []
    List<BusStop> connectedStops

    static mapping = {
        location(type: OType.EMBEDDED)
        connectedStops(edge: Travel)
    }

    Map toJson() { [
            name: name,
            location: location.toJson(),
            routes: routes
    ] }
}

@Edge(from = BusStop, to = BusStop)
@Canonical
@CompileStatic
class Travel {
    List<Point> path = []

    static mapping = {
        path(type: OType.EMBEDDEDLIST)
    }

    Map toJson() { [path: path*.toJson()] }
}