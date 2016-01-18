require 'webrick'
require_relative '../buses-common/buses_api/buses_api'

def get_route
  {
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
end

def all_routes
    [ "400", "500","600","700","800","900","1000","1100","1200","1300","1400","1500","1600","1700","1800","1900","2000","2100","2200","2300"]
end


class WebServer < WEBrick::HTTPServlet::AbstractServlet

  def do_GET (request, response)
    response.header['Access-Control-Allow-Origin']   = '*'
    response.header['Access-Control-Request-Method'] = '*'

    case request.path
      when "/routes"
        buses_api = BusesApi::Api.new
        response.status = 200
        response.content_type = "application/json"
        puts "Respondiendo"
        if request.query["id"]
          route_id = request.query["id"]
          result = get_route #buses_api.get_route(route_id)
        else
          result = all_routes #buses_api.get_routes
        end
        response.body = result.to_json.to_s + "\n"
      else
        response.body = "No such method\n"
    end

  end

end

server = WEBrick::HTTPServer.new(:Port => 1234)
server.mount "/", WebServer

trap("INT") {
  server.shutdown
}

server.start