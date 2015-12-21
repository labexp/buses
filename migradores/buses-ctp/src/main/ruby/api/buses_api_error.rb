module BusesApi
  class Error < StandardError
    attr_reader :data

    def initialize(data)
      @data = data
      super
    end
  end

  class MissingProperty < Error; end #429

  class BadRequest < Error; end #400

  class Unauthorized < Error; end #401

  class Forbidden < Error; end #403

  class NotFound < Error; end #404

  class MethodNotAllowed < Error; end #405

  class ServerError < Error; end #500

  class Unavailable < Error; end #503
end
