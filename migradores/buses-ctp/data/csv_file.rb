require 'sanitize'

require_relative 'datasource'
require_relative '../../buses-common/services/logging'
require_relative '../../buses-common/models/stop'
require_relative '../../buses-common/models/route'
require_relative '../../buses-common/models/point'
require 'csv'

class CsvFile < DataSource

  include Logging

  def initialize(filename=nil)
    @ways_f = filename[0]
    @stops_f = filename[1]
    @curr_stop = 1
    @curr_fid = -1
    @route_gen = RouteGenerator.new
  end

  def get_next_route
    route_list = []
    flag = 1
    started = false
    CSV.foreach(@ways_f) do |row| # Change for filename

      arr = clean(row)
      if @curr_fid == -1 then @curr_fid = arr[3] end

      if flag==1 && arr[3] == @curr_fid
        route_list = arr
        started = true
      end

      if started
        if arr[3] == @curr_fid
          if flag==1 then route_list[-2] = [route_list[-2]]
          else route_list[-2].push arr[-2] end
        else
          @curr_fid = arr[3]
          route = create_route(route_list)
          @curr_stop = 1
          return route
        end
        flag += 1
      end
    end
    # Add if was the last route.
    if route_list!=[]
      @curr_fid = 'done'
      route = create_route(route_list)
      return route
    end
    nil # Nil if there are no routes left.
  end

  private
    # Creates a route using the information given in the list.
    def create_route(route_list)
      @route_gen.info_list = route_list
      id = @route_gen.gen_id
      path = @route_gen.gen_path
      stops = gen_stops
      path = @route_gen.insert_stops_in_path(path, stops)

      #Insert start and end point as stops
      stops.insert(0,Stop.new("Punto inicial de la ruta",path.first))
      #stops.append(Stop.new("Punto final de la ruta",path.last))

      route = Route.new(id, path, stops)
      route
    end

    # Generates stop list for route.
    def gen_stops
      route_way = @route_gen.info_list[7]
      saved_way = route_way.dup
      stop_list = []
      route_way.downcase!
      route_way.tr("áéíóú", "aeiou")
      while ((stop = get_next_stop) != nil)
        stop_way = stop[18]
        # Checks what stops belong to the given route.
        if @route_gen.check_membership(route_way, stop_way)
          stop_list << Stop.new(stop[0],Point.new(stop[1].to_f, stop[2].to_f))
        end
      end
      if stop_list==[]
        logger.info "Unable to found stops for #{saved_way}"
      else
        logger.info "Found #{stop_list.size} stops for #{saved_way}"
      end
      stop_list
    end

    def clean(row)
      array = []
      transformer = lambda do |env|
        array << env[:node].text if env[:node].text?
      end
      row.each do |col|
        Sanitize.fragment(col,transformers:transformer)
      end
      array
    end

    def get_next_stop
      cnt = 1
      CSV.foreach(@stops_f) do |stop|
        if cnt == @curr_stop
          @curr_stop += 1
          return clean(stop)
        else
          cnt += 1
        end
      end
    end

end