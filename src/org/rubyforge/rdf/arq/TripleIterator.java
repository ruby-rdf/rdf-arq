package org.rubyforge.rdf.arq;

import org.jruby.*;
import java.util.Iterator;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.TripleMatch;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.rdf.model.AnonId;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.util.iterator.NiceIterator;

/**
 * Wraps a <code>ExtendedIterator&lt;Triple&gt;</code> interface around a
 * Ruby <code>RDF::Enumerator&lt;RDF::Statement&gt;</code> instance.
 *
 * @author Arto Bendiken
 */
public class TripleIterator extends NiceIterator<Triple> implements Iterable<Triple>, ExtendedIterator<Triple>, com.hp.hpl.jena.graph.TripleIterator {
  private Factory factory;
  private RubyEnumerator enumerator;
  private RubyObject generator;

  public TripleIterator() {} // FIXME

  /**
   * @param  object      a Ruby object responding to #to_enum
   */
  public TripleIterator(RubyObject object) {
    this((RubyEnumerator)object.callMethod("to_enum"));
  }

  /**
   * @param  enumerator  a Ruby enumerator
   */
  public TripleIterator(RubyEnumerator enumerator) {
    this.factory    = new Factory(enumerator.getRuntime());
    factory.require("generator");
    this.generator  = factory.newInstance(factory.getClass("Generator").getClass("Threaded"), enumerator);
    this.enumerator = enumerator;
  }

  /**
   * @see java.lang.Iterable#iterator()
   */
  @Override
  public Iterator<Triple> iterator() {
    return this;
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
    return yield((RubyObject)generator.callMethod("next"));
  }

  /**
   * @see com.hp.hpl.jena.graph.TripleIterator#nextTriple()
   */
  @Override
  public Triple nextTriple() {
    return next();
  }

  /**
   * @param  statement   an RDF::Statement instance
   * @return a Triple instance
   */
  protected Triple yield(RubyObject statement) {
    return new Triple(
      Factory.newNode((RubyObject)statement.callMethod("subject")),
      Factory.newNode((RubyObject)statement.callMethod("predicate")),
      Factory.newNode((RubyObject)statement.callMethod("object"))
    );
  }
}
