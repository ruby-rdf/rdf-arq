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
   * @param  runtime  the Ruby runtime to use
   */
  public Factory(Ruby runtime) {
    setRuntime(runtime);
    getModule();
  }

  /**
   * @param  runtime  the Ruby runtime to use
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
   * @return the RDF module
   */
  public RubyModule getModule() {
    return (module != null) ? module : (module = getRuntime().getModule("RDF"));
  }

  /**
   * @return the RDF::Repository class
   */
  public RubyClass getRepositoryClass() {
    return getModule().getClass("Repository");
  }

  /**
   * @return the RDF::Graph class
   */
  public RubyClass getGraphClass() {
    return getModule().getClass("Graph");
  }

  /**
   * @return the RDF::Pattern class
   */
  public RubyClass getPatternClass() {
    return getModule().getClass("Pattern");
  }

  /**
   * @return the RDF::Variable class
   */
  public RubyClass getVariableClass() {
    return getModule().getClass("Variable");
  }

  /**
   * @return the RDF::Statement class
   */
  public RubyClass getStatementClass() {
    return getModule().getClass("Statement");
  }

  /**
   * @return the RDF::Value class
   */
  public RubyClass getValueClass() {
    return getModule().getClass("Value");
  }

  /**
   * @return the RDF::Resource class
   */
  public RubyClass getResourceClass() {
    return getModule().getClass("Resource");
  }

  /**
   * @return the RDF::Node class
   */
  public RubyClass getNodeClass() {
    return getModule().getClass("Node");
  }

  /**
   * @return the RDF::URI class
   */
  public RubyClass getURIClass() {
    return getModule().getClass("URI");
  }

  /**
   * @return the RDF::Literal class
   */
  public RubyClass getLiteralClass() {
    return getModule().getClass("Literal");
  }

  /**
   * @param  object  a Ruby object responding to #to_enum
   * @return an Enumerator instance
   */
  public RubyEnumerator getEnumerator(IRubyObject object) {
    return (RubyEnumerator)object.callMethod(getCurrentContext(), "to_enum");
  }

  /**
   * @param  exception  a runtime exception instance
   * @return <code>true</code> if <code>exception</code> is a StopIteration instance
   */
  public boolean isStopIteration(RuntimeException exception) {
    return (exception instanceof RaiseException) && isStopIteration((RaiseException)exception);
  }

  /**
   * @param  exception  a raised exception instance
   * @return <code>true</code> if <code>exception</code> is a StopIteration instance
   */
  public boolean isStopIteration(RaiseException exception) {
    return getRuntime().getStopIteration().op_eqq(getCurrentContext(), exception.getException()).isTrue();
  }

  /**
   * @param  string  a Java string
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
   * @param  number  a Java integer
   * @return the corresponding Ruby integer
   */
  public RubyInteger newInteger(int number) {
    return getRuntime().newFixnum(number); // FIXME
  }

  /**
   * @return an RDF::Repository instance
   */
  public RubyObject newRepository() {
    return (RubyObject)getRepositoryClass().newInstance(getCurrentContext(), IRubyObject.NULL_ARRAY, Block.NULL_BLOCK);
  }

  /**
   * @return an RDF::Graph instance
   */
  public RubyObject newGraph() {
    return (RubyObject)getGraphClass().newInstance(getCurrentContext(), IRubyObject.NULL_ARRAY, Block.NULL_BLOCK);
  }

  /**
   * @param  s  the subject term
   * @param  p  the predicate term
   * @param  o  the object term
   * @return an RDF::Pattern instance
   */
  public RubyObject newPattern(IRubyObject s, IRubyObject p, IRubyObject o) {
    return newTriple(s, p, o); // FIXME
  }

  /**
   * @param  s  the subject term
   * @param  p  the predicate term
   * @param  o  the object term
   * @param  c  the context term
   * @return an RDF::Pattern instance
   */
  public RubyObject newPattern(IRubyObject s, IRubyObject p, IRubyObject o, IRubyObject c) {
    return newQuad(s, p, o, c); // FIXME
  }

  /**
   * @return a wildcard term for use in RDF::Pattern instances
   */
  public RubyObject getWildcard() {
    return (RubyNil)getRuntime().getNil();
  }

  /**
   * @param  name  the variable name
   * @return an RDF::Variable instance
   */
  public RubyObject newVariable(String name) {
    return newVariable(newString(name));
  }

  /**
   * @param  name  the variable name
   * @return an RDF::Variable instance
   */
  public RubyObject newVariable(IRubyObject name) {
    return (RubyObject)getVariableClass().newInstance(getCurrentContext(), new IRubyObject[]{name}, Block.NULL_BLOCK);
  }

  /**
   * @param  s  the subject term
   * @param  p  the predicate term
   * @param  o  the object term
   * @return an RDF::Statement instance
   */
  public RubyObject newStatement(IRubyObject s, IRubyObject p, IRubyObject o) {
    return newTriple(s, p, o); // FIXME
  }

  /**
   * @param  s  the subject term
   * @param  p  the predicate term
   * @param  o  the object term
   * @param  c  the context term
   * @return an RDF::Statement instance
   */
  public RubyObject newStatement(IRubyObject s, IRubyObject p, IRubyObject o, IRubyObject c) {
    return newQuad(s, p, o, c); // FIXME
  }

  /**
   * @param  s  the subject term
   * @param  p  the predicate term
   * @param  o  the object term
   * @return a Ruby array with 3 elements
   */
  public RubyObject newTriple(IRubyObject s, IRubyObject p, IRubyObject o) {
    return getRuntime().newArray(s, p, o);
  }

  /**
   * @param  s  the subject term
   * @param  p  the predicate term
   * @param  o  the object term
   * @param  c  the context term
   * @return a Ruby array with 4 elements
   */
  public RubyObject newQuad(IRubyObject s, IRubyObject p, IRubyObject o, IRubyObject c) {
    return getRuntime().newArray(s, p, o, c);
  }

  /**
   * @return an RDF::Node instance
   */
  public RubyObject newNode() {
    return (RubyObject)getNodeClass().newInstance(getCurrentContext(), IRubyObject.NULL_ARRAY, Block.NULL_BLOCK);
  }

  /**
   * @param  id  the blank node identifier
   * @return an RDF::Node instance
   */
  public RubyObject newNode(String id) {
    return newNode(newString(id));
  }

  /**
   * @param  id  the blank node identifier
   * @return an RDF::Node instance
   */
  public RubyObject newNode(IRubyObject id) {
    return (RubyObject)getNodeClass().newInstance(getCurrentContext(), new IRubyObject[]{id}, Block.NULL_BLOCK);
  }

  /**
   * @param  uri  the URI string
   * @return an RDF::URI instance
   */
  public RubyObject newURI(String uri) {
    return newURI(newString(uri));
  }

  /**
   * @param  uri  the URI string
   * @return an RDF::URI instance
   */
  public RubyObject newURI(IRubyObject uri) {
    return (RubyObject)getURIClass().newInstance(getCurrentContext(), new IRubyObject[]{uri}, Block.NULL_BLOCK);
  }

  /**
   * @param  value  the literal's value
   * @return an RDF::Literal instance
   */
  public RubyObject newLiteral(Object value) {
    return newLiteral(newString(value.toString()));
  }

  /**
   * @param  value  the literal's value
   * @return an RDF::Literal instance
   */
  public RubyObject newLiteral(String value) {
    return newLiteral(newString(value));
  }

  /**
   * @param  value  the literal's value
   * @return an RDF::Literal instance
   */
  public RubyObject newLiteral(IRubyObject value) {
    return (RubyObject)getLiteralClass().newInstance(getCurrentContext(), new IRubyObject[]{value}, Block.NULL_BLOCK);
  }

  /**
   * @param  value     the literal's value
   * @param  language  the language tag
   * @return an RDF::Literal instance
   */
  public RubyObject newLiteralWithLanguage(Object value, String language) { // TODO
    return (RubyObject)getLiteralClass().newInstance(getCurrentContext(), new IRubyObject[]{newString(value.toString())}, Block.NULL_BLOCK);
  }

  /**
   * @param  value     the literal's value
   * @param  datatype  the datatype URI string
   * @return an RDF::Literal instance
   */
  public RubyObject newLiteralWithDatatype(Object value, String datatype) { // TODO
    return (RubyObject)getLiteralClass().newInstance(getCurrentContext(), new IRubyObject[]{newString(value.toString())}, Block.NULL_BLOCK);
  }
}
