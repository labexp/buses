require_relative 'point'
require_relative 'stop'
require 'json'

class Route
  attr_accessor :id, :path, :stops
  def initialize(params)
    @id ||= params[:id]
    @path = params[:path]
    @stops = params[:stops]
    @path  ||= []
    @stops ||=[]
  end

  def to_json
    points_array = []
    @path.each do |point|
      points_array.push( {latitude: point.lat, longitude:point.lon})
    end

    stops_array = []
    @stops.each do |stop|
      stops_array.push({name:stop.name,location:{latitude:stop.lat,longitude:stop.lon}})
    end

    route = {}
    route['id'] = @id.to_s
    route['path'] = points_array
    route['stops'] = stops_array
    return route
  end
end

