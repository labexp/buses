require 'test/unit'
require_relative '../../data/mopt_ctp_api'

class MoptCtpApiTest < Test::Unit::TestCase
  def test_get_next_route
    sources = ['ways.csv','stops.csv']
    mopt_api = MoptCtpApi.new(sources)
    assert_nil mopt_api.get_next_route
  end

  def test_clean
    sources = ['ways.csv','stops.csv']
    mopt_api = MoptCtpApi.new(sources)
    assert_equal [], mopt_api.clean([])
  end
end