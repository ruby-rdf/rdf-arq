package org.rubyforge.rdf.arq;

import org.jruby.*;
import org.jruby.runtime.builtin.IRubyObject;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.Block;
import org.jruby.exceptions.RaiseException;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;   
import com.hp.hpl.jena.graph.TripleMatch;
import com.hp.hpl.jena.sparql.core.Quad;

/**
 * A factory class for instantiating RDF.rb objects.
 *
 * @author Arto Bendiken
 */
public class Factory extends org.rubyforge.rdf.Factory {
  /**
   * @param  node       the Jena node
   * @return an RDF::Value instance
   */
  public RubyObject newValue(Node node) {
    if (node == null || node == Node.ANY)
      return getWildcard();
    if (node.isBlank())
      return newNode(node.getBlankNodeId().toString());
    if (node.isURI())
      return newURI(node.getURI());
    if (node.isLiteral())
      return newLiteral(node.getLiteralValue()); // FIXME
    return null;
  }

  /**
   * @param  match      the Jena triple matcher
   * @return an RDF::Pattern instance
   */
  public RubyObject newPattern(TripleMatch match) {
    return newTriple(
      newValue(match.getMatchSubject()),
      newValue(match.getMatchPredicate()),
      newValue(match.getMatchObject())
    );
  }

  /**
   * @param  triple     the Jena triple
   * @return an RDF::Pattern instance
   */
  public RubyObject newPattern(Triple triple) {
    return newPattern(triple.getSubject(), triple.getPredicate(), triple.getObject());
  }

  /**
   * @param  quad       the ARQ quad
   * @return an RDF::Pattern instance
   */
  public RubyObject newPattern(Quad quad) {
    return newPattern(quad.getSubject(), quad.getPredicate(), quad.getObject(), quad.getGraph());
  }

  /**
   * @param  subject    the subject term
   * @param  predicate  the predicate term
   * @param  object     the object term
   * @return an RDF::Pattern instance
   */
  public RubyObject newPattern(Node subject, Node predicate, Node object) {
    return newTriple(subject, predicate, object); // FIXME
  }

  /**
   * @param  subject    the subject term
   * @param  predicate  the predicate term
   * @param  object     the object term
   * @param  context    the context term
   * @return an RDF::Pattern instance
   */
  public RubyObject newPattern(Node subject, Node predicate, Node object, Node context) {
    return newQuad(subject, predicate, object, context); // FIXME
  }

  /**
   * @param  triple     the Jena triple
   * @return an RDF::Statement instance
   */
  public RubyObject newStatement(Triple triple) {
    return newStatement(triple.getSubject(), triple.getPredicate(), triple.getObject());
  }

  /**
   * @param  quad       the ARQ quad
   * @return an RDF::Statement instance
   */
  public RubyObject newStatement(Quad quad) {
    return newStatement(quad.getSubject(), quad.getPredicate(), quad.getObject(), quad.getGraph());
  }

  /**
   * @param  subject    the subject term
   * @param  predicate  the predicate term
   * @param  object     the object term
   * @return an RDF::Statement instance
   */
  public RubyObject newStatement(Node subject, Node predicate, Node object) {
    return newTriple(subject, predicate, object); // FIXME
  }

  /**
   * @param  subject    the subject term
   * @param  predicate  the predicate term
   * @param  object     the object term
   * @param  context    the context term
   * @return an RDF::Statement instance
   */
  public RubyObject newStatement(Node subject, Node predicate, Node object, Node context) {
    return newQuad(subject, predicate, object, context); // FIXME
  }

  /**
   * @param  triple     the Jena triple
   * @return a Ruby array with 3 elements
   */
  public RubyArray newTriple(Triple triple) {
    return newTriple(triple.getSubject(), triple.getPredicate(), triple.getObject());
  }

  /**
   * @param  subject    the subject term
   * @param  predicate  the predicate term
   * @param  object     the object term
   * @return a Ruby array with 3 elements
   */
  public RubyArray newTriple(Node subject, Node predicate, Node object) {
    return newTriple(newValue(subject), newValue(predicate), newValue(object));
  }

  /**
   * @param  quad       the ARQ quad
   * @return a Ruby array with 4 elements
   */
  public RubyArray newQuad(Quad quad) {
    return newQuad(quad.getSubject(), quad.getPredicate(), quad.getObject(), quad.getGraph());
  }

  /**
   * @param  subject    the subject term
   * @param  predicate  the predicate term
   * @param  object     the object term
   * @param  context    the context term
   * @return a Ruby array with 4 elements
   */
  public RubyArray newQuad(Node subject, Node predicate, Node object, Node context) {
    return newQuad(newValue(subject), newValue(predicate), newValue(object), newValue(context));
  }
}
