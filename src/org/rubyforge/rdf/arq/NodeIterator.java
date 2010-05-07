package org.rubyforge.rdf.arq;

import org.jruby.*;
import com.hp.hpl.jena.graph.Node;

/**
 * Wraps a <code>java.util.Iterator&lt;Node&gt;</code> interface around a
 * Ruby <code>RDF::Enumerator&lt;RDF::Value&gt;</code> instance.
 *
 * @author Arto Bendiken
 */
public class NodeIterator extends org.rubyforge.rdf.EnumeratorIterator<Node> {
  /**
   * @see org.rubyforge.rdf.EnumeratorIterator#Constructor(RubyObject)
   */
  public NodeIterator(RubyObject object) {
    super(object);
  }

  /**
   * @see org.rubyforge.rdf.EnumeratorIterator#Constructor(RubyEnumerator)
   */
  public NodeIterator(RubyEnumerator enumerator) {
    super(enumerator);
  }

  /**
   * @param  value       an RDF::Value instance
   * @return an ARQ node instance
   */
  protected Node yield(RubyObject value) {
    return Factory.newNode(value);
  }
}
