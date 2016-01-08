require 'json'
require_relative 'services/route_parser'
require_relative 'data/osm_buses_api'

route = {
    "id": "400",
    "stops": [
        {
            "name": "La parada por el palo",
            "location": {
                "latitude": 9.894389,
                "longitude": -81.38932
            }
        },
        {
            "name": "El rancho de la esquina",
            "location": {
                "latitude": 9.43843,
                "longitude": -84.43784
            }
        }
    ],
    "path": [
        {
            "latitude": 9.894389,
            "longitude": -81.38932
        },
        {
            "latitude": 9.32832,
            "longitude": -81.38232
        },
        {
            "latitude": 10.4384,
            "longitude": -84.438943
        },
        {
            "latitude": 9.43843,
            "longitude": -84.43784
        }
    ]
}

route =  JSON.parse(route.to_json)
parser = RouteParser.new
m_route = parser.parse(route)

Rosemary::Api.base_uri 'api06.dev.openstreetmap.org'
Rosemary::Api.default_timeout 20

auth_client = Rosemary::BasicAuthClient.new('Duxel','duxelosm')
api = Rosemary::Api.new(auth_client)

osmapi = OSMBusesApi.new(api)
p osmapi.post_route(m_route,"Creating test with route id #{m_route.id}")
