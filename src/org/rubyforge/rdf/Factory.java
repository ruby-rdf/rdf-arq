package org.rubyforge.rdf;

import org.jruby.*;
import org.jruby.runtime.builtin.IRubyObject;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.Block;
import org.jruby.exceptions.RaiseException;

/**
 * A factory class for instantiating RDF.rb objects.
 *
 * @author Arto Bendiken
 */
public class Factory {
  private Ruby runtime;
  private RubyModule module;

  public Factory() {
    this(Ruby.getGlobalRuntime());
  }

  /**
   * @param  runtime     the Ruby runtime to use
   */
  public Factory(Ruby runtime) {
    setRuntime(runtime);
    getRDF();
  }

  /**
   * @param  runtime     the Ruby runtime to use
   */
  public void setRuntime(Ruby runtime) {
    this.runtime = runtime;
  }

  /**
   * @return the Ruby runtime used by this factory
   */
  public Ruby getRuntime() {
    return runtime;
  }

  /**
   * @return the current runtime thread context
   */
  public ThreadContext getCurrentContext() {
    return getRuntime().getCurrentContext();
  }

  /**
   * @param  library     the name of the library to be loaded
   */
  public void require(String library) {
    getRuntime().getLoadService().lockAndRequire(library);
  }

  /**
   * @param  name        the class name
   * @return a Ruby module
   */
  public RubyClass getClass(String name) {
    return getRuntime().getClass(name);
  }

  /**
   * @param  name        the module name
   * @return a Ruby module
   */
  public RubyModule getModule(String name) {
    return getRuntime().getModule(name);
  }

  /**
   * @return the RDF module
   */
  public RubyModule getRDF() {
    return (module != null) ? module : (module = getRuntime().getModule("RDF"));
  }

  /**
   * @return the RDF::Repository class
   */
  public RubyClass getRepositoryClass() {
    return getRDF().getClass("Repository");
  }

  /**
   * @return the RDF::Graph class
   */
  public RubyClass getGraphClass() {
    return getRDF().getClass("Graph");
  }

  /**
   * @return the RDF::Pattern class
   */
  public RubyClass getPatternClass() {
    return getRDF().getClass("Pattern");
  }

  /**
   * @return the RDF::Variable class
   */
  public RubyClass getVariableClass() {
    return getRDF().getClass("Variable");
  }

  /**
   * @return the RDF::Statement class
   */
  public RubyClass getStatementClass() {
    return getRDF().getClass("Statement");
  }

  /**
   * @return the RDF::Value class
   */
  public RubyClass getValueClass() {
    return getRDF().getClass("Value");
  }

  /**
   * @return the RDF::Resource class
   */
  public RubyClass getResourceClass() {
    return getRDF().getClass("Resource");
  }

  /**
   * @return the RDF::Node class
   */
  public RubyClass getNodeClass() {
    return getRDF().getClass("Node");
  }

  /**
   * @return the RDF::URI class
   */
  public RubyClass getURIClass() {
    return getRDF().getClass("URI");
  }

  /**
   * @return the RDF::Literal class
   */
  public RubyClass getLiteralClass() {
    return getRDF().getClass("Literal");
  }

  /**
   * @param  object      a Ruby object responding to #to_enum
   * @return an Enumerator instance
   */
  public RubyEnumerator getEnumerator(IRubyObject object) {
    return (RubyEnumerator)object.callMethod(getCurrentContext(), "to_enum");
  }

  /**
   * @param  exception   a runtime exception instance
   * @return <code>true</code> if <code>exception</code> is a StopIteration instance
   */
  public boolean isStopIteration(RuntimeException exception) {
    return (exception instanceof RaiseException) && isStopIteration((RaiseException)exception);
  }

  /**
   * @param  exception   a raised exception instance
   * @return <code>true</code> if <code>exception</code> is a StopIteration instance
   */
  public boolean isStopIteration(RaiseException exception) {
    return getRuntime().getStopIteration().op_eqq(getCurrentContext(), exception.getException()).isTrue();
  }

  /**
   * @param  string      a Java string
   * @return the corresponding Ruby string
   */
  public RubyString newString(String string) {
    return getRuntime().newString(string);
  }

  /**
   * @return an empty Ruby array
   */
  public RubyArray newArray() {
    return getRuntime().newArray();
  }

  /**
   * @param  number      a Java integer
   * @return the corresponding Ruby integer
   */
  public RubyInteger newInteger(int number) {
    return getRuntime().newFixnum(number); // FIXME
  }

  /**
   * @param  klass       the class to instantiate
   * @return a Ruby object instance
   */
  public RubyObject newInstance(RubyClass klass) {
    return (RubyObject)klass.newInstance(getCurrentContext(), IRubyObject.NULL_ARRAY, Block.NULL_BLOCK);
  }

  /**
   * @param  klass       the class to instantiate
   * @param  arg1        the first constructor argument
   * @return a Ruby object instance
   */
  public RubyObject newInstance(RubyClass klass, IRubyObject arg1) {
    return (RubyObject)klass.newInstance(getCurrentContext(), new IRubyObject[]{arg1}, Block.NULL_BLOCK);
  }

  /**
   * @return an RDF::Repository instance
   */
  public RubyObject newRepository() {
    return newInstance(getRepositoryClass());
  }

  /**
   * @return an RDF::Graph instance
   */
  public RubyObject newGraph() {
    return newInstance(getGraphClass());
  }

  /**
   * @param  subject     the subject term
   * @param  predicate   the predicate term
   * @param  object      the object term
   * @return an RDF::Pattern instance
   */
  public RubyObject newPattern(IRubyObject subject, IRubyObject predicate, IRubyObject object) {
    return newTriple(subject, predicate, object); // FIXME
  }

  /**
   * @param  subject     the subject term
   * @param  predicate   the predicate term
   * @param  object      the object term
   * @param  context     the context term
   * @return an RDF::Pattern instance
   */
  public RubyObject newPattern(IRubyObject subject, IRubyObject predicate, IRubyObject object, IRubyObject context) {
    return newQuad(subject, predicate, object, context); // FIXME
  }

  /**
   * @return a wildcard term for use in RDF::Pattern instances
   */
  public RubyObject getWildcard() {
    return (RubyNil)getRuntime().getNil();
  }

  /**
   * @param  name        the variable name
   * @return an RDF::Variable instance
   */
  public RubyObject newVariable(String name) {
    return newVariable(newString(name));
  }

  /**
   * @param  name        the variable name
   * @return an RDF::Variable instance
   */
  public RubyObject newVariable(IRubyObject name) {
    return newInstance(getVariableClass(), name);
  }

  /**
   * @param  subject     the subject term
   * @param  predicate   the predicate term
   * @param  object      the object term
   * @return an RDF::Statement instance
   */
  public RubyObject newStatement(IRubyObject subject, IRubyObject predicate, IRubyObject object) {
    return newTriple(subject, predicate, object); // FIXME
  }

  /**
   * @param  subject     the subject term
   * @param  predicate   the predicate term
   * @param  object      the object term
   * @param  context     the context term
   * @return an RDF::Statement instance
   */
  public RubyObject newStatement(IRubyObject subject, IRubyObject predicate, IRubyObject object, IRubyObject context) {
    return newQuad(subject, predicate, object, context); // FIXME
  }

  /**
   * @param  subject     the subject term
   * @param  predicate   the predicate term
   * @param  object      the object term
   * @return a Ruby array with 3 elements
   */
  public RubyArray newTriple(IRubyObject subject, IRubyObject predicate, IRubyObject object) {
    return getRuntime().newArray(subject, predicate, object);
  }

  /**
   * @param  subject     the subject term
   * @param  predicate   the predicate term
   * @param  object      the object term
   * @param  context     the context term
   * @return a Ruby array with 4 elements
   */
  public RubyArray newQuad(IRubyObject subject, IRubyObject predicate, IRubyObject object, IRubyObject context) {
    return getRuntime().newArray(subject, predicate, object, context);
  }

  /**
   * @return an RDF::Node instance
   */
  public RubyObject newNode() {
    return newInstance(getNodeClass());
  }

  /**
   * @param  id          the blank node identifier
   * @return an RDF::Node instance
   */
  public RubyObject newNode(String id) {
    return newNode(newString(id));
  }

  /**
   * @param  id          the blank node identifier
   * @return an RDF::Node instance
   */
  public RubyObject newNode(IRubyObject id) {
    return newInstance(getNodeClass(), id);
  }

  /**
   * @param  uri         the URI string
   * @return an RDF::URI instance
   */
  public RubyObject newURI(String uri) {
    return newURI(newString(uri));
  }

  /**
   * @param  uri         the URI string
   * @return an RDF::URI instance
   */
  public RubyObject newURI(IRubyObject uri) {
    return newInstance(getURIClass(), uri);
  }

  /**
   * @param  value       the literal's value
   * @return an RDF::Literal instance
   */
  public RubyObject newLiteral(Object value) {
    return newLiteral(newString(value.toString()));
  }

  /**
   * @param  value       the literal's value
   * @return an RDF::Literal instance
   */
  public RubyObject newLiteral(String value) {
    return newLiteral(newString(value));
  }

  /**
   * @param  value       the literal's value
   * @return an RDF::Literal instance
   */
  public RubyObject newLiteral(IRubyObject value) {
    return newInstance(getLiteralClass(), value);
  }

  /**
   * @param  value       the literal's value
   * @param  language    the language tag
   * @return an RDF::Literal instance
   */
  public RubyObject newLiteralWithLanguage(Object value, String language) { // TODO
    return newInstance(getLiteralClass(), newString(value.toString())); // FIXME
  }

  /**
   * @param  value       the literal's value
   * @param  datatype    the datatype URI string
   * @return an RDF::Literal instance
   */
  public RubyObject newLiteralWithDatatype(Object value, String datatype) { // TODO
    return newInstance(getLiteralClass(), newString(value.toString())); // FIXME
  }
}
