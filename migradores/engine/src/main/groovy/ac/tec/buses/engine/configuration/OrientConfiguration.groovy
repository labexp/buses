package ac.tec.buses.engine.configuration

import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

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

@Configuration
class OrientConfiguration {

    @Value('${ac.tec.buses.orient.url}')
    String url

    @Value('${ac.tec.buses.orient.usr}')
    String usr

    @Value('${ac.tec.buses.orient.pwd}')
    String pwd

    @Bean
    OrientGraphFactory orientGraphFactory() {
        return new OrientGraphFactory(url, usr, pwd).setupPool(5, 15)
    }

}
