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
      print 'BusesCli> '
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
          print_error 'Usage: export [-s base_uri] <route_id>'
        end

        if tokens[1] == '-s' and !tokens[2].nil?
          puts "Rosemary Base URI changed to http://#{tokens[2]}"
          @rosemary_uri = tokens[2]
        else
          export(tokens[1..-1]) unless tokens[1].nil?
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
            if response['status'] == 200
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

    # Temporary method to simulate received JSON.
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

    def export(id)
      id = id.join(' ')
      busesapi = BusesApi::Api.new
      route = get_route # busesapi.get_route(id)
      route = JSON.parse(route.to_json)
      osmapi = OSMBusesApi.new
      m_route = RouteParser.new.parse(route)
      p osmapi.post_route(m_route,"Creating test with route id #{m_route.id}")
    end

    def help
      puts ''
      puts 'Supported commands:'
      puts 'import: import data from file or api source'
      puts '  Usage: import <-f,-a> <ways> <stops>'
      puts 'export:'
      puts '  Usage: export [-s source] <route_id>'
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