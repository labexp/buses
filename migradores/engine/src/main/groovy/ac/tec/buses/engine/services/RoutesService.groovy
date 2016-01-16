package ac.tec.buses.engine.services

import ac.tec.buses.engine.model.BusStop
import ac.tec.buses.engine.model.Point

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
interface RoutesService {

    /**
     * @param routeId
     * @param stops <code>[[name: 'La Parada', location: [latitude: 9.947145, longitude: -84.051987]]</code>
     * @param path <code>[[latitude: 9.947145, longitude: -84.051987], ...]</code> Contains all the points in order from beginning to end for the route, including the points for the stops
     */
    void save(String routeId, List<Map> stops, List<Map> path)

    def getById(String id)
}
