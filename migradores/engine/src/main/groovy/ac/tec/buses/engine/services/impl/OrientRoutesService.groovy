package ac.tec.buses.engine.services.impl

import ac.tec.buses.engine.model.BusStop
import ac.tec.buses.engine.model.Point
import ac.tec.buses.engine.model.Travel
import ac.tec.buses.engine.services.RoutesService
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory
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
                BusStop busStop = new BusStop(
                        name: stop.name,
                        location: new Point(latitude: stop.location.latitude, longitude: stop.location.longitude),
                        routes: [routeId]
                )
                persistedStops["${stop.location.latitude},${stop.location.longitude}"] = busStop
            }

            // TODO: duplicate prevention validations
            BusStop from = null, to = null
            List<Point> points = []
            path.each { point ->
                BusStop stop = persistedStops["${point.latitude},${point.longitude}"] as BusStop
                if (stop != null) {
                    if (from == null) {
                        from = stop
                        points = []
                    } else if (to == null) {
                        to = stop
                        Travel persistedTravel = from.addToConnectedStops(to)
                        persistedTravel.path = points
                        from = to
                        to = null
                        points = []
                    }
                } else {
                    points << new Point(latitude: point.latitude, longitude: point.longitude)
                }
            }
        }
    }
}
