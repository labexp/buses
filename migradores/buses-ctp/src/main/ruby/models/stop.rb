class Stop

  attr_accessor :name, :location

  def initialize(name, location)
    @name = name
    @location = location # Location type is Point
  end
end