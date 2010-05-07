package org.rubyforge.rdf;

import org.jruby.*;
import java.util.Iterator;

/**
 * Wraps a <code>java.util.Iterator&lt;T2&gt;</code> interface around a
 * Ruby <code>RDF::Enumerator&lt;T1&gt;</code> instance.
 *
 * @author Arto Bendiken
 */
public abstract class EnumeratorIterator<T> implements Iterable<T>, Iterator<T> {
  private Factory factory;
  private RubyEnumerator enumerator;
  private RubyObject generator;

  public EnumeratorIterator() {} // FIXME

  /**
   * @param  object      a Ruby object responding to #to_enum
   */
  public EnumeratorIterator(RubyObject object) {
    this((RubyEnumerator)object.callMethod("to_enum"));
  }

  /**
   * @param  enumerator  a Ruby enumerator
   */
  public EnumeratorIterator(RubyEnumerator enumerator) {
    this.factory    = new Factory(enumerator.getRuntime());
    factory.require("generator");
    this.generator  = factory.newInstance(factory.getClass("Generator").getClass("Threaded"), enumerator);
    this.enumerator = enumerator;
  }

  /**
   * @see java.lang.Iterable#iterator()
   */
  @Override
  public Iterator<T> iterator() {
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
  public T next() {
    return yield((RubyObject)generator.callMethod("next"));
  }

  /**
   * @see java.util.Iterator#remove()
   */
  @Override
  public void remove() {
    throw new UnsupportedOperationException("EnumeratorIterator#remove");
  }

  protected abstract T yield(RubyObject object);
}
