require 'logger'

module Logging
  # This is the magical bit that gets mixed into your classes
  def logger
    Logging.logger
  end

  # Global, memoized, lazy initialized instance of a logger
  def self.logger
    config = YAML.load_file('../configuration/config.yml')
    log_path = config['logs_path']
    @logger ||= Logger.new(log_path+'info.log')
  end
end