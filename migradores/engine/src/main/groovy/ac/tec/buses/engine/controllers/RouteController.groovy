package ac.tec.buses.engine.controllers

import ac.tec.buses.engine.services.RoutesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

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
@RestController
@RequestMapping('/buses/v0.1/route')
class RouteController extends BaseController {

    @Autowired
    private RoutesService routeService

    /**
     * <code>
     * {
     *     id: '400',
     *     stops: [
     *          {
     *              name: 'Heredia',
     *              location: {
     *                  latitude: 9.894389,
     *                  longitude: -81.38932
     *              }
     *          },
     *          {
     *              name: 'San José',
     *              location: {
     *                  latitude: 9.43843,
     *                  longitude: -84.43784
     *              }
     *          }
     *     ],
     *     path: [
     *          {
     *              latitude: 9.32832,
     *              longitude: -81.38232
     *          },
     *          ...
     *          {
     *              latitude: 10.4384,
     *              longitude: -84.438943
     *          }
     *     ]
     * }
     * </code>
     * @param route
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    Map batchPost(@RequestBody Map route) {
        routeService.save(route.id, route.stops, route.path)
        return [status: 200]
    }
}
