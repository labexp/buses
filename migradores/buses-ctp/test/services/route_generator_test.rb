require 'test/unit'
require_relative '../../services/route_generator'
require_relative '../../models/stop'
require_relative '../../models/point'

class RouteGeneratorTest < Test::Unit::TestCase

  def test_gen_id_with_repeat
    route_gen = RouteGenerator.new
    route_gen.info_list = ["Escazu - Santa Ana", "Escazu - Santa Ana", "FID", "0", "sector", "Escazu - Santa Ana",
                           "ruta", "Ruta San Jose - Santa Ana - Piedades - Brasil por Pista", "Length", "44,10298",
                           "RUTA_1", "Ruta 09 (CÖA. DE INVERSIONES LA TAPACHULA, SA)", "ID_1", "171", "ORDEN", "5",
                           "DESPACHOS_", "162", "DESPACHOS1", "166", "AFORO", "X", "SUBSECT", "Subsector Escazu - Santa Ana - Ciudad Colon",
                           ["-84.20948252515252,9.927520517374099,0.0 -84.20948729847821,9.927521011626075,0.0 "], "Line"]
    id = route_gen.gen_id
    # Pos of array that form id [0, 1, 5,7,11,13,23] if not included
    expected_id = "Escazu - Santa Ana Ruta San Jose - Santa Ana - Piedades - "+
        "Brasil por Pista Ruta 09 (CÖA. DE INVERSIONES LA TAPACHULA, SA) 171 Subsector Escazu - Santa Ana - Ciudad Colon"
    assert_equal expected_id, id
  end

  def test_gen_id_without_repeat
    route_gen = RouteGenerator.new
    route_gen.info_list = ["San Jose", "Escazu - Santa Ana", "FID", "0", "sector", "Guillon",
                           "ruta", "Ruta San Jose - Brasil por Pista", "Length", "44,10298",
                           "RUTA_1", "Ruta 09 (CÖA. DE INVERSIONES LA TAPACHULA, SA)", "ID_1", "171", "ORDEN", "5",
                           "DESPACHOS_", "162", "DESPACHOS1", "166", "AFORO", "X", "SUBSECT", "Subsector Este",
                           ["-84.20948252515252,9.927520517374099,0.0 -84.20948729847821,9.927521011626075,0.0 "], "Line"]
    id = route_gen.gen_id
    # Pos of array that form id [0, 1, 5,7,11,13] if not included
    expected_id = "San Jose Escazu - Santa Ana Guillon Ruta San Jose - "+
        "Brasil por Pista Ruta 09 (CÖA. DE INVERSIONES LA TAPACHULA, SA) 171 Subsector Este"
    assert_equal expected_id, id
  end

  def test_gen_id_all_same
    route_gen = RouteGenerator.new
    route_gen.info_list = ["San Jose", "San Jose", "FID", "0", "sector", "San Jose",
                           "ruta", "San Jose", "Length", "44,10298",
                           "RUTA_1", "San Jose", "ID_1", "San Jose", "ORDEN", "5",
                           "DESPACHOS_", "162", "DESPACHOS1", "166", "AFORO", "X", "SUBSECT", "San Jose",
                           ["-84.20948252515252,9.927520517374099,0.0 -84.20948729847821,9.927521011626075,0.0 "], "Line"]
    id = route_gen.gen_id
    # Pos of array that form id [0, 1, 5,7,11,13] if not included
    expected_id = "San Jose"
    assert_equal expected_id, id
  end

  def test_gen_id_bad_id
    route_gen = RouteGenerator.new
    route_gen.info_list = ["Escazu - Santa Ana", "Escazu - Santa Ana", "FID", "0", "sector", "Escazu - Santa Ana",
                           "ruta", "Ruta San Jose - Santa Ana - Piedades - Brasil por Pista", "Length", "44,10298",
                           "RUTA_1", "Ruta 09 (CÖA. DE INVERSIONES LA TAPACHULA, SA)", "ID_1", "171", "ORDEN", "5",
                           "DESPACHOS_", "162", "DESPACHOS1", "166", "AFORO", "X", "SUBSECT", "Subsector Escazu - Santa Ana - Ciudad Colon",
                           ["-84.20948252515252,9.927520517374099,0.0 -84.20948729847821,9.927521011626075,0.0 "], "Line"]
    id = route_gen.gen_id
    # Pos of array that form id [0, 1, 5,7,11,13,23] if not included
    expected_id = "Escazu - Santa Ana Ruta San Jose - Santa Ana - Piedades - zxa"+
        "Brasil por Pista Ruta 09 (CÖA. DE INVERSIONES LA TAPACHULA, SA) 171 Subsector Escazu - Santa Ana - Ciudad Colon"
    assert_not_equal expected_id, id
  end

  def test_gen_path_solvable
    route_gen = RouteGenerator.new
    route_gen.info_list = ["San Jose", "San Jose", "FID", "0", "sector", "San Jose",
                           "ruta", "San Jose", "Length", "44,10298",
                           "RUTA_1", "San Jose", "ID_1", "San Jose", "ORDEN", "5",
                           "DESPACHOS_", "162", "DESPACHOS1", "166", "AFORO", "X", "SUBSECT", "San Jose",
                           ["-84.20948252515252,9.927520517374099,0.0 -84.20948729847821,9.927521011626075,0.0 -84.20948729847826,9.927521011626085,0.0",
                            "-84.20948729847826,9.927521011626085,0.0 -84.20948729847830,9.927521011626092,0.0"], "Line"]
    path = route_gen.gen_path
    assert_not_empty path
  end

  def test_gen_path_unsolvable
    route_gen = RouteGenerator.new
    route_gen.info_list = ["San Jose", "San Jose", "FID", "0", "sector", "San Jose",
                           "ruta", "San Jose", "Length", "44,10298",
                           "RUTA_1", "San Jose", "ID_1", "San Jose", "ORDEN", "5",
                           "DESPACHOS_", "162", "DESPACHOS1", "166", "AFORO", "X", "SUBSECT", "San Jose",
                           ["-84.20948252515252,9.927520517374099,0.0 -84.20948729847821,9.927521011626075,0.0 ",
                            "-84.20948729847826,9.927521011626085,0.0 -84.20948729847830,9.927521011626092,0.0"], "Line"]
    path = route_gen.gen_path
    assert_empty path
  end

  def test_check_membership_good_order
    route_gen = RouteGenerator.new
    route_way = "Ruta San Jose - Alajuelita - La Aurora"
    route_way = plain(route_way)
    stop_way = "San Jose - Alajuelita - La Aurora"
    res = route_gen.check_membership(route_way, stop_way)
    assert_true res
  end

  def test_check_membership_bad_order
    route_gen = RouteGenerator.new
    route_way = "Ruta San Jose - Alajuelita - La Aurora"
    route_way = plain(route_way)
    stop_way = "San Jose - La Aurora - Alajuelita"
    res = route_gen.check_membership(route_way, stop_way)
    assert_true res
  end

  def test_check_membership_lowercase
    route_gen = RouteGenerator.new
    route_way = "Ruta San Jose - alajuelita - la Aurora"
    route_way = plain(route_way)
    stop_way = "San Jose - Alajuelita - La Aurora"
    res = route_gen.check_membership(route_way, stop_way)
    assert_true res
  end

  def test_check_membership_accent
    route_gen = RouteGenerator.new
    route_way = "Ruta San José - Alajuelíta - La Aúrora"
    route_way = plain(route_way)
    stop_way = "San Jose - Alajuelita - La Aurora"
    res = route_gen.check_membership(route_way, stop_way)
    assert_true res
  end

  def test_check_non_membership
    route_gen = RouteGenerator.new
    route_way = "Ruta Uruca - Heredia"
    route_way = plain(route_way)
    stop_way = "San Jose - Alajuelita - La Aurora"
    res = route_gen.check_membership(route_way, stop_way)
    assert_false res
  end

  def test_check_membership_different_format
    route_gen = RouteGenerator.new
    route_way = "Ruta Uruca - Heredia"
    route_way = plain(route_way)
    stop_way = "Uruca-Heredia"
    res = route_gen.check_membership(route_way, stop_way)
    assert_true res
  end

  def test_check_membership_typo
    route_gen = RouteGenerator.new
    route_way = "Ruta Uruca - Alajuelita"
    route_way = plain(route_way)
    stop_way = "Uruca - Aluelita"
    res = route_gen.check_membership(route_way, stop_way)
    assert_false res
  end

  def test_insert_stops_in_path
    route_gen = RouteGenerator.new
    stops = []; path = []
    stops << Stop.new("Heredia", Point.new(9.894389,-81.38932))
    stops << Stop.new("San José", Point.new(9.43843,-84.43784))
    path << Point.new(9.32832, -81.38232)
    path << Point.new(10.4384, -84.438943)

    path_with_stops = route_gen.insert_stops_in_path(path,stops)
    expected = [Point.new(9.32832, -81.38232), Point.new(9.894389,-81.38932),
                Point.new(9.43843,-84.43784),Point.new(10.4384, -84.438943)]

    matches = 0
    expected.zip(path_with_stops).each do |e, o|
      matches += 1 if e == o
    end
    assert_equal matches, expected.size
  end

  def test_insert_stops_in_path_bad_result
    route_gen = RouteGenerator.new
    stops = []; path = []
    stops << Stop.new("Heredia", Point.new(9.894389,-81.38932))
    stops << Stop.new("San José", Point.new(9.43843,-84.43784))
    path << Point.new(9.32832, -81.38232)
    path << Point.new(10.4384, -84.438943)

    path_with_stops = route_gen.insert_stops_in_path(path,stops)
    expected = [Point.new(9.32832, -81.18232), Point.new(9.894389,-81.38932),
                Point.new(9.43843,-84.43784),Point.new(10.4384, -84.438943)]

    matches = 0
    expected.zip(path_with_stops).each do |e, o|
      matches += 1 if e == o
    end
    assert_not_equal matches, expected.size
  end

  private
    def plain(str)
      str.downcase!
      str.tr("áéíóú", "aeiou")
    end

end