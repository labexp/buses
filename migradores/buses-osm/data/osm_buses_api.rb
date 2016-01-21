require 'rosemary'
require_relative '../../buses-common/models/route'


class OSMBusesApi

  MAX_NODES = 2000

  def initialize
    # TODO: Change to production base_uri.
    Rosemary::Api.base_uri 'api06.dev.openstreetmap.org'
    Rosemary::Api.default_timeout 30
    auth_client = Rosemary::BasicAuthClient.new(ENV["OSM_USR"], ENV["OSM_PWD"])
    @api = Rosemary::Api.new(auth_client)
    @changeset = nil
  end

  def change_base_uri(uri)
    Rosemary::Api.base_uri uri
  end

  # Method to post route on OpenStreetMap
  def post_route(route, message)
    raise TypeError unless route.is_a? Route
    @changeset = @api.create_changeset(message, tags={'created_by'=>'ITCR'})
    ways_list = []
    nodes_list = create_node_list(route.path)

    until nodes_list.empty? # For node's maximum limit of a way
      way_nodes = nodes_list.take(MAX_NODES)
      nodes_list = nodes_list.drop(MAX_NODES)
      way_id = create_way(way_nodes)
      ways_list << way_id
    end

    relation = create_relation(ways_list) # Link ways to relation
    relation = add_stops(relation, route.stops) # Add bus stops to relation

    @api.save(relation, @changeset) # Save relation using the API
    puts 'Relation created succesfuly.'
    @api.close_changeset(@changeset)
    @changeset.id
  end

  private
    # Creates a node list based on the path of the route
    def create_node_list(path)
      puts 'Creating nodes, this may take several time...'
      print 'Processing ['
      ratio = (path.length/20).to_i
      count = 0
      node_list = []
      path.each do |point|
        node = Rosemary::Node.new(lat: point.lat, lon: point.lon)
        node_id = @api.save(node, @changeset)
        node_list << node_id
        count+=1
        print '=' if count%ratio == 0
      end
      puts ']'
      puts "There was #{node_list.length} nodes created successfully"
      node_list
    end

    # Creates a way given the list of member nodes
    def create_way(nodes_list)
      puts 'Creating Way...'
      way = Rosemary::Way.new
      nodes_list.each do |id|
        way << Rosemary::Node.new(id: id)
      end
      way.add_tags(highway: 'unclassified')
      @api.save(way, @changeset)
    end

    # Creates the relation and links the ways to that relation.
    def create_relation(ways_list)
      puts 'Creating relation...'
      relation = Rosemary::Relation.new(visible: 'true')
      ways_list.each do |way_id|
        relation.members << Rosemary::Member.new('way', way_id)
      end
      relation.add_tags(route: 'bus', type: 'route')
      relation
    end

    # Adds the list of stops as members of the relation
    def add_stops(relation, stops_list)
      puts 'Creating stops...'
      stops_id = []
      stops_list.each do |stop|
        point = stop.location
        node = Rosemary::Node.new(lat: point.lat, lon: point.lon)
        node.add_tags(name: stop.name, highway: 'bus_stop')
        node_id = @api.save(node, @changeset)
        stops_id << node_id
      end
      stops_id.each do |id|
        relation.members << Rosemary::Member.new('node', id)
      end
      puts "There was #{stops_id.length} stops added to relation."
      relation
    end

end

