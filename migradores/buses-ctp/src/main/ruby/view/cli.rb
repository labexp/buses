require_relative '../data/csv_file'
require_relative '../data/mopt_ctp_api'
require_relative '../buses_api/buses_api_client'

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
          puts 'Modo de uso import <-f,-a> <ways> <stops>'
        end
      else
        puts 'Me diste #{tokens[0]} -- No tengo idea que hacer con eso.'
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

    def export

    end

end