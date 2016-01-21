require 'rosemary'
require_relative '../../buses-ctp/data/csv_file'
require_relative '../../buses-ctp/data/mopt_ctp_api'
require_relative '../buses_api/buses_api_client'
require_relative '../../buses-osm/data/osm_buses_api'
require_relative '../../buses-osm/services/route_parser'

class Cli

  def run
    command = ''
    while command != 'exit'
      print 'MigratorsCli> '
      command = gets.chomp
      exec(command)
    end
  end

  private
    def exec(command)
      tokens = command.split(' ')
      case tokens[0]
      when 'import'
        if tokens[1]!=nil && tokens[2]!=nil
          tokens[3]!=nil
          import(tokens[1],[tokens[2],tokens[3]])
        else
          print_error 'Usage: import <-f,-a> <ways> <stops>'
        end
      when 'export'
        if tokens[1].nil?
          print_error 'Usage: export [-s base_uri] [-f file] [--all] <route(s)>'
          return
        end

        if tokens[1] == '-s' and !tokens[2].nil?
          puts "Rosemary Base URI changed to #{tokens[2]}"
          @rosemary_uri = tokens[2]
        else
          if tokens[1] == '-f' && tokens[2]!=nil
            export_file(tokens[2])
          elsif tokens[1] == '--all'
            export_all
          else
            export_route(tokens[1..-1]) unless tokens[1].nil?
          end
        end
      when 'exit'
        return
      when 'help'
        help
      else
        puts "You gave me #{tokens[0]} -- I don't know what to do with that."
      end
    end


    def import(type, source)
      if type=='-f'
        csv_file = CsvFile.new(source)
        busesapi = BusesApi::Api.new
        while ((route = csv_file.get_next_route)!=nil)
          if route.id != "" && route.path != []
            response = busesapi.save(route)
            if !response.nil?
              puts "Route #{route.id} created succesfully."
            else
              print "Error adding route #{route.id}. "
              puts "For more information see logs file."
            end
          else
            print "Error adding route #{route.id}. "
            puts "For more information see logs file."
          end
        end
        puts "Process done."
      elsif type=='-a'
        mopt_api = MoptCtpApi.new(source)
        puts "This option is not available."
      end
    end

    def export_file(source)
      File.readlines(source).each do |id|
        export_route(id)
      end
    end

    def export(route)
      puts 'Translating route...'
      route = JSON.parse(route.to_json)
      osmapi = OSMBusesApi.new
      osmapi.change_base_uri @rosemary_uri
      m_route = RouteParser.new.parse(route)
      #logger.info "Route #{route.id.to_s} parsed succesfully, exporting to OSM in process"
      changeset = osmapi.post_route(m_route,"Creating test with route id #{m_route.id}")
      puts "Route #{m_route.id} sucessfully added to OSM with changeset #{changeset}"
    end

    def export_route(id)
      id = id.join(' ')
      busesapi = BusesApi::Api.new
      puts 'Trying to reach route from database...'
      route = busesapi.get_route(id)
      if route.nil?
        puts "Route #{m_route.id} does not exist on database."
      else
        puts 'Route founded, exporting to OSM...'
        export route
      end
    end

    def export_all
      busesapi = BusesApi::Api.new
      id_list = busesapi.get_routes
      id_list.each do |route_id|
        export(busesapi.get_route route_id)
      end
    end

    def help
      puts ''
      puts 'Supported commands:'
      puts 'import: import data from file or api source'
      puts '  Usage: import <-f,-a> <ways> <stops>'
      puts 'export:'
      puts '  Usage: export [-s source] [-f file] [--all] <route_id>'
      puts 'help'
      puts '  Usage: help'
      puts 'exit'
      puts '  Usage: exit'
      puts ''
    end

    def print_error(message)
      puts''
      puts message
      puts ''
    end

end

Cli.new.run