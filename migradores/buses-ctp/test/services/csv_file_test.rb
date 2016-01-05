require 'test/unit'
require_relative '../../data/csv_file'
require_relative '../../view/cli'
require_relative '../test_helper'

class CsvFileTest < Test::Unit::TestCase
  def test_get_next_route
    #csv = CsvFile.new(['',''])
    csv = CsvFile.new(['files/recorrido_buses.csv','files/paradas.csv'])
    route = csv.get_next_route
    assert_true(route.is_a? Route)
  end

  def test_get_next_route_until_nil
    csv = CsvFile.new(['files/recorrido_buses.csv','files/paradas.csv'])
    while csv.get_next_route != nil; end
    assert true
  end

  def test_route_not_empty_id
    csv = CsvFile.new(['files/recorrido_stops.csv','files/coincide_recorrido.csv'])
    route = csv.get_next_route
    assert_not_nil(route)
    assert_not_equal route.id, ''
  end


  def test_route_empty_id
    csv = CsvFile.new(['files/no_ids.csv','files/coincide_recorrido.csv'])
    assert_raise NoMethodError do
      csv.get_next_route
    end

  end

  #If no route could be generated
  def test_route_empty_path
    csv = CsvFile.new(['files/recorrido_buses.csv','files/paradas.csv'])
    route = csv.get_next_route
    assert_not_nil(route)
    assert_true(route.path.eql? [])
  end

  def test_route_empty_stops
    csv = CsvFile.new(['files/recorrido_buses.csv','files/paradas.csv'])
    route = csv.get_next_route
    assert_not_nil(route)
    assert_true(route.stops.eql? [])
  end

  def test_not_empty_path
      csv = CsvFile.new(['files/todos_recorridos.csv','files/paradas.csv'])
      route = csv.get_next_route
      assert_not_nil(route)
      assert_not_equal route.path, []
  end

  def test_not_empty_stops
    csv = CsvFile.new(['files/recorrido_stops.csv','files/coincide_recorrido.csv'])
    route = csv.get_next_route
    assert_not_nil(route)
    assert_not_equal route.stops, []
  end
end