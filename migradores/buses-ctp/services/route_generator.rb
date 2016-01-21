require 'haversine'
require_relative '../../buses-common/services/logging'
require_relative '../../buses-common/models/point'

class RouteGenerator

  attr_accessor :info_list
  include Logging

  # Generates id for route.
  def gen_id
    arr_id = []
    index = [0, 1, 5,7,11,13] # positions for key words.
    if @info_list.length >= 23
      index << 23
    end


    index.each do |i|
      if not arr_id.include? @info_list[i]
        arr_id << @info_list[i]
      end
    end
    id = ''
    arr_id.each do |e|
      id = id+' '+e
    end
    id.strip
  end

  # Generates path for route.
  def gen_path
    route_way = @info_list[7] # Way pos
    coords_list = @info_list[-2]
    res = []
    coords_list.each do |line|
      arr = []
      c = line.split(" ")
      c.each do |point|
        temp = point.split(',')
        arr.push("#{temp[0]},#{temp[1]}")
      end
      res.push(arr)
    end
    first_length = res.length
    res = solve_path(res)
    solved_length = res.length
    if solved_length == 1
      logger.info "Path for #{route_way} was succesfully resolved, the path formed 1 single segment."
      return gen_points(res[0])
    else
      #TODO: Log error with original length and resolved length
      logger.info "Path for #{route_way} could not be resolved, however #{first_length} segments were compacted into #{solved_length}."
      return []
    end
  end

  # Checks if a stop belongs to a route
  # comparing the ways in both records.
  def check_membership(route_way, stop_way)
    stop_way = plain(stop_way)
    # To avoid the first word "Ruta"
    # All places are separated by " - "
    route_places_size = stop_places_size = 0
    route_way_arr = route_way[5..-1].split(' - ')
    route_way_arr.each do |e| e.strip!
      route_places_size += e.size
    end

    stop_way_arr = stop_way.split('-')
    stop_way_arr.each do |e| e.strip!
      stop_places_size += e.size
    end

    if route_places_size == stop_places_size
      matches = 0
      route_way_arr.each do |place|
        stop_way_arr.each do |place2|
          if place==place2
            matches += 1
            break
          end
        end
      end
      return true if route_way_arr.size == matches
    end
    false
  end

  # Looks for the pair of coordinates
  # with less difference to the stops' coords
  # to insert the stop in that point.
  def insert_stops_in_path(path,stops)
    stops.each do |stop|
      best_pos = -1
      best_difference = Float::INFINITY
      path.each_with_index do |point,pos|
        diff = get_distance stop.location,point
        if diff < best_difference
          best_pos = pos
        end
      end
      path.insert(best_pos,stop.location) if best_pos != -1
    end
    path
  end

  private
    def plain(str)
      str.downcase!
      str.tr("áéíóú", "aeiou")
      str
    end

    # Generates list of points
    # based of the path array.
    def gen_points(path)
      point_list = []
      path.each do |point|
        point = point.split(',').reverse.each.collect {|x| x.to_f}
        point_list << Point.new(*point)
      end
      point_list
    end

    # Join two row of coords if the last
    # or first points match.
    def get_same(path)
      pivot = path.delete_at(0)
      paths = []
      while path.length != 0
        pos = 0
        path_length = path.length
        path.each do |curr|
          if pivot.last == curr.first
            pivot = pivot.concat(curr)
            path.delete_at(pos)
            break
          elsif pivot.first == curr.last
            pivot = curr.concat(pivot)
            path.delete_at(pos)
            break
          end
          pos += 1
        end

        if path_length == path.length
          #puts 'son iguales'
          paths.push(pivot)
          pivot = path.delete_at(0)
        end
      end

      if paths.length == 0
        path.push(pivot)
      else
        paths.push(pivot)
      end
    end

    # Join two row of coords if the last match
    # with the last or first with first.
    # ( A reverse is required )
    def get_reverses(path)
      pivot = path.delete_at(0)
      paths = []

      while path.length != 0
        pos = 0
        path_length = path.length
        path.each do |curr|
          if pivot.last == curr.last
            pivot = pivot.concat(curr.reverse)
            path.delete_at(pos)
            break
          elsif pivot.first == curr.first
            pivot = curr.concat(pivot.reverse)
            path.delete_at(pos)
            break
          end
          pos += 1
        end

        if path_length == path.length
          #puts 'son iguales reverse'
          paths.push(pivot)
          pivot = path.delete_at(0)
        end
      end
      if paths.length == 0
        path.push(pivot)
      else
        #p path.length
        paths.push(pivot)
      end
    end

    # Resolve path
    # using same and reverse.
    def solve_path(path)
      res = path
      10.times do
        res = get_same(res)
        res = get_reverses(res)
      end
      res
    end

    # Returns distance between 2 points.
    def get_distance(point1, point2)
      x1 = point1.lat
      x2 = point2.lat
      y1 = point1.lon
      y2 = point2.lon
      Haversine.distance(x1,y1,x2,y2).to_m
    end

end