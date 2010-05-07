package org.rubyforge.rdf.arq;

import org.jruby.*;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.TripleMatch;
import com.hp.hpl.jena.graph.impl.GraphBase;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

/**
 * Wraps an RDF::Graph instance for Jena/ARQ compatibility.
 *
 * @author Arto Bendiken
 */
public class Graph extends GraphBase {
  private RubyObject graph;
  private Factory factory;

  public Graph() {
    super();
    this.factory = new Factory();
    this.graph   = factory.newGraph();
  }

  /**
   * @param  graph      the RDF::Graph instance
   */
  public Graph(RubyObject graph) {
    super();
    this.graph   = graph;
    this.factory = new Factory(graph.getRuntime());
  }

  /**
   * @see com.hp.hpl.jena.graph.impl.GraphBase#graphBaseSize()
   */
  @Override
  protected int graphBaseSize() {
    return ((Number)graph.callMethod("count").toJava(Number.class)).intValue();
  }

  /**
   * @see com.hp.hpl.jena.graph.impl.GraphBase#graphBaseContains()
   */
  @Override
  protected boolean graphBaseContains(Triple triple) {
    return graph.callMethod("has_triple?", factory.newTriple(triple)).isTrue();
  }

  /**
   * @see com.hp.hpl.jena.graph.impl.GraphBase#graphBaseFind()
   */
  @Override
  protected ExtendedIterator<Triple> graphBaseFind(TripleMatch match) {
    return new TripleIterator(factory.getEnumerator(graph.callMethod("query", factory.newPattern(match))));
  }

  /**
   * @see com.hp.hpl.jena.graph.impl.GraphBase#performAdd()
   * @see com.hp.hpl.jena.graph.impl.GraphWithPerform#performAdd()
   */
  @Override
  public void performAdd(Triple triple) {
    graph.callMethod("insert", factory.newTriple(triple));
  }

  /**
   * @see com.hp.hpl.jena.graph.impl.GraphBase#performDelete()
   * @see com.hp.hpl.jena.graph.impl.GraphWithPerform#performDelete()
   */
  @Override
  public void performDelete(Triple triple) {
    graph.callMethod("delete", factory.newTriple(triple));
  }
}
