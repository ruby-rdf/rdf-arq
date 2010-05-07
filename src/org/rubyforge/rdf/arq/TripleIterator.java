package org.rubyforge.rdf.arq;

import org.jruby.*;
import org.jruby.runtime.builtin.IRubyObject;
import org.jruby.runtime.Block;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.TripleMatch;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.rdf.model.AnonId;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.util.iterator.NiceIterator;

/**
 * Wraps an RDF::Enumerator instance for Jena/ARQ compatibility.
 *
 * @author Arto Bendiken
 */
public class TripleIterator extends NiceIterator<Triple> implements ExtendedIterator<Triple>, com.hp.hpl.jena.graph.TripleIterator {
  private Ruby runtime;
  private RubyEnumerator enumerator;
  private RubyObject generator;

  /**
   * @param  object  a Ruby object that responds to #to_enum
   */
  public TripleIterator(RubyObject object) {
    this((RubyEnumerator)object.callMethod("to_enum"));
  }

  /**
   * @param  enumerator  a Ruby enumerator
   */
  public TripleIterator(RubyEnumerator enumerator) {
    this.enumerator = enumerator;
    this.runtime    = enumerator.getRuntime();
    runtime.getLoadService().lockAndRequire("generator"); // require 'generator'
    this.generator  = (RubyObject)(runtime.getClass("Generator").getClass("Threaded").
      newInstance(runtime.getCurrentContext(), new IRubyObject[]{enumerator}, Block.NULL_BLOCK));
  }

  /**
   * @see java.util.Iterator#hasNext()
   */
  @Override
  public boolean hasNext() {
    return generator.callMethod("next?").isTrue();
  }

  /**
   * @see java.util.Iterator#next()
   */
  @Override
  public Triple next() {
    RubyObject statement = (RubyObject)generator.callMethod("next");
    return new Triple(
      makeNode((RubyObject)statement.callMethod("subject")),
      makeNode((RubyObject)statement.callMethod("predicate")),
      makeNode((RubyObject)statement.callMethod("object"))
    );
  }

  /**
   * @see com.hp.hpl.jena.graph.TripleIterator#nextTriple()
   */
  @Override
  public Triple nextTriple() {
    return next();
  }

  /**
   * @param  value  an RDF::Value instance
   * @return a Node instance
   */
  protected Node makeNode(RubyObject value) { // TODO
    String className = value.callMethod("class").toString();
    if (className.equals("RDF::Node")) {
      return Node.createAnon(AnonId.create(value.callMethod("id").toString()));
    }
    if (className.equals("RDF::URI")) {
      return Node.createURI(value.toString());
    }
    if (className.equals("RDF::Literal")) {
      return Node.createLiteral(value.callMethod("value").toString()); // FIXME
    }
    return null;
  }
}
