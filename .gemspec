#!/usr/bin/env ruby -rubygems
# -*- encoding: utf-8 -*-

GEMSPEC = Gem::Specification.new do |gem|
  gem.version            = File.read('VERSION').chomp
  gem.date               = File.mtime('VERSION').strftime('%Y-%m-%d')

  gem.name               = 'rdf-arq'
  gem.homepage           = 'http://rdf.rubyforge.org/arq/'
  gem.license            = 'Public Domain' if gem.respond_to?(:license=)
  gem.summary            = 'Jena/ARQ integration for RDF.rb'
  gem.description        = 'RDF.rb plugin for using the ARQ query engine for SPARQL. Requires JRuby 1.4 or newer.'
  gem.rubyforge_project  = 'rdf'

  gem.authors            = ['Arto Bendiken']
  gem.email              = 'arto.bendiken@gmail.com'

  gem.platform           = Gem::Platform::RUBY
  gem.files              = %w(AUTHORS README UNLICENSE VERSION) + Dir.glob('lib/**/*.rb') + Dir.glob('lib/**/*.jar')
  gem.bindir             = %q(bin)
  gem.executables        = %w()
  gem.default_executable = gem.executables.first
  gem.require_paths      = %w(lib)
  gem.extensions         = %w()
  gem.test_files         = %w()
  gem.has_rdoc           = false

  gem.required_ruby_version      = '>= 1.8.2'
  gem.requirements               = ['ARQ (>= 2.8.3)']
  gem.add_development_dependency 'rdf-spec',    '>= 0.1.9'
  gem.add_development_dependency 'rspec',       '>= 1.3.0'
  gem.add_development_dependency 'yard' ,       '>= 0.5.3'
  gem.add_runtime_dependency     'rdf',         '>= 0.1.9'
  gem.post_install_message       = nil
end
