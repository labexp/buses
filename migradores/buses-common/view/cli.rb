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


    def exec(command)
      tokens = command.split(' ')
      case tokens[0]
      when 'import'
        if tokens[1]!=nil && tokens[2]!=nil
          tokens[3]!=nil
          import(tokens[1],[tokens[2],tokens[3]])
        else
          print_error 'Modo de uso import <-f,-a> <ways> <stops>'
        end
      when 'export'

        if tokens[1].nil?
          print_error 'Modo de uso: export [-s base_uri] <route_id>'
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
        puts "Me diste #{tokens[0]} -- No tengo idea que hacer con eso."
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
              puts "Ruta #{route.id} creada correctamente"
            else
              puts "Error agregando ruta #{route.id}."
              puts "Para más información ver logs."
            end
          else
            puts "Error agregando ruta #{route.id}."
            puts "Para más información ver logs."
          end
        end
        puts "Proceso Terminado"
      elsif type=='-a'
        mopt_api = MoptCtpApi.new(source)
        puts "Esta opción no esta disponible."
      end
    end

    def export(id)
      id = id.join(' ')
      busesapi = BusesApi::Api.new
      route = busesapi.get_route id
      p route #Ok, the route exist, we can proceed

      Rosemary::Api.base_uri "http://#{@rosemary_uri}"
      Rosemary::Api.default_timeout 20
      #TODO : Extract username and password with figaro to variables
      auth_client = Rosemary::BasicAuthClient.new('Duxel','duxelosm')
      api = Rosemary::Api.new(auth_client)
      osmapi = OSMBusesApi.new(api)
      route = RouteParser.new.parse(route)
      p osmapi.add_route(route,"Creating test with route id #{m_route.id}")
    end

    def help
      puts ''
      puts 'Supported commands:'
      puts 'import: import data from file or api source'
      puts '  Usage: import <-f,-a> <ways> <stops>'
      puts 'export:'
      puts '  Uso: export [-s source] <route_id>'
      puts 'help'
      puts '  Uso: help'
      puts 'exit'
      puts '  Uso: exit'
      puts ''
    end

    def print_error(message)
      puts''
      puts message
      puts ''
    end

end

Cli.new.run