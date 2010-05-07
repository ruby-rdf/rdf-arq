#!/usr/bin/env jruby
$:.unshift(File.expand_path(File.join(File.dirname(__FILE__), 'lib')))
require 'rubygems'
require 'rdf/arq'

begin
  require 'rakefile' # http://github.com/bendiken/rakefile
rescue LoadError => e
end

desc "Build all Java class files"
task :build do
  sh "javac -cp src #{Dir.glob('src/**/*.java').join(' ')}"
end

task :default => :build
