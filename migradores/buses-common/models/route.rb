require 'json'

class Route

  attr_accessor :id, :path, :stops

  def initialize(id, path, stops)
    @id = id
    @path = path # Point List
    @stops = stops # Stop List
  end

  def to_json
    points_array = []
    @path.each do |point|
      points_array.push( {latitude: point.lat, longitude:point.lon})
    end

    stops_array = []
    @stops.each do |stop|
      stops_array.push({name:stop.name,location:{latitude:stop.location.lat,longitude:stop.location.lon}})
    end

    route = {}
    route['id'] = @id.to_s
    route['path'] = points_array
    route['stops'] = stops_array
    route
  end
end

