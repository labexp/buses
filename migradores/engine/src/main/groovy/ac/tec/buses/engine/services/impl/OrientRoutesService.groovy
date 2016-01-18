package ac.tec.buses.engine.services.impl

import ac.tec.buses.engine.model.BusStop
import ac.tec.buses.engine.model.Point
import ac.tec.buses.engine.model.Travel
import ac.tec.buses.engine.services.RoutesService
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

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
@Service
@CompileStatic
class OrientRoutesService implements RoutesService {

    @Autowired
    OrientGraphFactory graphFactory

    @Override
    void save(String routeId, List<Map> stops, List<Map> path) {
        //TODO: validate stops and path structure
        graphFactory.withTransaction {
            // TODO: duplicate prevention validations
            def persistedStops = [:]
            stops.each { stop ->
                Map location = (Map) stop.location
                Point locationPoint = new Point(latitude: (double) location.latitude, longitude: (double) location.longitude)
                BusStop busStop = new BusStop(
                        name: (String) stop.name,
                        location: locationPoint,
                        routes: [routeId]
                )
                persistedStops["${location.latitude},${location.longitude}".toString()] = busStop
            }

            // TODO: duplicate prevention validations
            BusStop from = null, to = null
            List<Point> points = []
            path.each { point ->
                BusStop stop = persistedStops["${point.latitude},${point.longitude}".toString()] as BusStop
                if (stop != null) {
                    if (from == null) {
                        from = stop
                        points = []
                    } else if (to == null) {
                        to = stop
                        Travel persistedTravel = from.addToConnectedStops(to)
//                        persistedTravel.route = routeId
                        persistedTravel.path = points
                        from = to
                        to = null
                        points = []
                    }
                } else {
                    points << new Point(latitude: (double) point.latitude, longitude: (double) point.longitude)
                }
            }
        }
    }

    @Override
    def getById(String id) {
        Map result = [id: id, stops: [], path: []]
        return graphFactory.withTransaction {
            BusStop firstStop = BusStop.graphQuery('SELECT FROM BusStop WHERE :routeId IN routes ORDER BY @rid LIMIT 1', [routeId: id])
            traverse(firstStop, result, id)
        }
    }

    def traverse(BusStop stop, Map result, String routeId) {
        (result.stops as List) << toJson(stop)
        (result.path as List) << toJson(stop.location)

        def edges = stop.vertex.pipe().inE('Travel').filter { it.getProperty('route') == routeId }.toList(Travel.class)
        if (edges != null && !edges.empty) {
            Travel travel = edges[0]
            travel.path.each { Point point ->
                (result.path as List) << toJson(point)
            }

            traverse(travel.out, result, routeId)
        }

        return result
    }

    private Map toJson(BusStop stop) {
        [
                name: stop.name,
                location: toJson(stop.location),
                routes: stop.routes
        ]
    }

    private Map toJson(Point point) {
        [
                latitude: point.latitude,
                longitude: point.longitude
        ]
    }

    private Map toJson(Travel travel) {
        [
                path: travel.path.collect { point ->
                    toJson(point)
                }
        ]
    }
}
