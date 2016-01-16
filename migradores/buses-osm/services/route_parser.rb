require_relative '../../buses-common/models/point'
require_relative '../../buses-common/models/stop'
require_relative '../../buses-common/models/route'

class RouteParser

  def parse(route)
    id = get_id(route['id'])
    path = get_path(route['path'])
    stops = get_stops(route['stops'])
    Route.new(id,path,stops)
  end

  private

  def get_id(id)
    id
  end

  def get_path(path)
    path_list = []
    path.each do |point|
      path_list << Point.new(point['latitude'], point['longitude'])
    end
    path_list
  end

  def get_stops(stops)
    stops_list = []
    stops.each do |stop|
      location = stop['location']
      point = Point.new(location['latitude'],location['longitude'])
      stops_list << Stop.new(stop['name'],point)

    end
    stops_list
  end

end