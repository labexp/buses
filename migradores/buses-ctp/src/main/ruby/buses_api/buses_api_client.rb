require 'httparty'
require 'json'
require_relative '../models/route'
require_relative 'buses_api_error'

module BusesApi

  class Api
    # Provide basic HTTP client behaviour.
    include HTTParty

    base_uri "http://localhost:8080"

    API_VERSION = "v0.1".freeze

    default_timeout 10

    def find_route(id)
      #TODO: Not implemented yet
    end

    def save(route)
      raise TypeError,"The value must be Route type" unless route.is_a? Route
      create(route)
      #TODO: if created put to update.
      #update(route)
    end

    private
    def create(route)
      post(route.class.to_s.downcase, body:JSON.generate(route.to_json))
    end

    def api_url(url)
      "/buses/#{API_VERSION}/" + url
    end

    def put(url)
      #TODO:Not implemented yet
    end

    def post(url, options = {})
      do_request(:post, url, options)
    end

    def do_request(method, url, options = {})
      begin
        response = self.class.send(method, api_url(url), options.merge(:headers => { 'Content-Type' => 'application/json' }))
        check_response_codes(response)
        response.parsed_response
      rescue Timeout::Error
        raise Unavailable.new('Service Unavailable')
      end
    end

    def check_response_codes(response)
      body = response['message']
      case response.code.to_i
        when 200 then return
        when 400 then raise BadRequest.new(body)
        when 401 then raise Unauthorized.new(body)
        when 403 then raise Forbidden.new(body)
        when 404 then raise NotFound.new(body)
        when 405 then raise MethodNotAllowed.new(body)
        when 500 then raise ServerError, "Internal Server Error #{response['message']}"
        when 503 then raise Unavailable, 'Service Unavailable'
        else raise "Unknown response code: #{response.code}"
      end
    end

  end

end