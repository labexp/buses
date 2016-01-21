require 'rubygems'
require 'rest-client'
require 'json'
require 'pp'
require 'minitest/autorun'

class TestReportSystem < MiniTest::Test

  def setup
    @host = "localhost"
    @port = 1234
  end

  def test_routes_list
    response = RestClient.get "http://#{@host}:#{@port}/routes"
    assert_equal response.code, 200
    parsed = JSON.parse response.to_str
    assert parsed.length > 1 # There is more than 1 route
  end

  def test_non_existing_resource
    response = RestClient.get "http://#{@host}:#{@port}/buses"
    assert_equal response.body, "No such method\n"
  end

  def test_bad_port
    assert_raises Errno::ECONNREFUSED do
      RestClient.get "http://#{@host}:1235/routes"
    end
  end

  def test_route_req_id
    response = RestClient.get "http://#{@host}:#{@port}/routes?id=SanJose-Heredia"
    parsed = JSON.parse response.to_str
    assert parsed["id"] != nil
  end

  def test_route_req_path
    response = RestClient.get "http://#{@host}:#{@port}/routes?id=SanJose-Heredia"
    parsed = JSON.parse response.to_str
    assert parsed["path"] != nil
  end

  def test_route_req_stops
    response = RestClient.get "http://#{@host}:#{@port}/routes?id=SanJose-Heredia"
    parsed = JSON.parse response.to_str
    assert parsed["stops"] != nil
  end

end
