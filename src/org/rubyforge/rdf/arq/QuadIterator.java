package org.rubyforge.rdf.arq;

import org.jruby.*;
import com.hp.hpl.jena.sparql.core.Quad;

/**
 * Wraps a <code>java.util.Iterator&lt;Quad&gt;</code> interface around a
 * Ruby <code>RDF::Enumerator&lt;RDF::Statement&gt;</code> instance.
 *
 * @author Arto Bendiken
 */
public class QuadIterator extends org.rubyforge.rdf.EnumeratorIterator<Quad> {
  /**
   * @see org.rubyforge.rdf.EnumeratorIterator#Constructor(RubyObject)
   */
  public QuadIterator(RubyObject object) {
    super(object);
  }

  /**
   * @see org.rubyforge.rdf.EnumeratorIterator#Constructor(RubyEnumerator)
   */
  public QuadIterator(RubyEnumerator enumerator) {
    super(enumerator);
  }

  /**
   * @param  statement   an RDF::Statement instance
   * @return an ARQ quad instance
   */
  protected Quad yield(RubyObject statement) {
    return new Quad(
      Factory.newNode((RubyObject)statement.callMethod("context")),
      Factory.newNode((RubyObject)statement.callMethod("subject")),
      Factory.newNode((RubyObject)statement.callMethod("predicate")),
      Factory.newNode((RubyObject)statement.callMethod("object"))
    );
  }
}
