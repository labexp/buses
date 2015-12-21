require 'csv'
require 'sanitize'
class CSV_File

  def get_route(id)

  end

  def clean(row)
    count = 0
    array = []

    transformer = lambda do |env|
      array << env[:node].text if env[:node].text?
    end

    row.each do |col|
      Sanitize.fragment(col,transformers:transformer)
    end

    return array
  end
end


CSV.foreach('../files/recorrido_buses.csv') do |row|

end

