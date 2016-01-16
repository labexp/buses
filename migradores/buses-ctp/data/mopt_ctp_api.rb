require_relative 'datasource'
require_relative '../services/route_generator'

class MoptCtpApi < DataSource

  def initialize(resource=nil)
    @resource = resource
    @route_gen = RouteGenerator.new
  end

  def get_next_route
    nil
  end

  def clean(row)
    []
  end
end