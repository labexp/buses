require 'webrick'
require_relative '../../buses-common/buses_api/buses_api'

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
          result = buses_api.get_route(route_id)
        else
          result = buses_api.get_routes
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