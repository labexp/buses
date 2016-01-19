#!/usr/bin/env ruby

require 'webrick'
require_relative '../buses-common/buses_api/buses_api'

=begin
    WEBrick is a Ruby library that makes it easy to build an HTTP server with Ruby.
    It comes with most installations of Ruby by default (it’s part of the standard library),
    so you can usually create a basic web/HTTP server with only several lines of code.

    The following code creates a generic WEBrick server on the local machine on port 1234,
    shuts the server down if the process is interrupted (often done with Ctrl+C).
    This example lets you call the URL's: "add" and "subtract" and pass through arguments to them
    Example usage:
        http://localhost:1234/ (this will show the specified error message)
        http://localhost:1234/add?a=10&b=10
        http://localhost:1234/subtract?a=10&b=9
=end

class MyNormalClass
  def self.add (a, b)
    a.to_i + b.to_i
  end

  def self.subtract (a, b)
    a.to_i - b.to_i
  end
end


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


class MyServlet < WEBrick::HTTPServlet::AbstractServlet
  def do_GET (request, response)
    response.header['Access-Control-Allow-Origin'] = '*'
    response.header['Access-Control-Request-Method'] = '*'

    if request.query["id"]
      route_id = request.query["id"]
      response.status = 200
      response.content_type = "application/json"
      result = nil

      case request.path
        when "/routes"
          buses_api = BusesApi::Api.new
          result = get_route #buses_api.get_route(route_id)
        else
          result = "No such method"
      end
      puts "Respondiendo"
      result = result.to_json
      p result
      response.body = result.to_s + "\n"
    else
      response.status = 200
      response.body = "You did not provide the correct parameters"
    end
  end
end

server = WEBrick::HTTPServer.new(:Port => 1234)

server.mount "/", MyServlet

trap("INT") {
  server.shutdown
}

server.start