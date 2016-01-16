class Point
  attr_accessor :lat, :lon

  def initialize(lat,lon)
    @lat = lat
    @lon = lon
  end

  def ==(point2)
    self.lat == point2.lat && self.lon==point2.lon
  end

end